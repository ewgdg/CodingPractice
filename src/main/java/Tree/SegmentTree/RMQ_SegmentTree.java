package Tree.SegmentTree;

import java.util.Arrays;

public class RMQ_SegmentTree {
    //range minimum query
    Integer[] tree; //tree in array format .
    //bc it is a full complete tree
    //we can compute the index of parent , children
    //  parent of i=  tree[(i-1)/2]
    //left child of i = tree[ 2*1+1] ;
    //right child = tree[2*i+2]
    int n;
    public RMQ_SegmentTree(int[] nums ){
        n= nums.length;
        int nextPowerOf2 = (Integer.highestOneBit(n-1)<<2);//need size >= 2*n and size == power of 2
        tree = new Integer[nextPowerOf2];
        construct(0,n-1, 0, nums);

        //for lazy propa
        lazy = new Integer[nextPowerOf2];
        Arrays.fill(lazy,0);

    }
    //O n
    public void construct(int lo, int hi, int root , int[] nums ){

        if(lo==hi){
            tree[root]=nums[lo];
            return;
        }


        int mid = lo+(hi-lo)/2;
        construct(lo,mid,root*2+1 , nums);
        construct(mid+1,hi, root*2+2 ,nums);

        Integer left=tree[root*2+1];
        Integer right= tree[root*2+2];

        if(left==null){
            tree[root]=right;
        }else if(right==null){
            tree[root]=left;
        }else{
            tree[root]= Math.min(left,right);
        }



    }

    //O logn
    public int rangeMinQuery(int i , int j){
        return rangeMinQuery(i,j,0,n-1,0);
    }

    //helper
    public Integer rangeMinQuery(int i, int j, int lo, int hi, int root){
        //lo,hi is the segment bound for cur node
        //total overlap/covered
        if( i<=lo && j>=hi  ){
            return tree[root];
        }

        //no overlap
        if(j<lo || i>hi){
            return null;
        }

        //partial overlap
        int mid = lo+(hi-lo)/2;
        Integer res_left = rangeMinQuery(i,j,  lo,mid,root*2+1);
        Integer res_right = rangeMinQuery(i,j,mid+1,hi,root*2+2);

        if(res_left==null) return res_right;
        if(res_right==null) return res_left;
        return Math.min(res_left,res_right);

    }

    //O n, update range i,j with delta
    public void update(int i,int j, int delta){
        if(i>j){
            return;
        }
        update(i,j,0,n-1,0,delta);

    }
    //helper
    public void update(int i, int j, int lo, int hi, int root, int delta){


        //no overlap
        if(i>hi || j<lo){
            return;
        }
        //reach leaves
        if(lo==hi){
            tree[root]+=delta;
            return;
        }

        //total or partial overlap
        int mid = lo+(hi-lo)/2;
        int left_child_index = root*2+1;
        int right_child_index =root*2+2;
        update(i,j, lo, mid,  left_child_index, delta );
        update(i,j,mid+1, hi, right_child_index,delta);

        if(tree[left_child_index]==null) tree[root] = tree[right_child_index];
        if(tree[right_child_index]==null) tree[root] = tree[left_child_index];
        tree[root]=Math.min(tree[left_child_index],tree[right_child_index]);

        return;

    }








    //below is the implementation with Lazy propagation
    Integer[] lazy;
    public void updateLazy(int i,int j, int delta){
        if(i>j) return;
        updateLazy(i,j,0,n-1,0,delta);

    }
    //O logn
    public void updateLazy(int i,int j, int lo, int hi, int root, int delta){
        int left= root*2+1;
        int right= root*2+2;
        //update
        if(lazy[root]!=0){
            tree[root]+=lazy[root];
            //propagate update
            if(lo!=hi) {//not a leaf child
                lazy[left] += lazy[root];
                lazy[right]+=lazy[root];
            }
            //remove old
            lazy[root]=0;
        }

        //no overlap
        if(j<lo|| i>hi){
            return;
        }

        //total overlap , if it is total overlap, all of its children will need to +=delta
        if(i<=lo && j>=hi){
//            lazy[root] += delta; //lazy update??? wrong , bc we will trace back to upper level and read this tree[root] value so we have to update tree[root] here immediately

            //update
            tree[root]+=delta;
            if(lo!=hi){
                lazy[left]+=delta;
                lazy[right]+=delta;
            }
            return;
        }

        //partial overlap
        int mid = lo+(hi-lo)/2;
        updateLazy(i,j,lo,mid,left,delta);
        updateLazy(i,j,mid+1,hi,right,delta);
        if(tree[left]==null) tree[root]=tree[right];
        if(tree[right]==null) tree[root]=tree[left];
        tree[root]=Math.min(tree[left],tree[right]);



    }

    public int rmqLazy(int i ,int j){
        if(i>j) return -1;
        return rmqLazy(i,j,0,n-1,0);
    }
    //O logn
    public Integer rmqLazy(int i,int j,int lo,int hi,int root){
        //first check update
        int left =root*2+1;
        int right= root*2+2;
        if(lazy[root]!=0){
            tree[root]+=lazy[root];
            if(lo!=hi) {//not a leaf child
                lazy[left] += lazy[root];
                lazy[right]+=lazy[root];
            }
            lazy[root]=0;
        }


        //terminating cond
        //no overlap
        if(j<lo || i>hi){
            return null;
        }

//        //reach leaf // covered by total overlap
//        if(lo==hi){
//            return tree[root];
//        }

        //total overlap
        if(i<=lo&& j>=hi){
            return tree[root];
        }

        //partial overlap
        int mid = lo+(hi-lo)/2;
        Integer res1 = rmqLazy(i,j,lo,mid,left);
        Integer res2 = rmqLazy(i,j,mid+1,hi,right);

        if(res1==null) return res2;
        if(res2==null) return res1;
        return Math.min(res1,res2);




    }




}
