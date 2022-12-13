import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class CSP {
    Variable[][] grid;
    LinkedList<Variable> unassignedVars;
    int N;
    int[] rowUnassignedCount;
    int[] colUnassignedCount;
    VariableOrderHeuristic usedHeuristic;



    public CSP( String fileName , int heuristicNumber) {
        readFromFile(fileName);
        selectHeuristic(heuristicNumber);
    }

    private void selectHeuristic(int i){
        if(i == 1)
            usedHeuristic = new Heuristic1_MRV();
        else if(i == 2)
            usedHeuristic = new Heuristic2_MaxForwardDegree();
        else if(i == 3)
            usedHeuristic = new Heuristic3_MRV_MaxDegree();
        else if(i == 4)
            usedHeuristic = new Heuristic4_MRV_MaxDegree_Ratio();
        else if(i == 5)
            usedHeuristic = new Heuristic5_Random();
    }

    private void readFromFile(String filename){
        Scanner scanner;
        try {

            scanner = new Scanner(new File(filename));

            String fLine = scanner.nextLine();
            int index = fLine.indexOf('=');
            N = Integer.parseInt(fLine.substring(index + 1,fLine.length() - 1));

            grid = new Variable[N][N];
            unassignedVars = new LinkedList<>();
            rowUnassignedCount = new int[N];
            colUnassignedCount = new int[N];

            scanner.nextLine();
            scanner.nextLine();
            for(int i = 0; i < N; ++i){
                String line = scanner.nextLine();
                var numbers = line.split(",");
                int val;
                for(int j = 0; j < N - 1; ++j){
                    val = Integer.parseInt(numbers[j].trim());
                    grid[i][j] = new Variable(new pair(i, j),null, val);

                }
                numbers[N-1] = numbers[N-1].trim();
                if(i == N-1){
                    val = Integer.parseInt(numbers[N-1].substring(0, numbers[N-1].length() - 4).trim());
                }
                else{
                    val = Integer.parseInt(numbers[N-1].substring(0, numbers[N-1].length() - 2).trim());
                }
                grid[i][N-1] = new Variable(new pair(i, N-1),null, val);

            }

            for(int i = 0; i < N; ++i){
                for(int j = 0; j < N; ++j){
                    determineDomain(grid[i][j]);
                    if(grid[i][j].value == 0){
                        unassignedVars.add(grid[i][j]);
                        rowUnassignedCount[i]++;
                        colUnassignedCount[j]++;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void determineDomain(Variable var){
        pair pos = var.index;
        LinkedList<Integer> lst = new LinkedList<>();
        int r = pos.f;
        int c = pos.s;

        boolean[] mask = new boolean[N + 1];

        for(int i = 0; i < N; ++i){
            mask[grid[i][c].value] = true;
            mask[grid[r][i].value] = true;
        }
        for(int i = 1; i <= N; ++i){
            if(!mask[i]) lst.add(i);
        }
        var.domain = lst;
    }

    private boolean checkConstraint(Variable var){
        pair pos = var.index;
        int r = pos.f;
        int c = pos.s;
        int val = grid[r][c].value;
        for(int i = 0; i < N; ++i){
            if(( i != r && grid[i][c].value == val ) || (grid[r][i].value == val && i != c))
                return false;
        }
        return true;
    }

    private LinkedList<Variable> MRV(){
        int mn = N;
        LinkedList<Variable> mxrVars = new LinkedList<Variable>();
        for(var x : unassignedVars){
            if(x.domain.size() < mn ){
                mn = x.domain.size();
                mxrVars.clear();
                mxrVars.add(x);
            }
            else if(x.domain.size() == mn){
                mxrVars.add(x);
            }
        }
        return mxrVars;

    }

    private Variable MaxDegree(LinkedList<Variable> lst){
        int mx = 0;
        Variable mxVar = null;
        for(var x : lst){
            int degree = rowUnassignedCount[x.index.f] + colUnassignedCount[x.index.s];
//            System.out.println(degree);
            if(degree >= mx){
                mx = degree;
                mxVar = x;
            }
        }
        return mxVar;
    }


    private Variable MinDomainDegreeRatio(){
        double mnr = N;
        Variable mnVar = null;
        for(var x : unassignedVars){
            double r2 = x.domain.size();
            r2 /= rowUnassignedCount[x.index.f] + colUnassignedCount[x.index.s];
            if(r2 <= mnr){
                mnr = r2;
                mnVar = x;
            }
        }
        return mnVar;
    }

    private Variable selectRandom(){
        return unassignedVars.get(new Random().nextInt(unassignedVars.size()));
    }

    interface VariableOrderHeuristic{
        Variable selectUnassignedVariable();
    }

    class Heuristic1_MRV implements VariableOrderHeuristic{
        @Override
        public Variable selectUnassignedVariable() {
            return MRV().get(0);
        }
    }

    class Heuristic2_MaxForwardDegree implements VariableOrderHeuristic{
        @Override
        public Variable selectUnassignedVariable() {
            return MaxDegree(unassignedVars);
        }
    }
    class Heuristic3_MRV_MaxDegree implements VariableOrderHeuristic{
        @Override
        public Variable selectUnassignedVariable() {
            return MaxDegree(MRV());
        }
    }
    class Heuristic4_MRV_MaxDegree_Ratio implements VariableOrderHeuristic{
        @Override
        public Variable selectUnassignedVariable() {
            return MinDomainDegreeRatio();
        }
    }

    class Heuristic5_Random implements VariableOrderHeuristic{
        @Override
        public Variable selectUnassignedVariable() {
            return selectRandom();
        }
    }




    void showGrid(){
        for(int i = 0; i < N; ++i){
            for(int j = 0; j  < N; ++j){
                var x = grid[i][j];
                System.out.print(x.value + ", ");
            }
            System.out.println();
        }
        System.out.println("Unassigned vars");
        for(var x : unassignedVars){
            System.out.printf("%d %d %d -> ", x.index.f, x.index.s, x.value);
            for(var y : x.domain){
                System.out.print(y + ", ");
            }
            System.out.println();
        }
        for(int i = 0; i < N; ++i){
            System.out.printf("%d, ", rowUnassignedCount[i]);
        }
        System.out.println();

        for(int i = 0; i < N; ++i){
            System.out.printf("%d, ", colUnassignedCount[i]);
        }
        System.out.println();
    }

    public boolean BackTrack(){
//        System.out.println("here");
        if(unassignedVars.size() == 0) return true;
        Variable var = usedHeuristic.selectUnassignedVariable();
        unassignedVars.remove(var);
        rowUnassignedCount[var.index.f]--;
        colUnassignedCount[var.index.s]--;
        for(Integer x : var.domain){
            var.value = x;
            if(checkConstraint(var)){
                if(BackTrack()){
                    return true;
                }
            }
        }

        rowUnassignedCount[var.index.f]++;
        colUnassignedCount[var.index.s]++;
        var.value = 0;
        unassignedVars.add(var);
        return false;
    }

}
