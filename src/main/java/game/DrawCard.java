package game;

import java.util.Arrays;
import java.util.HashMap;

public class DrawCard {

    //一道DP的题目。玩卡牌，N张卡，卡上有数字，可正可负。两个玩家，每个人最多可以选1，2或3张牌，自己先开始，问最多能获得的分数是多少，score就是player选择的卡上数字之和。

    //able to simplified further bc players1 and player2 are reverse of each other, greedy?
    public static int[] helper(int[] nums, int index, int player, HashMap<String,int[]> mem){
        //terminating
        if(index>=nums.length){
            return new int[]{0,0};
        }
        String key = index+":"+player;
        if(mem.containsKey(key)) return mem.get(key);
        int[] res = new int[]{Integer.MIN_VALUE,Integer.MIN_VALUE};
        int temp=nums[index];
        int[] sub1 = helper(nums,index+1,player^1,mem);
        if(sub1[player]+temp>res[player]) {
            res = Arrays.copyOf(sub1, sub1.length);
            res[player]=sub1[player]+temp;
        }

        int[] sub2 = helper(nums,index+2,player^1,mem);
        if(index+1<nums.length) {
            temp += nums[index + 1];
            if (sub2[player] + temp > res[player]) {
                res = Arrays.copyOf(sub2, sub2.length);
                res[player] = sub2[player] + temp;
//            System.out.println(Arrays.toString(sub2));
            }
        }
        int[] sub3 = helper(nums,index+3,player^1,mem);
        if(index+2<nums.length) {
            temp += nums[index + 2];
            if (sub3[player] + temp > res[player]) {
                res = Arrays.copyOf(sub3, sub3.length);
                res[player] = sub3[player] + temp;

            }
        }


        mem.put(key,res );
//        System.out.println(Arrays.toString(res));
        return res;



    }
    //further simplified without player id parameter
    public static int[] helper2(int[] nums, int index, HashMap<String,int[]> mem){
        //terminating
        if(index>=nums.length){
            return new int[]{0,0};
        }
        String key = String.valueOf(index);
        if(mem.containsKey(key)) return mem.get(key);
        int[] res = new int[]{Integer.MIN_VALUE,Integer.MIN_VALUE};
        int temp=nums[index];
        //res 0 indicates cur player. 1 indicates another player. the subproblem get the reversed meaning for cur problem
        int[] sub1 = helper2(nums,index+1,mem);
        if(sub1[1]+temp>res[0]) {
            res[0] = sub1[1]+temp;
            res[1]= sub1[0];
        }

        int[] sub2 = helper2(nums,index+2,mem);
        if(index+1<nums.length) {
            temp += nums[index + 1];
            if (sub2[1] + temp > res[0]) {
                res[0] = sub2[1] + temp;
                res[1] = sub2[0];
//            System.out.println(Arrays.toString(sub2));
            }
        }
        int[] sub3 = helper2(nums,index+3,mem);
        if(index+2<nums.length) {
            temp += nums[index + 2];
            if (sub3[1] + temp > res[0]) {
                res[0] = sub3[1] + temp;
                res[1] = sub3[0];

            }
        }


        mem.put(key,res );
//        System.out.println(Arrays.toString(res));
        return res;
    }

    public static int[] tabulation(int[] nums){
        //draw from last is differ from drawing from first, when build the table it is important to start from smallest subproblem drawing from last
        //such that the whole prob is drawing from first

//        int[][] dp = new int[nums.length][2];
        //space compression
        int[][] dp = new int[2][2];
        int[][] prev = new int[2][2];
        int[][] pprev = new int[2][2];
        int[][] ppprev = new int[2][2];

        for(int size=1;size<=nums.length;size++){
            int index=nums.length-size;


            dp = new int[2][2];
            for(int player =0; player<=1;player++){
                int temp = nums[index];
                int[] res = new int[]{Integer.MIN_VALUE,Integer.MIN_VALUE};
                int nextPlayer = player^1;
                if(prev[nextPlayer][player] + temp > res[player]){
                    res = Arrays.copyOf(prev[nextPlayer], prev[nextPlayer].length);
                    res[player]=prev[nextPlayer][player]+temp;
                }
                if(index+1<nums.length) {
                    temp += nums[index + 1];
                    if (pprev[nextPlayer][player] + temp > res[player]) {
                        res = Arrays.copyOf(pprev[nextPlayer], pprev[nextPlayer].length);
                        res[player] = pprev[nextPlayer][player] + temp;
                    }
                }
                if(index+2<nums.length) {
                    temp += nums[index + 2];
                    if (ppprev[nextPlayer][player] + temp > res[player]) {
                        res = Arrays.copyOf(ppprev[nextPlayer], ppprev[nextPlayer].length);
                        res[player] = ppprev[nextPlayer][player] + temp;
                    }
                }
                dp[player]=res;
            }
//            System.out.println(Arrays.toString(dp[0])+":" +Arrays.toString(dp[1]));
            ppprev = pprev;
            pprev= prev;
            prev = dp;

        }
        return dp[0];



    }

    public static int[] solve(int[] nums){
        HashMap<String, int[]> mem = new HashMap<>();
        int[] res= helper2(nums,0,mem);

        return res;
    }


    public static void main(String[] args){
        int[] nums = new int[]{1,2,-6,-3};
//        int[] nums = new int[]{1,2,-6,7,3,4,9,-1,8,-5,-3,4};
        // int[] nums2 = new int[]{4,-3,-5,8,-1,9,4,3,7,-6,2,1};
        System.out.println(Arrays.toString(solve(nums) ));
        System.out.println(Arrays.toString(tabulation(nums)));

    }
}
