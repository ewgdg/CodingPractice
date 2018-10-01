import java.util.HashMap;
import java.util.HashSet;

public class UndirectedGraph {
    class Edge{
        int point1;
        int point2;
        int weight;
        public Edge(int s,int d, int w){
            point1=s;
            point2=d;
            weight=w;
        }
        public boolean equals(Object obj){
            Edge that = (Edge)obj;
            return this.weight==that.weight && this.point1==that.point1 && this.point2==that.point2;

        }
        public int hashCode(){
            return point1*31+point2*17+weight;
        }
    }

    HashMap<Integer,HashMap<Integer,Integer>> neighbours;
    HashSet<Edge> edges = new HashSet<>();

    public UndirectedGraph(){
        neighbours=new HashMap<>();
    }

    public void addEdge(int p1,int p2, int w){
        neighbours.computeIfAbsent(p1,(k)->new HashMap<>()).put(p2,w);
        neighbours.computeIfAbsent(p2,(k)->new HashMap<>()).put(p1,w);
        edges.add(new Edge(p1,p2,w));
    }


}
