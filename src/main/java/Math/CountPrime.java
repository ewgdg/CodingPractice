package Math;

public class CountPrime {
//    Count the number of prime numbers less than a non-negative number, n.
    //every num can be decomposed into prime number that are smaller than it,
    //if we see a prime , we know all larger non-prime number formed by it
    public int countPrimes(int n) { //O n
        if(n<2){
            return 0;
        }

        boolean[] notPrime=new boolean[n];

        int count=0;
        for(int i=2;i<n;++i){
            if(notPrime[i]==false){
                count++;
                for(int j=i;i<n/(float)j;++j){ //or j<n ; j+=i , notPrime[j]=true
                    notPrime[i*j]=true;
                }
            }
        }
        return count;
    }

    //the naive method take N^2
    //verify each to test if it is prime by division all smaller
}
