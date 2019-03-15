import java.util.Arrays;

public class CommonSubsequence {
    //HARRY
    //SALLY
    //output :2
    //AY

    static int commonChild(String s1, String s2) {
        //test
        // ABCCCCC ACCCCCB
        // ABBBBBC ACBBBBB
        //each time we find a mismatch we have 2 option,
        // delete char from the first or second string
        //dp?

        //each subproblem can be expressed as (i ,j ) i is end index of s1
        //dp[i][j] = dp[i-1][j] || dp[i][j-1] //delete i or delete j
        //or dp[i-1][j-1]+1 if s1[i]==s2[j]
        //space compresion -> only need to store prev row
        //need offset 1 in case of j=0. j-1

        int m = s1.length();
        int n = s2.length();

        int[] dp = new int[m+1];
        Arrays.fill(dp,0);

        for(int i =1; i<= m;i++){//i is size here
            int[] next = new int[m+1];
            //init
            next[0] = 0;
            for(int j =1; j<=n;j++){
                next[j]= Math.max(dp[j],next[j-1]);
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                    next[j] = Math.max(next[j],dp[j-1]+1);
                }
            }
            //update dp
            dp = next;
        }

        return dp[m];



    }

    abstract class a{

        public void add(){
            return ;
        }
    }

}
