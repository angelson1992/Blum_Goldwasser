import javafx.util.Pair;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        BlumGoldwasser cypher = new BlumGoldwasser(499, 547, -57, 52, 159201);
        String message = "10011100000100001100";
        System.out.println("Inputting message " + message);
        Pair<ArrayList<String>, Integer> cyphertext = cypher.encrypt(message);
        System.out.println("Decrypting cyphertext " + cyphertext.toString());
        System.out.println("The decryption yielded " + cypher.decrypt(cyphertext));
    }
}
