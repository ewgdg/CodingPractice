package array;

import java.math.BigInteger;

public class superPalindrome {
    public static int superpalindromesInRange(String L, String R) {
        BigInteger l= new BigInteger(L),r=new BigInteger(R);
        int ans=0;
        for(int n=1;n<100000;n++)
            for(int p=0;p<2;p++){//p==1 odd size
                if(n>=10000&&p==0)continue;
                BigInteger t= BigInteger.valueOf(n),palin=BigInteger.valueOf(n);
                if(p==1)t= t.divide(BigInteger.valueOf(10));
                while(t.compareTo(BigInteger.ZERO)>0){
                    palin=palin.multiply(BigInteger.TEN).add(t.mod(BigInteger.TEN) );
                    t=t.divide(BigInteger.TEN);
                }
                palin= palin.multiply(palin);
                if(palin.compareTo(l)<0||palin.compareTo(r)>0)continue;
                String s= palin.toString();
                boolean ok=true;
                for(int i=0;i<s.length()/2;i++)
                    if(s.charAt(i)!=s.charAt(s.length()-1-i)){
                        ok=false;
                        break;
                    }
                if(ok)ans++;
            }
        return ans;
    }

    public static void main(String[] args){
        System.out.println(superpalindromesInRange("4","1000"));
    }
}
