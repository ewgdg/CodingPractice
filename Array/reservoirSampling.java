import java.util.Random;

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

    //prob of picking ith elem = k/i*(1-1/(i+1))*... = 1/n
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
