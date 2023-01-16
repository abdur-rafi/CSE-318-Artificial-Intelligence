package CHeuristics;

import java.util.ArrayList;
import java.util.Random;

import GraphColoring.Node;

public class RandomNode extends CHeursitics {

    private Random random;
    int c = 0;
    public RandomNode(ArrayList<Node> lst){
        super(lst);
        random = new Random(31);
    }

    @Override
    public Node getNextNode() {
        if (c > nodes.size()) return null;
        ++c;
        return nodes.get(this.random.nextInt(nodes.size()));
    }
    
}
