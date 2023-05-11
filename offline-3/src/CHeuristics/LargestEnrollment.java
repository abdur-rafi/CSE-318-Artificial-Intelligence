package CHeuristics;

import java.util.ArrayList;
import java.util.Comparator;

import GraphColoring.Node;

public class LargestEnrollment extends CHeursitics {
    private int i;

    public Comparator<Node> compare() {
        return (a, b)-> -1 * Integer.compare(a.getResidents(), b.getResidents());
    }

    public LargestEnrollment(ArrayList<Node> lst){
        super(lst);
        nodes = new ArrayList<>();
        nodes.addAll(lst);
        i = 0;
        nodes.sort(compare());
    }

    @Override
    public Node getNextNode() {
        if (i >= nodes.size())
            return null;
        i++;
        return nodes.get(i-1);
    }
}
