import java.util.Arrays;

public class StockIII {

//    Say you have an array for which the ith element is the price of a given stock on day i.
//
//    Design an algorithm to find the maximum profit. You may complete at most two transactions.
//
//            Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again

    //Input: [3,3,5,0,0,3,1,4]
    //Output: 6
    //Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
    //             Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.


    public static int maxProfit(int[] prices) {
        int released1=0,released2=0; int hold1=0-prices[0]; int hold2=released1-prices[0];


        for(int p:prices){
            //or use a loop if more than 2
            int cur_released1= Math.max(released1,hold1+p);
            int cur_hold1= Math.max(hold1,0-p);//released0=0;

            int cur_released2 = Math.max(released2,hold2+p);
            int cur_hold2 = Math.max(hold2,released1-p);

            released1=cur_released1;
            released2=cur_released2;
            hold1=cur_hold1;
            hold2=cur_hold2;

        }
        return Math.max(released1,released2);

    }
    class Result{

        int cash;
        int hold;
        public Result(int hold, int cash){
            this.hold=hold; this.cash=cash;
        }

    }




















    //naive recursive+mem  N^2

//    public static int maxProfit(int[] prices) {
//        int n = prices.length;
//        global_max=0;
//        Integer[][][] mem = new Integer[n][2][2+1];
//        //3 para : starting index, buy or sell, 1st or 2nd/remaining times
//        helper(mem,0,prices,0,2);
////        System.out.println(mem[6][0][1]);
//        return global_max;
//
//    }
//
//    static int global_max=0;
//    public static int helper(Integer[][][] mem, int statring_index, int[] prices ,int buyOrSell, int remainingTimes ){
//        if(remainingTimes == 0 || statring_index>=prices.length){
//            return 0;
//        }
//
//        if(mem[statring_index][buyOrSell][remainingTimes]!=null){
//            return (mem[statring_index][buyOrSell][remainingTimes]);
//        }
//        int res = 0;
////        System.out.println(index+" "+buyOrSell+" "+remainingTimes);
//
//        int nextRemainingTimes = buyOrSell==1?remainingTimes-1:remainingTimes;
//        for(int i = statring_index; i<prices.length; i++){
//
//            int temp = helper(mem,i+1,prices,buyOrSell^1,nextRemainingTimes);
//            if(buyOrSell==1){
//                //it is a sell
//                int prevProfit = prices[i] - prices[statring_index-1];
//                temp =Math.max(prevProfit+temp,prevProfit);
//            }
//            res = Math.max(res,temp);
//        }
//        mem[statring_index][buyOrSell][remainingTimes] = res;
//        global_max = Math.max(res, global_max);
//        return res;
//
//    }




    public static void main(String[] args){
        System.out.println(maxProfit(new int[]{3,3,5,0,0,3,1,4}));
    }

}
