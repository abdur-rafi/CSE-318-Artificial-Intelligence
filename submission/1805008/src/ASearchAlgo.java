import java.util.HashSet;
import java.util.PriorityQueue;

class ASearchAlgo{
    public static int explored = 0;
    public static int expanded = 0;

    public static Node search(Node startNode){
        explored = 0;
        expanded = 1;
        if(startNode.heuristicCost == 0) return startNode;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(startNode);
        HashSet<Node> st = new HashSet<>();
        st.add(startNode);
        while (!pq.isEmpty()){
            var f = pq.poll();
            ++explored;
            if(f.heuristicCost == 0) return f;
            var neighbors = f.getNextNodes();
            for(var x : neighbors){
                if(!st.contains(x)){
                    st.add(x);
                    pq.add(x);
                    ++expanded;
                }
            }
        }
        return null;
    }
}
