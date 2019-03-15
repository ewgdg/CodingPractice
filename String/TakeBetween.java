public class TakeBetween {
    public static String TakeBetween(String input, String left, String right, Boolean takeUntilEndIfRightMissing)
    {
        if(input.length()==0) return new String();
        if(left.length()==0||right.length()==0) return "exception";
        //early terminate
        if(left.length()>input.length()){
            return null;
        }
        int i = contains(input,0,left);
        if(i==-1) return null;

        int j = contains(input,i,right);
        if(j==-1){
            if(!takeUntilEndIfRightMissing) return null;
            else return input.substring(i);
        }
//        System.out.println(i);
//        System.out.println(j);
        return input.substring(i,j-right.length());

    }
    public static int[] getTransition(String pattern){
        int n  = pattern.length();
        int[] res = new int[n];

        int j=0;
        int i =1;
        while(i<n){
            char c = pattern.charAt(i);
            if(c==pattern.charAt(j)){
                res[i]=j+1;
                i++; j++;
            }else{
                if(j==0){
                    res[i]=0;
                    i++;
                }else {
                    j = res[j-1] - 1 + 1;
                }
            }
        }
        return res;
    }


    public static int contains(String parent, int start, String pattern){
        //check if pattern length > parent length
        int[] transition = getTransition(pattern);

        int i =start;
        int j =0;

        int m = parent.length();
        int n = pattern.length();
        while(i<m && j<n){
            if(parent.charAt(i)==pattern.charAt(j)){
                i++;j++;
            }else{
                if(j==0){
                    i++;
                }else {
                    j = transition[j-1];
                }

            }
        }
        if(j==n){
            return i;
        }else{
            return -1;
        }

    }
    public static void TestTakeBetween(String testName, String input, String left, String right, boolean takeUntilEndIfRightMissing, String expected)
    {
        String output;
        try {
            output = TakeBetween(input, left, right, takeUntilEndIfRightMissing);
        } catch (Exception e){
            output = "exception";
        }

        if((output != null && output.equals(expected)) || (output == null && expected == null) || (expected==null && "".equals(output)))
            System.out.println("Succeeded for " + testName);
        else
            System.out.println("Failed for " + testName + ": Failed with [" + output + "] rather than [" + expected + "]");
    }

    public static void RunTests()
    {
        TestTakeBetween("Simple Case", "<p>Simple case</p>", "<p>", "</p>", false, "Simple case");
        TestTakeBetween("Flipped And False", "</p>Flipped<p>End", "<p>", "</p>", false, null);
        TestTakeBetween("Flipped And True", "</p>Flipped<p>End", "<p>", "</p>", true, "End");
        TestTakeBetween("Not String Ends", "Not<p>String</p>Ends", "<p>", "</p>", false, "String");
        TestTakeBetween("Matching", "Both<p>Ends<p>Match", "<p>", "<p>", false, "Ends");
        TestTakeBetween("No Left And False", "No Right</p>", "<p>", "</p>", false, null);
        TestTakeBetween("No Left And True", "No Right</p>", "<p>", "</p>", true, null);
        TestTakeBetween("No Right And False", "<p>No Right", "<p>", "</p>", false, null);
        TestTakeBetween("No Right And True", "<p>No Right", "<p>", "</p>", true, "No Right");
        TestTakeBetween("Regex Escaping", "lead (a+*)regex escape test(a+*)follow", "(a+*)", "(a+*)", false, "regex escape test");
        TestTakeBetween("Multiple", "<p><p>Multiple</p></p>", "<p>", "</p>", false, "<p>Multiple");
        TestTakeBetween("Empty Input", "", "<p>", "</p>", false, "");
        TestTakeBetween("Empty Delim", "Something", "", "", false, "exception");
        TestTakeBetween("Empty Middle", "<p></p>", "<p>", "</p>", false, "");
    }
    public static void main(String[] args){

//        System.out.println(TakeBetween(" e ","","",true));
        RunTests();
    }
}
