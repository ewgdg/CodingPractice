package others;
import java.util.Arrays;

public class Smallest_RangeII {
    public static int smallestRangeII(int[] A, int K) {
        //want to make it balanced as possible
        //2 pointer
        int left=0;
        int right=A.length-1;
        Arrays.sort(A);

        int res=0;
        int min=A[A.length-1]+K;
        int max = A[0]-K;
        while(left<right){
            if(A[left]+K < A[right]-K){
                            //return A[right]-A[left]-K-K;
                min=Math.min(min,A[left]+K);
                max=Math.max(max,A[right]-K);
            }else if(A[right]-A[left] <= K ){
                return A[right]-A[left];
            }
            else{
                min=Math.min(min,A[right]-K);
                max=Math.max(max,A[left]+K);
            }
            left++;
            right--;
        }

        if(left==right){
            int num= A[left];
            if(Math.abs(num-min) == Math.abs(max-num)){
                if(min>=num){
                    max =Math.max(max,num+K);
                }else{
                    min = Math.min(min,num-K);
                }
            }
            else if( Math.abs(num-min) > Math.abs(max-num) ){
                min = Math.min(min,num-K);
            }else{
                max =Math.max(max,num+K);
            }

        }


        return max-min;
    }

    public static void main(String[] args){

        System.out.println( smallestRangeII(new int[]{3,1,10},4));
    }
}
