package graph;
import java.util.*;

public class Graph_complicate {

    //need to use child_id to represent each of vertices node, use child_id as a key to map
    //use hashMap to record list/hashset of vertices child_id + weight to parent into node


    public class Link {
        public int child_id;//child_id
        public int weight; //weight to parent
        public Link(int child_id, int weight){
            this.child_id = child_id;
            this.weight=weight;
        }
        public boolean equals(Object obj){
            return ((Link)obj).child_id == this.child_id;
        }
        public int hashCode(){
            return child_id;
        }

    }

    HashMap<Integer, HashSet<Link>> graph_map;

    public Graph_complicate(){
        graph_map= new HashMap<>();
    }

    public void addEdge(int source_id, int dest_id, int weight){
        graph_map.computeIfAbsent(source_id,(k)->new HashSet<>()).add(new Link(dest_id,weight));
    }

    public void removeEdge(int source, int dest){
        graph_map.computeIfAbsent(source,(k)->new HashSet<>()).remove(new Link(dest,0));
    }

    public HashSet<Link> getLinks(int id){
        return graph_map.getOrDefault(id,new HashSet<>());
    }


    public List<Integer> topologicalSort_dfs(){
        //dfs method better when given neighbour mapping
        Stack<Integer> stack = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();

        Set<Integer> ids = graph_map.keySet();
        for(Integer id:ids){
            topo_dfs(stack,visited,id);
        }

        List<Integer> res = new ArrayList<>();
        while(!stack.isEmpty()){
            res.add(stack.pop());
        }
        return res;
    }

    public void topo_dfs(Stack<Integer> stack,  HashSet<Integer> visited, int node_id){

        if(visited.contains(node_id)) return;

        visited.add(node_id);
        for(Link child: getLinks(node_id) ){
            int child_id = child.child_id;
            topo_dfs(stack,visited,child_id);
        }

        stack.push(node_id);
    }


    public List<Integer> topologicalSort_inDegree(){


        List<Integer> res= new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        HashMap<Integer,Integer> degree_map = new HashMap<>();
        for(Map.Entry<Integer,HashSet<Link>> entry: graph_map.entrySet()){

            for(Link childLink : entry.getValue()){
                degree_map.put(childLink.child_id,degree_map.getOrDefault(childLink.child_id,0)+1);
            }

        }

        for(Integer id: graph_map.keySet()){
            if(degree_map.getOrDefault(id,0)==0){
                queue.add(id);
            }
        }

        while(!queue.isEmpty()){
            int cur = queue.poll();
            res.add(cur);

            for(Link link: getLinks(cur)){
                int prev_degree = degree_map.get(link.child_id);
                int cur_degree=prev_degree-1;
                degree_map.put(link.child_id,cur_degree);
                if(cur_degree==0){
                    queue.add(link.child_id);
                }
            }
        }

        return res;
    }

}
