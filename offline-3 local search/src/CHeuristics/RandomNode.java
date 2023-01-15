package CHeuristics;

import java.util.ArrayList;
import java.util.Random;

import GraphColoring.Node;

public class RandomNode extends CHeursitics {

    int c = 0;
    public RandomNode(ArrayList<Node> lst){
        super(lst);
    }

    @Override
    public Node getNextNode() {
        if (c > nodes.size()) return null;
        ++c;
        return nodes.get(new Random().nextInt(nodes.size()));
    }
    
}
