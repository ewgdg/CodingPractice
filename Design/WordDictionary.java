import java.util.HashMap;

class WordDictionary {
//addWord("bad")
//addWord("dad")
//addWord("mad")
//search("pad") -> false
//search("bad") -> true
//search(".ad") -> true
//search("b..") -> true, regular expression string containing only letters a-z or '.'


    //use prefix tree
    class TrieNode{
        HashMap<Character,TrieNode> children;
        boolean isWord;
        public TrieNode(){
            children = new HashMap<>();
            isWord=false;
        }

    }

    TrieNode root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode cur = root;

        for(char c : word.toCharArray()){
            cur = cur.children.computeIfAbsent(c,k->new TrieNode());
        }

        cur.isWord=true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return searchFrom(word,root);
    }


    public boolean searchFrom(String word, TrieNode from){
        TrieNode cur = from;
        int idx=0;
        for(char c : word.toCharArray()){
            if(c=='.'){
                for(TrieNode child : cur.children.values()){
                    if(searchFrom( word.substring(idx+1,word.length()) , child )){
                        return true;
                    }
                }
                return false;
            }else{
                cur = cur.children.get(c);
                if(cur==null) return false;
                idx++;
            }
        }
        return cur.isWord;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */