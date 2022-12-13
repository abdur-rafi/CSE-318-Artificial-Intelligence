

class pair {
    int f, s;
    public pair(int a, int b){
        f = a;
        s = b;
    }
}



public class Main{
    public static void main(String[] args){
        CSP csp = new CSP("input.txt", 2, true);
        csp.showGrid();

        if(csp.BackTrack()){
            System.out.println("ok");
            csp.showGrid();
        }
        else{
            System.out.println("not ok");
        }


    }
}