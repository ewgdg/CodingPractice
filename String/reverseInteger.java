

public class reverseInteger {
    public int reverse(int x) {
        int ret=0;  // cannot make x abs(x) here bc if res = int.min , then -min will overflow
        while(x!=0){
            int digit=x%10;

            //detect overflow
            if(ret>0&&ret>(Integer.MAX_VALUE-digit)/10){
                return 0;
            }
            if(ret<0&&ret<(Integer.MIN_VALUE-digit)/10){//separate cond of min and max bc abs(min_value)!=max_value!!
                return 0;
            }

            ret=ret*10+digit;

            x=x/10;
        }
        return ret;


    }
}
