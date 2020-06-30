package graph;

import java.util.HashSet;

public class CycleDetect_UndirecteGraph {
    public boolean unionFind_detectCycle(Graph graph){
        DisjointSet disjointSet = new DisjointSet();
        for(Graph.Edge edge: graph.edges){
            disjointSet.makeSet(edge.src.id);
            disjointSet.makeSet(edge.dst.id);
            if(!disjointSet.union(edge.src.id,edge.dst.id)){
                //found
                return true;
            }
        }
        return false;
    }
    public boolean dfs_detectCycle(Graph graph){ //or we dont need visiting for undirected graph, just visited check
        HashSet<Graph.Vertex> visited = new HashSet<>();

        for(Graph.Vertex v : graph.vertices.values()){
            HashSet<Graph.Vertex> visiting = new HashSet<>();
            //visited check here if no visiting
            if(!dfsUtil(visited,visiting,v,null)){
                return true;
            }
        }
        return false;


    }

    public boolean dfsUtil(HashSet<Graph.Vertex> visited, HashSet<Graph.Vertex> visiting, Graph.Vertex cur, Graph.Vertex parent){
        if(visited.contains(cur)) return true;//not cycle

        if(visiting.contains(cur)) return false; //found cycle
        visiting.add(cur);

        for(Graph.Vertex child: cur.adjVertices.keySet()){
            if(child==parent) continue; //skip reverse path
            if(!dfsUtil(visited,visiting,child,cur)){
                return false;
            }
        }

        visiting.remove(cur);
        visited.add(cur);
        return true;
    }


    public static void main(String[] args){
        Graph graph = new Graph();

        graph.addEdgeUndirection(1,2,3);

        graph.addEdgeUndirection(1,3,3);

        graph.addEdgeUndirection(4,3,3);

        graph.addEdgeUndirection(2,4,3);

        graph.addEdgeUndirection(4,5,3);
//        graph.addEdge(5,6,3);
//        graph.addEdge(6,7,3);
//        graph.addEdge(7,5,3);
         /*
            1 ->  2     7
            d      |     ^
            |     \/      \
            3 <-  4 -> 5->6
         */


        CycleDetect_UndirecteGraph solver =new CycleDetect_UndirecteGraph();
        System.out.println(solver.dfs_detectCycle(graph));
        System.out.println(solver.unionFind_detectCycle(graph));
    }
}
