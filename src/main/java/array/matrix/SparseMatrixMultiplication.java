package array.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 311. Sparse Matrix Multiplication
// Medium

// 539

// 208

// Add to List
// Sparse Matrix: most of entries are 0;
// Share
// Given two sparse matrices A and B, return the result of AB.

// You may assume that A's column number is equal to B's row number.

// Example:

// Input:

// A = [
//   [ 1, 0, 0],
//   [-1, 0, 3]
// ]

// B = [
//   [ 7, 0, 0 ],
//   [ 0, 0, 0 ],
//   [ 0, 0, 1 ]
// ]

// Output:

//      |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
// AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
//                   | 0 0 1 |

// Constraints:

// 1 <= A.length, B.length <= 100
// 1 <= A[i].length, B[i].length <= 100
// -100 <= A[i][j], B[i][j] <= 100
public class SparseMatrixMultiplication {
  // matrix mul involves 3 nested loop i*j*k
  // i is rows from A, j is cols from B , k is cols from A and rows from B
  // we want to skip 0 entries so we need to use hashmap keyset

  // cacheA store entries from A in row,col format
  // cacheB do the other way in col,row format
  //the issue of this sol is that it takes extra mem, we can further optimize it
  //space complexity roughly ~O m+n but the worst case is O m*n
  public int[][] multiply(int[][] A, int[][] B) {
    int m = B.length;
    int n = m == 0 ? 0 : B[0].length;

    // preprocess b
    // if a row is all 0 , we can ignore that corresponding col in A
    // store B in hashmap for smaller mem?? cache locality
    // still need to store B into HashMap to skip 0 entry during nested loop
    // but this time store mapping of col,row instead of row,col
    Map<Integer, Map<Integer, Integer>> cacheB = new HashMap<>();
    boolean[] ignored = new boolean[m];
    for (int i = 0; i < m; i++) {
      boolean found = true;
      for (int j = 0; j < n; j++) {

        if (B[i][j] != 0) {
          cacheB.computeIfAbsent(j, (key) -> new HashMap<>()).put(i, B[i][j]);
          found = false;
        }

      }
      ignored[i] = found;//to further trim cols of A
    }

    // hashmap to store A so that we can skip ignored and 0
    Map<Integer, Map<Integer, Integer>> cacheA = new HashMap<>();
    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < m; j++) {
        if (!ignored[j] && A[i][j] != 0) {
          cacheA.computeIfAbsent(i, (key) -> new HashMap<>()).put(j, A[i][j]);
        }
      }
    }




    int[][] res = new int[A.length][n];
    for (Integer i : cacheA.keySet()) {
      for (Integer j : cacheB.keySet()) {
        int sum = 0;
        Map<Integer, Integer> rowA = cacheA.getOrDefault(i, new HashMap<>());
        Map<Integer, Integer> colB = cacheB.getOrDefault(j, new HashMap<>());

        Map<Integer, Integer> smallMap = rowA.size() > colB.size() ? colB : rowA;
        Map<Integer, Integer> largeMap = rowA.size() > colB.size() ? rowA : colB;
        for (Integer k : smallMap.keySet()) {

          sum += smallMap.get(k) * largeMap.getOrDefault(k, 0);
        }
        res[i][j] = sum;
      }

    }
    return res;

  }

  //directly store critical rows from A and critical cols from B and critical k
  //save some space cost, space complexity O m+n
  //worst case time complexity , m1*n2*n1 = 100*100*100=100,0000 when all nums stay in diagonal
  public int[][] multiply2(int[][] A, int[][] B) {
    int m1 = A.length;
    int n1 = m1 == 0 ? 0 : A[0].length;
    int m2 = B.length;
    int n2 = m2 == 0 ? 0 : B[0].length;

   
    int[][] res = new int[m1][n2];

    //i is indices for rows A
    Set<Integer> criticalIndicesI = new HashSet<>();
    //j is indices for cols B
    Set<Integer> criticalIndicesJ = new HashSet<>();
    //k is index for n1/m2;
    Set<Integer> criticalIndicesK = new HashSet<>();
    Set<Integer> criticalIndicesK1 = new HashSet<>();
    
    

    for(int i=0;i<m1;i++){
      for(int j=0;j<n1;j++){
        if(A[i][j]!=0){
          criticalIndicesI.add(i);
          criticalIndicesK1.add(j);
        }
      }
    }
    for(int i=0;i<m2;i++){
      for(int j=0;j<n2;j++){
        if(B[i][j]!=0){
          criticalIndicesJ.add(j);
          //an index k is critical if it is a shared index k for both matrix A and B
          if(criticalIndicesK1.contains(i)){
            criticalIndicesK.add(i);
          }
        }
      }
    }
    

    for(Integer i:criticalIndicesI){
      for(Integer j: criticalIndicesJ){
        
        for(Integer k: criticalIndicesK){
          res[i][j]+=A[i][k]*B[k][j];
        }
      }

    }






    return res;

  }

  //another instersting way , the best sol based on the contraints
  //compressed space rows csr and csc method
  //notice we dont have to follow the order when we calc res
  //we just iterate all non zero number and mulplity then accumulate the product to the corresponding res[i][j]+=product
  //worst case complexity 
  // max critical val * max critical val , based on the question the worst case is 100*100 =10000
  // if no such constraints m1*n1*m2*n2=10000^2 
  //avg time complexity if contraints is 0~m1+n1,   (m1+n1) * (m2+n2) = m1m2+m1n2+n1m2+n1n2 = 40000
  // space complexity = 100+100 =200
//   def to_csr(A):
//     row_idx, col_idx, vals = [], [], []
//     for y in range(len(A[0])):
//         for x in range(len(A)):
//             val = A[x][y]
//             if val:
//                 row_idx.append(x)
//                 col_idx.append(y)
//                 vals.append(val)
//     return list(zip(row_idx, col_idx, vals))


// def to_csc(A):
//     row_idx, col_idx, vals = [], [], []
//     for x in range(len(A)):
//         for y in range(len(A[0])):
//             val = A[x][y]
//             if val:
//                 row_idx.append(x)
//                 col_idx.append(y)
//                 vals.append(val)
//     return list(zip(row_idx, col_idx, vals))
    

// class Solution:
//     def multiply(self, A: List[List[int]], B: List[List[int]]) -> List[List[int]]:
//         N, M = len(A), len(B[0])
//         res_mat = [[0] * M for i in range(N)]
//         # compressed sparse row / column format
//         A_csr = to_csr(A)
//         B_csc = to_csc(B)
//         # NOTE: the 2x for loop doesn't take advantage of sorted rows in A_csr or cols in B_csc
//         for a_x, a_y, a_val in A_csr:
//             for b_x, b_y, b_val in B_csc:
//                 if a_y == b_x:
//                     res_mat[a_x][b_y] += a_val*b_val
//         return res_mat
  
  
  public int[][] multiply3(int[][] A, int[][] B) {
    // store tuple (i,j,val) wher val !=0
    List<int[]> criticalValsA = new ArrayList<>();
    List<int[]> criticalValsB = new ArrayList<>();
    if(B.length==0||A.length==0) return new int[0][0];
    int[][] res = new int[A.length][B[0].length];

    for(int i=0;i<A.length;i++){
      for(int j=0;j<A[0].length;j++){
        if(A[i][j]!=0){
          criticalValsA.add(new int[]{i,j,A[i][j]});
        }
      }
    }
    for(int i=0;i<B.length;i++){
      for(int j=0;j<B[0].length;j++){
        if(B[i][j]!=0){
          criticalValsB.add(new int[]{i,j,B[i][j]});
        }
      }
    }
    

    for(int[] valA : criticalValsA){
      for(int[] valB: criticalValsB){
        if(valA[1]==valB[0]){
          res[valA[0]][valB[1]]+=valA[2]*valB[2];
        }

      }
    }

    return res;
  }
  

}
