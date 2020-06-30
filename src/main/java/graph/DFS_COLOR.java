package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DFS_COLOR {
    //802. Find Eventual Safe States
    //In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.
    //
    //Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  More specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a terminal node in less than K steps.
    //
    //Which nodes are eventually safe?  Return them as an array in sorted order.
    //
    //The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.

    public List<Integer> eventualSafeNodes(int[][] graph) {

        //notice the requirement: for any choice of direction
        //means loop is not allowed

        int n = graph.length;//all nodes 0 to n-1

        // HashMap<Integer, List<Integer>> reversed_graph = new HashMap<>(); //can use HashSet to replace list

        //cycle detection
        //remove all cycle path and all path connected to a cycle!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // HashSet<Integer> removals = new HashSet<>();

        // HashMap<Integer,Boolean> visited = new HashMap<>(); // need to use hashmap for visited to indicate whether the checked node is in cycle


        List<Integer> res = new ArrayList<>();


        HashMap<Integer,Integer> color = new HashMap<>();
        for(int i=0;i<n;i++ ){
            if(dfsWithColor(i,graph,color)){
                res.add(i);
            }

        }


        // for(int i =0;i<n;i++){
        //     if(!removals.contains(i)){
        //         res.add(i);
        //     }
        // }



        return res;

    }

    // O number of links/edges//iterative cannot work!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//     public void iterativeDfs(int root, int[][] graph ,HashSet<Integer> visiting, HashSet<Integer> visited, HashSet<Integer> removals){
//         if(visited.contains(root)) return;

//         Stack<Integer> stack = new Stack<>();
//         stack.add(root);

//         while(!stack.isEmpty()){
//             Integer cur = stack.pop();

//             visiting.add(cur);
//             for(int adj : graph[cur]){
//                 if(visited.contains(adj)){
//                     return;//has checked
//                 }
//                 if(visiting.contains(adj)){
//                     //cycle detected
//                     removals.addAll(visiting);
//                     return;
//                 }
//                 stack.add(adj);
//             }
//             visiting.remove(cur); //doesnt work bc the cur hasnt been visited , it is in the stack for now!!!!!!
//             visited.add(cur);
//         }

//     }

    public boolean dfs(int cur, int[][] graph ,HashSet<Integer> visiting, HashMap<Integer,Boolean> visited){

        if(visited.containsKey(cur)){
            //has checked
            if(visited.get(cur)==false){
                //cycle detected on the following path, but what about prev path connceted to cycle, remove it
                // removals.addAll(visiting);
                return false;
            }else{
                return true;
            }
        }
        if(visiting.contains(cur)){
            //cycle detected
            for(int id:visiting){
                visited.put(id,false);
            }
            // removals.addAll(visiting);
            return false;
        }


        visiting.add(cur);//can be visited.put(cur,false);
        for(int adj : graph[cur]){
            //can check terminating here, but need to check visted as well outside this recursive helper
            if(!dfs(adj,graph,visiting,visited)){
                return false; //terminating
            }
        }
        //visiting stores all of the nodes in the current stack chain
        visiting.remove(cur);//need to remove visiitng otherwise we might add wrong node to removals
        visited.put(cur,true);
        return true;
    }

    //improvement with color state combine visiting with visited, dont need remove call
    public boolean dfsWithColor(int cur, int[][] graph , HashMap<Integer,Integer> color){
        //color 1 :means visiting or in cycle
        //2 : checked with no issue
        //0 : default not visited
        if(color.containsKey(cur)&&color.get(cur)!=0) return color.get(cur)==2; //if color is 1 then find cycle

        color.put(cur,1);
        for(int adj : graph[cur]){
            if(!dfsWithColor(adj,graph,color)){
                return false;
            }
        }
        color.put(cur,2);
        return true;
    }
}
