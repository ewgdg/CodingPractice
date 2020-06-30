package constraintsSatisfactionProblem;

import java.util.*;

public class cspNqueen {

    class Variable{
        int i,j;
        boolean isAssigned;

        //since no 2 queens can be placed at some row, we know that each queen will be in different row, we just need to decide the col
        HashSet<Integer> scope;

        public Variable(int n, int idx){
            scope=new HashSet<>();
            for(int i =0; i<n; i++){
                scope.add(i);
            }
            this.i=idx;
        }


    }

    public boolean plainPropagator(Variable[] variables, Variable var){

        for(Variable other: variables){
            if(other!=var && other.isAssigned){

                if(other.j==var.j ||  Math.abs(other.j-var.j) == Math.abs( other.i-var.i )  ){
                    return false;
                }

            }

        }
        return true;

    }

    public String solution(int n){
        Variable[] variables = new Variable[n];
        for(int i=0;i<n;i++){
            variables[i] = new Variable(n,i);
        }



        List<List<String>> res = new ArrayList<>();
        bt_search(variables,0,n,res);





        return res.toString();

    }



    public void bt_search(Variable[] variables, int i, int n, List<List<String>> res  ){
        if(i==n){//append sol
            List<String> solution = new ArrayList<>();
            for (int i1 = 0; i1 < n; i1++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    if (variables[i1].j == j)
                        sb.append('Q');
                    else
                        sb.append('.');
                }
                solution.add(sb.toString());
            }
            res.add(solution);
            return ;
        }

        Variable var= variables[i];
        HashSet<Integer> scope = var.scope;

        for(Integer pos : scope){

            var.j=pos;
            var.isAssigned=true;

            boolean checked = plainPropagator(variables,var);
            if(checked){
                bt_search(variables,i+1,n,res);
//                    return true;


            }

            var.isAssigned=false;

        }
//        return false;

    }


    public static void main(String[] args){
        cspNqueen csp = new cspNqueen();
        System.out.println(csp.solution(5));
    }




}
