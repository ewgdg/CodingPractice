package Math;

public class IsPrime {

    public static boolean isPrime(int n){

        //sqrt n solution

        // the break even point is sqrt n * sqrt n , the rest is duplicate of another half

        if(n<=1) return false;
        for(int i=2;i<Math.sqrt(n);i++){
            if(n%i==0){
                return false;
            }
        }
        return true;


    }
    //better
    public static boolean isPrime2(int n){

        //sqrt n solution



        if(n<=1 ||  (n&1)==0  ) return false; //check even
        for(int i=3;i<Math.sqrt(n);i+=2){//skip all even
            if(n%i==0){
                return false;
            }
        }
        return true;


    }


}
