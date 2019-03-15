import java.util.*;

public class panCakeSorting {

    //naively , dfs/bfs uniform cost search~=same as bfs bc cost is uniform for each k , too slow
    //analyze first!!!!, bfs takes at most n^n brach factor^ depth
    //observe pattern , each flip the last half is untouched so we can sort last elem first
    public List<Integer> pancakeSort(int[] A) {
        int n = A.length;
        int[] pos_order = new int[n];//sorted index


        NodeC[] sorted = new NodeC[n];
        for(int i=0;i<n;i++){
            sorted[i] = new NodeC(A[i],i);
        }
        Arrays.sort(sorted);
        //get the sorted index/ order_pos
        int[] order_pos = new int[n];
        for(int i=0;i<n;i++){
            NodeC cur = sorted[i];
            pos_order[cur.index]=i;
            order_pos[i]=cur.index;
        }

        //another way to get sorted index
        //List<Integer> ans = new ArrayList();
        //        int N = A.length;
        //
        //        Integer[] B = new Integer[N];
        //        for (int i = 0; i < N; ++i)
        //            B[i] = i+1;
        //        Arrays.sort(B, (i, j) -> A[j-1] - A[i-1]); //use customized comparator to sort
        // B is the order_pos
        //
        //        for (int i: B) {
        //            for (int f: ans)
        //                if (i <= f)
        //                    i = f+1 - i;
        //            ans.add(i);
        //            ans.add(N--);
        //        }
        //
        //        return ans;




        // System.out.println(Arrays.toString(pos_order));
        List<Integer> res = new ArrayList<>();

        for(int order =n-1 ;order>=0;order--){
            int pos = order_pos[order];
            if(pos==order){
                continue;
            }
            if(pos!=0){
                res.add(pos+1);
                reverse(pos_order,order_pos,pos+1);
            }
            res.add(order+1);
            reverse(pos_order,order_pos,order+1);

        }
        return res;

    }

    public void reverse(int[] pos_order,int[] order_pos, int k){

        int lo =0;
        int hi = k-1;
        while(lo<hi){
            int temp = pos_order[lo];
            order_pos[temp]=hi;
            order_pos[pos_order[hi]] = lo;
            pos_order[lo] = pos_order[hi];
            pos_order[hi]= temp;

            lo++;
            hi--;

        }

    }
    class NodeC implements Comparable<NodeC>{
        int val;
        int index;
        public NodeC(int v, int i){
            val = v;
            index= i;
        }
        public int compareTo(NodeC b){
            return val-b.val;
        }
    }

    //bfs
    public List<Integer> pancakeSort_bfs(int[] A) {

        Queue<State> front = new LinkedList<>();

        int[] sorted = Arrays.copyOf(A,A.length);
        Arrays.sort(sorted);
        Node goal  = new Node(sorted);
        Node start = new Node(A);



        front.add(new State(0,start ,null ) );
        Set<Node> seen  = new HashSet<>();

        int n = A.length;

        HashMap<Node, State> predecessors = new HashMap<>();

        while(!front.isEmpty()){

            State curState = front.poll();
            Node cur=  curState.node;

            if(seen.contains(cur)){
                // System.out.println("dup "+Arrays.toString(cur.data));
                continue;
            }
            seen.add(cur);
            // System.out.println(Arrays.toString(cur.data));
            predecessors.put(cur,new State(curState.k, curState.parent,null));

            if(goal.equals(cur)){
                // System.out.println("SSS");
                break;
            }

            for(int i = 1; i<=n; i++){
                int[] copy =  new int[n];
                flip( cur.data,copy,i );

                Node next = new Node(copy);
                front.add(new State(i,next,cur));
            }


        }


        if(!predecessors.containsKey(goal)) return new ArrayList<>();

        Node cur = goal;
        Deque<Integer> res = new LinkedList<>();
        while(!cur.equals(start)){
            State state =  predecessors.get(cur);
            res.addFirst( state.k );
            cur = state.node;
        }
        return new ArrayList<>(res);


    }
    public void flip(int[] A , int[] B, int k ){

        for(int i= k-1, j=0 ; i>=0&&j<k ; i--,j++){

            B[j] = A[i];
        }
        for(int i=k;i<A.length;i++){
            B[i]=A[i];
        }

    }

    class Node{
        int[] data;
        public Node(int[] data){
            this.data =data;
        }
        public boolean equals(Object o){
            if(o==null) return false;
            if(getClass()!=o.getClass()) return false;

            Node that  = (Node)o;
            return Arrays.equals(this.data,that.data);
        }

        public int hashCode(){
            return Arrays.hashCode(data);
        }

    }
    class State{
        Node node;
        Node parent;//cannot use state for parent, otherwise it would be a linked list and each state will link to a long list /large mem
        int k;
        public State(int k , Node node, Node parent){
            this.node= node;
            this.k=k;
            this.parent = parent;
        }
    }
}

