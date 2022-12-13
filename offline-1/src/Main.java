import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


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
        startNode.printGrid();
        if(!startNode.isSolvable()){
            System.out.println("The board is not solvable");
            return;
        }
        System.out.println("The board is solvable");
        var solution = ASearchAlgo.search(startNode);
        System.out.println("==================== Hamming Distance =========================");
        System.out.printf("Explored nodes: %d Expanded nodes: %d\n", ASearchAlgo.explored, ASearchAlgo.expanded);
        printPath(solution, 0);
        startNode.setHeuristic(new ManhattanDistance());
        solution = ASearchAlgo.search(startNode);
        System.out.println("==================== Manhattan Distance =========================");
        System.out.printf("Explored nodes: %d Expanded nodes: %d\n", ASearchAlgo.explored, ASearchAlgo.expanded);
        printPath(solution, 0);

    }

    public static void main(String[] args) throws FileNotFoundException {
        offline1();
    }
    static void printPath(Node n, int depth){
        if(n!= null){
            printPath(n.parent, depth + 1);
            n.printGrid();
        }
        else
            System.out.println("Total moves: " + (depth - 1));
    }
}
