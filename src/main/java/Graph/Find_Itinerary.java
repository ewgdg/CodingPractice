package Graph;

import java.util.*;

public class Find_Itinerary {

    //iven a list of airline tickets represented by pairs of departure and arrival airports [from, to],
    // reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.

    //    Directed graph, traverse edge once
    //Example 1:
    //
    //Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
    //Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]

    //Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
    //Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
    //Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
    //             But it is larger in lexical order.

//assume all tickets form at least one valid itinerary.
    ////    Directed graph, traverse edge once

//    Hierholzer’s Algorithm for Eulerian path
//basically the dfs version of topological sort or convert to tree then PostOrder Traversal
//use dequeue for children, poll out/remove each time, so it wont repeat
//there is only one terminating end and one starting point


    public List<String> findItinerary(String[][] tickets) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();//reconstruct the graph for dfs
        for (String[] ticket : tickets) {
            map.computeIfAbsent(ticket[0], (k)->new PriorityQueue<String>()).add(ticket[1]);//use prio //ensure the lexical order
        }
        Deque<String> res = new LinkedList<>();
        dfs(res, map, "JFK");
        return new ArrayList<>(res);
    }

    public void dfs(Deque<String> res, Map<String, PriorityQueue<String>> map, String cur  ){
        PriorityQueue<String> heap = map.getOrDefault(cur,null);
        while(heap!=null && !heap.isEmpty()){
            String child = heap.poll();
            dfs(res,map,child);
        }
        res.addFirst(cur);

    }


}
