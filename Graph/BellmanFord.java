import java.util.HashMap;

public class BellmanFord {

    public static HashMap<Integer,Integer> solution(Graph graph, int src,boolean undirected){
        //O(VE)
        //benefit: able to detect negative cycle
        //if negative edge but no cycle -> good?
        HashMap<Integer,Integer> dist = new HashMap<>();
        dist.put(src,0);

//        HashMap<Integer,Integer> predecessors = new HashMap<>(); //single src mult dst, not able to use predecessors

        for(int i=0;i<graph.vertices.size();i++){

            for(Graph.Edge edge: graph.edges){
                int u = edge.src.id;
                int v = edge.dst.id;
                if(dist.containsKey(u)) {
                    int updated = dist.get(u)+edge.weight;
                    if (!dist.containsKey(v)|| dist.get(v)>updated ){
                        dist.put(v,updated);
//                        predecessors.put(v,u);
                    }
                }
                if(undirected){
                    u = edge.dst.id; //flip edge
                    v = edge.src.id;
                    if(dist.containsKey(u)) {
                        int updated = dist.get(u)+edge.weight;
                        if (!dist.containsKey(v)|| dist.get(v)>updated ){
                            dist.put(v,updated);
//                            predecessors.put(v,u);
                        }
                    }
                }
            }

        }


        //detect negative cycle
        for(Graph.Edge edge: graph.edges){
            int u = edge.src.id;
            int v = edge.dst.id;
            if(dist.containsKey(u)){
                int updated=dist.get(u)+edge.weight;
                if(updated<dist.get(v)){//check if dist.contains v first otherwise means unreachable
                    //cycle found;
                    System.out.println("Found negative cycle!");
                }
            }
        }
        System.out.println("Finish.");
//        System.out.println(predecessors);

        return dist;


    }

    public static void main(String[] args){
        System.out.println(solution(Graph.DAG,1,false));
        System.out.println(solution(Graph.DirectedGraph,1,false));
        System.out.println(solution(Graph.UndirectedGraph,1,true));
    }

}
