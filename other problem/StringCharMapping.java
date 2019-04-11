import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class StringCharMapping {

    //判断target字符串是否可以由给定字符串转换得到。转换的规则是：每次转换要变所有的相同字母
    //比如：abca -> cdec 可以 abca -> abea -> cbec -> cdec
    //这道题当时看面经的时候就觉得很怪，也没仔细想，加上和第一轮又很像，当时有点慌，讨论了蛮久，还好面试官super nice
    //最后讨论的结果是很简单，只要check对应的映射规则都满足就行了，当然长度一定要相同。
    //然后问什么时候需要中间变量？比如ab -> ba，必须先ab -> cb -> ca -> ba，不能直接a到b，b到a。讨论了一下lz说check有没有环，面试官满意。然后写了个dfs检查有没有环，这一轮就结束了

    public static boolean solution(String s, String p){
        HashMap<Character,Character> mapping = new HashMap<>();

        if(s.length()!=p.length()) return false;


        //s mapping to p
        for(int i =0;i<s.length();i++){
            char c = s.charAt(i);
            if(!mapping.containsKey(c)){
                mapping.put(c,p.charAt(i));
            }

            if(mapping.get(c)!=p.charAt(i)){
                return false;
            }

        }

        //check reverse mapping? no need , no restriction on  2 char cannot map to same


        return true;



    }

    public static boolean needIntermidiate(String s, String p){
        HashMap<Character,Character> mapping = new HashMap<>();

        if(s.length()!=p.length()) return false;

        for(int i =0;i<s.length();i++){
            char c = s.charAt(i);
            if(!mapping.containsKey(c)){
                mapping.put(c,p.charAt(i));
            }

            if(mapping.get(c)!=p.charAt(i)){
                return false;
            }

        }


        //do dfs on mapping
        HashSet<Character> visited = new HashSet<>();
        for(Character c: mapping.keySet()){
            //notice the corner case a->a
            if(mapping.get(c)==c) continue;
            if(!dfs(mapping,c,new HashSet<>(),visited)) //cycle found
                return true;
        }
        return false;
    }

    public static boolean dfs(HashMap<Character,Character> map, Character c, HashSet<Character> visiting, HashSet<Character> visited){
        if(visited.contains(c)) return true;
        if(visiting.contains(c)) return false;
        visiting.add(c);
        if(map.containsKey(c)) {
            Character child = map.get(c);
            if(!dfs(map,child,visiting,visited)){
                return false;
            }
        }
        visited.add(c);
        return true;
    }


    public static void main(String[] args){
        System.out.println(solution("abd","bdb"));// need 2 direction mapping check? no
//        System.out.println(needIntermidiate("abca","cdec"));
        System.out.println(needIntermidiate("ab","bd")); //ab -> bd  b->d then a->b false

        //notice the corner case a->a
        System.out.println(needIntermidiate("aa","aa"));
    }

}
