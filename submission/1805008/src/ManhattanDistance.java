public class ManhattanDistance implements Heuristic{
    @Override
    public int calc(int[][] grid) {
        int dist = 0;
        int k = grid.length;
        for(int i = 0; i < k; ++i){
            for(int j = 0; j < k; ++j){
                int c = grid[i][j];
                if(c == 0) continue;
                c--;
                dist += Math.abs(c/k - i) + Math.abs(c % k - j);
            }
        }
        return dist;
    }


}
