package CHeuristics;
import java.util.ArrayList;
import java.util.Comparator;

import GraphColoring.Node;

public class LargestDegree extends CHeursitics {
    
    private int i;
    public Comparator<Node> compare() {
        return (a, b)->{
            return -1 * Integer.compare(a.getNeighbors().size(), b.getNeighbors().size());
        };
    }

    public LargestDegree(ArrayList<Node> lst){
        super(lst);
        lst.sort(compare());
        i = 0;
    }

    @Override
    public Node getNextNode() {
        if(i >= nodes.size())
            return null;
        i++;
        return nodes.get(i - 1);
    }
}
