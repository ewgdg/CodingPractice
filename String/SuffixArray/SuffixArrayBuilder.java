package SuffixArray;

public class SuffixArrayBuilder {

//    O nlogn
    public static int[] createSA(String input, int letters, int maxsize){

        int[] first = new int[maxsize];
        int[] second = new int[maxsize];
        int[] third = new int[maxsize];
        int[] count = new int[maxsize];
        int[] suffarr = new int[maxsize];

        int size = input.length();
        char[] arr = input.toCharArray();
        for (int i = 0; i < size; i++) count[arr[i]]++;
        for (int j = 1; j < letters; j++) count[j] += count[j-1];
        for (int k = 0; k < size; k++) first[--count[arr[k]]] = k; // a sorted index : map alphabetical order to index
        int sum = 1;
        suffarr[first[0]] = 0;
        for (int i = 1; i < size; i++) {
            if (arr[first[i]] != arr[first[i-1]]) sum++;
            suffarr[first[i]] = sum - 1;//convert char array to id array , id = order of that char
        }
        for (int m = 0; (1<<m) < size; m++) {
            for (int i = 0; i < size; i++){
                second[i] = first[i] - (1<<m); // find the second index to form a substring
                if (second[i] < 0) second[i] += size;
            }
            count = new int[maxsize];
            for (int i = 0; i < size; i++) count[suffarr[i]]++;
            for (int j = 1; j < sum; j++) count[j] += count[j-1];
            for (int k = size-1; k >= 0; k--) first[--count[suffarr[second[k]]]] = second[k];
            sum = 1;
            third[first[0]] = 0;
            for (int i = 1; i < size; i++){
                int pos1 = (first[i] + (1<<m))%size;
                int pos2 = (first[i-1] + (1<<m))%size;
                if (suffarr[first[i]] != suffarr[first[i-1]] || suffarr[pos1] != suffarr[pos2]) sum++;
                third[first[i]] = sum - 1;
            }
            for (int i = 0; i < size; i++) suffarr[i] = third[i];
        }
        return suffarr;//notice the suffixarray is mapping index to order
    }

//    public static int[] createSA2(String input){
//        int size=128;
//        int n = input.length();
//        size=  Math.max(size,n);
//        int[] count  = new int[size];
//
//        int[] order_index1 = new int[n];
//        int[] order_index2 = new int[n];
//        int[] temp_suffArr = new int[n];
//
//        int[] suffarr = new int[n];
//
//
//        char[] arr = (input+'$').toCharArray();
//
//
//        //radix sort
//        for(char c: arr){
//            int index = c-'$';
//            count[ ]
//        }
//
//        for (int i = 0; i < size; i++) count[arr[i]]++;
//        for (int j = 1; j < letters; j++) count[j] += count[j-1];
//        for (int k = 0; k < size; k++) first[--count[arr[k]]] = k; // a sorted index : map alphabetical order to index
//        int sum = 1;
//        suffarr[first[0]] = 0;
//        for (int i = 1; i < size; i++) {
//            if (arr[first[i]] != arr[first[i-1]]) sum++;
//            suffarr[first[i]] = sum - 1;//convert char array to id array , id = order of that char
//        }
//        for (int m = 0; (1<<m) < size; m++) {
//            for (int i = 0; i < size; i++){
//                second[i] = first[i] - (1<<m); // find the second index to form a substring
//                if (second[i] < 0) second[i] += size;
//            }
//            count = new int[maxsize];
//            for (int i = 0; i < size; i++) count[suffarr[i]]++;
//            for (int j = 1; j < sum; j++) count[j] += count[j-1];
//            for (int k = size-1; k >= 0; k--) first[--count[suffarr[second[k]]]] = second[k];
//            sum = 1;
//            third[first[0]] = 0;
//            for (int i = 1; i < size; i++){
//                int pos1 = (first[i] + (1<<m))%size;
//                int pos2 = (first[i-1] + (1<<m))%size;
//                if (suffarr[first[i]] != suffarr[first[i-1]] || suffarr[pos1] != suffarr[pos2]) sum++;
//                third[first[i]] = sum - 1;
//            }
//            for (int i = 0; i < size; i++) suffarr[i] = third[i];
//        }
//        return suffarr;//notice the suffixarray is mapping index to order
//    }

    //or build a suffix tree
}
