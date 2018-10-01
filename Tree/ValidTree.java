import java.util.HashMap;

public class ValidTree {

    public boolean validTree(int n, int[][] edges) {
        //valid if we can traverse the whole tree without cycle from any point
        //disjoint set to see if they finally join as one group
        DisjointSet set = new DisjointSet();

        for(int i=0;i<n;i++){
            set.makeSet(i);//add all points first
        }

        for(int[] edge:edges ){
            if(!set.union(edge[0],edge[1])){
                return false;
            }
        }

        return set.count==1; //or edges.length==n-1 , equivalent

    }


    class DisjointSet{
        HashMap<Integer, Node> map;
        int count =0 ; //number of group
        public DisjointSet(){
            map = new HashMap<>();
            count=0;
        }
        public Node makeSet(int id){
            if(!map.containsKey(id)){
                count++;
                map.put(id, new Node(id));
            }
            return map.get(id);
        }

        public Node find( Node cur ){
            if(cur.parent!=cur){
                cur.parent = find(cur.parent);
            }
            return cur.parent;
        }

        public int find(int id){
            return find(makeSet(id)).id;
        }

        public boolean union(int id1 , int id2){
            Node node1 = makeSet(id1);
            Node node2 = makeSet(id2);

            Node parent1 = find(node1);
            Node parent2 = find(node2);

            if(parent1!=parent2){
                count--;
            }else {
                return false;
            }

            if(parent1.rank>=parent2.rank){
                if(parent1.rank==parent2.rank){
                    parent1.rank++;
                }

                parent2.parent = parent1;

            }else{
                parent1.parent = parent2;
            }

            return true;


        }

    }
    class Node{
        public int id;
        public Node parent;
        public int rank;

        public Node(int id){
            this.id=id;
            this.parent=this;
            this.rank=0;
        }

    }
}
