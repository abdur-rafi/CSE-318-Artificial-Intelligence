import java.util.ArrayList;
import java.util.Arrays;
class pair{
    int f,s;
    pair(int a, int b){
        f = a; s  = b;
    }
}

public class Node implements Comparable<Node>{

    public int distance;
    public int heuristicCost;
    public int[][] grid;
    public Heuristic h;
    public Node parent;

    public int[][] deepClone(int[][] src){
        var t = new int[src.length][];
        for(int i = 0; i < src.length; ++i){
            t[i] = src[i].clone();
        }
        return t;
    }


    public Node(int[][] grid, Heuristic h, int distance, Node parent){
        this.grid = deepClone(grid);
        heuristicCost = h.calc(this.grid);
        this.distance = distance;
        this.h = h;
        this.parent = parent;

    }

    public void setHeuristic(Heuristic h){
        this.h = h;
    }

    public ArrayList<Node> getNextNodes(){
        ArrayList<Node> t = new ArrayList<>();
        int k = grid.length;
        int i = 0, j = 0;
        boolean f = false;
        for(; i < k; ++i){
            for(j = 0; j < k; ++j){
                if(grid[i][j] == 0){
                    f = true;
                    break;
                }
            }
            if(f) break;
        }
        ArrayList<pair> newBlankPos = new ArrayList<>();
        if (i - 1 >= 0) newBlankPos.add(new pair(i - 1, j));
        if(i + 1 < k) newBlankPos.add(new pair(i + 1, j));
        if(j - 1 >= 0) newBlankPos.add(new pair(i, j - 1));
        if(j + 1 < k) newBlankPos.add(new pair(i, j + 1));

        for(var v : newBlankPos){
            if(parent != null && parent.grid[v.f][v.s] == 0) continue;
            var g2 = deepClone(grid);
            g2[i][j] = g2[v.f][v.s];
            g2[v.f][v.s] = 0;
            t.add(new Node(g2, h, distance + 1, this));
        }
        return t;
    }

    void printGrid(){
        int k = grid.length;
        System.out.print("_");

        for (int j = 0; j < k; ++j) {
            System.out.printf("%s","______");
        }
        System.out.println();
        for (int i = 0; i < k; ++i) {
            System.out.print("|");
            for (int j = 0; j < k; ++j) {
                String s = Integer.toString(grid[i][j]);
                if(s.equalsIgnoreCase("0"))
                    s = "*";
                System.out.printf("  %-3s|", s);
            }
            if(i == k - 1) continue;
            System.out.println();
            System.out.print("|");

            for (int j = 0; j < k; ++j) {
                System.out.printf("%s","-----");
                if (j == k-1)
                    System.out.print("|");
                else
                    System.out.print("+");

            }
            System.out.println();
        }
        System.out.println();
        System.out.print("-");
        for (int j = 0; j < k; ++j) {
            System.out.printf("%s","------");
        }
        System.out.println();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            var t = ((Node) obj).grid;
            for(int i = 0; i < t.length; ++i){
                for(int j = 0; j < t.length; ++j){
                    if(t[i][j] != grid[i][j]) return false;
                }
            }
            return true;
        }
        return false;
    }

    boolean isSolvable(){
        int k = grid.length;
        int inversions = countInversion();
        if(k % 2 == 1){
            return inversions % 2 == 0;
        }
        int i,j;
        boolean f = false;
        for( i = 0; i < k; ++i){
            for(j = 0; j < k; ++j){
                if(grid[i][j] == 0) {
                    f = true;
                    break;
                }
            }
            if(f){
                break;
            }
        }
        int distFromLast = k - i;
        return ((distFromLast % 2 == 0 && inversions % 2 == 1) || (distFromLast % 2 == 1 && inversions % 2 == 0));
    }

    public int countInversion(){
        int c = 0;
        int k = grid.length;
        int n = k * k ;
        for(int i = 0; i < n; ++i ){
            int a = grid[i/k][i%k];
            if(a == 0) continue;
            for(int j = i + 1; j < n; ++j){
                int b = grid[j/k][j%k];
                if(b == 0) continue;
                if(a > b) ++c;
            }
        }
        return c;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(distance + heuristicCost, o.distance + o.heuristicCost);
    }
}
