package Array.Stack;

import java.util.Arrays;
import java.util.Stack;

public class MinMaxRiddle {

    //for sliding window size = 1 to n
    //find min for each window, and find max among all such min, store into res[size]



    static long[] riddle(long[] arr) {
        // complete this function

        // O n
        //find min for prev smaller and next smaller
        // so we know the min between prev and next ,  for each i, len=prev-next-1,  update res[len] =Math.max(res,arr[i]);

        //corner case
        //some len cannot have such a min
        //update from other res
        //res[len] = max (res[len],res[len+1])

        Stack<Integer> monotone  =new Stack<>();
        int n = arr.length;
        int[] prev= new int[n];
        int[] next= new int[n];
        Arrays.fill(prev,-1);
        Arrays.fill(next,n);
        long[] res= new long[n];
        for(int i =0;i<n;i++){
            while(!monotone.isEmpty()  &&  arr[ monotone.peek() ] > arr[i]   ){
                int pop = monotone.pop();
                next[pop]=i;
            }
            while(!monotone.isEmpty()  &&  arr[ monotone.peek() ] == arr[i]   ){
                monotone.pop();
            }

            if(!monotone.isEmpty())
                prev[i]= monotone.peek();

            monotone.add(i);

        }

        for(int i =0;i<n;i++){
            long num=arr[i];
            int prev_num = prev[i];
            int next_num= next[i];

            int len  = next_num-prev_num-1;
            res[len-1] = Math.max(res[len-1],num);

        }

        // System.out.println(Arrays.toString(prev));
        for(int i=n-2;i>=0;i--){
            res[i]=Math.max(res[i],res[i+1]);
        }

        return res;







        //n^2
        // //monotone queue to find mini for subarray


        // int n = arr.length;
        // long[] res = new long[n];
        // for(int size=1; size<=n;size++){
        //     long max = 0;
        //     Deque<Long> monotone = new LinkedList<>();
        //     for(int i=0;i<n;i++){
        //         long num = arr[i];

        //         while(!monotone.isEmpty() &&  monotone.peekLast()>num  ){
        //             monotone.removeLast();
        //         }
        //         monotone.addLast(num);
        //         if(  i>=size  ){
        //             long pop = arr[i-size];
        //             if(!monotone.isEmpty() && monotone.peekFirst()==pop ){
        //                 monotone.removeFirst();
        //             }

        //         }
        //         // System.out.println(monotone.peekFirst());
        //         if(i+1>=size)
        //             max = Math.max(max,monotone.peekFirst());

        //     }
        //     res[size-1]=max;
        // }
        // return res;



    }
}
