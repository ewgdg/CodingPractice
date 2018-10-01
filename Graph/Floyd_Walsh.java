import java.util.HashMap;
import java.util.Map;

public class Floyd_Walsh {
    //O(V^3)
    //benefit: pre-compute all source-dst dist pairs in v^3
    public HashMap<Integer,HashMap<Integer,Integer>> getDist(Graph graph){

        HashMap<Integer,HashMap<Integer,Integer>> dist = new HashMap<>();
        //init
        for(Graph.Edge edge : graph.edges){
            dist.computeIfAbsent(edge.src.id,(k)->new HashMap<>()).put(edge.dst.id,edge.weight);
        }

        for(Graph.Vertex mid: graph.vertices.values()){//vertex as mid
            for(Graph.Vertex src: graph.vertices.values()){
                for(Graph.Vertex dst:graph.vertices.values()){
                    Integer dist1 = dist.computeIfAbsent(src.id,(k)->new HashMap<>()).getOrDefault(mid.id,null);
                    Integer dist2 = dist.computeIfAbsent(mid.id,(k)->new HashMap<>()).getOrDefault(dst.id,null);
                    if(dist1!=null && dist2!=null) dist.get(src.id).put(dst.id, Math.min(dist1+dist2, dist.get(src.id).getOrDefault(dst.id,dist1+dist2) )  );
                }

            }
        }
        print(dist);
        return dist;
    }
    public void print(HashMap<Integer,HashMap<Integer,Integer>> dist){
        for(Map.Entry<Integer,HashMap<Integer,Integer>> entry: dist.entrySet()){
            for(Map.Entry<Integer,Integer> child: entry.getValue().entrySet()){

                System.out.println(entry.getKey()+"->" + child.getKey()+" : " + child.getValue());
            }

        }
    }

    public static void main(String[] args){
        Graph graph = new Graph();
        /*
            1 ->  2     7
            ^      |   < ^
            |     \/   /  \
            3 <-  4 -> 5->6
         */
        graph.addEdge(1,2,3);
        graph.addEdge(1,1,0);//self
        graph.addEdge(3,1,3);
        graph.addEdge(4,3,3);
        graph.addEdge(2,4,3);
        graph.addEdge(4,5,3);
        graph.addEdge(5,6,3);
        graph.addEdge(6,7,3);
        graph.addEdge(7,5,3);
        graph.addEdge(6,5,4);//extra

        graph.makeVertex(8);//new vertice singleton
        graph.addEdge(8,8,4);//extra

        Floyd_Walsh solver = new Floyd_Walsh(); //if negative edge -> wrong dist?
        solver.getDist(graph);


    }

}
