package others;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonPrefix {

    //give list of strings 1000001111 ,11110000 ,find the max len exclude common prefix


    class TrieNode{
        HashMap<Character,TrieNode> children;
        boolean isEnd;

        public TrieNode(){
            children = new HashMap<>();
        }

    }
    TrieNode root = new TrieNode();

    public void insert(String s,TrieNode root){
        TrieNode cur=root;

        for(char c:s.toCharArray()){
            cur= cur.children.computeIfAbsent(c,(k)->new TrieNode());

        }
        cur.isEnd=true;


    }
    int max =0;
    public int solution(List<String> strs){
        for(String s:strs){
            insert(s,root);
        }

        findDepth(root);
        return max;
    }

    public int findDepth(TrieNode node){
        //only 2 branch if string is 0 and 1
        int curMax =0;
        for(Map.Entry<Character,TrieNode> entry: node.children.entrySet()){
            int depth = findDepth(entry.getValue());
            if(curMax!=0||node.isEnd==true)
                max = Math.max(max,depth+curMax);

            curMax = Math.max(curMax,depth);

        }
        return curMax+1;



    }

    public static void main(String[] args){
        CommonPrefix solver =new CommonPrefix();

        List<String> strs= new ArrayList<>();
//        strs.add("sa13070");
//        strs.add("sa13101");
        strs.add("sabbbbb");
        strs.add("sasds");
        strs.add("sasdsd");
        System.out.println(solver.solution(strs));

    }


}
