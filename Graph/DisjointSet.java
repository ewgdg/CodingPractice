import java.util.*;

public class DisjointSet {

    class Node{
        int data;//need this bc after we find parent we might need to use the data
        Node parent;
        int rank;
        public Node(int data){
            this.data=data;
            this.parent=this;
            rank=0;
        }

    }
    int count; //count number of group !!

    public HashMap<Integer,Node> map;
    public DisjointSet(){
        map = new HashMap<>();
        count=0;
    }

    public Node makeSet(int data){//makeSetIfAbsent
        if(!map.containsKey(data)){
            count++;
            map.put(data,new Node(data));
        }
        return map.get(data);
    }

    public int findParent(int data){//dont need this
        if(!map.containsKey(data)) return -1;
        return findParent(map.get(data)).data;
    }
    public Node findParent(Node node){//should name it find , it find which group it belongs to
        if(node.parent!=node){
            node.parent = findParent(node.parent);
        }
        return node.parent;
    }

    public boolean union(int a, int b){
        Node n1 = makeSet(a); //to ensure we always get the id we use makeset instead of map.get
        Node n2 = makeSet(b);

        Node p1 = findParent(n1);
        Node p2 = findParent(n2);

        if(p1==p2) return false;//if same set dont join

        count--;
        if(p1.rank>= p2.rank){
            if(p1.rank==p2.rank){
                p1.rank++;
            }
            p2.parent=p1;
        }else{
            p1.parent=p2;
        }
        return true;

    }

    public List<HashSet<Integer>> getSets(){
        HashMap<Integer,HashSet<Integer>> res = new HashMap<>();
        for(Map.Entry<Integer,Node> entry: map.entrySet() ){
            Node node = entry.getValue();
            res.computeIfAbsent(findParent(node).data,(k)->new HashSet<>()).add(node.data);
        }
        return new ArrayList<>(res.values());
    }
    public static void main(String[] args){
        DisjointSet disjointSet = new DisjointSet();
        disjointSet.makeSet(1);
        disjointSet.makeSet(2);
        disjointSet.union(1,2);
        disjointSet.makeSet(3);
        disjointSet.union(1,3);
        disjointSet.makeSet(4);
        disjointSet.makeSet(5);
        disjointSet.makeSet(3);
        disjointSet.union(3,5);
        disjointSet.makeSet(7);
        disjointSet.union(7,4);

        System.out.println(disjointSet.getSets());



    }


}
