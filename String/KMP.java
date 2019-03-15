public class KMP {


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


    public static boolean contains(String parent, String pattern){
        int[] transition = getTransition(pattern);

        int i =0;
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
        return j==n;

    }


    public static void main(String[] args){
        System.out.println(contains("asdfasdfdsfsdfsssfdddewgdgdf","asdfds"));
    }
}
