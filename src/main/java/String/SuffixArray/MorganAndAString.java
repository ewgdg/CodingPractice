package String.SuffixArray;

import java.util.Arrays;

public class MorganAndAString {
//    Build string from 2 stack of chars, want lexicographically minimal string

    //naively, compare the top char and pick the smaller
    //issue : 2 top char could be same so we need to check the whole suffix
    //--> slow O n^2

    //improvement
    //use suffix array  -O nlogn or O n


    private static void compute(String first, String second){

        StringBuilder result = new StringBuilder();
        String process = first.concat("z").concat(second).concat("z"); //append y or z means if it terminate then it should be the larger one, z >> Z, $ do the opposite behaviour
        int[] suffarr = SuffixArrayBuilder.createSA(process,128,200005);
        System.out.println(Arrays.toString(suffarr));
        int indexf = 0;
        int fsize = first.length();
        int psize = process.length();
        int indexs = fsize+1;

        for(int i = 0; i < process.length(); i++){
            if(indexs > psize-1 && indexf > fsize-1) break;
            if(indexs > psize-1){
                result.append(process.substring(indexf, fsize-1));
                break;
            }
            if(indexf > fsize-1){
                result.append(process.substring(indexs, psize-1));
                break;
            }
            if(suffarr[indexf] < suffarr[indexs]){
                result.append(process.charAt(indexf));
                indexf++;
            }
            else{
                result.append(process.charAt(indexs));
                indexs++;
            }
        }

        System.out.println(result.toString());
    }

    //method 2:

    //char[] s = a.toCharArray();
    //            char[] t = b.toCharArray();
    //            int p = 0, q = 0, r = 0;
    //            char[] ret = new char[s.length+t.length];
    //            outer:
    //            while(r < ret.length){
    //                if(q == t.length){
    //                    ret[r++] = s[p++];
    //                }else if(p == s.length){
    //                    ret[r++] = t[q++];
    //                }else if(s[p] < t[q]){
    //                    ret[r++] = s[p++];
    //                }else if(s[p] > t[q]){
    //                    ret[r++] = t[q++];
    //                }else{
    //                    int i = p, j = q;
    //                    for(;i < s.length && j < t.length;i++,j++){
    //                        if(s[i] < t[j]){
    //                            for(int k = p, o = k;k < i && s[k] == s[o];k++){
    //                                ret[r++] = s[p++];
    //                            }
    //                            continue outer;
    //                        }
    //                        if(s[i] > t[j]){
    //                            for(int k = q, o = k;k < j && t[k] == t[o];k++){
    //                                ret[r++] = t[q++];
    //                            }
    //                            continue outer;
    //                        }
    //                    }
    //                    if(i == s.length){
    //                        for(int k = q, o = k;k < j && t[k] == t[o];k++){
    //                            ret[r++] = t[q++];
    //                        }
    //                        continue outer;
    //                    }
    //                    if(j == t.length){
    //                        for(int k = p, o = k;k < i && s[k] == s[o];k++){
    //                            ret[r++] = s[p++];
    //                        }
    //                        continue outer;
    //                    }
    //                    throw new RuntimeException();
    //                }
    //            }
    //            return new String(ret);
    public static void main(String[] args){
//        System.out.println( (char)('$') + ":"+(int)'$' + ":"+((char)0)+":");
//        compute("DBC","DBCA");
        int i =0;
        int res=0;
        for(i=2;i<=14;i+=3){
            res++;
        }
        if(i>13) res+=100;
        System.out.println(res);
    }

}
