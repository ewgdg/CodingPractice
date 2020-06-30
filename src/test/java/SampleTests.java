
import java.util.*;

import org.junit.jupiter.api.Test;



public class SampleTests {
    @Test
    public void test() {
        final Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);

        final ArrayList<Integer> list = new ArrayList<>(stack);

        System.out.println(stack.iterator().next());
        System.out.println(list);

        final int[] a = new int[]{1,3,2};
        final Integer[] b = Arrays.stream(a).boxed().toArray(size->new Integer[size]);
        Arrays.sort(b, new Comparator<Integer>(){
            public int compare(final Integer a ,final Integer b){
                return a-b;
            }
        } );
        System.out.println(Arrays.toString(b));


        System.out.println("start");
      
        System.out.println("cont");




    }
}
