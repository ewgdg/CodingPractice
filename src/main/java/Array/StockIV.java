package Array;

public class StockIV {
    //Say you have an array for which the ith element is the price of a given stock on day i.
    //
    //Design an algorithm to find the maximum profit. You may complete at most k transactions.
    //Input: [2,4,1], k = 2
    //Output: 2
    //Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.


    //The idea is to use state machine？？？？  i think pipeline greedy state is a better name
    // to keep track of every possible profit
    //For one transaction, we have two states: buying and selling
    //For every state: we could stay, or buying/selling, but we should choose the maximum profit way
    //For buying: we will pay stock price so profit -= stock price from last selling state
    //For selling: we will earn money so profit += stock price from last buying state
    //For rest: we remain same profit

    //another method is dp
//    the diff between dp and greedy is that more state info we can get from greedy previous
    //e.g buy1/hold1 sell1/release1 buy2 sell2
    //but in dp we store only profit at jth so that we need more step to get other info

    public int maxProfit(int k, int[] prices) {
        if(k<=0||prices.length<=1) return 0;
        Integer[] release = new Integer[k+1];
        Integer[] hold = new Integer[k+1];

        release[0]=0;
        hold[0] = 0;//useless

        int max=0;
        for(int price: prices){

            for(int i =k ; i>=1 ;i--){//reversed order to avoid create cur temp array
                Integer cur_hold=hold[i];
                Integer cur_release=release[i];
                if(release[i-1]!=null) {
                    if (cur_hold == null) {
                        cur_hold = release[i - 1] - price;
                    } else {
                        cur_hold = Math.max(cur_hold, release[i - 1] - price);
                    }
                }
                if(hold[i]!=null){
                    if(cur_release==null){
                        cur_release=hold[i]+price;
                    }else{
                        cur_release=Math.max(cur_release,hold[i]+price);
                    }
                }
                release[i]=cur_release;
                hold[i]=cur_hold;

                if(release[i]!=null)
                    max = Math.max(max,release[i]);
            }


        }

        return max;


    }
}
