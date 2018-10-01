import org.apache.log4j.Priority;

import java.util.*;

public class UniformCostSearch {

    //O(E+VlogV) for a graph
    //b^(d+1) d=C/epsilon ,C=path cost, epsilon = minimal cost per edge

    class State implements Comparable<State>{
        int cost;
        int id;
        public State(int id, int cost){
            this.cost=cost;
            this.id=id;
        }
        public int compareTo(State that){
            return this.cost-that.cost;
        }
    }
    public int solution(Graph graph, int src, int dst){
        HashMap<Integer,Integer> predecessor = new HashMap<>();

        PriorityQueue<State> open = new PriorityQueue<>();
        HashSet<Integer> visited = new HashSet<>();

        visited.add(src);
        open.add(new State(src,0));//can have multiple src to find out the shortest src

        int res =-1;
        while(!open.isEmpty()){
            State cur = open.poll();
            if(cur.id == dst){
                //goal
                res= cur.cost;
                break;
            }
            Graph.Vertex v = graph.makeVertex(cur.id);
            for(Graph.Vertex child: v.getChildren()){
                if(!visited.contains(child.id)){
                    State next =new State(child.id,cur.cost+v.getWeight(child));
                    predecessor.put(child.id,cur.id);
                    visited.add(child.id);
                    open.add(next);
                }
            }

        }

        Stack<Integer> path = new Stack<>();
        int cur = dst;
        while(cur!=src){
            path.add(cur);
            cur  = predecessor.get(cur);
        }

        path.add(cur);
        List<Integer> list = new ArrayList<>();
        while(!path.isEmpty()){
            list.add(path.pop());
        }
        System.out.println( list );
        return res;

    }

    public static void main(String[] args){
        UniformCostSearch sovler =new UniformCostSearch();
        System.out.println(sovler.solution(Graph.UndirectedGraph,1,7));
        System.out.println(sovler.solution(Graph.DAG,1,7));

    }

}
