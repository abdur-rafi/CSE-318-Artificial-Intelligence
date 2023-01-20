package CHeuristics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import GraphColoring.Node;

public class RandomNode extends CHeursitics {

    private Random random;
    int c = -1;
    private ArrayList<Integer> order;
    public RandomNode(ArrayList<Node> lst){
        super(lst);
        random = new Random(31);
        order = new ArrayList<>();
        for(int i = 0; i < lst.size(); ++i)
            order.add(i);
        Collections.shuffle(order, random);
        // for(var x : order)
        //     System.out.println(x);
    }

    @Override
    public Node getNextNode() {
        ++c;
        if (c >= nodes.size()) return null;
        return nodes.get(order.get(c));
    }
    
}
