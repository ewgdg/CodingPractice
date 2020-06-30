package graph;

import java.util.HashSet;

public class CycleDetect_DirectedGraph {




    public boolean dfs_detectCycle(Graph graph){
        HashSet<Graph.Vertex> visited = new HashSet<>(); //use color method better

        for(Graph.Vertex v : graph.vertices.values()){
            HashSet<Graph.Vertex> visiting = new HashSet<>();
            if(!dfsUtil(visited,visiting,v)){
                return true;
            }
        }
        return false;


    }

    public boolean dfsUtil(HashSet<Graph.Vertex> visited, HashSet<Graph.Vertex> visiting, Graph.Vertex cur){
        if(visited.contains(cur)) return true;//not cycle

        if(visiting.contains(cur)) return false; //found cycle
        visiting.add(cur);

        for(Graph.Vertex child: cur.adjVertices.keySet()){
            if(!dfsUtil(visited,visiting,child)){
                return false;
            }
        }

        visiting.remove(cur);
        visited.add(cur);
        return true;
    }


    public static void main(String[] args){
        Graph graph = new Graph();
        graph.addEdge(1,2,3);
        graph.addEdge(1,3,3);
//        graph.addEdge(3,1,3);
        graph.addEdge(4,3,3);
        graph.addEdge(2,4,3);
        graph.addEdge(4,5,3);
        graph.addEdge(5,6,3);
        graph.addEdge(6,7,3);
//        graph.addEdge(7,5,3);
         /*
            1 ->  2     7
            d      |     ^
            |     \/      \
            3 <-  4 -> 5->6
         */


        CycleDetect_DirectedGraph solver =new CycleDetect_DirectedGraph();
        System.out.println(solver.dfs_detectCycle(graph));
    }
}
