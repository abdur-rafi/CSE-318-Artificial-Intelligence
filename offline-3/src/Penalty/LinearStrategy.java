package Penalty;

public class LinearStrategy implements PenaltyStrategy {
    
    @Override
    public long penalty(int a, int b) {
        int absDiff = Math.abs(a - b);
        if(absDiff == 0){
            System.out.println("Panic\n");
        }
        if(absDiff > 5) return 0;
        return 2 * (5 - absDiff);
    }
}
