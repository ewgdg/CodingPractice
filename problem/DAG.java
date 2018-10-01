import java.util.HashMap;
import java.util.List;

public class DAG {
    //find shortest path to all nodes
    //could be multi source, just set dist to sources as 0

    public static int findDist(int source, int dest, Graph_complicate graph){
        List<Integer> nodes = graph.topologicalSort_inDegree();

        HashMap<Integer, Integer> dist = new HashMap<>();
        dist.put(source,0);

        for(Integer node:nodes){

            if(dist.containsKey(node)){//dist to cur != infinite
                int dist_to_cur = dist.get(node);

                for(Graph_complicate.Link link: graph.getLinks(node)){
                    int child = link.child_id;
                    if(dist.getOrDefault(child,Integer.MAX_VALUE)  >=  dist_to_cur+ link.weight ) {
                        dist.put(child,dist_to_cur+ link.weight );
                    }

                }


            }


        }

        return dist.getOrDefault(dest,Integer.MAX_VALUE);
    }



    public static void main(String[] args){

        /*         1
                 3/ \4
                 2   3
                   3/\1
                   4  5
                  2\  /7
                     6
                   3/\2
                   8 7
        */
        Graph_complicate g = new Graph_complicate();
        g.addEdge(1,2,3);
        g.addEdge(1,3,4);
        g.addEdge(3,4,3);
        g.addEdge(3,5,1);
        g.addEdge(4,6,2);
        g.addEdge(5,6,7);
        g.addEdge(6,8,3);
        g.addEdge(6,7,2);
//        g.removeEdge(6,7);

        System.out.println(findDist(1,8,g));

    }
}
