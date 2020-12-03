package online;

import java.util.Random;


//online algorithm
public class reservoirSampling {
    //from a large stream
    public static int[] solve(int[] nums, int k){
        int[] res = new int[k];

        Random rand = new Random();

        int index=0;
        for(int num:nums){
            if(index<k){
                res[index]=num;
            }else{
                int p = rand.nextInt(index);
                if(p<k) res[p]=num;
            }
            index++;
        }
        return res;
    }

    //Case 1: For last n-k stream items, i.e., for stream[i] where k <= i < n
    //prob of picking ith elem at the end= k/i*(1-1/(i+1))*... *1/k= 1/n
    //prob of each item in reservoir at the end is k/n

    //prob of ith item in reservoir at ith iter = k/i 
    //prob of that item not being removed at i+1 = 1- 1/(i+1)
    //prob of ith item in reservoir at i+1 = k/i* (1-1/(i+1)) = k/(i+1)
    // ...
    //                              at n = k/n


    //Case 2: For first k stream items, i.e., for stream[i] where 0 <= i < k
    // The first k items are initially copied to reservoir[] and may be removed later in iterations for stream[k] to stream[n].
    // The probability that an item from stream[0..k-1] is in final array = Probability that the item is not picked when items 
    // stream[k], stream[k+1], …. stream[n-1] are considered = [k/(k+1)] x [(k+1)/(k+2)] x [(k+2)/(k+3)] x … x [(n-1)/n] = k/n

    //ReservoirSample(S[1..n], R[1..k])
    //  // fill the reservoir array
    //  for i = 1 to k
    //      R[i] := S[i]
    //
    //  // replace elements with gradually decreasing probability
    //  for i = k+1 to n
    //    j := random(1, i)   // important: inclusive range
    //    if j <= k
    //        R[j] := S[i]
}
