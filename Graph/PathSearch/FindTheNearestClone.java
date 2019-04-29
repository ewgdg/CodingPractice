package PathSearch;

import java.util.*;

public class FindTheNearestClone {
    //In this challenge, there is a connected undirected graph where each of the nodes is a color.
    // Given a color, find the shortest path connecting any two nodes of that color.

    //multi-src search
    //multi-directional search. search from both dst and src
    //create a class State to record cost and the "root" src node.
    //put first visited  <node,state> into seen.
    //if found a seen state with different root/src node then we found the goal
    //early terminated if less than 2 cloned nodes


    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
        // solve here
        HashMap<Integer,State> seen = new HashMap<>();
        Queue<State> queue = new LinkedList<>();
        // Set<Integer> srcs = new HashSet<>();
        for(int i =0; i<graphNodes;i++){
            if(ids[i]==val){
                queue.add(new State(i+1,0,i+1));
                // srcs.add(i);
            }
        }
        if(queue.size()<=1) return -1;//early terminated
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for(int i=0;i<graphFrom.length; i++){
            int from = graphFrom[i];
            int to = graphTo[i];
            //undirected so add both dir
            graph.computeIfAbsent(from,k->new ArrayList<>()).add(to);
            graph.computeIfAbsent(to,k->new ArrayList<>()).add(from);
        }


        while(!queue.isEmpty()){


            State cur = queue.poll();

            if(seen.containsKey(cur.node_id)){

                State target = seen.get(cur.node_id);
                if(target.src!=cur.src){
                    //found
                    return cur.cost+target.cost;
                }
                else{//visited node but not target node
                    continue;
                }
            }else{
                //should not update seen bc the first seen is the shorstest
                seen.put(cur.node_id,cur);
            }

            List<Integer> children = graph.get(cur.node_id);
            if(children==null) continue;
            for(Integer child:  children  ){
                State next = new State(cur.src,cur.cost+1,child);
                queue.add(next);
            }



        }

        //not found
        return -1;

    }
    static class State{
        int src;
        int cost;
        int node_id;
        public State(int src, int cost, int id){
            this.src=src;
            this.cost=cost;
            this.node_id=id;
        }
    }


}
