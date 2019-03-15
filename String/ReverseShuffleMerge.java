import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReverseShuffleMerge {
    //find the lexicographically smallest A such that
    //  merge (reverse A , shuffle A) = S
    //merge maintain the relative order for each string

    //Sample Input 0
    //
    //eggegg
    //Sample Output 0
    //
    //egg
    //Explanation 0
    //
    //Split "eggegg" into strings of like character counts: "egg", "egg"
    //reverse("egg") = "gge"
    //shuffle("egg") can be "egg"
    //"eggegg" belongs to the merge of ("gge", "egg")



    static String reverseShuffleMerge(String s) {

        HashMap<Character,Integer> counter = new HashMap<>();

        for(char c:s.toCharArray()){
            counter.put(c,counter.getOrDefault(c,0)+1);
        }
        HashMap<Character,Integer> chars = new HashMap<>(counter);
        for(Map.Entry<Character,Integer> entry:counter.entrySet()){
            entry.setValue(entry.getValue()/2);
        }

        s=new StringBuilder(s).reverse().toString();

        int n=s.length();
        char[] res= new char[n/2];
        // helper(res,0,0,s,counter); // too slow

        //iterative method

        int startIndex=0;
        for(int i =0; i< res.length;i++){

            for(char c='a' ;c<='z';c++){
                boolean valid = true;
                if(counter.getOrDefault(c,0)<=0) continue;
                HashMap<Character,Integer> avail = new HashMap<>(chars);
                int j = startIndex;
                for( j=startIndex;j<n;j++){

                    if(s.charAt(j)==c  ){//only need to test the first char == c
                        break;
                    }
                    avail.put(s.charAt(j) , avail.get(s.charAt(j))-1 );
                }

                for(char c2: avail.keySet() ){
                    if(avail.get(c2) < counter.get(c2)  ){
                        valid =false;
                        break;
                    }
                }
                if(valid){
                    res[i]=c;
                    startIndex=j+1;

                    avail.put(s.charAt(j) , avail.get(s.charAt(j))-1 );
                    chars=avail;
                    counter.put(c,counter.get(c)-1);
                    break;
                }



            }

        }


        return new String(res);


    }
    public static boolean helper(char[] res,int p, int index, String s, HashMap<Character,Integer> counter){

        if(p==res.length){
            return true;
        }
        if(index>=s.length()){
            return false;
        }

        //if we cache the res then the complexity will be n^2
        //otherwise it will be n!
        //but it took a lot of space to cache
        for(char c='a';c<='z';c++){
            if(counter.getOrDefault(c,0)<=0) continue;
            for(int i = index;i<s.length();i++ ){
                if(c==s.charAt(i)){
                    counter.put(c,counter.get(c)-1);
                    if(helper(res,p+1,i+1,s,counter) ){
                        res[p]=c;
                        return true;
                    }
                    counter.put(c,counter.get(c)+1);
                }
            }
        }
        return false;
    }
}
