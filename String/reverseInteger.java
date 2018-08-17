

public class reverseInteger {
    public int reverse(int x) {
        int ret=0;
        while(x!=0){
            int digit=x%10;

            //detect overflow
            if(ret>0&&ret>(Integer.MAX_VALUE-digit)/10){
                return 0;
            }
            if(ret<0&&ret<(Integer.MIN_VALUE-digit)/10){
                return 0;
            }

            ret=ret*10+digit;

            x=x/10;
        }
        return ret;


    }
}
