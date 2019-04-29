package Trie;

import java.util.HashMap;

public class MaximumXor {

//  given q  find the maximum value of q xor arr[i] for all elem in arr
    //multiple q might be given

    static int[] maxXor(int[] arr, int[] queries) {
        // solve here
        //Binary Trie Search for best branch
        Trie tree = new Trie();
        for (int num : arr) {
            tree.insert(num);

        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int q = queries[i];
            res[i] = tree.xorSearch(q);
        }
        return res;
    }


    static class Trie {
        class Node {
            HashMap<Integer, Node> children;//use arr for less runtime
            boolean isEnd;

            public Node() {
                children = new HashMap<>();
                isEnd = false;
            }
        }

        Node root;

        public Trie() {
            root = new Node();

        }

        public void insert(int num) {

            Node cur = root;
            for (int i = 30; i >= 0; i--) {
                int bit = (num >> i) & 1;

                cur = cur.children.computeIfAbsent(bit, k -> new Node());
            }
            cur.isEnd = true;
        }

        public int xorSearch(int q) {

            Node cur = root;
            int res = 0;
            for (int i = 30; i >= 0; i--) {


                int bit = (q >> i) & 1;


                bit = bit^1; // cannot use ~bit, bc the whole 32 bits will be flipped
                if (!cur.children.containsKey(bit)) {
                    bit = bit^1;
                }
                cur = cur.children.get(bit);
                res = (res << 1) | (bit);



            }

            res = res ^ q;
//            System.out.println(res);
            return res;


        }


    }
}
