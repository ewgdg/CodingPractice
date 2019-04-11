package Stack;
//Alexa has two stacks of non-negative integers, stack  and stack  where index denotes the top of the stack. Alexa challenges Nick to play the following game:
//
//In each move, Nick can remove one integer from the top of either stack  or stack .
//Nick keeps a running sum of the integers he removes from the two stacks.
//Nick is disqualified from the game if, at any point, his running sum becomes greater than some integer x given at the beginning of the game.
//Nick's final score is the total number of integers he has removed from the two stacks.
//Given , , and  for  games, find the maximum possible score Nick can achieve (i.e., the maximum number of integers he can remove without being disqualified) during each game and print it on a new line.
public class TwoStack {
    static int twoStacks(int x, int[] a, int[] b) {
        /*
         * Write your code here.
         */

        // greedy strategy doesnt work
        //dp??too complicated
        //sliding windows!!!! across stack a and stack b
        //bounding point method

        int sum=0;
        int res=0;
        int left =-1;
        int right=-1;
        for (int i =0;i<a.length;i++ ){
            if(sum+a[i]<=x){
                sum+=a[i];
                left=i;
                // System.out.println(x);
            }else{
                break;
            }
        }
        //  System.out.println(left);
        res = Math.max(res,left+1);
        for(int i=0;i<b.length;i++  ){

            sum+=b[i];
            while(sum>x && left>=0){
                sum-=a[left--];
            }
            right=i;
            if(sum>x){
                break;
            }
            res = Math.max(res,left+right+1+1);
        }
        return res;


    }
}
