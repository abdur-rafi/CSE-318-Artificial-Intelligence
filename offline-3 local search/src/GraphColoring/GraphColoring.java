package GraphColoring;

import java.util.HashSet;
import java.util.Set;

import CHeuristics.CHeursitics;

public class GraphColoring {
    CHeursitics heursitic;
    public GraphColoring(CHeursitics heursitic){
        this.heursitic = heursitic;
    }

    private Set<Integer> getNeighborsColors(Node n){
        Set<Integer> st = new HashSet<>();
        for(var x : n.getNeighbors()){
            if(x.isColored())
                st.add(x.getColor());
        }
        return st;
    }

    public int color(){
        int color = 1;
        int mxColor = -1;
        Node node;
        while((node = heursitic.getNextNode()) != null){
            Set<Integer> st = getNeighborsColors(node);
            color = 1;
            while(st.contains(color)){
                ++color;
            }
            mxColor = Math.max(color, mxColor);
            node.setColor(color);
        }
        return mxColor;
    }
}
