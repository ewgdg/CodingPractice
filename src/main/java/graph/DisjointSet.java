package graph;

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
    public Node findParent(Node node){//should name it find!!! , it find which group/root it belongs to
        if(node.parent!=node){
            node.parent = findParent(node.parent); //path compression
        }
        return node.parent;

    }
    public Node findIterative(Node node){
        //iterative path compression
        //get root
        Node cur = node;
        while(cur.parent!=cur){
            cur= cur.parent;
        }
        Node root =cur;
        cur=node;
        //path compression
        while(cur!=root){
            Node next=cur.parent;
            cur.parent=root;
            cur=next;
        }
        return root;


    }

    public boolean union(int a, int b){
        Node n1 = makeSet(a); //to ensure we always get the id we use makeset instead of map.get
        Node n2 = makeSet(b);

        Node p1 = findParent(n1);
        Node p2 = findParent(n2);

        if(p1==p2) return false;//if same set dont join

        count--;
        //smaller rank indicates shorter path to root
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

//another similar implenmentation without Node struct, less readable
// Union Find, call union for every edge, return count().
// O(n + edges.length) time because Union Find constructor costs O(n) time, and we call union() exactly once for every edge where one union() call costs O(1) time (in theory its very very slightly above O(1)).
// O(n) space because of Union Find.
// public int countComponents(int n, int[][] edges) { 
//     UnionFind uf = new UnionFind(n); 
//     for (int[] edge : edges) {
//         uf.union(edge[0], edge[1]);
//     }
//     return uf.count();
// }


// private class UnionFind {
//     private int[] parents;     
//     private int[] weight;
//     private int count;
    
//     public UnionFind(int n) {
//         parents = new int[n];
//         weight = new int[n];
//         count = n;
//         for (int i = 0; i < parents.length; ++i) {
//             parents[i] = i;
//             weight[i] = 0;
//         }
//     }
    
//     public void union(int p, int q) {
//         int pRoot = find(p);
//         int qRoot = find(q);
//         if (pRoot == qRoot) return;     // p and q already belong to the same set.
        
//         // Weighted union: make the less deeper tree a subtree of the deeper tree.
//         if (weight[pRoot] < weight[qRoot]) {
//             parents[pRoot] = qRoot;
//         } else if (weight[pRoot] > weight[qRoot]) {
//             parents[qRoot] = pRoot;
//         } else {
//             parents[qRoot] = pRoot;
//             weight[pRoot]++;        
//         }
//         count--;    
//     }
    
//     public int find(int p) {
//         int pRoot = p;
//         while (pRoot != parents[pRoot]) {
//             pRoot = parents[pRoot];
//         }
        
//         // Path compression: make each node on the path from p to its root point to the root.
//         while (p != parents[p]) {
//             int temp = parents[p];
//             parents[p] = pRoot;
//             p = temp;
//         }
//         return pRoot;
//     }
    
//     public int count() {
//         return count;
//     }
// }