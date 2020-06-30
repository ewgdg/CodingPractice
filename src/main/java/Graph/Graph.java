package Graph;

import java.util.*;

public class Graph {
    public class Vertex {
        int id;
        HashMap<Vertex,Edge> adjVertices;
        public Vertex(int id){
            this.id=id;
            this.adjVertices = new HashMap<>();
        }
        public void add_adjVertice(Vertex v, Edge edge){
            adjVertices.put(v,edge);
        }
        public Set<Vertex> getChildren(){
            return adjVertices.keySet();
        }
        public int getWeight(Vertex v){
            Edge edge= adjVertices.getOrDefault(v,null);
            if(edge==null) return 0;
            return edge.weight;
        }

        public boolean equals(Object obj){
            if(obj==null) return false;
            if(obj.getClass() != getClass()) return false;

            Vertex that = (Vertex)obj;
            return this.id == that.id;
        }

        public int hashCode(){
            return id;
        }

    }
    public class Edge{
        Vertex src;
        Vertex dst;
        int weight;
        public Edge(Vertex src, Vertex dst, int weight){
            this.src=src; this.dst=dst; this.weight = weight;
        }
        //to avoid dup edge for undirected graph we can sort v based on id the always use smaller id as src
        //then implement equals and hashCode
    }
    HashMap<Integer, Vertex> vertices;
    List<Edge> edges;

    public Graph(){
        edges= new ArrayList<>();
        vertices= new HashMap<>();
    }

    public Vertex makeVertex(int id){
        return vertices.computeIfAbsent(id,(k)-> new Vertex(k));
    }
    public void addEdge(int src, int dst, int weight){
        Vertex v1 = makeVertex(src);
        Vertex v2 = makeVertex(dst);
        Edge edge = new Edge(v1,v2,weight);
        v1.add_adjVertice(v2,edge);
        edges.add(edge);
    }

    public void addEdgeUndirection(int src, int dst, int weight){
        Vertex v1 = makeVertex(src);
        Vertex v2 = makeVertex(dst);
        Edge edge = new Edge(v1,v2,weight);
        v1.add_adjVertice(v2,edge);
        v2.add_adjVertice(v1,edge);
        edges.add(edge);
    }
    public Graph transpose(){
        Graph transposed = new Graph();
        for(Edge edge: this.edges){
            transposed.addEdge(edge.src.id,edge.dst.id,edge.weight);
        }
        return transposed;

    }

    static Graph DAG;
    static{
        DAG = new Graph();
        /*
            1 ->  2     7
           \/      |   > ^
            |     \/   /  \
            3 <-  4 -> 5->6
         */
        DAG.addEdge(1,2,3);
//        DAG.addEdge(1,1,0);//self
        DAG.addEdge(1,3,1);
        DAG.addEdge(4,3,3);
        DAG.addEdge(2,4,2);
        DAG.addEdge(4,5,3);
        DAG.addEdge(5,6,3);
        DAG.addEdge(6,7,3);
        DAG.addEdge(5,7,3);

    }

    static Graph DirectedGraph;
    static{
        DirectedGraph = new Graph();
        /*
            1 ->  2     7
           /\      |   < ^
            |     \/   /  \
           3 <-  4 -> 5<->6
         */
        DirectedGraph.addEdge(1,2,3);
        DirectedGraph.addEdge(1,1,0);//self
        DirectedGraph.addEdge(3,1,1);
        DirectedGraph.addEdge(4,3,3);
        DirectedGraph.addEdge(2,4,2);
        DirectedGraph.addEdge(4,5,3);
        DirectedGraph.addEdge(5,6,3);
        DirectedGraph.addEdge(6,7,3);
        DirectedGraph.addEdge(7,5,3);
        DirectedGraph.addEdge(6,5,3);

    }

    static Graph UndirectedGraph;
    static{
        UndirectedGraph = new Graph();
        /*
            1 ->  2     7
           /\      |   < ^
            |     \/   /  \
           3 <-  4 -> 5<->6
         */
        UndirectedGraph.addEdgeUndirection(1,2,3);
//        DAG.addEdgeUndirection(1,1,0);//self
        UndirectedGraph.addEdgeUndirection(3,1,1);
        UndirectedGraph.addEdgeUndirection(4,3,3);
        UndirectedGraph.addEdgeUndirection(2,4,2);
        UndirectedGraph.addEdgeUndirection(4,5,3);
        UndirectedGraph.addEdgeUndirection(5,6,3);
        UndirectedGraph.addEdgeUndirection(6,7,3);
        UndirectedGraph.addEdgeUndirection(7,5,3);
        UndirectedGraph.addEdgeUndirection(6,5,3);

    }

    static Graph DirectedGraph_NegativeCycle;
    static{
        DirectedGraph_NegativeCycle = new Graph();
        /*
            1 ->  2     7
           /\      |   < ^
            |     \/   /  \
           3 <-  4 -> 5<->6
         */
        DirectedGraph_NegativeCycle.addEdge(1,2,3);
        DirectedGraph_NegativeCycle.addEdge(1,1,0);//self
        DirectedGraph_NegativeCycle.addEdge(3,1,-10);
        DirectedGraph_NegativeCycle.addEdge(4,3,3);
        DirectedGraph_NegativeCycle.addEdge(2,4,2);
        DirectedGraph_NegativeCycle.addEdge(4,5,3);
        DirectedGraph_NegativeCycle.addEdge(5,6,3);
        DirectedGraph_NegativeCycle.addEdge(6,7,3);
        DirectedGraph_NegativeCycle.addEdge(7,5,3);
        DirectedGraph_NegativeCycle.addEdge(6,5,3);

    }


}
