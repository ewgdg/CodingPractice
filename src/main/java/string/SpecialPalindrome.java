package string;

public class SpecialPalindrome {
    //A string is said to be a special palindromic string if either of two conditions is met:
    //
    //All of the characters are the same, e.g. aaa.
    //All characters except the middle one are the same, e.g. aadaa.
    //A special palindromic substring is any substring of a string which meets one of those criteria. Given a string, determine how many special palindromic substrings can be formed from it.



    // Complete the substrCount function below.
    static long substrCount(int n, String s) {
        //aaa
        //aa
        //aba
        //abba
        // ssmee -> sme ssmee
        //abcbaba //test driven

        long count=0;

        if(n==0) return 0;
        //num of dup from right side
        int[] right = new int[n];
        right[n-1]=1;
        for(int i=n-2;i>=0;i--){
            if(s.charAt(i)==s.charAt(i+1)){
                right[i]=right[i+1]+1;
            }else{
                right[i]=1;
            }
        }

        int left_count=1;
        for(int i =0;i<n;i++){

            if(i-1>=0 && s.charAt(i)==s.charAt(i-1)){
                left_count++;
                //case 2
                count+=left_count;
            }else{

                //case 1
                if( i-1>=0 &&i+1<n && s.charAt(i-1)==s.charAt(i+1))
                    count+= Math.min(right[i+1], left_count );

                left_count=1;
                count+=1;
            }


        }


        return count;
    }
}
