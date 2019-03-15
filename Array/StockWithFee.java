public class StockWithFee {
    //Your are given an array of integers prices, for which the i-th element is the price of a given stock on day i; and a non-negative integer fee representing a transaction fee.
    //
    //You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction. You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share before you buy again.)
    //
    //Return the maximum profit you can make.

    //Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
    //Output: 8
    //Explanation: The maximum profit can be achieved by:
    //Buying at prices[0] = 1
    //Selling at prices[3] = 8
    //Buying at prices[4] = 4
    //Selling at prices[5] = 9
    //The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.


    //previously we can buy any vally price
    //but now we cannot , if the profit is smaller than the fee
    //need dp

    //final state
    public int maxProfit(int[] prices, int fee) {
        return helper(prices,fee,prices.length-1).cash;
    }

    class Result{
        int hold;//max money if we bought/hold a stock
        int cash;//max profit if we sold a stock
        public Result(int hold, int cash){
            this.hold= hold;
            this.cash= cash;
        }
    }
    public Result helper(int[] prices, int endIndex, int fee ){//bc the time flow from day1 to end,  recursive helper must start from end index final state
        if(endIndex==0){
            return new Result(0-prices[0],0);
        }

        Result prevRes = helper(prices,endIndex-1,fee);

        //2 options

        //option1 :
        //buy cur stock instead of prev stock if this gives more fund as holding a stock
        int hold = Math.max(prevRes.cash-prices[endIndex], prevRes.hold);

        //opt2:
        //sell a stock if this give more cash
        int cash = Math.max(prevRes.hold+prices[endIndex]-fee, prevRes.cash);

        //option3:
        //do nothing so cash and hold maintains the same, done in Math.max step


        return new Result(hold,cash);
    }

    public int maxProfit_tabulation(int[] prices, int fee) {
        int cash=0; int hold = 0-prices[0];
        for(int i=0;i<prices.length;i++){
            int cur_cash = Math.max(cash,hold+prices[i]-fee);
            hold = Math.max(hold, cash-prices[i] ); //here the old cash might be updated, but the res wont be affected bc the updated cash is always larger then old hold. and updated cash-price is always smaller than the old hold , so the old cash is the hold is unchanged if cash is updated.
            cash=cur_cash;

        }
        return cash;
    }



}
