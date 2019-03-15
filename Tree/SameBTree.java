import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SameBTree {

    public boolean solution(Node a , Node b){
        if(a==null&&b==null) return true;
        else if(a==null||b==null) return false;
        else if(a.val!=b.val) return false;

        return solution(a.left,b.left)&&solution(a.right,b.right);
    }

    //follow up same vertices?
    //1.edges into hashset compare
    //2. traverse vertices

    public boolean SameGraph(HashMap<Integer,HashMap<Integer,Integer>> grapha, HashMap<Integer,HashMap<Integer,Integer>> graphb){

        HashSet<Integer> visited=  new HashSet<>();
        for(Map.Entry<Integer, HashMap<Integer,Integer>> entrya :  grapha.entrySet()){
            Integer cur = entrya.getKey();
            if( !graphb.containsKey(cur)){
                return false;
            }
            if(!dfs(cur,grapha,graphb,visited)){
                return false;
            }
        }
        return true;

    }

    public boolean dfs(Integer id,HashMap<Integer,HashMap<Integer,Integer>> grapha,HashMap<Integer,HashMap<Integer,Integer>> graphb, HashSet<Integer> visited ){

        if(visited.contains(id)){
            return true;//true here
        }

        visited.add(id);

        HashMap<Integer,Integer> edgesa= grapha.getOrDefault(id,new HashMap<Integer,Integer>());//very important to avoid get null
        HashMap<Integer,Integer> edgesb= graphb.getOrDefault(id,new HashMap<Integer,Integer>());

        for(Map.Entry<Integer,Integer> entrya : edgesa.entrySet() ){
            if(!edgesb.containsKey(entrya.getKey())  || edgesb.get(entrya.getKey())!=entrya.getValue()  ){
                return false;
            }
            if(!dfs(entrya.getKey(),grapha,graphb,visited)){
                return false;
            }

        }
        return true;

    }


    public static void main(String[] args){
        Graph_simpler grapha = new Graph_simpler();
        grapha.addEdge(1,2,3);
        grapha.addEdge(2,3,4);
        grapha.addEdge(3,1,2);
        grapha.addEdge(3,4,5);

        Graph_simpler graphb = new Graph_simpler();
        graphb.addEdge(3,1,2);
        graphb.addEdge(3,4,5);
        graphb.addEdge(1,2,3);
        graphb.addEdge(2,3,4);

        Node treea = new Node(1);
        treea.left = new Node(2);
        treea.left.left= new Node(3);
        treea.right=new Node(4);
        treea.right.left= new Node(2);
        treea.right.right = new Node(5);

        Node treeb = new Node(1);
        treeb.left = new Node(2);
        treeb.left.left= new Node(3);
        treeb.right=new Node(4);
        treeb.right.left= new Node(2);
        treeb.right.right = new Node(5);

        SameBTree solver = new SameBTree();
        System.out.println(solver.solution(treea,treeb));
        System.out.println(solver.SameGraph(grapha.graph,graphb.graph));
    }

}
