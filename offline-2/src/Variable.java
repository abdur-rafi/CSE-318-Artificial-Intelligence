import java.util.LinkedList;

public class Variable{
    pair index;
    LinkedList<Integer> domain;
    int value;

    public Variable(pair index, LinkedList<Integer> domain, int value) {
        this.index = index;
        this.domain = domain;
        this.value = value;
    }
}

