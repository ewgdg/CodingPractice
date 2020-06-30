package others;
import java.util.HashMap;
import java.util.Map;

public class processEmail {
    public static int solution(String[] L) {
        // write your code in Java SE 8

        HashMap<String, Integer> map= new HashMap<>();
        int count =0;

        for(String str: L){
            StringBuilder processed = new StringBuilder();
            boolean hasPlus = false;
            boolean isDomain = false;
            for(char c: str.toCharArray()){
                if(!isDomain){
                    if(c=='@'){
                        isDomain=true;
                        processed.append(c);
                    }else if(c=='+'&& !hasPlus){
                        hasPlus=true;
                    }else if(c=='.'){
                        continue;//ignore dot
                    }else if(!hasPlus){
                        processed.append(c);
                    }
                }else{
                    processed.append(c);
                }

            }
            String res = processed.toString();
            map.put(res , map.getOrDefault(res,0)+1 );
        }

        for(Map.Entry<String,Integer> entry : map.entrySet()){
            Integer v= entry.getValue();
            if(v>1) count++;
        }
        return count;

    }

    public static void main(String[] args){
        String[] l = new String[]{"a.b@example.com", "x@example.com", "x@exa.mple.com", "ab+1@example.com", "y@example.com", "y@example.com", "y@example.com"};
        System.out.println(solution(l) );
    }

}
