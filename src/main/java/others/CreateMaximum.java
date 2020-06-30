package others;
import java.lang.reflect.Array;
import java.util.Arrays;

public class CreateMaximum {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {


        int n = nums1.length;
        int m = nums2.length;
        int[] res = new int[k];
        for(int i = Math.max(0,k-m);i<= n && i<=k ;i++ ){
            int j = k-i;
            int[] max1 = findMaximum(nums1,i);
            System.out.println(Arrays.toString(max1));

            int[] max2 = findMaximum(nums2,j);
            System.out.println(Arrays.toString(max2));

            int[] ret = merge(max1,max2,k);

            if(compare(ret,0,res,0)>0){
                res = ret;
            }

        }
        return res;


    }

    public int[] findMaximum( int[] nums , int k){

        //find k largest ele while mantain the relative order
        int n = nums.length;
        if(n<=k) return nums;

        int[] res = new int[k]; // use this as a monotone queue/stack
        int index=0; //point to the top of the queue
        for(int i=0;i<n;i++){
            int cur = nums[i];
            while( index>0 && cur>res[index-1] &&  (n-(i+1))  >= (k-(index))  ){ //while larger cur pop, when there is enough ele left
                index--; //pop from the monotone queue/stack
            }
            if(index<k)
                res[index++] = nums[i];

        }
        return res;
    }

    public int compare(int[] nums1,int i1, int[] nums2, int i2){
        int compare =0;
        int n = nums1.length-i1;
        int m = nums2.length-i2;

//        if(n<m) return -1;
//        else if(n<m) return 1;
        int i=0;
        for( i=0;i<Math.min(m,n);i++){
            if(nums1[i+i1]!=nums2[i+i2] ){
                return nums1[i+i1]-nums2[i+i2];

            }

        }
        if(i<n) compare =1;
        else if(i<m) compare= -1;
        return compare;
    }
    public int[] merge(int[] nums1, int[] nums2, int k){
        //merge to find k largest elem
        int[] res = new int[k];

        int i1=0;
        int i2=0;
        int n1 = nums1.length;
        int n2= nums2.length;
        for(int i=0;i<k;i++){
            if(i1>=n1){
                res[i]=nums2[i2++];
            }else if(i2>=n2){
                res[i]=nums1[i1++];
            }else{
                if(compare(nums1,i1,nums2,i2)>0){
                    res[i]=nums1[i1++];
                }else{
                    res[i]=nums2[i2++];
                }
            }
        }
        return res;
    }
    public static void main(String[] args){

        int[] nums1 = new int[]{6,3,9,0,5,6};
        int[] nums2 = new int[]{2,2,5,2,1,4,4,5,7,8,9,3,1,6,9,7,0};
        CreateMaximum solver =new CreateMaximum();


        System.out.println(Arrays.toString( solver.maxNumber(nums1,nums2,23) ));

    }
}
