package dynamicProgramming;

import java.util.HashMap;

public class MinimumEditDistance {

    //abc , abd
    //a to b
    public static int solution(String a, String b){
        HashMap<String,Integer> mem = new HashMap<>();
        return  helper(a,0,b,0,mem);

    }
    static int  call=0;
    public static int helper(String a, int indexa, String b, int indexb, HashMap<String,Integer> mem){
        String key = indexa +":" +indexb;
        if(mem.containsKey(key)){
            call++;
            return  mem.get(key);
        }

        if(indexa==a.length()&&indexb==b.length()){
            return 0;
        }else if(indexa==a.length()){
            int remaining = b.length()-indexb;
            //should delete them
            return remaining;
        }else if(indexb==b.length()){
            int remaining = a.length()-indexa;
            return remaining;
        }

        int res=0;
        if(a.charAt(indexa)==b.charAt(indexb)){
            //no op
            res += helper(a,indexa+1,b,indexb+1,mem);
        }else{
            //3 ways
            //case1 : replace it
            int res1 = helper(a,indexa+1,b,indexb+1,mem)+1;
            //case2 : delete it
            int res2 = helper(a,indexa,b,indexb+1,mem)+1;
            //case3 : insert it
            int res3 = helper(a,indexa+1,b,indexb,mem)+1;

            res = Math.min(res1,Math.min(res2,res3));
        }

        mem.put(key,res);
        return res;

    }


    public static int dp_solution(String a,String b){
        int m = a.length();
        int n = b.length();
        int[][] dp =new int[m+1][n+1];
        //bc the base case starts from index -1, set offset to 1
        int offset = 1;

        //init
        for(int i=1;i<m+1;i++){
            dp[i][0]=i;
        }
        for(int i=1;i<n+1;i++){
            dp[0][i]=i;
        }

        for(int i=1;i<m+1;i++){ //can do space compression to keep one row only//imagine the table and the flow of index
            for(int j=1;j<n+1;j++){
                if(a.charAt(i-offset)==b.charAt(j-offset)){
                    dp[i][j]= dp[i-1][j-1];
                }else{
                    dp[i][j]= Math.min(dp[i-1][j-1]+1,dp[i][j-1]+1);//replace or delete char at j
                    dp[i][j] = Math.min(dp[i][j],dp[i-1][j]+1);//insert char at j+1 to match i
                }
            }
        }


        return dp[m][n];



    }

    public static int tabulation_spaceCompression(String a, String b){
        int m = a.length();
        int n = b.length();
        int offset  = 1;
        int[] dp = new int[n+offset];
//        int[] prev = dp ;// wrong, point to same obj
//        int[] prev = new int[n+offset];
        //init prev;
        for(int j=0;j<=n;j++){
            dp[j]=j;
        }

        for(int i = offset; i<m+offset;i++){
            int index1 = i-offset;
//            dp[0] = index1+1;
            int[] next = new int[n+1];//use next to avoid copy of array
            next[0] = index1+1;
            for(int j=offset; j<(n+offset);j++){
                int index2 = j-offset;
                if(a.charAt(index1)==b.charAt(index2)){
                    next[j] = dp[j-1];
                }else{
                    next[j]= Math.min(next[j-1]+1,dp[j]+1);
                    next[j] = Math.min(next[j],dp[j-1]+1);
                }


            }
            //prev = dp; //wrong!!!!!!!!!!!!!!!!! , point to the same obj
//            prev=Arrays.copyOf(dp,dp.length);
            //or to avoid copy use next[]
            dp=next;

        }
        return dp[n];

    }
    public static void main(String[] args){
        String a = "abcd";
        String b = "abac";

        System.out.println(solution(a,b));
        System.out.println(dp_solution(a,b));
        System.out.println(tabulation_spaceCompression(a,b));
        System.out.println( call);
    }
}
