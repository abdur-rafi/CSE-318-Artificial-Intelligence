package PHeuristics;

import java.util.ArrayList;

import GraphColoring.Node;

public class PHeuristics {
    ArrayList<Node> nodes;
    CalculatePenalty penalty;
    private ArrayList<Node> color1;
    private ArrayList<Node> color2;
    private ArrayList<Boolean> visited;

    public PHeuristics(ArrayList<Node> nodes, CalculatePenalty penalty){
        this.nodes = nodes;
        this.penalty = penalty;
        color1 = new ArrayList<>();
        color2 = new ArrayList<>();
        visited = new ArrayList<>();
    }

    private void dfs(Node node, int secondColor){
        for(var x : node.getNeighbors()){
            if(!visited.get(x.getId())){
                visited.set(x.getId(), true);
                if(x.getColor() == node.getColor()){
                    color1.add(x);
                }
                else if(x.getColor() == secondColor){
                    color2.add(x);
                }
                else{
                    continue;
                }
                dfs(x, secondColor);
            }
        }
    }

    public void KempeChain(Node node, int secondColor){
        color1.clear();
        color2.clear();
        visited = new ArrayList<>();
        for(int i = 0; i < nodes.size(); ++i){
            visited.add(false);
        }
        dfs(node, secondColor);
        int firstColor = node.getColor();
        for(var x : color1)
            x.setColor(secondColor);
        for(var x : color2)
            x.setColor(firstColor);
            
        
    }

    public void oneStep(){
        System.out.println(penalty.penalty());
        KempeChain(nodes.get(0), 2);
        System.out.println(penalty.penalty());

    }
}
