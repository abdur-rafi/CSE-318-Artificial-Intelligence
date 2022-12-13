
public class HammingDistance implements Heuristic{
    @Override
    public int calc(int[][] grid) {
        int c = 1;
        int dist = 0;
        int k = grid.length;
        for (int[] ints : grid) {
            for (int j = 0; j < k; ++j) {
                int t = ints[j];
                if (t != 0 && t != c) {
                    dist++;
                }
                c++;
            }
        }
        return dist;
    }
}
