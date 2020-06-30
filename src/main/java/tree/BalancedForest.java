package tree;

import java.util.*;
// import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BalancedForest {

    //goal is to be able to cut two edges and have the values of each of the three new trees sum to the same amount
    //can insert one more non-negative node
    //find the min val to insert

    //see linear method in BalancedForest_linear

    //On^2 worst case ,avg nlogn
    class Node {
        long time_in; ////time when dfs arrived in
        long time_out;
        long sum; //sum of subtree
        int id;
        public Node(){
            sum=0;
            time_in=0;
            time_out=0;
        }
    }
    public BalancedForest(){
        nodes=new HashMap<>();
        time=0;
    }

    Long res;
    public long balancedForest(int[] c, int[][] edges) {

        //assume any node is a root
        //or find the root with smallest height; with bfs from leaves to root.
        time=0;
        nodes=new HashMap<>();
        Map<Integer,List<Integer>> graph = new HashMap<>();
        for(int[] edge:edges){
            int src =edge[0];
            int dst = edge[1];
            graph.computeIfAbsent(src,k->new ArrayList<>()).add(dst);
            graph.computeIfAbsent(dst,k->new ArrayList<>()).add(src);

        }



        //assume node 1 is root
        dfs(graph,new HashSet<>(),c,1);


        int root_id= 1;
        Node root = nodes.get(root_id);
        final long total = root.sum;


        HashMap<Long,TreeMap<Long,Node>> sum_node =new HashMap<>();
        HashMap<Long,TreeMap<Long,Node>> sum_node2 =new HashMap<>();


        for(Node node:nodes.values()){
            sum_node.computeIfAbsent(node.sum,k->new TreeMap<>()).put(node.time_in,node);
            sum_node2.computeIfAbsent(node.sum,k->new TreeMap<>()).put(node.time_out,node);
        }





//        System.out.println(total);
        res=null;

        //iterate node instead of edge
        //at least one subtree is used as a whole tree
        //use bst, binary search to find the second node
        int n = c.length;



        for(int i=1;i<=n;i++){
            // Long prev= res;
            Node node = nodes.get(i);
            long sum1 = node.sum;

            if(sum1<=total/3){

                //case1 : find independent subtree
                boolean found=false;
                //early terminated
                if( (total-sum1)%2!=0 ) continue;
                long target = (total - sum1)/2;

                found = search(target,i,sum_node,sum_node2);
                if(found){
                    if(res==null||res==-1){
                        res= target-sum1;
                    }else{
                        res= Math.min(res, target-sum1);
                    }

                    continue;
                }



            }else if( sum1>total/3 && sum1<= total/2 ) {


//                System.out.println("sum "+ sum1);
                boolean found = false;
                long target = sum1;

                if(sum1==6){
                    System.out.println();
                }
                found = search(target,i,sum_node,sum_node2);
                if(found){
                    if(res==null||res==-1){
                        res= 3*target-total;
                    }else{
                        res= Math.min(res, 3*target-total);
                    }
                    continue;
                }

                //another possible sum target we can search
                long target2 = total-sum1*2;
                found = search(target2,i,sum_node,sum_node2);
                if(found){
                    if(res==null||res==-1){
                        res= 3*target-total;
                    }else{
                        res= Math.min(res, 3*target-total);
                    }
                }





            }else if(sum1==total/2 && total%2==0){//covered by prev case
                if(res==null||res==-1){
                    res= sum1;
                }else{
                    res= Math.min(res, sum1);
                }
            }else if(sum1>total/2){
                continue;
            }
//            System.out.println("sum1 "+sum1+" res "+res);
//            if(     (prev==null&&res!=null) ||          (  prev!=null&&   !prev.equals(res) ) )
//                System.out.println("res c " +res);
        }



//        for(int i =0;i<edges.length;i++){
//            int[] edge1= edges[i];
//            int x=edge1[0];
//            if( isAncestor(edge1[0],edge1[1]) ){
//                x=edge1[1];
//            }
//            long sum2 = nodes.get(x).sum;
//            long sum1 = total-sum2;
//
//
//            //not necessary to cut the second edge
//            if(sum1==sum2){
//                long temp =  calculate(sum1,sum2,0);
//                if(res==null||res==-1) res = temp;
//                else if(temp!=-1)
//                    res =Math.min ( temp,res);
//                continue;
//            }
//
//
//            for(int j=i+1;j<edges.length;j++){
//                int[] edge2 = edges[j];
//                int y = edge2[0];
//                if(isAncestor(edge2[0],edge2[1])){
//                    y=edge2[1];
//                }
//
//                long sum3 = nodes.get(y).sum;
//                long cur_sum2=sum2;
//                long cur_sum1=sum1;
//                if(isAncestor(x,y)){
//                    cur_sum2-=sum3;
//                }else{
//                    if(isAncestor(y,x)){
//                        sum3-=cur_sum2;
//                    }
//                    cur_sum1-=sum3;
//                }
//
//                long temp = calculate(cur_sum1,cur_sum2,sum3);
//                if(res==null || res==-1){
//                    res=temp;
//                }else{
//                    if(temp!=-1)
//                        res = Math.min(temp,res);
//                }
//
//            }
//
//        }
//        System.out.println(res);
        if(res==null) return -1;
        return res.longValue();


    }
    public boolean search(long target ,int given_node ,Map<Long,TreeMap<Long,Node>> sum_node, Map<Long,TreeMap<Long,Node>> sum_node2 ){

        //case 1: independent subtree


        TreeMap<Long,Node> tree = sum_node.getOrDefault(target,new TreeMap<>());
        TreeMap<Long,Node> tree2 = sum_node2.getOrDefault(target,new TreeMap<>());
        Node given = nodes.get(given_node);

        if(tree.higherEntry(given.time_out)!=null||tree2.lowerEntry(given.time_in)!=null){
            return true;
        }



        //case 2: as ancestor of given node
        long target2= target+nodes.get(given_node).sum;
        tree = sum_node.getOrDefault(target2,new TreeMap<>());
        tree2 = sum_node2.getOrDefault(target2,new TreeMap<>());

        Set<Integer> set = new HashSet<>();

        set.addAll(   tree.headMap(given.time_in,false).values().stream().map(i->i.id).collect(Collectors.toList())   );


        for(Node node:tree2.tailMap(given.time_out,false).values()){
            if(set.contains(node.id)){
                return true;
            }
        }

//        if(tree.subMap(given.time_in,false,given.time_out,false).size()>0){
//            return true;
//        }


        return false;
    }
    public long calculate(long sum1,long sum2, long sum3){
//        System.out.println(sum1+" "+sum2+" "+sum3);
        long res=-1;
        if(sum1==sum2){
            res= sum1-sum3;
        }else if(sum1==sum3){
            res= sum1-sum2;
        }else if(sum2==sum3){
            res= sum2-sum1;
        }
        if(res<0){
            return -1;
        }
//        System.out.println("res "+res);
        return res;


    }

    public boolean isAncestor(int a, int b){//check if a is ancestor of b
        Node node1 = nodes.get(a);
        Node node2 = nodes.get(b);
        return node2.time_out<=node1.time_out && node1.time_in<=node2.time_in;
    }

    Map<Integer,Node> nodes ;
    long time;

    public boolean binarySearch(long target, int given_node, List<Node> list, boolean findAncestor, boolean findChild){
        int lo=0;
        int hi = list.size()-1;

        while(lo<=hi){
            int mid = lo + (hi-lo)/2;
            Node cur = list.get(mid);

            if(cur.sum ==target  ){

                if(findAncestor && isAncestor(cur.id,given_node)) {
                    return true;
                }else if(findChild && isAncestor(given_node,cur.id)){
                    return true;
                }else if(!findChild && !findAncestor){
                    return true;
                }else{
                    if(findAncestor){
                        lo=mid-1;
                    }else{
                        hi=mid+1;
                    }
                }

            }else if(cur.sum > target  ){
                lo=mid+1;

            }else{
                hi=mid-1;
            }
        }

        return false;

    }



    public long dfs(Map<Integer, List<Integer>> graph, Set<Integer> seen, int[] c,int cur  ){

        if(seen.contains(cur)) return 0;
        time++;
        seen.add(cur);



        Node node = new Node();
        node.time_in = time;

        long sum=c[cur-1];
        for(int child: graph.getOrDefault(cur,new ArrayList<>())){
            long subsum= dfs(graph,seen,c,child);
            sum+=subsum;

        }

        time++;
        node.sum=sum;
        node.time_out=time;
        node.id=cur;
        nodes.put(cur,node);


        return sum;

    }



}
