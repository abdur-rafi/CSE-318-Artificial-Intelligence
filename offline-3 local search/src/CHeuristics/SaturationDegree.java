package CHeuristics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import GraphColoring.Node;

public class SaturationDegree extends CHeursitics {
    

    public SaturationDegree(ArrayList<Node> lst){
        super(lst);
    }


    
    @Override
    public Node getNextNode() {
        Node n = null;
        Set<Integer> colors = new HashSet<>();
        int notColored;
        int mxColored = -1;
        int mxUncolored = -1;

        for(var x : nodes){
            if(x.isColored())
                continue;
            colors.clear();
            notColored = 0;
            for(var y : x.getNeighbors()){
                if(y.isColored()){
                    colors.add(y.getColor());
                }
                else{
                    notColored++;
                }
            }
            if(mxColored < colors.size()){
                mxColored = colors.size();
                mxUncolored = notColored;
                n = x;
            }
            else if(mxColored == colors.size() && mxUncolored < notColored){
                mxColored = colors.size();
                mxUncolored = notColored;
                n = x;
            }

        }

        return n;
    }
}
