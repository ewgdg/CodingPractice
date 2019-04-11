package Stack;

import java.util.Stack;

public class AndXorOr {

    //find the maximum S in any interval [L,R]
    //where S = ((a&b)^(a|b))&(a^b);  a is smallest elem in the interval , b is the second smallest elem

    static int op(int a, int b){
        // return ((a&b)^(a|b))&(a^b);
        //simplify the exp to a^b
        return a^b;
    }
    static int andXorOr(int[] a) {
        /*
         * Write your code here.
         */


        // 1 2 3 4
        //many sub interval share the same pair
        //find pair directly

        //assume a[i] is second smallest, we just need to find the smallest pair
        //from left smaller or right smaller
        //monotone stack!!
        int n =a.length;
        if(n<2) return 0;

        Stack<Integer> stack = new Stack<>();
        int res = op(a[0],a[1]);
        for(int num:a){

            while(!stack.isEmpty() && stack.peek()>num   ){
                res = Math.max(res, op(num,stack.pop())   );
            }
            if(!stack.isEmpty())
                res = Math.max(res, op(stack.peek(),num));
            // while(!stack.isEmpty() &&  stack.peek()==num){
            //     res = Math.max(res, op(num,stack.pop());
            // }
            stack.add(num);

        }
        return res;

    }
}
