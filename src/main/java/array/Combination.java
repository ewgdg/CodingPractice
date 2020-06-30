package Array;

import java.util.ArrayList;
import java.util.List;

public class Combination {

    //method 1 bt , without cache O 2^n , with cache O(n^2)*number of res from sub prob ~= 2^n  ( or ~=n!?? )
    //T(n) = T(n-1) + T(n-2) + ... T(1) , as we can notice T(n-1) = T(n-2)+...T(1) , so T(n) = 2T(n-1) , ~= 2*2*2*2....2*T(1) = 2^n


    //method 2 binary expression from 000000 to 111111, 0 indicate the existence of letter in that pos
    //method 3 bfs or double for loop , for each char for each list in curRes append char to the list , concat the res to prev res
    //method 4 binary dfs, for each char 2 branch take it or not

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new ArrayList<Integer>() , 1, n,k  );
        return res;

    }

    public void backtrack(List<List<Integer>> res, List<Integer> tempList, int start, int n , int k){
        if(tempList.size()==k ){
            res.add(new ArrayList<>(tempList) );
            return;
        }

        for(int i = start; i<=n ; i++){
            tempList.add(i);
            backtrack(res,tempList,i+1,n,k);
            tempList.remove(tempList.size()-1);
        }


    }
}
