import java.util.*;

class AutocompleteSystem {
    class TrieNode{
        HashMap<Character, TrieNode> children ;
        int times;
        public TrieNode(){
            children = new HashMap<Character,TrieNode>();
            int times=0;
        }

    }
    class Node{
        public String s;
        public int times;
        public Node(String s, int times){
            this.s =s ;
            this.times = times;
        }
    }

    public void insert(String s, TrieNode root, int times){
        TrieNode cur = root;
        for(char c : s.toCharArray()){
            cur = cur.children.computeIfAbsent( c , (k)->new TrieNode()  );
        }
        cur.times+=times;

    }

    public List<Node> lookup(TrieNode root, String s){
        TrieNode cur = root;
        for(char c : s.toCharArray()){
            if(cur.children.containsKey(c)){
                cur = cur.children.get(c);
            }else{
                return new ArrayList<>();
            }
        }


        //traverse the current trieNode for all children
        List<Node> ans = new ArrayList<>();
        //dfs traverse
        traverse(cur,new StringBuilder(s),ans);
        return ans;

    }

    //dfs
    public void traverse(TrieNode cur, StringBuilder s , List<Node> list){//use StringBuilder for effi?
        if(cur.times>0){
            list.add(new Node(s.toString(),cur.times));
        }
        for(Map.Entry<Character,TrieNode> entry: cur.children.entrySet()){
            traverse( entry.getValue() , s.append(entry.getKey()) ,list );
            s.deleteCharAt(s.length()-1);
        }
    }


    String user_in = "";
    TrieNode root;

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        user_in = new String();
        int i= 0;
        for(String s:sentences){
            insert(s,root,times[i]);
            i++;
        }


    }

    public List<String> input(char c) {
        List<String> ans = new ArrayList<String>();
        if(c=='#'){

            insert(user_in, root, 1);
            user_in = "";
            return ans;
        }else{
            user_in+=c;
        }
        List<Node>  ret = lookup(root, user_in);
        Collections.sort( ret, (a,b)->{return a.times==b.times? a.s.compareTo(b.s): b.times-a.times; } );

        for (int i = 0; i < Math.min(3, ret.size()); i++)
            ans.add(ret.get(i).s);


        return ans;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */