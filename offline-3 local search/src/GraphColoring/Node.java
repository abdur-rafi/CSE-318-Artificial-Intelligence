package GraphColoring;

import java.util.List;

public interface Node {
    public void setColor(int color);
    public int getColor();
    public List<Node> getNeighbors();
    // public int degree();
    public int getResidents();
    public boolean isColored();
    public int getId();
}
