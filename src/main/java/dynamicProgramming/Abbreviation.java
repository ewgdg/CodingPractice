package dynamicProgramming;

import java.util.HashMap;

public class Abbreviation {
    // You can perform the following operations on the string, :
    //
    // Capitalize zero or more of 's lowercase letters.
    // Delete all of the remaining lowercase letters in .

    // Given two strings, a and b , determine if it's possible to make string a
    // equal to string b which is in uppercase
    // If so, print YES on a new line. Otherwise, print NO.

    // Complete the abbreviation function below.

    // naive recursive solution
    static String naive(String a, String b) {

        return naive_helper(a, b, 0, 0) ? "YES" : "NO";

    }

    static boolean naive_helper(String a, String b, int i, int j) {
        // terminating
        if (i >= a.length() || j >= b.length()) {
            return true;
        }

        char c1 = a.charAt(i);
        char c2 = b.charAt(j);

        if (c1 >= 'A' && c1 <= 'Z') {
            if (c1 == c2) {
                return naive_helper(a, b, i + 1, j + 1);
            } else
                return false;
        } else {
            // branch 1: delete c1 , skip to i+1
            boolean subresult1 = naive_helper(a, b, i + 1, j);
            // branch 2: capitalize c1
            boolean subresult2 = false;
            c1 = (char) (c1 + 'A' - 'a');

            if (c1 == c2) {
                subresult2 = naive_helper(a, b, i + 1, j + 1);
            }
            return subresult1 || subresult2;

            // the issue is that at many branches the subproblems defined by i and j are
            // repeatedly showing up.
            // solution is to memorize the subprob.

        }

    }

    // recusive with memoization (dp)
    static String recursive_solution(String a, String b) {
        HashMap<String, Boolean> mem = new HashMap<>();
        String res = memoization_helper(a, b, 0, 0, mem) ? "YES" : "NO";
        // System.out.println(mem);
        return res;
    }

    static boolean memoization_helper(String a, String b, int i, int j, HashMap<String, Boolean> mem) {
        // generate unique identification for the subproblem
        // since the subprob is defined by i and j so the id can be a string "i" + ':' +
        // "j"
        String id = String.valueOf(i) + ':' + String.valueOf(j);

        if (mem.containsKey(id)) {
            // read the stored result from mem
            // System.out.println("found repeating subprob");
            return mem.get(id);
        }

        // terminating
        if (i >= a.length() || j >= b.length()) {
            return true;
        }

        char c1 = a.charAt(i);
        char c2 = b.charAt(j);

        boolean result = false;

        if (c1 >= 'A' && c1 <= 'Z') {
            if (c1 == c2) {
                result = memoization_helper(a, b, i + 1, j + 1, mem);
            } else
                result = false;
        } else {
            // branch 1: delete c1 , skip to i+1
            boolean subresult1 = memoization_helper(a, b, i + 1, j, mem);
            // branch 2: capitalize c1
            boolean subresult2 = false;
            c1 = (char) (c1 + 'A' - 'a');
            if (c1 == c2) {
                subresult2 = memoization_helper(a, b, i + 1, j + 1, mem);
            }
            result = subresult1 || subresult2;

        }
        // store result into cache
        mem.put(id, result);
        return result;

    }

    // tabulation solution: another way to memorize subprob, start from the smallest
    // subprob and induce subresult from smaller subprob
    // the memoization helper can be transformed into dp tabulation
    // for example :
    /*
     * res = helper( i+1, j+1 ) ==> dp[i][j] = dp[i+1][j+1] or if we let i represent
     * the end index instead of the start index of a substring then dp[i][j]=
     * dp[i-1][j-1]
     *
     */
    static String abbreviation(String a, String b) {
        // abc
        // ABC
        // test driven!!!!!!!!!!!
        // aBBcC
        // ABBC

        // Aabb
        // ABB

        // if a==b
        // dp[i][j] = dp[i-1][j-1]
        // else if a[i] is in lower case
        // dp[i][j] = dp[i-1][j] || uppder(a[i])==b[j] ? dp[i-1][j-1]:false
        // else false

        // space compression is possible cause each case relies only on i-1 and j-1
        // space compression can reduce the space costs and dimensions

        int n = b.length();
        int m = a.length();

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            dp[i] = false;
        }
        // System.out.println(Arrays.toString(dp));

        for (int i = 1; i <= m; i++) {
            boolean[] next = new boolean[n + 1];
            if (a.charAt(i - 1) <= 'Z' && a.charAt(i - 1) >= 'A') {
                next[0] = false;
            } else {
                next[0] = dp[0];
            }
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    next[j] = dp[j - 1];
                } else if (Character.isLowerCase(a.charAt(i - 1))) {
                    if (Character.toUpperCase(a.charAt(i - 1)) == b.charAt(j - 1)) {
                        next[j] = dp[j - 1];
                    }
                    next[j] = dp[j] || next[j];
                } else {
                    next[j] = false;
                }
            }
            // update
            dp = next;
            // System.out.println(Arrays.toString(dp));
        }

        return dp[n] ? "YES" : "NO";

        // non-backward traverse
        // wrong method cannot handle
        /// aBBcC
        // ABBC

        // int j=0;
        // for(int i =0 ; i<n; i++){
        // char c = b.charAt(i);
        // boolean found = false;
        // while( j<m&&!found ){
        // char cand = a.charAt(j);
        // if(cand>='A' && cand<='Z'){
        // if(cand!=c){
        // return "NO";
        // }else{
        // found = true;
        // }
        // }
        // if( (char)(cand-'a'+'A')==c ){
        // found = true;
        // }
        // j++;
        // }
        // if(!found){
        // return "NO";
        // }

        // }
        // while(j<m){//extra tail
        // char c = a.charAt(j) ;
        // if( c>='A' && c<='Z'){
        // return "NO";
        // }
        // j++;
        // }
        // return "YES";

    }

    public static void main(String[] args) {

        System.out.println(abbreviation("aBBcC", "ABBC"));
        System.out.println(naive("aBBcC", "ABBC"));
        // System.out.println(recursive_solution("aBBsdAsfDsSFSCDasdcC",
        // "ABBADSFSCDSC"));
        System.out.println(recursive_solution("aabc", "ABC"));
    }
}
