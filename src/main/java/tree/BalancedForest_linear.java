package tree;

import java.util.*;

public class BalancedForest_linear extends BalancedForest{

    //improvement
    //the key point is to find the sum from ancestors,
    //we can get this info by using a set + dfs to store ancestor sum only


    class Node{
        boolean visited;
        List<Node> children;
        long sum;
        int id;
        public Node(int id) {
            this.id=id;
            this.children= new ArrayList<>();
            sum=0;
            visited=false;
        }
    }

    Map<Integer,Node> nodes =new HashMap<>();

    public void buildGraph(int[][] edges){
        for(int[] edge:edges){
            Node a = nodes.computeIfAbsent(edge[0],k->new Node(k));
            Node b = nodes.computeIfAbsent(edge[1],k->new Node(k));

            a.children.add(b);
            b.children.add(a);
        }
    }


    public long dfs_computeSum(Node root, int[] c){
        if(root.visited){
            return 0;
        }else{
            root.visited=true;
        }
        long sum=c[root.id-1];
        for(Node child: root.children){
            sum+=dfs_computeSum(child,c);
        }
        root.sum =sum;
        return sum;

    }



    public void reset_node_state(){
        for(Node node:nodes.values()){
            node.visited=false;
        }
    }
    long total;
    public void solve(Node root,Set<Long> sums_from_ancestors, Set<Long> sums_from_visited){


//        long res=-1;
        if(root.visited) return ;

        root.visited=true;


//        if(root.sum==30){
//            System.out.println();
//        }

        //case1
        long target = root.sum;
        if(find(target,root,sums_from_ancestors,sums_from_visited)){
            res =min(res,3*target-total);//skip if temp<0
        }
        //case2
        target = total-(root.sum<<1);
        if(find(target,root,sums_from_ancestors,sums_from_visited)){
            res = min(res,root.sum-target);
        }


        //case 3
        target = (total-root.sum);
        if(  (target&1)==0  ){//divisible by 2
            target= target>>1;
            if(find(target,root,sums_from_ancestors,sums_from_visited)){
                res = min(res,target-root.sum);
            }
        }


        sums_from_ancestors.add(root.sum);


        for(Node child: root.children){
            solve(child,sums_from_ancestors,sums_from_visited) ;
        }

        sums_from_ancestors.remove(root.sum);//remove if no dup found, but we know there wont be dup
        sums_from_visited.add(root.sum);//add after return from child such that sum from visited does not contains ancestor sum
//        return res;
    }
    public long min(Long a, Long b){
        if( b==null|| b<0 ){
            return a;
        }
        if(a==null || a<0){
            return b;
        }

        return Math.min(a,b);
    }
    public boolean find(long target, Node cur ,Set<Long> sums_from_ancestors, Set<Long> sums_from_visited){
        return sums_from_ancestors.contains(target+cur.sum)||sums_from_visited.contains(target);
    }

    long res = -1;
    @Override
    public long balancedForest(int[] c, int[][] edges){
        //reset
        res=-1;
        total=0;
        nodes = new HashMap<>();


        buildGraph(edges);
        Node root = nodes.get(1);
        if(root==null) return -1;

        dfs_computeSum(root,c);
        total = root.sum;
        reset_node_state();

//        System.out.println(root);
        solve(root, new HashSet<>(), new HashSet<>());

        return res;


    }










}
