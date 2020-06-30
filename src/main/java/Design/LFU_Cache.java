package Design;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class LFU_Cache {
    //when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.
    //Could you do both operations in O(1) time complexity?

    //bc we need O(1) so every nodes with same freq with be grouped together ,otherwise we might have all nodes with same freq so the complexity will be O(n)
    //we dont need to sort the count just need the min count, bc each time we pop/remove the least freq node means we put a new node with freq of 1, the new node will be new min


    class Node{
        int value;
        int count;
        public Node(int value){
            this.value=value;
            count=1;
        }
    }


    HashMap<Integer,Node> key_val;
    HashMap<Integer, LinkedHashSet<Integer>> count_keys; // LinkedHashSet for quick find and remove when update freq

    int min;
    int capacity;
    public LFU_Cache(int capacity){
        this.capacity=capacity;
        this.min=0;

        key_val=new HashMap<>();
        count_keys = new HashMap<>();

    }

    public int get(int key){
        Node node = key_val.get(key);

        //update
        LinkedHashSet<Integer> group = count_keys.get(node.count);
        group.remove(key);
        if(group.size()==0 && min==node.count){
            min=node.count+1;
        }

        node.count+=1;
        count_keys.computeIfAbsent(node.count,(k)->new LinkedHashSet<>()).add(key);

        return node.value;

    }

    public void put(int key, int val){
        if(capacity<=0) return;
        if(key_val.containsKey(key)) {

            get(key);//update
            key_val.get(key).value=val;
            return;
        }

        if(key_val.size()>=capacity){
            pop();
        }
        min=1;

        Node node = new Node(val);
        key_val.put(key,node);

        count_keys.computeIfAbsent(min,(k)->new LinkedHashSet<>()).add(key);



    }
    public void pop(){
        LinkedHashSet<Integer> group = count_keys.get(min);
        Integer LRU = group.iterator().next();
        group.remove(LRU);

        key_val.remove(LRU);

        //reset min to 1
    }


}
