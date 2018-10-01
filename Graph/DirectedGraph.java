import java.util.HashMap;
import java.util.Map;

public class DirectedGraph {
    public HashMap<Integer,HashMap<Integer,Integer>> vertices;
    public DirectedGraph(){
        vertices = new HashMap<>();
    }

    public void addEdge(int source, int dest, int weight){
        HashMap<Integer,Integer> link = vertices.computeIfAbsent(source,(k)->new HashMap<>());
        link.put(dest,weight);

    }
    public void addEdge_biDirection(int source, int dest, int weight){
        //addEdge(source,dest,weight);
        //addEdge()//
        HashMap<Integer,Integer> link = vertices.computeIfAbsent(source,(k)->new HashMap<>());
        link.put(dest,weight);

        HashMap<Integer,Integer> link2 = vertices.computeIfAbsent(dest,(k)->new HashMap<>());
        link2.put(source,weight);

    }

    public HashMap<Integer,Integer> getEdges(int id){
        return vertices.getOrDefault(id,new HashMap<>()); // must set get or default to empty to avoid null
    }


    public DirectedGraph transpose(){
        DirectedGraph transposed = new DirectedGraph();
        for(Map.Entry<Integer,HashMap<Integer,Integer>> entry: vertices.entrySet()   ){
            for(Map.Entry<Integer,Integer> end : entry.getValue().entrySet()){
                transposed.addEdge(end.getKey(),entry.getKey(),end.getValue());

            }
        }
        return transposed;
    }

}
