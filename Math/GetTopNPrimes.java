//import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GetTopNPrimes {


    public static int[] get(int n) { //O n

        int count=0;
        int i=2;
        int[] res = new int[n];
        while(count<n){
            boolean isPrime=true;
            for(int j=2;j<Math.sqrt(i);j++){
                if(i%j==0){
                    isPrime=false;
                    break;
                }
            }
            if(isPrime){
                res[count++]=i;
            }
            i++;

        }
        return res;
    }

    public static void main(String[] argss){

        System.out.println(Arrays.toString( get(21) ));
    }
}
