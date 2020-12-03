package array.matrix;

import java.util.*;

// On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.

// We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their assigned bike is minimized.

// The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.

// Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.


//greedy doent really work
//2 ideas , dp + memoization
//uniform cost search to traverse the problem tree - > worst case not improvement but best case can be better if the optimal cost is way smaller than other path 

public class CampusBikesII {

  //dp sol
  //notice the assigned bike array itself can be used as key cause the unused bikes indicates the current worker index
  class Solution {
    public int assignBikes(int[][] workers, int[][] bikes) {
        
        
        Map<Integer,Integer> mem = new HashMap<>();
        
        return dfs(workers,bikes,0,0,mem);
    }
    
    public int dfs(int[][] workers, int[][] bikes, int workerIndex, int assignment, Map<Integer,Integer> mem ){
        //terminating
        if(workerIndex==workers.length){
            return 0;
        }
        
        if(mem.containsKey(assignment)){
            return mem.get(assignment);
        }
        
        int min=Integer.MAX_VALUE;
        for(int i=0;i<bikes.length;i++){
            if(  (assignment & (1<<i)) == 0 ){//never assigned
                int cost =  dist(workers[workerIndex],bikes[i]) + dfs(workers,bikes,workerIndex+1, assignment|(1<<i) , mem );
                min = Math.min(cost,min);
                
            }
            
        }
        
        
        mem.put(assignment,min);
        
        return min;
        
    }
    
    public int dist(int[] worker, int[] bike){
        return Math.abs(worker[0]-bike[0]) + Math.abs(worker[1]-bike[1]);
    }
}

  // ucs sol
  // def assignBikes(self, workers, bikes):
  // def dis(i, j):
  //     return abs(workers[i][0] - bikes[j][0]) + abs(workers[i][1] - bikes[j][1])
  // h = [[0, 0, 0]]
  // seen = set()
  // while True:
  //     cost, i, taken = heapq.heappop(h)
  //     if (i, taken) in seen: continue
  //     seen.add((i, taken))
  //     if i == len(workers):
  //         return cost
  //     for j in xrange(len(bikes)):
  //         if taken & (1 << j) == 0:
  //             heapq.heappush(h, [cost + dis(i, j), i + 1, taken | (1 << j)])
}
