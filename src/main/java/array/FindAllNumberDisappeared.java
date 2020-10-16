package array;

import java.util.ArrayList;
import java.util.List;

/*
Find All Numbers Disappeared in an Array
Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements of [1, n] inclusive that do not appear in this array.

Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.

Example:

Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]


*/
public class FindAllNumberDisappeared {

  //sol1, convert input array into counter
  public List<Integer> findDisappearedNumbers(int[] nums) {
        
    //convert the input array into counter
    //negative number indicates count
    
    for(int i=0;i<nums.length;i++){
        int num= nums[i];
        if(num>0){
            if(nums[num-1]>0){

              if(num-1>i){
                
                nums[i]=nums[num-1];
                i--;
              }
                
              nums[num-1]=-1;
               
            }else{
                nums[num-1]-=1;
            }
        }
    }
    
    List<Integer> res = new ArrayList<>();
    for(int i=0;i<nums.length;i++){
        if(nums[i]>0){
            res.add(i+1);
        }
    }
    return res;
  }

  //sol2, swap to the proper sorted position
  public List<Integer> sol2(int[] nums){


    for(int i=0;i<nums.length;i++){
      int num= nums[i];
      if(i+1!=num && nums[num-1]!=num){
        nums[i]=nums[num-1];
        nums[num-1]=num;
        if(num-1>i){
          i--;
        }
      }
      
    }

    List<Integer> res = new ArrayList<>();

    for(int i=0;i<nums.length;i++){
      int num= nums[i];
      if(num!=i+1){
        res.add(i+1);
      }

    }

    
    return res;

  }
  //sol3, mark the index
  //by converting nums[i] to negative val if found the matched num-1==i
  

  
}