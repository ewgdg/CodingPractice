import java.util.*;

public class GraduatedValue {
//    StringBuilder buffer;
//    interface StateOperation{
//        public void dispatch(Deque<String> buffer, long num);
//    }
//    static double TRILLION = 1e12;
//    static double BILLION  = 1e9;
//    static double MILLION = 1e6;
//    static long THOUSAND = 1000;
//    static HashMap<Integer,StateOperation> states;
//    static{
//        states= new HashMap<>();
//        states.put(0, (buffer,num) -> {});
//        states.put(1, (buffer,num) ->{
//            if(num>THOUSAND){
//                long prefix = (long)(num/THOUSAND);
//                buffer.addFirst("K");
//                buffer.append();
//            }
//        });
//        states.put(){
//
//        }
//    }

    ////        if(value==0l) return "0.0K";
    ////        long prefix = value;
    ////        final long THOUSAND = 1000;
    ////        String[] units = new String[]{"K","M","B","T"};
    ////        Stack<Long> stack = new Stack<>();
    ////        StringBuilder sb = new StringBuilder();
    ////        int i =0;
    ////        while(prefix>0 && i<units.length){
    ////            long remaining = prefix%THOUSAND;
    ////            prefix = prefix/THOUSAND;
    ////
    ////            stack.add(remaining);
    ////            i++;
    ////        }
    ////
    ////        if(prefix>0||stack.size()==1){
    ////            stack.add(prefix);
    ////        }
    ////
    ////        while(stack.size()>2){
    ////            sb.append(String.valueOf(stack.pop()));
    ////            sb.append(units[stack.size()-1]);
    ////        }
    ////        long integer = stack.pop();
    ////        long decimal = stack.pop();
    ////        if(decimal!=0 || addDecimalForSingleDigit){
    ////            sb.append(integer+"."+String.valueOf(decimal));
    ////        }
    ////        sb.append(units[0]);
    ////        return sb.toString();

    public static String GraduatedValue(long value, int decimalPlaces, boolean addDecimalForSingleDigit)
    {
        StringBuilder sb= new StringBuilder();


        if(value < 0) sb.append("-");
        value = Math.abs(value);


        final String decimal_point = ".";
        final String[] units = new String[]{"","K","M","B","T","Q"};



        String str = String.valueOf(value);


        int n =str.length();


        int l2 = (n-1)/3*3;
        int l1=n-l2;



        if(l2/3>=units.length){
            int temp=(units.length-1)*3;
            l1 += l2-temp;
            l2=temp;
        }

        if(l1<0) l1=0;


        String str1 = str.substring(0,l1);
        String str2 = str.substring(str1.length());
        if(str1.length()==0) str1="0";


        int precision=decimalPlaces;

        if(addDecimalForSingleDigit&&str1.length()==1){
            precision+=1;
        }
        if(precision>0) {
            sb.append(str1);
            sb.append(decimal_point);
            sb.append(round(str2, precision));
        }else{
            //round integer
            int tenth = (str2.length()>0)?(str2.charAt(0)-'0'):0;
            if(tenth>5  ){
                sb.append( Integer.valueOf(str1)+1 );
            }else if(tenth<5){
                sb.append(str1);
            }else{
                if((Integer.valueOf(str1)&1)==0){
                    sb.append(str1);
                }else{
                    sb.append(Integer.valueOf(str1)+1);
                }
            }
        }

        int index = str2.length()/3;

        String unit = units[index];
        sb.append(unit);
        return sb.toString();


    }

    public static String round(String str, int prec){
        if(prec==0) return "";
        StringBuilder sb= new StringBuilder();
        if(str.length()>prec) {
            int lastDigit = str.charAt(prec)-'0';
            int num = Integer.parseInt(str.substring(0, prec));
            if (lastDigit >= 5) {
                num += 1;
            }

            sb.append(String.valueOf(num));
        }else{
            sb= new StringBuilder(str);

        }
        for(int i=sb.length();i<prec;i++){
            sb.append("0");
        }
        return sb.toString();

    }
    public static void TestGraduatedValue(String testName, long value, int decimalPlaces, boolean addDecimalForSingleDigit, String expected)
    {
        String output;
        try {
            output = GraduatedValue(value, decimalPlaces, addDecimalForSingleDigit);
        } catch (Exception e) {
            output = "exception";
        }

        if((output != null && output.equals(expected)) || (output == null && expected == null) || (expected==null && "".equals(output)))
            System.out.println("Succeeded for " + testName);
        else
            System.out.println("Failed for " + testName + ": Failed with [" + output + "] rather than [" + expected + "]");
    }

    public static void RunTests()
    {
        TestGraduatedValue("Simplest Low Value", 123, 0, false, "123");
        TestGraduatedValue("Zero", 0, 0, false, "0");
        TestGraduatedValue("Decimal Zeros", 0, 4, false, "0.0000");

        TestGraduatedValue("Simple One Grad", 12345, 0, false, "12K");
        TestGraduatedValue("Simple With Decimals", 12345, 3, false, "12.345K");

        TestGraduatedValue("AddDec Shouldnt Affect", 12345, 0, true, "12K");
        TestGraduatedValue("Zero AddDec", 0, 0, true, "0.0");

        TestGraduatedValue("Round Integer Up", 1880, 0, false, "2K");
        TestGraduatedValue("Round Decimal Up", 1880, 1, false, "1.9K");
        TestGraduatedValue("Round After AddDec", 1880, 0, true, "1.9K");

        TestGraduatedValue("Bankers Rounding Up", 1500, 0, false, "2K");
        TestGraduatedValue("Bankers Rounding Down", 4500, 0, false, "4K");

        TestGraduatedValue("Small Negatives", -123, 0, false, "-123");
        TestGraduatedValue("Negatives", -1000, 0, false, "-1K");
        TestGraduatedValue("Negatives Bankers Up", -1500, 0, false, "-2K");
        TestGraduatedValue("Bankers Bankers Down", -4500, 0, false, "-4K");

        TestGraduatedValue("Large With Decimals", 9372036854775807L, 1, true, "9.37Q");
        TestGraduatedValue("Large No More Grad", 9223372036854775807L, 0, true, "9223Q");

        TestGraduatedValue("No Premature Grad", 999, 0, false, "999");
    }
    public static void main(String[] args){
        System.out.println(GraduatedValue(50000,0,true));
        System.out.println(GraduatedValue(5860,0,true));
        System.out.println(GraduatedValue(5860,2,true));
        System.out.println(GraduatedValue(15860,2,true));
        System.out.println(GraduatedValue(500,2,false));
        System.out.println(GraduatedValue(500,3,true));
        System.out.println(GraduatedValue((long)1e15,3,true));
        System.out.println(GraduatedValue(1299999,3,false));
        RunTests();


    }





}





