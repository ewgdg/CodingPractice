import java.util.HashMap;


public class PredictWinner {


    //pick number from either end one by one
    //larger sum win
    public boolean PredictTheWinner(int[] nums) {
        // HashMap<String,Integer> mem = new HashMap<>();
        // return helper(nums,0,nums.length-1,mem)>=0;
        return tabulation(nums)>=0;
        // return minmax(nums,0,nums.length-1,0,1)>=0;
        // return alphaBeta(nums,0,nums.length-1,0,1,Integer.MIN_VALUE,Integer.MAX_VALUE)>=0;
    }

    public int tabulation3(int[] nums){
        int n = nums.length;
        int[][] dp = new int[n][n]; //dp[i][j] i=lo j=hi
        for(int i =0;i<n;i++){
            dp[i][i] = nums[i]; //lo==hi
        }

        for(int size=2;size<=n;size++){ //need to start from size 2 bc size 1(i==j) relys on size 0 and size 0 indicate i>j where out of boundary cond might show up at j=n-1, i=0
            for(int i =0; i+size-1<n;i++){
                int j = i+size-1;
                dp[i][j] = Math.max(nums[i]-dp[i+1][j], nums[j]-dp[i][j-1]);//at size iter, all i,j of smaller size has been calcuated
            }
        }
        return dp[0][n-1];


    }

    public int tabulation22(int[] nums){
        int n = nums.length;
        int[][] dp = new int[n+1][n]; //dp[size][i] j=i+size-1
        for(int i =0;i<n;i++){
            dp[0][i] = 0; //base cond  if(lo>hi) return 0;
            dp[1][i] = nums[i]; //lo==hi
        }

        for(int size=2;size<=n;size++){
            for(int i =0; i+size-1<n;i++){
                int j = i+size-1;
                dp[size][i] = Math.max(nums[i]-dp[size-1][i+1], nums[j]-dp[size-1][i]);
            }
        }
        return dp[n][0];


    }

    public int tabulation(int[] nums){
        int[] dp= new int[nums.length]; //space compression reduce from 2d to 1d with dp[size][i]
        //dp[i][j] represent max from subarray i,j. compressed j so that j is always i+size-1 ， dp[size][i] j=i+size-1
        int n = nums.length;
        //init
        for(int i=0;i<n;i++){
            dp[i] = nums[i];
        }
        for(int size=2;size<= n; size++){
            for(int i=0;i+size-1<n;i++){
                dp[i]= Math.max( nums[i+size-1]-dp[i], nums[i]-dp[i+1]);
                //nums[j]-dp[i] || nums[i]-dp[i+1]
                //nums[j] - dp[i][j-1] || nums[i] - dp[i+1][j]
            }
        }
        return dp[0];
    }
    public int helper(int[] nums, int lo , int hi, HashMap<String,Integer> mem){
        String key = lo+":"+hi;
        if(mem.containsKey(key)) return mem.get(key);

        if(lo>hi) return 0;
        if(lo==hi) return nums[lo];//not necessary
        //assume optimal opponent
        int res = 0;
        //option 1, pick lo
        int res1 = nums[lo]-helper(nums,lo+1,hi,mem);
        int res2 = nums[hi]-helper(nums,lo,hi-1,mem);

        res = Math.max(res1,res2);
        mem.put(key,res);
        return res;
    }

    public int minmax(int[] nums, int lo , int hi, int val,int player){
        if(lo>hi) return 0;
        int sign=1;
        if(player!=1) sign = -1;
        if(lo==hi) return nums[lo]*sign+val;//the evaluation score of max player = posi Sum-nega sum
        //assume optimal opponent
        int res = 0;
        int res1 = minmax(nums,lo+1,hi,val+nums[lo]*sign,player^1);
        int res2 = minmax(nums,lo,hi-1,val+nums[hi]*sign,player^1);
        if(player==1){
            res = Math.max(res1,res2);
        }else{
            res = Math.min(res1,res2);
        }

        return res;
    }

    public int alphaBeta(int[] nums, int lo , int hi, int val,int player, int alpha, int beta){
        if(lo>hi) return 0;
        int sign=1;
        if(player!=1) sign = -1;
        if(lo==hi) return nums[lo]*sign+val;

        int res = 0;
        if(player==1){
            int res1 = alphaBeta(nums,lo+1,hi,val+nums[lo]*sign,player^1,alpha,beta);
            if(res1>alpha){
                alpha=res1;
            }
            if(alpha>=beta) return alpha;
            int res2 = alphaBeta(nums,lo,hi-1,val+nums[hi]*sign,player^1,alpha,beta);
            if(res2>alpha){
                alpha=res2;
            }
            return Math.max(res1,res2);//return alpha;//alpha is always the max
        }else{
            int res1 = alphaBeta(nums,lo+1,hi,val+nums[lo]*sign,player^1,alpha,beta);
            if(res1<beta){
                beta=res1;
            }
            if(alpha>=beta) return beta;
            int res2 = alphaBeta(nums,lo,hi-1,val+nums[hi]*sign,player^1,alpha,beta);
            if(res2<beta){
                beta=res2;
            }
            return Math.min(res1,res2);//return beta;

        }


    }
    public static void main(String[] args){
        PredictWinner solver = new PredictWinner();

        int[] nums = new int[]{3,4,5,6,2,8,9,7};

        System.out.println(solver.tabulation(nums));
        System.out.println(solver.tabulation22(nums));
        System.out.println(solver.tabulation3(nums));

    }
}
