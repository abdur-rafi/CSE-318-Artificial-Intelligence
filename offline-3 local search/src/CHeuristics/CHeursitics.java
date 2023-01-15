package CHeuristics;
import java.util.ArrayList;

import GraphColoring.Node;

public abstract class CHeursitics {

    protected ArrayList<Node> nodes;

    public CHeursitics(ArrayList<Node> lst){
        nodes = lst;
    }


    public abstract Node getNextNode();

}


