import java.lang.reflect.Array;
import java.util.*;

public class cspColorfilling {

    class Variable{
        char color;
        boolean isAssigned;
        HashSet<Character> scope ;

        public Variable(){
            scope= new HashSet<>();
            for(char c: colors) {
                scope.add(c);
            }
//            scope=Arrays.copyOf(colors,colors.length);
        }

    }
    public static char[] colors = new char[]{'r','g','b'};

    public boolean plainPropagator(char[][] board, int n, int i, int j ){//plain bt propogator //check only contraints that has all var assigned
        if(i-2>=0 && board[i][j] == board[i-1][j] && board[i-2][j]==board[i][j] ){
            return false;
        }
//        if(i+2<n && board[i][j] == board[i+1][j] && board[i+2][j]==board[i][j]){ //unassigned , cannot check
//            return false;
//        }
        if(j-2>=0 && board[i][j] == board[i][j-1] && board[i][j-2]==board[i][j] ){
            return false;
        }
//        if(j+2<n && board[i][j] == board[i][j+1] && board[i][j+2]==board[i][j]){
//            return false;
//        }
        return true;
    }

    public boolean FCPropagator(Variable[][] variables, int n, int i, int j, HashMap< Variable,Character> pruned){ // check constraints with only one var unassigned
        if(i-1>=0 && i+1<n && variables[i-1][j].color==variables[i][j].color){
            char c = variables[i][j].color;
            if(variables[i+1][j].scope.contains(c))
                variables[i+1][j].scope.remove(c);

            //pruned var is recorded for restoring in future
            pruned.put(variables[i+1][j],c);


            if(variables[i+1][j].scope.isEmpty()){
                return false;
            }
        }

        if(j-1>=0 && j+1<n && variables[i][j-1].color==variables[i][j].color){
            char c = variables[i][j].color;
            if(variables[i][j+1].scope.contains(c))
                variables[i][j+1].scope.remove(c);

            pruned.put(variables[i][j+1],c);

            if(variables[i][j+1].scope.isEmpty()){ // check DWO
                return false;
            }

        }
        return true;


    }

    public  char[][] generateBoard(int n){
//        char[][] board = new char[n][n];

        Variable[][] variables = new Variable[n][n];
        Deque<Variable> unassign_variables = new ArrayDeque<>();


        for(int i =0;i<n; i++){
            for(int j =0;j<n;j++){
                Variable var = new Variable();
                variables[i][j]=var;
                unassign_variables.addLast(var);
            }
        }

        boolean status = bt_search(variables,n,0,0,unassign_variables);

        char[][] res = new char[n][n];
        for(int i =0;i<n;i++){
            for(int j=0; j<n; j++){
                res[i][j]= variables[i][j].color;
            }
        }
        if(status)
            return res;
        else return null;
    }

    public void restorePruned(HashMap<Variable, Character> pruned) {
        for(Map.Entry<Variable,Character> entry : pruned.entrySet()){
            Variable var = entry.getKey();
            Character c= entry.getValue();
            var.scope.add(c);
        }

    }

    public Character[] randomOrder(Variable var){
        HashSet<Character> scope = var.scope;
        Random random = new Random();

        Character[] res = scope.toArray(new Character[scope.size()]);

        for(int right = scope.size()-1; right>=0; right-- ){
            int rand = random.nextInt(right+1); //exclusive boundary

            char temp = res[right];
            res[right]=res[rand];
            res[rand]=temp;

        }
        return res;

    }
    public boolean bt_search(Variable[][] variables,int n, int i,int j, Deque<Variable> unassigned){

        //here we can use i and j to target unassigned var but we can also use unassigned var deque

        if(i==n && j==0) return true;//no unassigned var

        boolean status = false;
        Variable var = variables[i][j];
        Character[] scope = randomOrder(variables[i][j]);
        for(char color : scope){
            //assign variable
            variables[i][j].color = color;
            var.isAssigned=true;

            HashMap< Variable,Character> pruned = new HashMap<>();
            boolean checked = FCPropagator(variables,n,i,j,pruned);

            if(checked){ //or we can use plain bt propagator
                if(j==n-1)
                     status = bt_search(variables, n, i+1,0,unassigned);//next level
                else
                     status =bt_search(variables,n,i,j+1,unassigned);
            }

            if(status){
                return true;
            }

            //unassign variable
            variables[i][j].color = 0;
            var.isAssigned=false;
            restorePruned(pruned);

        }

        return false;

    }


    public static void main(String[] args){
        cspColorfilling csp = new cspColorfilling();

        System.out.println(Arrays.deepToString( csp.generateBoard(5)) );

    }

}
