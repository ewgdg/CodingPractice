import java.util.*;

public class calculatorIV {
    //only support + - * var , for more see calculatorV
    static class Term{// a term is a list of var which multiply with each other
        public List<String> vars;

        public Term(List<String> vars){
            this.vars=vars;

        }
        public void sort(){
            Collections.sort(vars);
        }
        @Override
        public boolean equals(Object another){
            Term that = (Term) another;
            return this.vars.equals(that.vars);
        }
        @Override
        public int hashCode(){
            return this.vars.hashCode();
        }
    }

    static class Poly{ //poly expression contains a bunch of terms added together
        public HashMap<Term, Integer> term_coef_map;
        public Poly(){
            term_coef_map = new HashMap<>();
        }
        public Poly(String exp){
            this();
            Term term= new Term(new ArrayList<>());
            if( Character.isDigit(exp.charAt(0))){//constant value expr
                term_coef_map.put(term,Integer.valueOf(exp) );
            }else{
                term.vars.add(exp);
                this.update(term,1);
            }
        }

        public Poly(int num){
            this();
            Term term= new Term(new ArrayList<>());

            term_coef_map.put(term,num );
        }

        public void update(Term term, Integer coefficient){
            term_coef_map.put(term, term_coef_map.getOrDefault(term,0)+coefficient);
        }

        public Poly calculate(Poly b, char op){
            return operations.get(op).calculate(this,b);
        }

        public int compareTerm(Term a, Term b){
            List<String> alist = a.vars;
            List<String> blist = b.vars;

            for(int i =0; i<alist.size();i++){
                String var1 = alist.get(i);
                String var2 = blist.get(i);
                if(var1.compareTo(var2)!=0){
                    return var1.compareTo(var2);
                }
            }
            return 0;
        }
        public List<String> toList(){
            List<String> ans = new ArrayList<>();
            List<Term> terms =  new ArrayList<>(term_coef_map.keySet());
            //sort terms
            Collections.sort(terms,(a,b)->{ return a.vars.size()!=b.vars.size()?b.vars.size()-a.vars.size() : compareTerm(a,b);  });

            for(Term term:terms){
                int coef = term_coef_map.get(term);
                if(coef==0) continue;

                StringBuilder word = new StringBuilder();
                word.append(coef);
                for(String var : term.vars){
                    word.append("*");
                    word.append(var);
                }

                ans.add(word.toString());

            }

            return ans;
        }

    }
    public interface Operation{
        public Poly calculate(Poly a , Poly b);
    }

    public static class addition implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.term_coef_map.entrySet()){
                res.update(e1.getKey(),e1.getValue());
            }
            for(Map.Entry<Term,Integer> e2:b.term_coef_map.entrySet()){
                res.update(e2.getKey(),e2.getValue());
            }
            return res;
        }
    }

    public static class substruction implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.term_coef_map.entrySet()){
                res.update(e1.getKey(),e1.getValue());
            }
            for(Map.Entry<Term,Integer> e2:b.term_coef_map.entrySet()){
                res.update(e2.getKey(),-e2.getValue());
            }
            return res;
        }
    }

    public static class mutiplication implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.term_coef_map.entrySet()){
                for(Map.Entry<Term,Integer> e2:b.term_coef_map.entrySet()){
                    List<String> list = new ArrayList<>();
                    list.addAll(e1.getKey().vars);
                    list.addAll(e2.getKey().vars);
                    Term term = new Term(list);

                    term.sort();//sort it

                    int coef = e1.getValue()*e2.getValue();
                    res.update(term,coef);
                }
            }
            return res;
        }
    }

    public static class division implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            return null;
        }
    }



    public static HashMap<Character,Operation> operations = new HashMap<>();
    static {
        operations.put('+',new addition());
        operations.put('-',new substruction());
        operations.put('*',new mutiplication());
        operations.put('/',new division());
    }




    public HashMap<String,Integer> map = new HashMap<>();


    public List<String> solution(String expression, String[] evalvars, int[] evalints){

        for (int i=0;i<evalvars.length;i++) map.put(evalvars[i],evalints[i]);
        Stack<Poly> polyStack=new Stack<>();
        Stack<Integer> priStack=new Stack<>();
        Stack<Character> opStack= new Stack<>();

        int index =0;
        int n = expression.length();
        int pri=0;
        while( index < n){

            char c = expression.charAt(index);

            if(Character.isDigit(c)){
                int num =0 ;
                while(index<n && Character.isDigit( (c= expression.charAt(index))  ) ){
                    num=num*10 + c-'0';
                    index++;
                }
                polyStack.add( new Poly( num ));
                index--;
            }

            else if( Character.isLetter(c)){
                String var = "";
                while(index<n && Character.isLetter( (c= expression.charAt(index))  ) ){
                    var=var+ c;
                    index++;
                }
                if(map.containsKey(var)){
                    polyStack.add( new Poly( map.get(var) ));
                }
                else
                    polyStack.add( new Poly( var ));
                index--;
            }

            else if (c=='(') pri+=2;
            else if (c==')') pri-=2;
            else if(c=='+'||c=='-'||c=='*'){
                int curPri = pri;
                if(c=='*') curPri++;

                while(!priStack.isEmpty() && priStack.peek()>= curPri ){
                    priStack.pop();
                    char op = opStack.pop();
                    Poly b = polyStack.pop();
                    Poly a = polyStack.pop();

                    Poly res = a.calculate(b,op);
                    polyStack.push(res);

                }
                opStack.push(c);
                priStack.push(curPri);
            }
            index++;
        }

        while(!priStack.isEmpty() ){
            priStack.pop();
            char op = opStack.pop();
            Poly b = polyStack.pop();
            Poly a = polyStack.pop();

            Poly res = a.calculate(b,op);
            polyStack.push(res);

        }

        return polyStack.pop().toList();
    }



    public static void main(String[] args){
        calculatorIV calculator = new calculatorIV();
        String expr = "(a+b)*(c+d) +c*b -5*3+2";
        String[] evalvars = new String[]{ "a" };
        int[] vals = new int[]{3};

        System.out.println(calculator.solution(expr,evalvars,vals));


    }
}
