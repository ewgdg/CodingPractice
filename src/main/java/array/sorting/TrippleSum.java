package array.sorting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class TrippleSum {
    //given 3 arrays a b c
    //find all distinct trip  (p,q,r)
    // p belongs to a
    // q -> b
    //r belongs to c
    // and q >= p && q>=r
    // Complete the triplets function below.
    static long triplets(int[] a, int[] b, int[] c) {
        //7 8 9
        //9
        //1 2 3

        //remove dup
        //middle strategy
        //non-decreasing index


        //sort
        Set<Integer> set = Arrays.stream(a).boxed().collect(Collectors.toSet());
        a= set.stream().mapToInt(i->i).toArray();


        set= Arrays.stream(b).boxed().collect(Collectors.toSet());
        b= set.stream().mapToInt(i->i).toArray();

        set=Arrays.stream(c).boxed().collect(Collectors.toSet());
        c=set.stream().mapToInt(i->i).toArray();


        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);

        int j=0,k=0;
        long res=0;
        for(int i=0;i<b.length;i++){
            while(j<a.length && a[j]<=b[i]  ){
                j++;
            }
            while(k<c.length && c[k]<=b[i]  ){
                k++;
            }
            if((long)j*k<=0){
                System.out.println(j);
                System.out.println(k);
            }
            //res+=(long)(j*k); // wrong!!!!! j*k will be cast into int and since j*k might overflow into int so we need to make it long first
            res += (long)j * (long)k;

        }
        return res;




    }

    private static Scanner scanner = null;

    static {
        try {
            scanner = new Scanner(Paths.get("TestCases/TrippleSumTest.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        //145333908482693

        String[] lenaLenbLenc = scanner.nextLine().split(" ");

        int lena = Integer.parseInt(lenaLenbLenc[0]);

        int lenb = Integer.parseInt(lenaLenbLenc[1]);

        int lenc = Integer.parseInt(lenaLenbLenc[2]);

        int[] arra = new int[lena];

        String[] arraItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lena; i++) {
            int arraItem = Integer.parseInt(arraItems[i]);
            arra[i] = arraItem;
        }

        int[] arrb = new int[lenb];

        String[] arrbItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenb; i++) {
            int arrbItem = Integer.parseInt(arrbItems[i]);
            arrb[i] = arrbItem;
        }

        int[] arrc = new int[lenc];

        String[] arrcItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenc; i++) {
            int arrcItem = Integer.parseInt(arrcItems[i]);
            arrc[i] = arrcItem;
        }

        long ans = triplets(arra, arrb, arrc);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

//        bufferedWriter.close();

        scanner.close();
        System.out.println(1e-3);
        System.out.println( (float)(0.3- 0.2*1f));
        System.out.println((float)((float)(0.3- 0.2*1f) -0.1f) );
        System.out.println((float)((float)(0.3- 0.2*1f) -(double)0.1) > 1e-3);

        float[] coins = new float[]{0.01f,0.02f,0.05f,0.1f,0.2f,0.5f,1f,2f};
        float amount=0.32f;
        int j=coins.length-1;
        int res=0;
        while(amount>0){
            float coin = coins[0];
            while(j>=0){
                if(amount>=coins[j]){
                    coin = coins[j];
                    break;
                }
                j--;
            }
            float count = (float)Math.floor(  amount/coin   );
//            System.out.println(amount+" "+coin+" "+count);
            amount = amount - coin*count;
            res+=count;
            System.out.println(amount+" "+coin+" "+count);
        }
        System.out.println(res);
    }
}
