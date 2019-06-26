import java.util.HashMap;

public class Non_divisible_subset {
    //Given a set of distinct integers, print the size of a maximal subset of  where the sum of any 2 numbers in the subset is not evenly divisible by k.


    static int nonDivisibleSubset(int k, int[] s) {
        //For any number K, the sum of 2 values (A & B) is evenly divisible by K if the sum of the remainders of A/K + B/K is K.
        // (There is also a special case where both A & B are evenly divisible, giving a sum of 0.)

        HashMap<Integer,Integer> counter = new HashMap<>();

        for(int i=0;i<s.length;i++){
            int remainder = s[i]%k;
            counter.put(remainder,counter.getOrDefault(remainder,0)+1);

        }
        int res=0;
        if(k%2==0){
            res+=Math.min(1,counter.getOrDefault(k/2,0)) ;
        }else if(k/2>0){
            res+=Math.max(counter.getOrDefault(k/2,0),counter.getOrDefault(k-k/2,0));
        }

//        if(k%2==0){
//            counter.put(k/2, Math.min(counter.getOrDefault(k/2,0),1 ) );
//        }
        for(int i=1;i<k/2;i++){//we can only pick one remainder from i and k-i
            res+=Math.max(counter.getOrDefault(i,0),counter.getOrDefault(k-i,0));
        }

        res+=Math.min(1,counter.getOrDefault(0,0));
        return res;
    }

}
