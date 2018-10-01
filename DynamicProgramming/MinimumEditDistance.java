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

        //init
        for(int i=1;i<m+1;i++){
            dp[i][0]=i;
        }
        for(int i=1;i<n+1;i++){
            dp[0][i]=i;
        }

        for(int i=1;i<m+1;i++){ //can do space compression
            for(int j=1;j<n+1;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j]= dp[i-1][j-1];
                }else{
                    dp[i][j]= Math.min(dp[i-1][j-1]+1,dp[i][j-1]+1);//replace or delete char at j
                    dp[i][j] = Math.min(dp[i][j],dp[i-1][j]+1);//insert char at j+1 to match i
                }
            }
        }


        return dp[m][n];



    }
    public static void main(String[] args){
        String a = "abcd";
        String b = "abac";

        System.out.println(solution(a,b));
        System.out.println(dp_solution(a,b));
        System.out.println( call);
    }
}