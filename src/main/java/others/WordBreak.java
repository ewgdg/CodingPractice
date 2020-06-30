package others;
// import java.lang.reflect.Array;
import java.util.*;

public class WordBreak {
    //devide and conquer + memory
    public static boolean wordBreak1(String s, List<String> wordDict){
        int n = s.length();
        if(n<=0) return true;
        HashSet<String> dict  = new HashSet<>(wordDict);
        HashMap<Integer,Boolean> mem = new HashMap<>();
        return helper(s,0,dict,mem);

    }

    public static boolean helper(String s, int start, HashSet<String> dict, HashMap<Integer,Boolean> mem){//devide into subproblem substring starts with start
        if(mem.containsKey(start)){
            return mem.get(start);
        }

        if(start>=s.length()) return true;

        boolean ret = false;
        for(int i= start+1; i<= s.length(); i++){
            if(dict.contains(s.substring(start,i))){
                if(helper(s,i,dict,mem)){
                    ret=true; break;
                }
            }
        }
        mem.put(start,ret);

        return ret;


    }


    public static List<List<String>> wordBreak2( String s, List<String> wordDict ){
        int n = s.length();
//        if(n<=0) return new ArrayList<>();

        HashSet<String> dict = new HashSet<>(wordDict);
        HashMap<Integer,List<List<String>>> mem = new HashMap<>();

        return helper2(s,s.length()-1,dict,mem);
    }

    public static List<List<String>> helper2(String s, int end, HashSet<String> dict, HashMap<Integer, List<List<String>>> mem){ //from end to 0 so that the res list is in order
        if(mem.containsKey(end)) return mem.get(end);

        List<List<String>> res = new ArrayList<>();
        if(end<0){
            res.add(new ArrayList<>());
            return res;
        }


        for(int i =end; i>=0; i--){
            if(dict.contains(s.substring(i,end+1))){
                List<List<String>> ret = helper2(s,i-1,dict,mem);
                List<List<String>> clone = new ArrayList<>(); //need to do deep copy for nested structure
                for(List<String> l : ret){
                    List<String> clonelist = new ArrayList<>(l);
                    clone.add(clonelist);
                    clonelist.add(s.substring(i,end+1));
                }
                res.addAll(clone);

            }
        }
        mem.put(end,res);
        return res;

    }

    public static void main(String[] args){
        System.out.println(wordBreak1("applepenapple", Arrays.asList(new String[]{"apple", "pen"})));
        System.out.println(wordBreak2("applepenapple", Arrays.asList(new String[]{"apple", "pen","applepen"})));
    }

}
