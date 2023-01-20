package PHeuristics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import GraphColoring.Node;

public class PHeuristics {
    ArrayList<Node> nodes;
    CalculatePenalty penalty;
    private ArrayList<Node> color1;
    private ArrayList<Node> color2;
    private ArrayList<Boolean> visited;
    private Random random;

    public PHeuristics(ArrayList<Node> nodes, CalculatePenalty penalty){
        this.nodes = nodes;
        this.penalty = penalty;
        color1 = new ArrayList<>();
        color2 = new ArrayList<>();
        visited = new ArrayList<>();
        this.random = new Random(23);
    }

    private void dfs(Node node, int firstColor, int secondColor){
        for(var x : node.getNeighbors()){
            if(!visited.get(x.getId() - 1)){
                visited.set(x.getId() - 1, true);
                if(x.getColor() == firstColor){
                    color1.add(x);
                }
                else if(x.getColor() == secondColor){
                    color2.add(x);
                }
                else{
                    continue;
                }
                dfs(x, firstColor, secondColor);
            }
        }
    }

    private ArrayList<Node> findKempeChain(Node node, int secondColor){
        color1.clear();
        color2.clear();
        visited = new ArrayList<>();
        for(int i = 0; i < nodes.size(); ++i){
            visited.add(false);
        }
        visited.set(node.getId() - 1, true);
        color1.add(node);

        dfs(node, node.getColor(), secondColor);
        ArrayList<Node> chainNodes = new ArrayList<>();
        chainNodes.addAll(color1);
        chainNodes.addAll(color2);
        // if( chainNodes.size() == 1)
        // System.out.println(chainNodes.size());
        return chainNodes;
    }

    public ArrayList<Node> PairSwap(){
        ArrayList<Node> changedColoredNode = null;
        for(var x : nodes){
            for(var y : nodes){
                if(x.getColor() == y.getColor()){
                    continue;
                }
                if(x.getNeighbors().contains(y)){
                    continue;
                }
                int sz1 = findKempeChain(x, y.getColor()).size();
                int sz2 = findKempeChain(y, x.getColor()).size();
                // if(sz1 == 1 || sz2 == 1)
                //     System.out.println(sz1 + " " + sz2);
                if(sz1 == 1 && sz2 == 1){
                    // System.out.println("here");
                    int xc = x.getColor();
                    x.setColor(y.getColor());
                    y.setColor(xc);
                    changedColoredNode = new ArrayList<>();
                    changedColoredNode.add(x);
                    changedColoredNode.add(y);
                    return changedColoredNode;
                }
            }
        }
        return changedColoredNode;
    }

    public void KempeChain(Node node, int secondColor){
        findKempeChain(node, secondColor);
        int firstColor = node.getColor();
        swapColor(firstColor, secondColor);
        
    }

    public Node randomNode(){
        return nodes.get(this.random.nextInt(nodes.size()));
    }

    public int randomColor(int mxColor){
        return this.random.nextInt(mxColor) + 1;
    }

    public int neighboringColor(Node node){
        var x = node.getNeighbors();
        if(x.isEmpty()){
            return -1;
        }
        return x.get(this.random.nextInt(x.size())).getColor();
    }

    public void swapColor(int firstColor, int secondColor){
        for(var x : color1){
            x.setColor(secondColor);
        }
        for(var x : color2){
            x.setColor(firstColor);
        }

    }

    public void reduceByKempe(int itr){
        long prevPenalty = penalty.penalty();
        long newPenalty;
        int mxColor = -1;
        int iterations = 0;
        for(var x : nodes){
            mxColor = Math.max(x.getColor(), mxColor);
        }

        while(iterations < itr){
            Node node = randomNode();
            int color = randomColor(mxColor);
            while(color == node.getColor())
                color = randomColor(mxColor);
            int firstColor = node.getColor();
            int secondColor = color;
            KempeChain(node, color);
            newPenalty = penalty.penalty();
            if(prevPenalty > newPenalty){
                prevPenalty = newPenalty;
            }
            else{
                swapColor(secondColor, firstColor);
            }
            iterations++;

        }
    }

    public void reduce(int itr, BufferedWriter writer) throws IOException{
        reduceByKempe(itr);
        writer.write("penalty: " + penalty.averagePenalty() + "\n");
        reduceByPairSwap(itr);

    }
    public void reduceByPairSwap(int itr){
        int iterations = 0;
        long prevPenalty = penalty.penalty();
        while(iterations < itr ){
            var x = PairSwap();
            if(x == null)
                return;
            long newPenalty = penalty.penalty();
            if(newPenalty >= prevPenalty){
                Node f = x.get(0);
                Node s = x.get(1);
                int fcolor = f.getColor();
                f.setColor(s.getColor());
                s.setColor(fcolor);
            }
            else{
                prevPenalty = newPenalty;
            }
            iterations++;
        }
    }
}



    // System.out.println(mxColor);
        // while(true){
        //     for(var x : nodes){
        //         for(int j = 1; j <= mxColor; ++j){
        //             if(x.getColor() != j){
        //                 KempeChain(x, j);
        //                 newPenalty = penalty.penalty();
        //                 if(iterations > 1000){
        //                     ex = true;
        //                     break;
        //                 }
        //                 currentPenalty = newPenalty;
        //                 iterations++;
        //             }
        //         }
        //         if(ex)
        //             break;
        //     }
        //     if(ex)
        //         break;
        // }
        


        // while(true){
        //     for(var x : nodes){
        //         // for(int j = 1; j <= mxColor; ++j){
        //         //     if(x.getColor() != j){
        //         //         KempeChain(x, j);
        //         //         newPenalty = penalty.penalty();
        //         //         if(iterations > 1000){
        //         //             ex = true;
        //         //             break;
        //         //         }
        //         //         currentPenalty = newPenalty;
        //         //         iterations++;
        //         //     }
        //         // }
        //         // if(ex)
        //         //     break;
        //         // int color = neighboringColor(x);
        //         int color = randomColor(mxColor);
                
        //         while(color == x.getColor()) color = randomColor(mxColor);

        //         KempeChain(x, color);
        //         newPenalty = penalty.penalty();
        //         iterations++;
        //         if(currentPenalty >= newPenalty){
        //             currentPenalty = newPenalty;
        //             warning = 0;
        //         }
        //         else{
        //             warning++;
        //         }
        //         if(iterations > 1000 || warning > 10){
        //             ex = true;
        //             break;
        //         }
        //     }
        //     if(ex)
        //         break;
        // }

