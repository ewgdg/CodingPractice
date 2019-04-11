import java.util.Arrays;
import java.util.HashMap;

public class evaluateDivision {
//
//
//    Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
//
//    Example:
//    Given a / b = 2.0, b / c = 3.0.
//    queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
//            return [6.0, 0.5, -1.0, 1.0, -1.0 ].
//
//    The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.
//
//    According to the example above:
//
//    equations = [ ["a", "b"], ["b", "c"] ],
//    values = [2.0, 3.0],
//    queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
//    The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.


    //follow up what is query is very large . -> precompute with floyd-wash
    public static double[] solution(String[][] equations, double[] values, String[][] queries) {
        DisjointSet disjointSet = new DisjointSet();
        int n= equations.length;
        //union find method for common ancestor
        for(int i =0 ; i<n;i++ ){
            String[] eq = equations[i];
            disjointSet.makeSet(eq[0],1);
            disjointSet.makeSet(eq[1],1);
            disjointSet.union(eq[0],eq[1],values[i]);

        }

        double[] res = new double[queries.length];
        for(int i =0; i< queries.length; i++){
            String[] query= queries[i];
            res[i] = disjointSet.calculate(query[0],query[1]);

        }
        return res;

    }

    static class DisjointSet{
        HashMap<String,Node> map;
        class Node{
            String symbol;
            Node parent;
            double weightToParent;    //==parent/val

            int rank;
            public Node(String symbol, double weightToParent){
                this.symbol=symbol;
                this.weightToParent=weightToParent;
                this.parent=this;
                rank=0;
            }
        }

        public DisjointSet(){
            map = new HashMap<>();
        }

        public void makeSet(String symbol,double weight){
            if(!map.containsKey(symbol)){
                Node node = new Node(symbol,weight);
                map.put(symbol,node);
            }
        }

        public String find(String symbol){
            return find(map.get(symbol)).symbol;
        }
        public Node find(Node node){
            Node cur = node;
            if(cur.parent!=cur){
                cur.parent = find(cur.parent);
                cur.weightToParent *= cur.parent.weightToParent;
            }

            return cur.parent;
        }

        public void union(String d1, String d2, double weight){
            Node n1 = map.get(d1);
            Node n2 = map.get(d2);
            if(n1==null || n2==null) return;

            Node p1 = find(n1);
            Node p2 = find(n2);

            if(p1==p2){
                return;
            }


            p2.parent=p1;
            p2.weightToParent = weight * n1.weightToParent/n2.weightToParent; //p1/p2 = n1*n1.weight/ n2*n2.weight = n1/n2 * n1.weight/n2.weight


        }

        public double calculate(String a, String b){
            Node n1 = map.get(a);
            Node n2 = map.get(b);
            if(n1==null || n2==null) return -1.0;

            Node p1 = find(n1);
            Node p2 = find(n2);
            if(p1!=p2) return -1.0;

            return n2.weightToParent/n1.weightToParent;
        }

    }

    public static void main(String[] args){
        System.out.println(Arrays.toString(solution(new String[][]{{"a","b"},{"b","c"}} , new double[]{2.0,3.0}, new String[][]{{"a","c"}, {"b","c"},{"a", "e"},{"a","a"},{"x","x"}} ) ));

    }


}
