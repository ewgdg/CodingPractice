package array.stack;

import java.util.Stack;

public class LargestRectInHistogram {
    // Complete the largestRectangle function below.
    static long largestRectangle(int[] h) {
        //use monotone queue to find the next smaller col
        // 1 2 3 4 5
        // 1 3 1
        // 2 4 5 3 1

        //special case
        // 2 4 3 3 3 4 2!!!! , need to find both the prev smaller and next smaller

        Stack<Integer> stack = new Stack<>();
        int n  = h.length;
        long max=0;
        for(int i =0;i<n;i++){

            while(!stack.isEmpty() && h[stack.peek()]>h[i] ){
                int height = h[stack.pop()];
                int prev =stack.isEmpty()?-1: stack.peek();
                max= Math.max(max, (i-prev-1)*height  );
            }

            stack.add(i);
        }

        while(!stack.isEmpty()){
            int height = h[stack.pop()];
            int prev =stack.isEmpty()?-1: stack.peek();
            max = Math.max(max, (n-prev-1)*height );
            // System.out.println(index);
        }
        return max;
    }
}
