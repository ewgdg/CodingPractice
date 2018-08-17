public class reverseString {
    public String reverseString(String s) {
        StringBuilder sb = new StringBuilder();
        int size=s.length();
        for(int i=size-1;i>=0;i--){
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

}
