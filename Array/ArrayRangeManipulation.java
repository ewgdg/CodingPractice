import java.util.*;

public class ArrayRangeManipulation {
    //Starting with a 1-indexed array of zeros and a list of operations, for each operation add a value to each of the array element between two given indices, inclusive. Once all operations have been performed, return the maximum value in your array.
    //
    //For example, the length of your array of zeros . Your list of queries is as follows:
    //
    //    a b k
    //    1 5 3
    //    4 8 7
    //    6 9 1
    //Add the values of  between the indices  and  inclusive:
    //
    //index->	 1 2 3  4  5 6 7 8 9 10
    //	[0,0,0, 0, 0,0,0,0,0, 0]
    //	[3,3,3, 3, 3,0,0,0,0, 0]
    //	[3,3,3,10,10,7,7,7,0, 0]
    //	[3,3,3,10,10,8,8,8,1, 0]
    //The largest value is  after all operations are performed.


    //Sub-Optimal Brute Force:
    //
    //Given each update , for each index in the range from [, ], add the value  to each number in the range.
    //
    //The final step is to go through the whole array and find the maximum value and print that maximum value.
    //
    //The complexity of this solution is O() which is too high to pass in time.
    //
    //Optimal:n+m , but n>>m
    //
    //Given a range[, ] and a value  we need to add  to all the numbers whose indices are in the range from [, ].
    //We can do an O() update by adding  to index  and add  to index .
    //Doing this kind of update, the  number in the array will be prefix sum of array from index 1 to i because we are adding to the value at index  and subtracting  from the value at index  and taking prefix sum will give us the actual value for each index after  operations .
    //So, we can do all  updates in O(m) time. Now we have to check the largest number in the original array. i.e. the index i such that prefix sum attains the maximum value.
    //We can calculate all prefix sums as well as maximum prefix sum in O(n) time which will execute in time.
    //Optimal: mlogm
    //
    //This can be further optimized to run in O(m logm) time because we have to check the value of prefix sum at only indices. i.e.  and  values of all the updates.
    //We have, in total  queries and each query has a range [, ] which needs to be updated. So, in total we have indices.
    //For each query, we can insert both  and  in an array and sort the array.
    //Now, we have to just take the prefix sum of the array and find the maximum element which will be our answer.
    //
    //Check the setter's code for better understanding.

    //similar method for 2D
    //record the val at leftbottom point and then
    // val at upRight+1+1
    // -val at upLeft and bottomRight corner
    //update prefixSum for each row
    //then based on the updated matrix to update the prefixSum for each Col



    public int solve(int[][] nums){
        int m =  nums.length;
        int n = m==0?0: nums[0].length;

        //getting prefix sum
        //for a range of adding k, we can record it as  +k at a, -k at b+1
        //and calculate the prefix sum
        //to neglect iterate all n elem
        //we just record critial points and sort them for iteration

        //to sort an entry node, we can create a class or
        //we can use customized comparator, doesnt work , only work if the sorted one is index so we can get val

//        int[] critical_index = new int[m];
//        int[] critical_val  = new int[m];

        //actually we can just use hashMap entry, must group all val of same index together before we cal max

        List<Node> critical_points= new ArrayList<>();
        HashMap<Integer,Integer> map = new HashMap<>();
        int p=0;
        for(int[] row : nums){
            int c1 = row[0];
            int c2 = row[1];
            int val  =row[2];
            map.put(c1,map.getOrDefault(c1,0)+val);
            map.put(c2+1,map.getOrDefault(c2+1,0)-val);



        }
        for(Map.Entry<Integer,Integer>  entry : map.entrySet()){
            critical_points.add( new Node(entry.getKey(),entry.getValue()) );
        }

        Collections.sort(critical_points,(a,b)->a.index-b.index );

        int max =Integer.MIN_VALUE;
        int prefixSum=0;
        for(Node node : critical_points){
            //while next node.index == index
            prefixSum+=node.val;
            max= Math.max(max,prefixSum);
        }
        return max;




    }
    class Node{
        int index;
        int val;
        public Node(int i, int v){
            index= i; val = v;
        }
    }


}
