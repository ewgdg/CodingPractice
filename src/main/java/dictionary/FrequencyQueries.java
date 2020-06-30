package dictionary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// query 1 x -> insert x
// query 2 y -> delete y
//query 3 z -> return 1 if found number with freq count of z

public class FrequencyQueries {
    // Complete the freqQuery function below.
    static HashMap<Integer,Operation> command_list;
    // static HashMap<Integer,HashSet<Integer>> count_num = new HashMap<>();
    //no need to store the whole number set
    //just the freq of count
    static HashMap<Integer,Integer> count_freq = new HashMap<>();
    static HashMap<Integer,Integer> num_count = new HashMap<>();
    static{
        command_list= new HashMap<>();
        command_list.put(1,v->update(v,1));
        command_list.put(2,v->update(v,-1));
        command_list.put(3,v->count_freq.getOrDefault(v,0)>0?1:0);

    }
    public static interface Operation{
        public int execute(int val);
    }

    static List<Integer> freqQuery(List<int[]> queries) {


        List<Integer> res =new ArrayList<>();
        for(int[] query:queries){
            int command = query[(0)];
            int val = query[(1)];

            int r= command_list.get(command).execute(val);
            if(command==3){
                res.add(r);
            }
        }
        return res;



    }
    public static int update(int v, int change){
        int count = num_count.getOrDefault(v,0);
        num_count.remove(v);
        if(count>0) {
            count_freq.put(count, count_freq.getOrDefault(count, 0) - 1);
            if (count_freq.get(count) <= 0) {
                count_freq.remove(count);
            }
        }
        if(count+change>0){
            num_count.put(v,count+change);
            count_freq.put(count+change, count_freq.getOrDefault(count+change,0)+1);
        }
        return 0;
    }

    public static void main(String[] args){

        List<int[]> queries = new ArrayList<>();
        queries.add(new int[]{1,1});
        queries.add(new int[]{3,1});
        System.out.println(freqQuery(queries));
    }
}
