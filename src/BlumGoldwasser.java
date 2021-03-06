import javafx.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by John on 11/5/2018.
 */
public class BlumGoldwasser {

  private int PrimeP;
  private int PrimeQ;
  private int IntegerA;
  private int IntegerB;
  private int XNaut;

  public BlumGoldwasser(int primeP, int primeQ, int integerA, int integerB, int xNaut) {
    PrimeP = primeP;
    PrimeQ = primeQ;
    IntegerA = integerA;
    IntegerB = integerB;
    XNaut = xNaut;
  }

  public Pair<ArrayList<String>, Integer> encrypt(String message){

    ArrayList<String> answer = new ArrayList<>();

    int N = PrimeP * PrimeQ;
    int k = (int) (Math.log(N)/Math.log(2));
    int h = (int) (Math.log(k)/Math.log(2));
    int t = message.length()/h;
    if(message.length()%h != 0){t++;}

    ArrayList<String> choppedMessage = new ArrayList<>();
    for(int i = 0; i+h <= message.length(); i+=h){
      choppedMessage.add(message.substring(i, i+h));
    }
    if(message.length()%h !=0 ){
      choppedMessage.add(message.substring((message.length()/h) * h));
    }
    System.out.println("Encrypting message " + choppedMessage.toString());

    long XsubI = XNaut;
    for(int i = 0; i < t; i++){

      String XsubIString = Integer.toString((int) XsubI, 2);
      String leastSigBits = XsubIString.substring(XsubIString.length()-h);

      int PsubIasInt = Integer.parseInt(leastSigBits, 2);
      int MsubIasInt = Integer.parseInt(choppedMessage.get(i), 2);

      int CsubIasInt = PsubIasInt ^ MsubIasInt;
      String CsubI = Integer.toString(CsubIasInt, 2);

      while(CsubI.length() < 4){
        CsubI = "0" + CsubI;
      }
      answer.add(CsubI);

      XsubI = ( XsubI * XsubI ) % N;
    }
    XsubI = ( XsubI * XsubI ) % N;
    return new Pair<>(answer, (int)XsubI);
  }

  public String decrypt(Pair<ArrayList<String>, Integer> cyphertext){

    String answer = "";
    int N = PrimeP * PrimeQ;

    int t = cyphertext.getKey().size();
    int valueD1 = powerWithModulus( (PrimeP+1)/4, t+1, PrimeP-1);
    int valueD2 = powerWithModulus( (PrimeQ+1)/4, t+1, PrimeQ-1);

    int valueU = powerWithModulus(cyphertext.getValue(), valueD1, PrimeP);
    int valueV = powerWithModulus(cyphertext.getValue(), valueD2, PrimeQ);

    long Vap = (long) valueV * (long) IntegerA * (long) PrimeP;
    long ubq = (long) valueU * (long) IntegerB * (long) PrimeQ;
    long Xnaut = (Vap + ubq) % N;
    if(Xnaut < 0){Xnaut = N - Xnaut;}
    long XsubI = Xnaut;

    int k = (int) (Math.log(N)/Math.log(2));
    int h = (int) (Math.log(k)/Math.log(2));

    for(int i = 0; i < t; i++){

      String XsubIString = Integer.toString((int) XsubI, 2);
      String leastSigBits = XsubIString.substring(XsubIString.length()-h);

      int PsubIasInt = Integer.parseInt(leastSigBits, 2);
      int CsubIasInt = Integer.parseInt(cyphertext.getKey().get(i), 2);

      int MsubIasInt = PsubIasInt ^ CsubIasInt;
      String MsubI = Integer.toString(MsubIasInt, 2);

      while(MsubI.length() < 4){
        MsubI = "0" + MsubI;
      }
      answer = answer + MsubI;

      XsubI = ( XsubI * XsubI ) % N;
    }

    return answer;

  }

  public int powerWithModulus(int base, int exponent, int modulus){

    if(exponent < 0){
      System.out.println("powerWithModulus only works with positive exponents,");
    }
    long answer = 1;
    for(int i = 0; i < exponent; i++){

      answer = answer * base % modulus;

    }
    return (int)answer;
  }



}
