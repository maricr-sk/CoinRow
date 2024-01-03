import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoinRow {
    public static class CoinRowSolution{
        int [] coins;
        int sum;

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Max value: ");
            builder.append(sum);
            builder.append(System.lineSeparator());
            builder.append("Coins used: ");
            builder.append(Arrays.toString(coins));
            return builder.toString();
        }
    }

    public static CoinRowSolution coinRow(int [] coins){
        int [] C, F, S;
        C = new int[coins.length +1];
        F = new int[C.length];
        S = new int [C.length];
        for(int i = 0; i < coins.length; i++){
            C[i+1] = coins[i];
        }
        F[1] = C[1];
        for(int i = 2; i < C.length; i++){
            int useIt= C[i] + F[i-2], loseIt = F[i-1];
            if(useIt > loseIt) {
                F[i] = useIt;
                S[i] = i - 2;
            } else{
                F[i] = loseIt;
                S[i] = i -1;
            }
        }
        //BackTrack
        CoinRowSolution solution = new CoinRowSolution();
        List<Integer> coinsUsed = backtrack(C,F,S);
        solution.coins = coinsUsed.stream().mapToInt(i -> i).toArray();
        solution.sum = F[F.length-1];
        return solution;
    }

    private static List<Integer> backtrack(int[]C, int[]F, int[]S){
        int i = F.length -1;
        List<Integer> coinsUsed = new ArrayList<>();
        while(i > 0){
            if(F[i] > F[i-1]){
                coinsUsed.add(C[i]);
            }
            i = S[i];
        }
        Collections.reverse(coinsUsed);
        return coinsUsed;
    }

    public static int coinRowRecursive(int [] coins){
        int[] C = new int[coins.length +1];
        for(int i = 0; i < coins.length; i++){
            C[i+1] = coins[i];
        }
        return coinRowHelper(C, C.length-1);
    }

    private static int coinRowHelper(int [] C, int index){ // slow exponential way
        if(index <= 0){
            return 0;
        }
        return Math.max(C[index] + coinRowHelper(C, index-2),
                                    coinRowHelper(C, index-1));
    }

    public static void main(String[] args) {
        int [] coins = new int [] {5,2,3,1,5,7};
       // System.out.println("Mac value: " + coinRowRecursive(coins));
        System.out.println(coinRow(coins));
    }
}