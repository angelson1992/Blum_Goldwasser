import javafx.util.Pair;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        BlumGoldwasser cypher = new BlumGoldwasser(499, 547, -57, 52, 159201);
        String message = "10011100000100001100";

        int bigNumber = 499 * 547;
        long newBigNumber = ((long)(159201 -1) * (long)(159201 -1)) % bigNumber;

        Pair<ArrayList<String>, Integer> cyphertext = cypher.encrypt(message);
        System.out.println(cyphertext.toString());
        cypher.decrypt(cyphertext);
    }
}
