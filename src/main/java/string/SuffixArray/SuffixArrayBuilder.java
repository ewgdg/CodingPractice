package string.SuffixArray;

// import java.math.BigInteger;
import java.util.Arrays;

public class SuffixArrayBuilder {

    // O nlogn

    public static int[] createSA(String input, int letters, int maxsize) {

        // a subarray can be viewed as left id + right id

        // char[] arr = (input+'$').toCharArray(); to indicate the last char
        // The suffix array contains the starting positions of these sorted suffixes

        int[] first = new int[maxsize];// right index , sorted index
        int[] second = new int[maxsize];// extend to left index, updated first
        int[] third = new int[maxsize];// find dup assign id/rank
        int[] count = new int[maxsize];
        int[] suffarr = new int[maxsize];// same as third , to return

        int size = input.length();
        char[] arr = input.toCharArray();
        for (int i = 0; i < size; i++)
            count[arr[i]]++;
        for (int j = 1; j < letters; j++)
            count[j] += count[j - 1];
        for (int k = 0; k < size; k++)
            first[--count[arr[k]]] = k; // a sorted index : map alphabetical order to index
        int sum = 1;
        suffarr[first[0]] = 0;
        for (int i = 1; i < size; i++) {
            if (arr[first[i]] != arr[first[i - 1]])
                sum++;
            suffarr[first[i]] = sum - 1;// convert char array to id array , id = order of that char
        }

        // System.out.println(Arrays.toString(first));
        // System.out.println(Arrays.toString(suffarr));
        for (int m = 0; (1 << m) < size; m++) {
            for (int i = 0; i < size; i++) {
                second[i] = first[i] - (1 << m); // find the second index to form a substring , place the second index
                                                 // on the position of first(right) index, for the purpose of stable
                                                 // sort
                if (second[i] < 0)
                    second[i] += size;
            }
            count = new int[maxsize];
            for (int i = 0; i < size; i++)
                count[suffarr[i]]++; // same as count[suffarr[second[i]]]++
            for (int j = 1; j < sum; j++)
                count[j] += count[j - 1];
            for (int k = size - 1; k >= 0; k--)
                first[--count[suffarr[second[k]]]] = second[k];
            sum = 1;
            third[first[0]] = 0;
            for (int i = 1; i < size; i++) {// find dup
                int pos1 = (first[i] + (1 << m)) % size;// find right id for i
                int pos2 = (first[i - 1] + (1 << m)) % size;
                if (suffarr[first[i]] != suffarr[first[i - 1]] || suffarr[pos1] != suffarr[pos2])
                    sum++;
                third[first[i]] = sum - 1;
            }
            for (int i = 0; i < size; i++)
                suffarr[i] = third[i];
        }
        return suffarr;// notice the suffixarray is mapping index to order
    }

    // A suffix array will contain integers that represent the starting indexes of
    // the all the suffixes of a given string,
    // after the aforementioned suffixes are sorted.
    // https://cp-algorithms.com/string/suffix-array.html

    // As an example look at the string s=abaab.
    // the suffix array for s will be (2, 3, 0, 4, 1).
    // aab
    // ab
    // abaab
    // b
    // baab

    // sort cyclic substrings, abaab$ , equilvalent to suffix array, 
    // append $, $ < all chars in the input string
    // aab$ab ,start index 2
    // ab$aba , index = 3
    // abaab$ , index = 0
    // b$abaa , 4
    // baab$a , 1

    // sparse table approach
    // The algorithm we discuss will perform ⌈logn⌉+1 iterations. In the k-th
    // iteration (k=0…⌈logn⌉) we sort the n cyclic substrings of s of length 2^k.
    // After the ⌈logn⌉-th iteration the substrings of length 2^⌈logn⌉≥n will be
    // sorted, so this is equivalent to sorting the cyclic shifts altogether.

    // a more readable implentation
    // input is appended with $
    public static int[] buildSA(String input) {
        char[] s = input.toCharArray();
        int n = input.length();
        // p[i] at kth iter is the index of the i-th substring (starting with
        // input[index=p[i]] at index and with length 2^k) in the sorted order
        // p is sorted subarray-index that maps sorted order/rank to subarray index
        // it is equilvalent to suffix array
        int[] p = new int[n];

        // c[i] corresponds to the equivalence class to which the substring(starting
        // with input[i] and with length 2^k) belongs.
        // because some of the substrings will be identical, and the algorithm needs to
        // treat them equally
        // c[i] will be assigned in such a way that they preserve information about the
        // order
        // if all subarrays are different, then c[p[i]] = i
        // c maps subarray index to sorted class label 
        // e.g input = aaba -> 0th iter -> subarrays = [a a b a] -> sorted subarray = [a
        // a a b] -> p = [0, 1, 3, 2] -> c = [0,0,1,0]
        int[] c = new int[n];

        // 0th iteration , counting sort , init p and c , k=0
        int[] cnt = new int[Math.max(256, n)]; //there could be as much as max(number of chars,n) classes 
        for (int i = 0; i < n; i++) {
            cnt[s[i]]++;
        }
        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            p[--cnt[s[i]]] = i;
        }
        // builc c based on p
        int classes = 1; // number different classes
        c[p[0]] = classes - 1;
        for (int i = 1; i < n; i++) {
            if (s[p[i]] != s[p[i - 1]]) { // bc it is the 0th iter, we only need to compare one letter
                classes++;
            }
            c[p[i]] = classes - 1;
        }

        // deal with the rest iterations
        // for two substrings of length 2^k starting at position i and j,
        // all necessary information to compare the 2 substrings is contained
        // in the pairs (c[i] at k-1 iter with subarray len= 2^(k-1) , c[i+2^k−1]) and
        // (c[j], c[j+2^k−1]).
        // construct p with radix sort , sort second half subarray then the first half
        for (int h = 0; (1 << h) < n; h++) { //index h = k-1
            // pn is temp p based on order of second half elem
            int[] pn = new int[n];
            for (int i = 0; i < n; i++) {
                pn[i] = p[i] - (1 << h); // p[i] is the index of second half , to get the real index we need to
                                         // substract the half len
                if(pn[i]<0){//notice this is a circular substring and the index need to be positive
                    pn[i]+=n;
                }
            }
            Arrays.fill(cnt, 0);
            // sort on the first half with counting sort , sort by the class labels
            // can we just simply flip p such that p[p[i]]=i?
            // no, bc we are doing radix sort here， we need a stable sort that utilize order
            // from pn
            for (int i = 0; i < n; i++) {
                cnt[c[pn[i]]]++;
            }
            for (int i = 1; i < n; i++) {
                cnt[i]+=cnt[i-1];
            }

            for (int i = n - 1; i >= 0; i--) {
                p[--cnt[c[pn[i]]]] = pn[i];
            }

            classes = 1;
            // cn is temp c , new c for cur iter
            int[] cn = new int[n];
            cn[p[0]] = classes - 1;
            for (int i = 1; i < n; i++) {
                int substringIndex = p[i];
                int substringIndexPrev = p[i-1];
                // compare subtrings of index and index-1
                if (c[substringIndex] != c[substringIndexPrev]
                        || c[ (substringIndex + (1 << h)) %n] != c[ (substringIndexPrev + (1 << h))%n  ]  ) {
                    classes += 1;
                }
                cn[substringIndex] = classes - 1;
            }

            c = cn;

        }
        return p;

    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(createSA("aba$", 100, 256)));
        System.out.println(Arrays.toString(buildSA("aba$")));
    }
    // public static int[] createSA2(String input){
    // int size=128;
    // int n = input.length();
    // size= Math.max(size,n);
    // int[] count = new int[size];
    //
    // int[] order_index1 = new int[n];
    // int[] order_index2 = new int[n];
    // int[] temp_suffArr = new int[n];
    //
    // int[] suffarr = new int[n];
    //
    //
    // char[] arr = (input+'$').toCharArray();
    //
    //
    // //radix sort
    // for(char c: arr){
    // int index = c-'$';
    // count[ ]
    // }
    //
    // for (int i = 0; i < size; i++) count[arr[i]]++;
    // for (int j = 1; j < letters; j++) count[j] += count[j-1];
    // for (int k = 0; k < size; k++) first[--count[arr[k]]] = k; // a sorted index
    // : map alphabetical order to index
    // int sum = 1;
    // suffarr[first[0]] = 0;
    // for (int i = 1; i < size; i++) {
    // if (arr[first[i]] != arr[first[i-1]]) sum++;
    // suffarr[first[i]] = sum - 1;//convert char array to id array , id = order of
    // that char
    // }
    // for (int m = 0; (1<<m) < size; m++) {
    // for (int i = 0; i < size; i++){
    // second[i] = first[i] - (1<<m); // find the second index to form a substring
    // if (second[i] < 0) second[i] += size;
    // }
    // count = new int[maxsize];
    // for (int i = 0; i < size; i++) count[suffarr[i]]++;
    // for (int j = 1; j < sum; j++) count[j] += count[j-1];
    // for (int k = size-1; k >= 0; k--) first[--count[suffarr[second[k]]]] =
    // second[k];
    // sum = 1;
    // third[first[0]] = 0;
    // for (int i = 1; i < size; i++){
    // int pos1 = (first[i] + (1<<m))%size;
    // int pos2 = (first[i-1] + (1<<m))%size;
    // if (suffarr[first[i]] != suffarr[first[i-1]] || suffarr[pos1] !=
    // suffarr[pos2]) sum++;
    // third[first[i]] = sum - 1;
    // }
    // for (int i = 0; i < size; i++) suffarr[i] = third[i];
    // }
    // return suffarr;//notice the suffixarray is mapping index to order
    // }

    // or build a suffix tree

}
