import java.util.Arrays;

public class Abbreviation {
    //You can perform the following operations on the string, :
    //
    //Capitalize zero or more of 's lowercase letters.
    //Delete all of the remaining lowercase letters in .

    //Given two strings, a and b , determine if it's possible to make a equal to b  as described. If so, print YES on a new line. Otherwise, print NO.


    // Complete the abbreviation function below.
    static String abbreviation(String a, String b) {
        //abc
        //ABC
        //test driven!!!!!!!!!!!
        //aBBcC
        //ABBC

        //Aabb
        //ABB

        //if a==b
        //dp[i][j] = dp[i-1][j-1]
        // else a is lower case
        //dp[i][j] = dp[i-1][j] || uppder(a)==b ? dp[i-1][j-1]:false
        //else false

        int n = b.length();
        int m = a.length();

        boolean[] dp = new boolean[n+1];
        dp[0] = true;
        for(int i=1;i<=n;i++){
            dp[i]=false;
        }
//        System.out.println(Arrays.toString(dp));

        for(int i=1;i<=m;i++){
            boolean[] next = new boolean[n+1];
            if(a.charAt(i-1)<='Z' && a.charAt(i-1)>='A' ){
                next[0]=false;
            }else{
                next[0]=dp[0];
            }
            for(int j=1;j<=n;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    next[j] = dp[j-1];
                }else if(Character.isLowerCase( a.charAt(i-1) ) ){
                    if(Character.toUpperCase(a.charAt(i-1))==b.charAt(j-1)){
                        next[j] =  dp[j-1];
                    }
                    next[j] = dp[j]||next[j];
                }else{
                    next[j]=false;
                }
            }
            //update
            dp = next;
//            System.out.println(Arrays.toString(dp));
        }

        return dp[n]? "YES" : "NO";



        //non-backward traverse
        //wrong method cannot handle
        ///aBBcC
        // ABBC


        // int j=0;
        // for(int i =0 ; i<n; i++){
        //     char c = b.charAt(i);
        //     boolean found = false;
        //     while( j<m&&!found ){
        //         char cand = a.charAt(j);
        //         if(cand>='A' && cand<='Z'){
        //             if(cand!=c){
        //                 return "NO";
        //             }else{
        //                 found = true;
        //             }
        //         }
        //         if( (char)(cand-'a'+'A')==c ){
        //             found = true;
        //         }
        //         j++;
        //     }
        //     if(!found){
        //         return "NO";
        //     }

        // }
        // while(j<m){//extra tail
        //     char c = a.charAt(j) ;
        //     if( c>='A' && c<='Z'){
        //         return "NO";
        //     }
        //     j++;
        // }
        // return "YES";



    }

    public static void main(String[] args){

        System.out.println(abbreviation("aBBcC","ABBC"));
    }
}
