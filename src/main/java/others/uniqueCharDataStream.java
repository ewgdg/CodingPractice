package others;
import java.util.LinkedHashMap;
import java.util.Map;

public class uniqueCharDataStream {
    //given a long data stream how to find the first unique char
    //cannot use two pass method hashmap then iter again, 2 option use hashset + linkedhashset(store valid char only) , or simply one single linkedHashMap
    public static char solution(char[] stream){

        LinkedHashMap<Character,Boolean>  map = new LinkedHashMap<>();
        for(char c: stream){
            if(map.containsKey(c)){
                map.put(c,false);
            }else{
                map.put(c,true);
            }
        }

        for(Map.Entry<Character,Boolean>  entry: map.entrySet() ){
            if(entry.getValue()){
                return entry.getKey();
            }
        }
        return 'a';
    }


    public static void main(String[] args){
        System.out.println(solution(new char[]{'b','a','c','c'}));
    }

}
