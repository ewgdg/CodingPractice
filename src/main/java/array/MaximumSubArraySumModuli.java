package array;
import java.util.TreeSet;

public class MaximumSubArraySumModuli {
//Given an  element array of integers, , and an integer m, determine the maximum value of the sum of any of its subarrays modulo m.
// For example, Assume  [1 2 3 ]and  2 . The following table lists all subarrays and their moduli:
//
//		sum	%2
//[1]		1	1
//[2]		2	0
//[3]		3	1
//[1,2]		3	1
//[2,3]		5	1
//[1,2,3]	6	0
    static long maximumSum(long[] a, long m) {
        //prefix sum -> pref i- prefj ) %m
        // pref i %m - pref j %m
        //equilvalent to max subarray
        int n =  a.length;

        a[0] = a[0]%m;
        //find the prefixSum%m
        for(int i =1;i<n;i++){
            a[i]= (a[i-1]+a[i]%m )%m;
        }
        long res=0;
        long min=0;
        TreeSet<Long> treeset = new TreeSet<>();
        treeset.add(0l);
        for(int i =0;i<n;i++){ //i>j
            long temp = a[i]-min; //case 1  pref i > pref j
            if(treeset.higher(a[i]) != null){
                temp = Math.max(temp,a[i] - treeset.higher(a[i]) + m ); //case 2 pref i < prefj
            }
            min = Math.min(a[i],min);
            treeset.add(a[i]);
            res = Math.max(temp,res);
        }
        return res;




    }
}
