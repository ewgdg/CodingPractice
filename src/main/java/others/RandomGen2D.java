package others;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class RandomGen2D {
    int m,n ;
//    int[][][] board; // too slow bc it take large amount of mem
    int size;
    //convert 2d into 1d by int pos = i*col+j;
    int p;
    Random random;
    HashMap<Integer,Integer> virtualArray ;
    public RandomGen2D(int n_rows, int n_cols) {
        m=n_rows;
        n=n_cols;

        random=new Random();


        size = m*n;


        reset();
    }

    public int[] flip() {
        int randPos = random.nextInt(p+1);
        int val = virtualArray.getOrDefault(randPos,randPos);

        virtualArray.put(randPos,virtualArray.getOrDefault(p,p));
        p--;



        return new int[]{val/n,val%n};

    }

    public void reset() {
        //since the board might not swapped , we might need to init a new board
        virtualArray = new HashMap<>();
        p=size-1;
        System.out.println(";");
    }


    public static void main(String[] args){

        RandomGen2D r = new RandomGen2D(10000,10000);



        System.out.println(Arrays.toString( r.flip()));
        System.out.println(Arrays.toString( r.flip()));
        System.out.println(Arrays.toString( r.flip()));
        System.out.println(Arrays.toString( r.flip()));
        r.reset();
        System.out.println(Arrays.toString( r.flip()));
        System.out.println(Arrays.toString( r.flip()));
        System.out.println(Arrays.toString( r.flip()));
        System.out.println(Arrays.toString( r.flip()));


    }
}
