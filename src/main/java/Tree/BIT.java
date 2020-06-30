package Tree;

public class BIT {// BinaryIndexTree
    int[] tree;
    int[] nums;
    int n ;
    public BIT(int n ){
        this.n=n;
        tree = new int[n+1];
        nums = new int[n];
    }

    public BIT(int[] nums ){
        this.n=nums.length;
        tree = new int[n+1];
        this.nums = new int[n];

        for(int i=0;i<n;i++){
            update(i,nums[i]);
        }
    }

    public void update(int index, int val){

        int diff = val-nums[index];
        nums[index] = val;

        index+=1;
        while(index<=n){
            tree[index]+= diff;
            index+=(-index&index);
        }

    }

    public int getSum(int index){
        index+=1;
        int sum=0;
        while(index>0){
            sum+=tree[index];
            index-= (-index&index) ;
        }
        return sum;
    }



    public static void main(String[] args){
        int[] nums= new int[]{2,3,1,4,6,3,7};
        BIT tree = new BIT(nums);
        System.out.println(tree.getSum(5));
        tree.update(0,3);
        System.out.println(tree.getSum(5));
        tree.update(3,100);
        System.out.println(tree.getSum(5));

    }
}
