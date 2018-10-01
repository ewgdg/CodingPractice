import java.util.HashMap;

public class buttonQuestion_similarToSnackpack {

    //这一轮是最难的，可以说是唯一一道hard级别的题吧，所以我特意设置低一点的积分，希望更多的人能看到。
    //可乐饮料机，有一系列按钮，每个按钮按下去会得到一定体积范围的可乐。先给定一个目标体积范围，问不限制按按钮次数，能否确定一定能得到目标范围内的可乐？
    //举例：有三个按钮，按下去得到的范围是[100, 120], [200, 240], [400, 410],
    //假设目标是[100, 110], 那答案是不能。因为按下一，可能得到120体积的可乐，不在目标范围里。
    //假设目标是[90, 120]，那答案是可以。因为按下一，一定可以得到此范围内的可乐。
    //假设目标是[300, 360], 那答案是可以，因为按下一再按二，一定可以得到此范围内
    //假设目标是[310, 360], 那答案是不能，因为按下一再按二，有可能得到300，永远没可能确定得到这个范围内的可乐。
    //假设目标是[1, 9999999999]，那答案是可以。随便按一个都确定满足此范围。
    //lz两眼一懵，真的做的快出汗了，一直试图跟面试官讲自己的思路，然后发现每个思路都有问题，最后在面试官提示下想到了dp，然后又在提示下想到了recursion，最后终止条件实在是快想不出来了疯狂举例要hint，最后终于被面试官带着做出来了。
    //这道题真的很有趣，但是当时脑子很僵又是最后一轮，感觉很崩。

    static class Range{
        int lo;
        int hi;
        public Range(int lo, int hi){
            this.lo=lo;
            this.hi=hi;
        }

        @Override
        public boolean equals(Object o){
            Range that =  (Range)o;
            return that.lo==this.lo && that.hi==this.hi;
        }

        @Override
        public int hashCode(){
            return lo*31+hi;
        }
    }

    public static boolean solution(int[][] buttons, int[] target ){
        Range range =  new Range(target[0],target[1]);
        HashMap<Range,Boolean> mem = new HashMap<>();


        return dfs(buttons,range,mem);

    }
    public static boolean dfs(int[][] buttons, Range target, HashMap<Range,Boolean> mem){
        if(mem.containsKey(target)) return mem.get(target);

        //terminating cond
        if(target.hi<0) return false;

        if(target.lo<=0 && target.hi>=0) return true; //obj>=lo , obj<=hi

        boolean status=false;
        for(int i=0;i<buttons.length;i++){

            Range newTarget = new Range(target.lo-buttons[i][0],target.hi-buttons[i][1] );
            if( dfs(buttons, newTarget, mem) ){
                status=true;
                return true;
            }


        }

        mem.put(target,status);
        return status;





    }

    public static boolean tabulationSolution(int[][] buttons, int[] target){


        boolean[][] dp = new boolean[target[0]+1][target[1]+1]; //dp[i][j]= dp[i-button][j-button] || dp[i][j]

        dp[0][0]=true;

        for(int i=0;i<=target[0];i++){
            for(int j=i;j<=target[1];j++){
                for(int[] button:buttons){
                    if(i-button[0]>=0 && j-button[1]>=0 ){
                        dp[i][j] = dp[i][j]|| dp[i-button[0]][j-button[1]];
                        if(dp[i][j] && i>=target[0]) return true;//dont need exactly match, just within
                    }

                }

            }

        }

        return false;

    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        for(int i=0;i<1e6;i++)
            solution( new int[][]{{100, 120},{200, 240},{400, 410}} , new int[]{300, 360} );
        System.out.println(System.currentTimeMillis()-start);

        System.out.println( solution( new int[][]{{100, 120},{200, 240},{400, 410}} , new int[]{300, 360} ));
        System.out.println( tabulationSolution( new int[][]{{100, 120},{200, 240},{400, 410}} , new int[]{300, 360} ));

    }
}
