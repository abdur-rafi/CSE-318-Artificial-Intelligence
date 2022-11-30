import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

interface Heuristic{
    int calc(int[][] grid);
}
class pair{
    int f,s;
    pair(int a, int b){
        f = a; s  = b;
    }
}

class Node implements Comparable<Node>{

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

//    public void calculateCost(){
//        if(h == null){
//            System.out.println("Set Heuristic First");
//        }
//        else{
//            cost = steps + h.calc(grid);
//        }
//    }

//    public Node(Node n){
//        cost = n.cost;
//        h = n.h;
//        grid = n.grid.clone();
//        steps
//    }


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
        if(i + 1 < k) newBlankPos.add(new pair(i + 1, j));
        if (i - 1 >= 0) newBlankPos.add(new pair(i - 1, j));
        if(j + 1 < k) newBlankPos.add(new pair(i, j + 1));
        if(j - 1 >= 0) newBlankPos.add(new pair(i, j - 1));

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
//        System.out.println("dis : " + distFromLast);
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

class HammingDistance implements Heuristic{
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

class ManhattanDistance implements Heuristic{
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


class ASearchAlgo{
    public static int explored = 0;
    public static int expanded = 0;

    public static Node search(Node startNode){
        explored = 0;
        expanded = 1;
//        Scanner scanner = new Scanner(System.in);
        if(startNode.heuristicCost == 0) return startNode;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(startNode);
        HashSet<Node> st = new HashSet<>();
        st.add(startNode);
        while (!pq.isEmpty()){
            var f = pq.poll();
            ++explored;
            if(f.heuristicCost == 0) return f;
//            System.out.println("d: " + f.distance + ", m: " + f.heuristicCost);
//            scanner.nextInt();
            var neighbors = f.getNextNodes();
//            System.out.println(neighbors.size());
//            f.printGrid();

            for(var x : neighbors){
                if(!st.contains(x)){
                    st.add(x);
                    pq.add(x);
                    ++expanded;
                }
            }
        }
        return null;
    }
}

public class Main {

    public static int[][] readNodeFromFile() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);
        int k = scanner.nextInt();
        int[][] grid = new int[k][k];
        for(int i = 0; i < k; ++i){
            for(int j = 0; j < k; ++j){
                String s = scanner.next();
                if(s.equalsIgnoreCase("*")){
                    grid[i][j] = 0;
                }
                else{
                    grid[i][j] = Integer.parseInt(s);
                }
            }
        }
        return grid;
    }

    public static void offline1() throws FileNotFoundException {
        var grid = readNodeFromFile();
        Node startNode = new Node(grid, new HammingDistance(), 0, null);

//        System.out.println(startNode.countInversion());
        startNode.printGrid();
        if(!startNode.isSolvable()){
            System.out.println("The board is not solvable");
            return;
        }
        var solution = ASearchAlgo.search(startNode);
        System.out.println("==================== Hamming Distance =========================");
        System.out.printf("Explored nodes: %d Expanded nodes: %d\n", ASearchAlgo.explored, ASearchAlgo.expanded);
        printPath(solution);
        System.out.println(c);
        startNode.setHeuristic(new ManhattanDistance());
        solution = ASearchAlgo.search(startNode);
        System.out.println("==================== Manhattan Distance =========================");
        System.out.printf("Explored nodes: %d Expanded nodes: %d\n", ASearchAlgo.explored, ASearchAlgo.expanded);
        printPath(solution);
        System.out.println(c);

    }

    public static void main(String[] args) throws FileNotFoundException {
//        int t[][] = {
//                {1, 2, 3},
//                {4, 0, 6},
//                {7, 8, 5}
//        };
//        Node node = new Node( t, new Heuristic() {
//            @Override
//            public int calc(int[][] grid) {
//                return 0;
//            }
//        },0, null);
//        var t2 = node.getNextNodes();
//        for(var x : t2){
//            System.out.println("======");
//            x.printGrid();
//            System.out.println("======");
//        }
//        t2= t2.get(0).getNextNodes();
//        t2.get(0).printGrid();
//        HashSet<Node> n = new HashSet<>();
//        //        s.add(t);
//        int t3[][] = {
//                {1, 2, 3},
//                {4, 0, 6},
//                {7, 8, 5}
//        };
//        Node n2 = new Node( t3, new Heuristic() {
//            @Override
//            public int calc(int[][] grid) {
//                return 0;
//            }
//        }, 0, null);
//        n.add(node);
//        System.out.println(n.contains(n2));
//
//
//        int t4[][] = {
//                {7, 2, 4},
//                {6, 0, 5},
//                {8, 3, 1}
//        };
////        System.out.println(new HammingDistance().calc(t4));
////        System.out.println(new ManhattanDistance().calc(t4));
//
//        int t5[][] = {
//                {1, 3, 4},
//                {5, 0, 6},
//                {7, 2, 8}
//        };
//        Node n3 = new Node( t5, new Heuristic() {
//            @Override
//            public int calc(int[][] grid) {
//                return 0;
//            }
//        }, 0, null);
//
//        System.out.println(n3.countInversion());

//
//        int t6[][] = {
//                {0, 1, 3},
//                {4, 2, 5},
//                {7, 8, 6}
//        };
//        int t7[][] = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 0}
//        };
//
//        Node n4 = new Node( t6, new ManhattanDistance(), 0, null);
//        var soln = ASearchAlgo.search(n4);
//        printPath(soln);

        offline1();
    }
    static int c = 0;
    static void printPath(Node n){
        if(n!= null){
            printPath(n.parent);
            n.printGrid();
            ++c;
        }
        else
            c = 0;
    }
}
