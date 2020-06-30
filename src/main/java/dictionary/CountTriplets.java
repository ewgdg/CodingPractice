package dictionary;
import java.util.HashMap;
import java.util.List;

public class CountTriplets {
    //You are given an array and you need to find number of tripets of indices  such that the elements at those indices are in geometric progression for a given common ratio  and .
    //

    //
    //Complete the countTriplets function in the editor below. It should return the number of triplets forming a geometric progression for a given  as an integer.
    //
    //countTriplets has the following parameter(s):
    //
    //arr: an array of integers
    //r: an integer, the common ratio

    //Sample Input 0
    //
    //1 2 2 4
    //Sample Output 0
    //
    //2 triplets of 1->2->4
    //with index (0,1,3) (0,2,3)

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> arr, long r) {
        //while iterate(from left to right  ,so i <j <k) we store cur number as a candidate into map with key= next expected ,            //val = count of cand of dup

        //2 map
        //first map for cand1 with first num, size/rank= 1
        //second map for cand2 with 2 num ,size/rank= 2
        //or a map with node(val,  size/rank ) as key

        HashMap<Long,Long> map1 = new HashMap<>();
        HashMap<Long,Long> map2 = new HashMap<>();
        long count=0;
        for(Long num:  arr){
            Long next = num*r;
            //search cand for triplet
            if(map2.containsKey(num)){
                count+=map2.get(num);
            }


            //need to update map2 first in case next==cur num
            if(map1.containsKey(num)){
                map2.put(next, map2.getOrDefault(next,0l)+map1.get(num));
            }

            map1.put(next, map1.getOrDefault(next,0l)+1);

        }
        return count;

    }
    //another method for triplet type of question

    //An elegent solution will be to represent our triplets by (, , ), where r is the common ratio and . So, we need to find  and  where .
    //We use two maps. Let's call them  and . Initially, in the  map we store the frequency of all the elements.
    // Now, as we traverse the array elements from left side, we first decrement it's count from right map by 1, then we check the count of aj/r in the left map (say,c1 )
    // and the count of check aj*r in the right map (say, c2). We, increment our answer by c1*c2.
    // At last we increment the count of aj in the  left map by 1 .
    //The intuition behind this is the  map will always hold elements which have indices less than the current index and the  map will hold elements which have indices greater than the current index.
    //Time complexity of this approach: .





}
