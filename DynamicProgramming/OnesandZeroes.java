import java.util.HashMap;

public class OnesandZeroes {

    //Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
    //Output: 4
    //
    //Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”


    public int findMaxForm(String[] strs, int m, int n) {
        int[][] counter = new int[strs.length][];
        int index=0;
        for(String s:strs){
            counter[index]=count(s);
            index++;
        }

        return helper(strs,0,m,n,counter,new HashMap<>());

    }

    static int call=0;
    static int tot=0;
    static int calc=0;
    public int helper(String[] strs, int index, int m , int n , int[][] counter, HashMap<String, Integer> mem){

        tot++;
        //terminating cond
        if(index>=strs.length){
            return 0;
        }
        if(m<0 || n<0){
            return 0;
        }
        if(m==0 && n==0){
            return 0;
        }

        String key = index+":"+m+":"+n;
        if(mem.containsKey(key)){
            call++;
            return mem.get(key);
        }



        String cur = strs[index];

        int res = 0;
        int sub1 = helper(strs,index+1,m,n,counter,mem);
        int[] num = counter[index];//
        calc++;

        int sub2= 0;
        if(m>=num[0] && n>=num[1])
            sub2 = helper(strs,index+1, m-num[0],n-num[1],counter,mem  )+1;//take that string

        res = Math.max(sub1,sub2);
        mem.put(key,res);
        return res;
    }


    public int[] count(String str){//we can precompute the 0s and 1s
        int count0=0;
        int count1=0;
        for(char c:str.toCharArray()){
            if(c=='0') count0++;
            else count1++;
        }
        return new int[]{count0,count1};
    }


    public int tabulation(String[] strs, int m , int n){

//        dp[size][m][n] = dp[size-1][m][n] || dp[size-1][m-num[0]][n-nums[1]];

        int[][] counter = new int[strs.length][];
        int si=0;
        for(String s:strs){
            counter[si]=count(s);
            si++;
        }

        int[][] dp = new int[m+1][n+1];
        dp[0][0]=0;
        //init


        for(int size=1; size<=strs.length; size++){
            int index = size-1;
            int[][] nextdp = new int[m+1][n+1];
            int[] nums =counter[index];
            for(int i=0;i<=m;i++){
                for(int j=0;j<=n;j++){

                    if(i>=nums[0] && j>=nums[1]){
                        nextdp[i][j] = dp[i-nums[0]][j-nums[1]]+1;//take the  str
                    }
                    nextdp[i][j] = Math.max(nextdp[i][j],dp[i][j]);
                }
            }
            dp = nextdp;
        }

        return dp[m][n];


    }


    public static void main(String[] args){

        OnesandZeroes solver = new OnesandZeroes();

        System.out.println(solver.findMaxForm(new String[]{"10","0001","111001","1","0","10", "0", "1","10","0001","111001","1","0","10","0001","111001","1","0","10", "0", "1","10","0001","111001","1","0"},100,127));
        System.out.println( call);
        System.out.println(tot);
        System.out.println(calc);
        System.out.println(solver.tabulation(new String[]{"10","0001","111001","1","0","10", "0", "1"},1,1));
    }


}
