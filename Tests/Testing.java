import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Testing {
    @Test
    public void test() {
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);

        ArrayList<Integer> list = new ArrayList<>(stack);

        System.out.println(stack.iterator().next());
        System.out.println(list);

        int[] a = new int[]{1,3,2};
        Integer[] b = Arrays.stream(a).boxed().toArray(size->new Integer[size]);
        Arrays.sort(b, new Comparator<Integer>(){
            public int compare(Integer a ,Integer b){
                return a-b;
            }
        } );
        System.out.println(Arrays.toString(b));


        System.out.println("start");
        Integer aaa = null;
        if(aaa==1) System.out.println("1");
        System.out.println("cont");




    }
}