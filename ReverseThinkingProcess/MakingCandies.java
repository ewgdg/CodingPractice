import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class MakingCandies {
    //Complete the minimumPasses function in the editor below. The function must return a long integer representing the minimum number of passes required.
    //
    //minimumPasses has the following parameter(s):
    //
    //m: long integer, the starting number of machines
    //w: long integer, the starting number of workers
    //p: long integer, the cost of a new hire or a new machine
    //n: long integer, the number of candies to produce

    //each day can make m*w candies
    // cost p candies to buy one w or m
    //need n candies

    //naive way to try all possible way --> too complicated
    //guess a number day and verify
    // Complete the minimumPasses function below.
    static long minimumPasses(long m, long w, long p, long n) {
// 1 2 100 4
        //binary search
        //looking for a specific goal number
        //reverse thinking process,
        //guess a result  and then verify

        long left=0;
        long right = (long)Math.ceil((double)(n)/m/w)   ;  //m*w will overflow ,use long long or /m/w

        while(left<=right){

            long mid = left+(right-left)/2;
            System.out.println(mid);
            boolean ok = verify(m,w,p,n,mid);

            if(ok){
                right = mid-1;
            }else{
                left = mid+1;
            }

        }
        return right+1;


    }
    public static boolean verify(long m , long w , long p, long n, long day){

        long cur =0;

        while(true){
//            System.out.println(cur);
            if( Math.ceil((double)(n-cur)/m/w) <= day  ){  // (n-cur + (m*w-1) ) / m*w , m*w-1/m*w = 0.99999   which will add 1 to the final result if there are remainder
                return true;
            }


            if(cur<p){

                //next buy
                long rnd = (long)Math.ceil( (double)(p-cur)/m/w)  ;
                cur=cur+rnd*m*w;
                day-=rnd;

            }
            {
                //buy one m or w and then try again, bc we know if we never buy we never reach goal
                if (m <= w) {
                    m++;
                }else{
                    w++;
                }
                cur -= p;
            }

            if(day<=0){//important , if use day<0 --> long long loop for cur -=p to decrease to 0 when p is small and cur is very large and close to n and day==0;
                return false;
            }

        }


    }
//    public static boolean verify(long m , long w , long p, long n, long day){
//
//        BigDecimal cur = BigDecimal.ZERO;
//        BigDecimal bigm = BigDecimal.valueOf(m);
//        BigDecimal bigw = BigDecimal.valueOf(w);
//        BigDecimal bign = BigDecimal.valueOf(n);
//        BigDecimal bigd = BigDecimal.valueOf(day);
//        BigDecimal bigp = BigDecimal.valueOf(p);
//
//        while(true){
////            System.out.println(cur);
//            if( Math.ceil(   bign.subtract( cur).divide( bigm,MathContext.DECIMAL32 ).divide( bigw, MathContext.DECIMAL32 ).doubleValue() )<= day  ){  // (n-cur + (m*w-1) ) / m*w , m*w-1/m*w = 0.99999   which will add 1 to the final result if there are remainder
//                return true;
//            }
//
//
//            if( cur.compareTo(bigp)<0){
//
//                //next buy
//                long rnd = (long)Math.ceil( (bigp.subtract(cur)).divide(bigm,MathContext.DECIMAL32).divide(bigw,MathContext.DECIMAL32).doubleValue() )  ;
////                System.out.println("rnd"+ rnd);
//                cur=cur.add(BigDecimal.valueOf(rnd).multiply(bigm).multiply(bigw) ) ;
//                day-=rnd;
//
//            }
//            {
//                //buy one m or w and then try again, bc we know if we never buy we never reach goal
//                if ( bigm.compareTo(bigw) <= 0) {
//                    bigm=bigm.add(BigDecimal.ONE);
//                }else{
//                    bigw=bigw.add(BigDecimal.ONE);
//                }
//                cur= cur.subtract(bigp);
//                if(day==0)
//                    System.out.println("cur "+cur);
//            }
//
//            if(day<=0){
//                return false;
//            }
////            System.out.println(day);
//
//        }
//
//
//    }


    public static void main(String[] args){

        System.out.println(
                minimumPasses(3,13,13,1000000000000l )
        );
    }



}
