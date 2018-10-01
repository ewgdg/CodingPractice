import java.util.*;

public class StronglyConnectedComponent {
    //undirected just dfs to add to set
    //directed graph
    //application facebook strongly connected people group common interest
    public List<HashSet> solve(DirectedGraph graph){
        Stack<Integer> ordered = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();
        for(Integer node: graph.vertices.keySet()){
            dfsUtil(ordered,visited,node,graph);
        }
        graph=graph.transpose();

        visited.clear();//reset visited
        List<HashSet> res = new ArrayList<>();
        while(!ordered.isEmpty()){
            Integer node = ordered.pop();
            HashSet<Integer> set = new HashSet<>();
            dfsUtil_track(set,visited,node,graph);
            if(!set.isEmpty())
                res.add(set);
        }
        return res;
    }


    public void dfsUtil(Stack<Integer> stack, HashSet<Integer> visited, Integer id, DirectedGraph graph ){
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
        if(visited.contains(id)){
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
