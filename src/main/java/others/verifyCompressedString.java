package others;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;

public class verifyCompressedString {
//    验证string，string只包含数字、小写字母。数字代表之后的字符串的长度。但数字也可以视字符串的一部分。例：4gray6hunter, true(4: gray; 6: hunter); 11gray6hunter,
// true(11: gray6hunter); 124gray6hunter, true(1:2; 4: gray; 6: hunter); 先用暴力走了个过场，
// 然后说用dp，dp[j], i表示在处结束的数字，在j处结束的字符串，有问题，无法确定在i结束时的数字。dp[j][k], 从i到j，以k为分割线，切数字和字符串。没写完。求大神讲解。 wordbreak?

    static int mem_count =0;
    static int calls =0;
    public static boolean solution(String str){

        HashMap<String,Boolean> mem = new HashMap<>();
        return helper(str,mem);

    }

    //worst case n^2 all digits+some char and failed, 1+2+3+..n -> n*(n+1)/2
    public static boolean helper(String str, HashMap<String,Boolean> mem){
        if(mem.containsKey(str)){
            mem_count++;
            return mem.get(str);
        }

        if(str.length()==0) return true;

        int index=0;
        int k=0;


        while(index<str.length() && str.charAt(index)<='9' && str.charAt(index)>='0'   ){
            calls++;
            int digit = str.charAt(index)-'0' ;
            if( k > ((Integer.MAX_VALUE-digit)/10.0 )  ){
                //overlow
                mem.put(str,false);
                return false;

            }
            k=k*10+digit;
//            calls++;
            if(index+k<str.length()){
                //could be valid split
                if(helper(str.substring(index+k+1),mem)){
                    mem.put(str,true);//or store status and break then put status after while loop
                    return true;
                }
            }

            index++;


        }
        mem.put(str,false);
        return false;

    }

    public static void main(String[] args){
        long startTime = System.currentTimeMillis(); //程序开始记录时间

        String str = "111111111111112222222233333333333111111133333322222222222222223333333311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112111111111111111111111111111111111111111111111111111111111111111111111ssssssssss22sssssssssssssssssss111ssssssssssssssssssss333333333333333333333333333333333333ssssssssssssssssssssssssssssssssssssssss111111111111112222222233333333333111111133333322222222222222223333333311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112111111111111111111111111111111111111111111111111111111111111111111111ssssssssss22sssssssssssssssssss111ssssssssssssssssssss333333333333333333333333333333333333ssssssssssssssssssssssssssssssssssssssss";

        System.out.println(solution(str));


        long endTime   = System.currentTimeMillis(); //程序结束记录时间
        long TotalTime = endTime - startTime;
        System.out.println(TotalTime);
        System.out.println("calls: "+calls);
        //it seems it is 10n = linear complexity with mem
        System.out.println("mem_uses: "+mem_count);
        System.out.println(str.length());
    }

}
