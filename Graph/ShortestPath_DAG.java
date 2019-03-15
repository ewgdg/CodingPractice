import java.util.*;

public class ShortestPath_DAG {
    //topological order traverse
    //relax each dist

    public static HashMap<Integer,Integer> solution(Graph graph,int src){// or a few src
        TopologicalSort sorter = new TopologicalSort();
        Stack<Integer> stack  = sorter.sort_dfs(graph);

        HashMap<Integer,Integer> predecessors= new HashMap<>();
        HashMap<Integer,Integer> dist = new HashMap<>();
        dist.put(src,0);

        while(!stack.isEmpty()){
            Integer id = stack.pop();
            if(dist.containsKey(id)){
                Graph.Vertex cur = graph.makeVertex(id);
                for(Graph.Vertex child : cur.adjVertices.keySet()){
                    int newCost = dist.get(id)+ cur.getWeight(child);
                    if(!dist.containsKey(child.id)|| newCost<dist.get(child.id)  ){
                        dist.put(child.id,newCost);
                        predecessors.put(child.id,cur.id);
                    }
                }
            }
        }
        System.out.println(predecessors); //use deque to rebuild path insert to front ,return new arraylist(queue)
        return dist;
    }

    public static void main(String[] args){
        Graph graph = new Graph();
        /*
            1 ->  2     7
            |      |   > ^
           \/     \/   /  \
            3 <-  4 -> 5->6
         */
        graph.addEdge(1,2,3);
//        graph.addEdge(1,1,0);//self
        graph.addEdge(1,3,1);
        graph.addEdge(4,3,3);
        graph.addEdge(2,4,3);
        graph.addEdge(4,5,3);
        graph.addEdge(5,6,3);
        graph.addEdge(6,7,3);
        graph.addEdge(5,7,2);
//        graph.addEdge(6,5,4);//extra
        graph.makeVertex(8);

        System.out.println(solution(graph,1));

    }
}
