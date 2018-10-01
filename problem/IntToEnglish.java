import java.util.HashMap;

public class IntToEnglish {
    //Input: 123
    //Output: "One Hundred Twenty Three"


    //billion  1,000,000,000
    //million  1,000,000

    static HashMap<Integer,String> map = new HashMap<>();
    static{
        map.put(0,""); map.put(1,"One"); map.put(2,"Two"); map.put(3,"Three");
        map.put(4,"Four");map.put(5,"Five");map.put(6,"Six");map.put(7,"Seven");
        map.put(8,"Eight");map.put(9,"Night");map.put(10,"Ten");map.put(11,"Eleven");
        map.put(12,"Twelve");map.put(13,"Thirteen");map.put(14,"Fourteen");map.put(15,"Fifteen");
        map.put(16,"Sixteen");map.put(17,"Seventeen");map.put(18,"Eighteen");map.put(19,"Nineteen");
        map.put(20,"Twenty");map.put(30,"Thirty");map.put(40,"Forty");map.put(50,"Fifty");
        map.put(60,"Sixty");map.put(70,"Seventy");map.put(80,"Eighty");map.put(90,"Ninety");

    }
    public static StringBuilder helper(int num){
        int suffix_num=0;
        int prefix_num=0;
        String middle = "";

        StringBuilder res = new StringBuilder();
        if(num>=1000000000){//billon
            prefix_num=num/1000000000;
            suffix_num=num%1000000000;
            middle="Billion";
        }else if(num>=1000000){//million
            prefix_num=num/1000000;
            suffix_num=num%1000000;
            middle="Million";
        }else if(num>=1000){
            prefix_num=num/1000;
            suffix_num=num%1000;
            middle="Thousand";
        }else if(num>=100){
            prefix_num=num/100;
            suffix_num=num%100;
            middle="Hundred";
        }else if(num>=20){

             res.append(map.get( (num/10) *10));
             if(num%10>0)
                res.append(" "+ map.get(num%10));
             return res;
        }else{
            return res.append(map.get(num));
        }

        res.append(helper(prefix_num));
        res.append(" ");
        res.append(middle);
        if(suffix_num>0){
            res.append(" ");
            res.append(helper(suffix_num));
        }

        return res;



    }

    public static String convert(int num){
        if(num==0) return "Zero";
        return helper(num).toString();
    }

    public static void main(String[] args){
        System.out.println(convert(1234567891));
        System.out.println(convert(1234567));
        System.out.println(convert(12345));
    }
}

