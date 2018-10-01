public class Guess_Number_Higher_or_Lower_II {
//I pick a number from 1 to n. You have to guess which number I picked.
//
//Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.
//
//However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.

//Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.

    public static int getMoneyAmount(int n) {
        int[][] dp = new int[n+1][n+1];
        return helper(1,n,dp);
    }


    //dfs+mem
    public static int helper(int lo,int hi, int[][] dp){
        if(lo>=hi) return 0;

        if(dp[lo][hi]!=0) return dp[lo][hi];

        int res=Integer.MAX_VALUE;
        for(int x =lo; x<=hi; x++ ){
            int temp = x + Math.max(helper(lo,x-1,dp)  ,helper(x+1,hi,dp)   )  ; //worst branch
            res = Math.min(res,temp) ;//best x
        }

        dp[lo][hi]=res;
        return res;


    }

    public  static int tabulation_solution(int n){
        int[][] dp = new int[n+1][n+1];


        for(int size=1;size<n;size++){//size start from 1 bc if i==j then dp should return 0
            for(int i=1;i+size<=n;i++){
                int j=i+size;
                int res =Integer.MAX_VALUE;
                for(int x=i;x<j;x++){ //dont need to pick x=j , otherwise need to test bound x+1<j
                    int worst_branch  = x + Math.max(dp[i][x-1],dp[x+1][j]);
                    res = Math.min(res,worst_branch);
                }
                dp[i][j]=res;

            }
        }
        return dp[1][n];
    }



    public static void main(String[] args){
        int n = 400;
        System.out.println(tabulation_solution(n));
        System.out.println(getMoneyAmount(n));
    }
}
