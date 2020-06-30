package array;

public class StockWithCooldown {
    //Say you have an array for which the ith element is the price of a given stock on day i.
    //
    //Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
    //
    //You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
    //After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)


    //again with greedy state info
    //buy: money if cur day buying one stock /holding a stock
    //sell : if cur day selling it
    //cooldown : if cur day is cooldown



    public static int maxProfit(int[] prices) {
        int n = prices.length;
        if(n==0) return 0;

        int buy = 0-prices[0]; //init capital/sell is 0, 0-price ~= int.min_val so we always pick the first price if we never buy , or use null as init val;
        int sell = 0;
        int cooldown = 0;
        int max=0;
        for(int price: prices){ // can start from index 1
            int cur_buy = Math.max(buy,cooldown-price);
            int cur_sell = Math.max(sell,buy+price);
            int cur_cooldown = Math.max(cooldown,sell);//rest from prev cooldown or from prev sell

            sell=cur_sell;
            buy=cur_buy;
            cooldown=cur_cooldown;

            max= Math.max(sell,max);
        }
        return max;

    }

    public static void main(String[] args){
        System.out.println(maxProfit(new int[]{1,2,3,0,2}));//3
    }
}
