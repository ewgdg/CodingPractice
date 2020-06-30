package Graph;

import java.util.*;


public class TopologicalSort {

    public Stack<Integer> sort_dfs(Graph graph){
        HashSet<Graph.Vertex> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        for(Graph.Vertex v: graph.vertices.values()){
            dfsUtil(stack,graph,v,visited);
        }
        return stack;
    }

    public void dfsUtil(Stack<Integer> stack, Graph graph, Graph.Vertex v, HashSet<Graph.Vertex> visited){
        if(visited.contains(v)) return;
        visited.add(v);

        for(Graph.Vertex child: v.adjVertices.keySet()){
            dfsUtil(stack,graph,child,visited);
        }
        stack.add(v.id);
    }

    public List<Integer> sort_bfs(Graph graph){
        Queue<Graph.Vertex> front = new LinkedList<>();
        HashMap<Graph.Vertex,Integer> inDegreeMap = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        for(Graph.Edge edge: graph.edges){
            if(edge.src!=edge.dst)
                inDegreeMap.put(edge.dst,inDegreeMap.getOrDefault(edge.dst,0)+1);
        }
        for(Graph.Vertex v: graph.vertices.values()){// should iterate inDegreeMap since it contains only degree>0 , iterate from the whole graph since some singleton vertex are not in inDegree map
            if(inDegreeMap.getOrDefault(v,0)==0){
                front.add(v);
            }
        }
        while(!front.isEmpty()){
            Graph.Vertex v=  front.poll();
            res.add(v.id);
            for(Graph.Vertex child : v.adjVertices.keySet()){
                if(child==v) continue; //skip self loop
                inDegreeMap.put(child,inDegreeMap.get(child)-1);
                if(inDegreeMap.get(child)==0){
                    front.add(child);
                }
            }
        }
        return res;


    }


    public static void main(String[] args){
        Graph graph = new Graph();
         /*
            1 ->  2     7
                  |   <
                 \/   /
            3 <-  4 -> 5->6
         */



        graph.addEdge(7,5,3);


        graph.addEdge(4,3,3);
        graph.addEdge(2,4,3);
        graph.addEdge(4,5,3);
        graph.addEdge(5,6,3);

        graph.addEdge(1,2,3);
        graph.addEdge(1,1,0);//self
//        graph.addEdge(3,1,3);

//        graph.addEdge(6,7,3);


        graph.makeVertex(8);//new vertice singleton





        TopologicalSort solver = new TopologicalSort();
        System.out.println(solver.sort_dfs(graph));
        System.out.println(solver.sort_bfs(graph));

    }
}
