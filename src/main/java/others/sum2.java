package others;

import java.util.*;

public class sum2 {

    //Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] + B[j] + C[k] + D[l] is zero.
    //
    //To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {

//        HashMap<Integer,Integer> a = new HashMap<>();
//        HashMap<Integer,Integer> b = new HashMap<>();
//        HashMap<Integer,Integer> c = new HashMap<>();
//        HashMap<Integer,Integer> d = new HashMap<>();
//
//        for(int num: A){
//            if(a.containsKey(num)){
//                a.put(num,a.get(num)+1);
//            }else{
//                a.put(num,1);
//            }
//        }
//
//        for(int num: B){
//            if(b.containsKey(num)){
//                b.put(num,b.get(num)+1);
//            }else{
//                b.put(num,1);
//            }
//        }
//
//        for(int num: C){
//            if(c.containsKey(num)){
//                c.put(num,c.get(num)+1);
//            }else{
//                c.put(num,1);
//            }
//        }
//
//        for(int num: D){
//            if(d.containsKey(num)){
//                d.put(num,d.get(num)+1);
//            }else{
//                d.put(num,1);
//            }
//        }
//
//
//
//        class Link{
//            public int sum;
//            public int layer;
//            public int duplicate;
//
//            public Link(int sum,int layer,int dup){
//                this.sum=sum;
//                this.layer=layer;
//                this.duplicate=dup;
//            }
//        }
//        Stack<Link> open = new Stack<>();
//
//
//        Link init = new Link(0,0,1);
//        int count=0;
//
//
//        open.add(init);
//        while(!open.empty()){
//            Link cur=open.pop();
//
//
//            int layer = cur.layer;
//            switch (layer){
//                case 0:
//                    for(Map.Entry<Integer,Integer> entry: a.entrySet()) {
//                        open.add(new Link(cur.sum + entry.getKey(),layer+1, entry.getValue()*cur.duplicate));
//                    }
//                    break;
//
//                case 1:
//                    for(Map.Entry<Integer,Integer> entry: b.entrySet()) {
//                        open.add(new Link(cur.sum + entry.getKey(),layer+1, entry.getValue()*cur.duplicate));
//                    }
//                    break;
//                case 2:
//                    for(Map.Entry<Integer,Integer> entry: c.entrySet()) {
//                        open.add(new Link(cur.sum + entry.getKey(),layer+1, entry.getValue()*cur.duplicate));
//                    }
//                    break;
//                case 3:
//                    for(Map.Entry<Integer,Integer> entry: d.entrySet()) {
//                        open.add(new Link(cur.sum + entry.getKey(),layer+1, entry.getValue()*cur.duplicate));
//                    }
//                    break;
//
//                case 4:
//                    if(cur.sum==0){
//                        count+=cur.duplicate;
//                    }
//                    break;
//
//
//
//
//            }
//
//        }
//        return count;



//        int count =0;
//
//
//        HashMap<Integer,Integer> summedup = new HashMap<>();
//
//
//        for(int num:C){
//            for(int num2:D){
//                int sum= num+num2;
//                if(summedup.containsKey(sum)){
//                    summedup.put(sum,summedup.get(sum)+1);
//                }else{
//                    summedup.put(sum,1);
//                }
//
//            }
//        }
//
//        Set<Map.Entry<Integer,Integer>> entrys = summedup.entrySet();
//        summedup = new HashMap<>();
//        for(int num:B){
//            for(Map.Entry<Integer,Integer> entry:entrys){
//                int sum= num+entry.getKey();
//                if(summedup.containsKey(sum)){
//                    summedup.put(sum,summedup.get(sum)+entry.getValue());
//                }else{
//                    summedup.put(sum,entry.getValue());
//                }
//            }
//        }
//
//
//
//
//
//        entrys = summedup.entrySet();
//        summedup = new HashMap<>();
//        for(int num:A){
//            for(Map.Entry<Integer,Integer> entry:entrys){
//                int sum= num+entry.getKey();
//                if(sum==0) count+=entry.getValue();
//            }
//        }
//        return  count;

        Map<Integer,Integer> seen = new HashMap<Integer,Integer>();

        int count=0;
        for(int num:C){
            for(int num2:D){
                int sum= num+num2;
                seen.put(sum,seen.getOrDefault(sum,0)+1);

            }
        }

        for(int num: A){
            for(int num2:B){
                int sum=num+num2;
                int expected = 0-sum;
                count += seen.getOrDefault(expected,0);

            }
        }

        return count;


    }

}
