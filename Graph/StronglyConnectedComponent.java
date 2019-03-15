import java.util.*;

public class StronglyConnectedComponent {
    //undirected just dfs to add to visiting set,+ visited to prevent revisit, without cycle detect so immediate visited or check both visited and visiting
    //directed graph
    //application facebook strongly connected people group common interest


    //if we know scc from sink to source we can print scc with dfs

    // However, if we do a DFS of graph and store vertices according to their finish times,
    // we make sure that the finish time of a vertex that connects to other SCCs (other that its own SCC),
    // will always be greater than finish time of vertices in the other SCC

    //In the reversed graph, the edges that connect two components are reversed.
//    So if we do a DFS of the reversed graph using sequence of vertices in stack, we process vertices from sink to source (in reversed graph).
//    That is what we wanted to achieve and that is all needed to print SCCs one by one.



    public List<HashSet> solve(DirectedGraph graph){
        Stack<Integer> ordered = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();
        for(Integer node: graph.vertices.keySet()){
            dfsUtil(ordered,visited,node,graph);
        }
        graph=graph.transpose();

        visited.clear();//reset visited
        List<HashSet> res = new ArrayList<>();
        while(!ordered.isEmpty()){//topo order? what if we use reversed order and dont reverse the graph?? doesnt work in mult scc , the last node reach all other points but not reversely
            Integer node = ordered.pop();
            HashSet<Integer> set = new HashSet<>();
            dfsUtil_track(set,visited,node,graph);
            if(!set.isEmpty())
                res.add(set);
        }
        return res;
    }


    public void dfsUtil(Stack<Integer> stack, HashSet<Integer> visited, Integer id, DirectedGraph graph ){
        //basically topological sort but different since the cycle
        if(visited.contains(id)){
            return;
        }
        visited.add(id);


        for(Map.Entry<Integer,Integer> entry:   graph.getEdges(id).entrySet()){ //get or default empty list
            int child = entry.getKey();
            dfsUtil(stack,visited,child,graph);
        }

        stack.add(id);



    }

    public void dfsUtil_track(HashSet<Integer> set, HashSet<Integer> visited, Integer id, DirectedGraph graph ){
        if(visited.contains(id)){//or || set.contains(id)
            return;
        }
        visited.add(id);
        set.add(id);

        for(Map.Entry<Integer,Integer> entry:   graph.getEdges(id).entrySet()){ //get or default empty list
            int child = entry.getKey();
            dfsUtil_track(set,visited,child,graph);
        }

    }


    public static void main(String[] args){

        StronglyConnectedComponent solver = new StronglyConnectedComponent();

        /*
            1 ->  2     7
            ^      |   < ^
            |     \/   /  \
            3 <-  4 -> 5->6
         */

        DirectedGraph graph = new DirectedGraph();
        graph.addEdge(1,2,3);
        graph.addEdge(3,1,3);
        graph.addEdge(4,3,3);
        graph.addEdge(2,4,3);
        graph.addEdge(4,5,3);
        graph.addEdge(5,6,3);
        graph.addEdge(6,7,3);
        graph.addEdge(7,5,3);
        graph.addEdge(6,5,3);//extra

        graph.addEdge(1,1,0);//test single node
        graph.addEdge(9,9,0);//test single node

        System.out.println(solver.solve(graph));

    }

}
