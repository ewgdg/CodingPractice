package dynamicProgramming;

import java.util.HashMap;

public class Domino_Tromino_tiling {

//    https://www.youtube.com/watch?v=S-fUTfqrdq8


//    https://web.stanford.edu/class/cs97si/04-dynamic-programming.pdf

    //find out number of ways to fill the board 2*n
    // domino : 2*1 block           XX    |X
    // trimino : L shape 2*2 block        |XX


    /*recursive way
    if the subproblem is a perfect rectangle board (state 2)
        filling 1 :  X
                     X X


         filling 2 : X
                     X

         3:  X X  //order doesnt matter zz is another domino
             Z Z

         4:
            X X
            X


        3 state for the sub problem
        X X ...
          X ...   -->> only one filling way

        X...      -->perfect rectangle case : 4 filling way
        X...

          X....
        X X....   state 3 is the symmetrical state of state 1, can be merge to one case

    */

    public static int recursive_solution(int n){
        HashMap<String,Integer> mem = new HashMap<>();
        return helper(0,2,n,mem);
    }

    public  static int helper( int start_col, int state , int n, HashMap<String, Integer> mem){
        String key = start_col+ ":" + state;
        if(mem.containsKey(key)) return mem.get(key);

        //terminating
        if(start_col==n){
            if(state==2){
                return 1;
            }else{
                return 0;
            }
        }else if(start_col>n){
            return 0;
        }

        int res=0;
        if(state==2){//perfect rectangle

            res= (res+ helper(start_col+1,1,n,mem) ) % prime;
            res=(res +helper(start_col+1,2,n,mem) ) %prime;
            res=(res+ helper(start_col+2,2,n,mem) )%prime;
            res = (res+helper(start_col+1,3,n,mem))%prime ;//same as first case
        }else if(state==1||state==3){
            res += helper(start_col+2,2,n,mem)%prime;
            res += helper(start_col+1,state==3?1:3,n,mem)%prime;
        }

        res=res%prime;

        mem.put(key,res);
        return res;
    }


    public static int dp_solution(int n){
        int[][] dp = new int[n+1][3]; //only rely on i-1 i-2 ,can reduce the dp to const space//offset=2
        dp[0][0]=1;
        dp[1][0]=1;

        //state 0: perfect rect
        //state 1: one spare out
        //state 2 symmetrical to state 1, merged into state 1
        for(int i=2;i<=n;i++){
            dp[i][0] = (dp[i-1][0]+dp[i-2][0])%prime + (2*dp[i-1][1])%prime ;
            dp[i][0]=dp[i][0]%prime;
            dp[i][1] = dp[i-1][1] + dp[i-2][0];
            dp[i][1]=dp[i][1]%prime;
        }

        return dp[n][0];

    }

    //with space compression, using prev[] instead of next[];
    public static int tabulation2(int n){
//        rely on i-1 i-2 , index starts from 0, need offset set 0-2 to 0
        // int offset = 2;
        int[] dp = new int[2];
        int[] prev = new int[2];
        int[] pprev = new int[2];

        prev[0]=1;//base case when index = -1;//size=0
        for(int index = 0; index <n ; index++){
//            int i = index+offset;
            //reset dp, bc prev point to dp , need to allocate a new obj for dp
            dp = new int[2];
            dp[0] = (int)(long)((prev[0]+pprev[0])%prime+(prev[1]*2)%prime)%prime;
            dp[1] = (prev[1]+pprev[0])%prime;

            //update prev
            pprev= prev;
            prev= dp;

        }
        return dp[0];


    }

    public static int tabulation_wrong(int n ){//wrong!!!!!!!!!!!!!//doesnt work bc dp[i] = dp[i-1] + ....  dp[i-x] ...dp[0] ,where x <= i
        //5 ways for perfect filling
        //dp[i] = dp[i-1]+2*dp[i-2]+2*dp[i-3]+2*dp[i-4];
        //offset =4 //define the subproblem by size //wrong!!!,need all till dp[0]
        int offset = 4;
        int[] dp = new int[n+offset];
        dp[3]=1;//size=0

        for(int i=4;i<dp.length;i++){
            dp[i] = (int)(long)(dp[i-1]+dp[i-2]+2*dp[i-3]+2*dp[i-4])%prime;
        }
        return dp[n+offset-1];

    }


    final static int prime = (int)(1e9)+7;
    public static void main(String[] args){
        int n =29;
        System.out.println(recursive_solution(n));

        System.out.println(dp_solution(n));
        System.out.println(tabulation2(n));

    }




}

