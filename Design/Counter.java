import java.util.HashMap;
import java.util.Map;

public class Counter<K> {

    Map<K,Integer> map ;
    public Counter(){
        map = new HashMap<>();
    }

    public void add(K key, int count){
        map.put(key, map.getOrDefault(key,0)+count);
    }

    public int get(K key){
        return map.getOrDefault(key,0);
    }
    public boolean contains(K key){
        return map.containsKey(key);
    }

    public void remove(K key){
        map.remove(key);
    }


}
