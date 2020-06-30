package array;
import java.util.Arrays;

public class MinimumSwap2 {


    //You are given an unordered array consisting of consecutive integers  [1, 2, 3, ..., n] without any duplicates. You are allowed to swap any two elements. You need to find the minimum number of swaps required to sort the array in ascending order.
    //
    //For example, given the array  we perform the following steps:
    //
    //i   arr                         swap (indices)
    //0   [7, 1, 3, 2, 4, 5, 6]   swap (0,3)
    //1   [2, 1, 3, 7, 4, 5, 6]   swap (0,1)
    //2   [1, 2, 3, 7, 4, 5, 6]   swap (3,4)
    //3   [1, 2, 3, 4, 7, 5, 6]   swap (4,5)
    //4   [1, 2, 3, 4, 5, 7, 6]   swap (5,6)
    //5   [1, 2, 3, 4, 5, 6, 7]
    //It took  swaps to sort the array.

    // Complete the minimumSwaps function below.
    public static int minimumSwaps(int[] arr) {

        int n = arr.length;
        Integer[] sorted_index = new Integer[n];//if we are going to use comparator we need to use Object instead of primitive int
        for(int i=0;i<n;i++){
            sorted_index[i]=i;
        }

        Arrays.sort(sorted_index, (a, b)->arr[a]-arr[b] );
        int[] index_order= new int[n];
        for(int i =0;i<n;i++){
            index_order[ sorted_index[i] ] = i;
        }
        int[] indexArr = new int[n];

        for(int i =0;i<n;i++){
            indexArr[i]=i;
        }

        int i=0;
        int res=0;
        while(i<n){
            int index= indexArr[i];
            int desired_order = index_order[index];

            //another idea is to move the target num into cur pos
            //here we move cur num into desired pos
            if( i== desired_order ){
                i++;
            }else{
                res++;
                //swap
                swap(indexArr,i,desired_order);

            }


        }


        return res;


    }
    public static void swap(int[] arr, int i , int j){
        int temp = arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }


}
