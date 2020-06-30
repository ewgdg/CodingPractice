package array;

import java.util.Random;

public class randBasdOnWeight {
    int sum;
    int[] prefixSum;
    int n;
    Random rand;
    public randBasdOnWeight(int[] w) {
        n=w.length;
        sum=0;
        rand = new Random();
        prefixSum = new int[n];

        for(int i=0;i<n;i++){
            sum+=w[i];
            prefixSum[i]=sum;
        }

    }

    public int pickIndex() {
        int x = rand.nextInt(sum);
        int i = binarySearch(x);
        return i;
    }

    //or use treeMap
    public int binarySearch(int x){
        int lo = 0;
        int hi = n-1;
        while(lo<hi){
            int mid = lo+ (hi-lo)/2;//offset mid to right
            if(prefixSum[mid]<=x){ //exclusive prefixSUm
                lo=mid+1;
            }else{
                hi=mid;
            }
        }
        // assert(prefixSum[lo]>=x && (lo-1 <0 || prefixSum[lo-1] < x) );
        return lo;
    }
    public int binarySearch2(int x){
        int lo =0;
        int hi = n-1;
        while(lo<=hi){
            int mid = lo + (hi-lo)/2;
            if(prefixSum[mid]<x){
                lo=mid+1;
            }else if(prefixSum[mid]==x){ //exclusive prefixSUm
                lo=mid+1;
            }else{
                hi= mid-1;
            }
        }
        return lo;//want the find the larger val than target

    }
}
