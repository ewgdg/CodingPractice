package Graph;
import java.util.HashMap;

public class Graph_simpler {
    public HashMap<Integer,HashMap<Integer,Integer>> graph;
    public Graph_simpler(){
        graph= new HashMap<>();
    }

    public void addEdge(int source, int dest, int weight){
        HashMap<Integer,Integer> link = graph.computeIfAbsent(source,(k)->new HashMap<>());
        link.put(dest,weight);

    }

}
