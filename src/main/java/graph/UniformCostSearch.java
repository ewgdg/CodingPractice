package graph;

import java.util.*;

public class UniformCostSearch {

    //O(E+VlogV) for a graph
    //b^(d+1) d=C/epsilon ,C=path cost, epsilon = minimal cost per edge
    //why the cost does not count the priority queue cost??? 
    //bc the pq cost is not dominating facotr and is ignored.???
    //the total cost might be something like O(b^(d+1) + vlogv) ???where n <= b^d , so nlogn <= b^d * log(b^d) == b^d * dlog(b)  vs  b^(d+1) == b^d * b
    //after some research the real cost should be O logQ * min(N,b^d) , N = total number of states, Q = avg size of pq

    //another similar 
    //Dijkstra's algorithm uses a data structure for storing and querying partial solutions sorted by distance from the start. 
    // While the original algorithm uses a min-priority queue and runs in time
    //  ((|V|+|E|)\log |V|) where |V| is the number of nodes and |E| is the number of edges), it can also be implemented 
    // in {\displaystyle \Theta (|V|^{2})}{\displaystyle \Theta (|V|^{2})} using an array. 
    // The idea of this algorithm is also given in Leyzorek et al. 1957. Fredman & Tarjan 1984 propose 
    // using a Fibonacci heap min-priority queue to optimize the running time complexity to 
    // {\displaystyle \Theta (|E|+|V|\log |V|)}{\displaystyle \Theta (|E|+|V|\log |V|)}. 

    class State implements Comparable<State>{ // probably we should name the other way around, Node { State state, int cost }, State{coordinates/id}
        int cost;
        int id; //or store Node node(with pos)
        int pid; //parent id/Node for predecessor , another way is to store Operation/State parent(mem costly like linkedlist).
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

        visited.add(src);//wrong!! bc we check visited after poll , dont need to add here.
        open.add(new State(src,0));//can have multiple src to find out the shortest src

        int res =-1;
        while(!open.isEmpty()){
            State cur = open.poll();

            //check visited here !!. //check visited after poll bc we need to sort it first
            //if(visited ) continue;
           

            if(cur.id == dst){
                //goal
                res= cur.cost;
                break;
            }



            Graph.Vertex v = graph.makeVertex(cur.id);
            for(Graph.Vertex child: v.getChildren()){
                if(!visited.contains(child.id)){ //wrong!!!, we cannot do visited here , bc it is not sorted yet, we might discard a state that is shorter than those in the front/open.
                    //another better way is to do a member test and replace the node in frontier if cur node is better
                    //but it requires frontier to be both a hashmap and priority queue
                    //https://www.ccs.neu.edu/home/rplatt/cs5335_2016/slides/bfs_ucs.pdf
                    State next =new State(child.id,cur.cost+v.getWeight(child));
                    predecessor.put(child.id,cur.id);//wrong, need to sort first
                    visited.add(child.id);
                    open.add(next);
                }
            }

            
            // visited.add(cur); //add visited after  all
            // predecessor.put(cur.id,cur.pid);

        }

        Stack<Integer> path = new Stack<>(); //use Deque add to front, then return new ArrayList<>(deque);
        int cur = dst;
        while(cur!=src){//or cur!=null , cur=predecessors.getOrDefault(cur,null)
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
