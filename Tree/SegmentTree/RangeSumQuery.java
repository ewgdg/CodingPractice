package SegmentTree;

import java.util.Arrays;

public class RangeSumQuery {
    /* method 1:
        use prefix sum array
        update requires O n
        preprocess O n
        query O 1
        space O n

     */

    /*
        method 2:

        use segment tree
        query: O logn
        lazy update: O logn
        preprocess : O n
        space O n


     */

    /*
        method 3:
        binary indexed tree , similar to segment tree
    */


    //here we use segment tree for faster update

    int n ;
    Integer[] tree;
    int[] lazy;
    int[] nums;

    public RangeSumQuery(int[] nums){
        n  =nums.length;

        int nextPowerOf2 = Integer.highestOneBit(n-1)<<2;

        tree = new Integer[nextPowerOf2];
        lazy = new int[nextPowerOf2];
        Arrays.fill(tree,0);
        this.nums = Arrays.copyOf(nums,nums.length);

        construct(nums,0,n-1,0);
    }

    public void construct(int[] nums, int lo, int hi, int root){
        //terminating
        if(lo==hi){
            tree[root] = nums[lo];
            return;
        }

        int mid = lo+ (hi-lo)/2;
        int left  = root*2+1;
        int right = root*2+2;
        construct(nums, lo, mid,  left   );
        construct(nums, mid+1, hi, right);

        tree[root] =  tree[left]+tree[right];
        return;
    }



    public void set(int i, int val){
        int old = nums[i];
        int delta = val-old;

        update(i,i,0,n-1,delta,0);
    }

    public void update(int i, int j , int lo, int hi, int delta ,int root){
        int left =root*2+1;
        int right = root*2+2;
        if(lazy[root]!=0){
            tree[root]+=lazy[root];
            if(hi!=lo) {
                lazy[left] += lazy[root];
                lazy[right] += lazy[root];
            }
            //reset
            lazy[root]=0;
        }

        //if no overlap
        if(j<lo|| i>hi){
            return;
        }
        //total overlap
        if( i<=lo && j>=hi){
            tree[root]+=delta;

            if(lo!=hi){
                lazy[left]+=delta;
                lazy[right]+=delta;
            }
            return;
        }


        //partial overlap
        //keep dfs for total overlap
        int mid = lo+ (hi-lo)/2;
        update(i,j,lo,mid,delta,left);
        update(i,j,mid+1,hi,delta,right);
        //read val from children and update
        tree[root] = tree[left]+tree[right];
        return;
    }

    public int getSum(int i, int j){
        if(i>j) return -1;
        return getSum_helper(i,j,0,n-1,0);
    }
    public int getSum_helper(int i, int j, int lo ,int hi, int root){
        //update
        int left= root*2+1;
        int right = root*2+2;
        if(lazy[root]!=0){
            tree[root]+=lazy[root];
            if(lo!=hi){
                lazy[left]+=lazy[root];
                lazy[right]+=lazy[root];
            }
            //reset lazy
            lazy[root]=0;
        }

        //no overlap
        if( j<lo|| i>hi){
            return 0;
        }
        //total
        if( i<=lo&&j>=hi){
            return tree[root];
        }

        //partial
        //keep search for total
        int mid=lo+(hi-lo)/2;
        int res1= getSum_helper(i,j,lo,mid,left);
        int res2 = getSum_helper(i,j,mid+1,hi, right);

        return res1+res2;
    }

}
