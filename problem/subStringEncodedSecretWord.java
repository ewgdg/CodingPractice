import java.util.HashMap;
import java.util.HashSet;

public class subStringEncodedSecretWord {
    //和一个encoding rule如下：对secret中的每个字母做变换，!!!!!不同的字母不能变化到同一个字母!!!!!!! very import constraint unlike Stringcharmapping 。如banana -> xyzyzy，但banana不可以变成xyyyyy，因为这样就没法decode回来。 来源一亩.三分地论坛.
    //现input是一个很长的string，要求判断string中是否存在substring可以由以上的encoding rule变换而来。
    //题不难，面试官大叔人特别好，一直跟我说这栋楼新开了gym但我还是越来越胖 -。- lz说就只能挨个substring扫一遍，想不到啥更好的方法。
    // 他说没啥更好的方法，要我开始写。lz当时有点脑洞可能写的不是最常规的解法，他说interesting。。。然后follow up问如果secret word有很多怎么办，聊了聊天，愉快的结束了
    //follow up , cache encode secret word to avoid repeated calc  e.g abb -> 011 ,  cdd -> 011

    static HashMap<String,Boolean> cache = new HashMap<>();

    public static String encode(String secret){
        StringBuilder out = new StringBuilder();
        HashMap<Character, Integer> mapping = new HashMap<>();
        for(char c:secret.toCharArray()){
            if(!mapping.containsKey(c)){
                mapping.put(c,mapping.size());
            }
            out.append(mapping.get(c));

        }
        return out.toString();
    }
    public static boolean solution(String s, String secret){
        int l = secret.length();

        int n = s.length();

        String key = encode(secret);
        if(cache.containsKey(key)){
            System.out.println("cached "+key);
            return cache.get(key);
        }

        int right = l;


        for(int left=0;left+l<=n;left++){
            right =left+l;
            HashMap<Character,Character> map = new HashMap<>();
            HashSet<Character> used = new HashSet<>();
            int i ;
            for( i =left; i<right; i++){
                if(!map.containsKey(secret.charAt(i-left))){
                    if(used.contains(s.charAt(i))){
                        //invalid
                        break;
                    }
                    map.put(secret.charAt(i-left),s.charAt(i));
                    used.add(s.charAt(i));
                }
                else if(s.charAt(i)!=map.get(secret.charAt(i-left))){
                    break; //invalid
                }
            }
            if(i==right){
                //found
                System.out.println(s.substring(left,right));
                cache.put(key,true);
                return true;
            }
        }
        cache.put(key,false);
        return false;

    }


    public static void main(String[] args){
        System.out.println(solution("abccaafaaa","ddbb"));
        System.out.println(solution("abccaafaaa","aacc"));
    }
}
