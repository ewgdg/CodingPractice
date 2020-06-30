package String;

public class makingPalindrome {


    //return min number of deletion to make 2 string palindrome
    // Complete the  function below.
    static int makeAnagram(String a, String b) {
        //test driven
        //abc
        //dddb

        //dp
        //dp[i][j] = dp[i+1][j-1] if char i == char j
        //or min(dp[i+1][j]  ,dp[i][j-1] )+1//important to +1!!!!!!!
        int m = a.length();
        int n=b.length();
        // int[][] dp = new int[m][n];
        //space compression
        int[] dp = new int[n+1];
        dp[0] = 0;
        for(int j=1;j<=n;j++){
            dp[j]=j;
        }
        for(int i=1;i<=m;i++){
            int[] next = new int[n+1];
            next[0]=i;
            for(int j =1 ; j<=n;j++){
                if(a.charAt(m-i) == b.charAt(j-1) ){
                    //m-i bc a starts from last char for palindrome
                    next[j] = dp[j-1];
                }else{
                    next[j] =Math.min(next[j-1] , dp[j])+1;
                    // System.out.println( "start" );
                    // System.out.println( i );
                    // System.out.println( j  );
                    // System.out.println(next[j]);
                }

            }
            //update dp
            dp = next;
        }
        return dp[n];


    }
}
