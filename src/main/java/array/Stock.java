package Array;

public class Stock {
    //only one transaction

    //The points of interest are the peaks and valleys in the given graph. We need to find the largest peak following the smallest valley.

    public static int maxProfit(int[] prices) {

        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice)
                minprice = prices[i];
            else if (prices[i] - minprice > maxprofit)
                maxprofit = prices[i] - minprice;
        }
        return maxprofit;

    }


}
