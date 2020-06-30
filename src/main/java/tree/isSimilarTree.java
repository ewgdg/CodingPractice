package Tree;
public class isSimilarTree {
    
    //给你两棵binary tree，tree的左右子树可以任意替换。如果第一个tree可以各种替换子树的左右子树，变成另外一个tree，就是similar。写个算法求是否similar. 用了recursive解出来

    public boolean solution(Node a, Node b){
        if(a==null && b==null) return true;
        else if(a==null||b==null) return false;
        else if(a.val!=b.val) return false;


        return (solution(a.left,b.left)&&solution(a.right,b.right) )
                || (solution(a.left,b.right)&&solution(a.right,b.left));


    }

    //T(n) = 4*T(n/2) + 1
    //master theorem   aT(n/b) + f(n)  if dominating factor is n^(logb(a))  >> f(n)  then complexity T(n) = n^c where c = logb(a)
    //if f(n) =   (log(n))^k  *  n^c then T(n) = (log(n))^k+1 * n^c
    //else if af(n/b) <<< kf(n)   &&   f(n) >>> n^c  then T = f(n)

    // depth = log2(n)
    //branch factor = 4
    // total nodes = 4^(log2(n))  , let n = 4^log4(n) --> then total nodes = n^(log2(4)) = n^2 =T(n)
    
    // follow up : reduce the constant factor a from 4 to 3.


    //https://www.geeksforgeeks.org/tree-isomorphism-problem/

    //for each layer
    //calculate the diff tree of left and right sub for both a, b
    //if the diff tree are same , compare  a,b on left sub , if same return true
    // if diff are negative of another compare a left to b right , if same return true
    //else go to next layer



}
