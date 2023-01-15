package Penalty;
public class ExponentialStrategy implements PenaltyStrategy{

    @Override
    public long penalty(int a, int b) {
        int absDiff = Math.abs(a - b);
        if(absDiff == 0 || absDiff > 5) return 0;
        return 1 << (5 - absDiff);
    }
}