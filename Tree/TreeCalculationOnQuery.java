import java.util.*;

public class TreeCalculationOnQuery {
    //https://www.hackerrank.com/challenges/kittys-calculations-on-a-tree/problem
    //calculate the expression for each set, each set contains some tree node value
    //sum of( u*v*dist(u,v) ) mod (1e7+9),where u,v is all of possible pair from the set

    //topological sort reversed order, from bottom level to upper level
    //such that we can find LCA for each sub node v
    //update sum for each level of critical node
    //each level represent a unit of dist(u,v)


    public static void solve(  int[][] edges, int[][] queries  ){

        //build tree
        Map<Integer,Integer> in_degrees = new HashMap<>();
        Map<Integer, List<Integer>> tree = new HashMap<>();//DAG
        Map<Integer,Integer> parents = new HashMap<>();

        for(int[] edge:edges){
            in_degrees.put(edge[1],in_degrees.getOrDefault(edges[1],0)+1);
            tree.computeIfAbsent(edge[0],k->new ArrayList<>()).add(edge[1]);
            parents.put(edge[1],edge[0]);

            in_degrees.putIfAbsent(edge[0],0);//record every node
        }

        Queue<Integer> queue = new LinkedList<>();
        Deque<Integer> ordered = new LinkedList<>();
        for(Map.Entry<Integer,Integer> entry:in_degrees.entrySet()){
            if(entry.getValue()==0){
                queue.add(entry.getKey());
            }
        }
        while(!queue.isEmpty()){
            int node = queue.poll();
            ordered.addFirst(node);
            for(int child:tree.get(node)){
                in_degrees.put(child,in_degrees.get(child)-1);
                if(in_degrees.get(child)==0){
                    queue.add(child);
                }
            }
        }

        long mod = (long)(1e9+7);
        for(int[] query: queries){
            long query_sum = 0;
            Map<Integer,Long> critical_nodes = new HashMap<>();
            for(int node : query){
                query_sum+=node;
                critical_nodes.put(node, critical_nodes.getOrDefault(node,0l)+(long)node);
            }
            long sum = 0;
            for(Integer node : ordered){
                if(critical_nodes.containsKey(node)){

                    sum = (sum%mod+ (critical_nodes.get(node)%mod)* ((query_sum-critical_nodes.get(node))%mod))%mod ;
                    Integer parent = parents.get(node);
                    if(parent!=null){
                        critical_nodes.put(parent,critical_nodes.getOrDefault(parent,0l)+critical_nodes.get(node));
                    }

                }
            }
            System.out.println(sum);

        }



    }

}
