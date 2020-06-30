package array;

import java.util.*;

public class CalculatorV {
    static class Term{// a term is a list of var which multiply with each other

        public HashMap<String, Integer> vars; // var map to power

        public Term(String var){
            this.vars= new HashMap<>();
            update(var,1);

        }
        public Term(HashMap<String, Integer> vars){
            this.vars= vars;
        }

        public void update(String var, int pow){
            vars.put(var,vars.getOrDefault(var,0)+pow);
            if(vars.get(var)==0) vars.remove(var);
        }
        @Override
        public boolean equals(Object obj){
            if(obj==null) return false;
            if(obj.getClass()!=getClass()) return false;
            Term that = (Term) obj;
            return this.vars.equals(that.vars);
        }
        @Override
        public int hashCode(){
            return this.vars.hashCode();
        }
    }

    static class Poly{ //poly expression contains a bunch of terms added together
        public HashMap<Term, Integer> terms; //term map to coefficient
        public Poly(){
            terms = new HashMap<>();
        }
        public Poly(String exp){
            this();
            Term term= new Term(new HashMap<>());
            if( Character.isDigit(exp.charAt(0))){//constant value expr
                terms.put(term,Integer.valueOf(exp) );
            }else{
                term.update(exp,1);
                this.update(term,1);
            }
        }

        public Poly(int num){
            this();
            Term term= new Term(new HashMap<>());
            terms.put(term,num );
        }

        public void update(Term term, Integer coefficient){
            terms.put(term, terms.getOrDefault(term,0)+coefficient);

        }

        public Poly calculate(Poly b, char op){
            return operations.get(op).calculate(this,b);
        }

        public List<String> toList(){
            List<String> ans = new ArrayList<>();
            List<Term> terms =  new ArrayList<>(this.terms.keySet());
            //sort terms

            for(Term term:terms){
                int coef = this.terms.get(term);
                if(coef==0) continue;

                StringBuilder word = new StringBuilder();
                word.append(coef);
                for(Map.Entry<String,Integer> varEntry : term.vars.entrySet()){
                    String var = varEntry.getKey();
                    int pow = varEntry.getValue();
                    if(pow<0) {
                        pow=-pow;
                        for (int t = 0; t < pow; t++) {
                            word.append("/");
                            word.append(var);
                        }
                    }else {
                        for (int t = 0; t < pow; t++) {
                            word.append("*");
                            word.append(var);
                        }
                    }
                }

                ans.add(word.toString());

            }

            return ans;
        }

    }
    public interface Operation{
        public Poly calculate(Poly a , Poly b);
    }
    //similar to func pointer in c
    public static class addition implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.terms.entrySet()){
                res.update(e1.getKey(),e1.getValue());
            }
            for(Map.Entry<Term,Integer> e2:b.terms.entrySet()){
                res.update(e2.getKey(),e2.getValue());
            }
            return res;
        }
    }

    public static class substruction implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.terms.entrySet()){
                res.update(e1.getKey(),e1.getValue());
            }
            for(Map.Entry<Term,Integer> e2:b.terms.entrySet()){
                res.update(e2.getKey(),-e2.getValue());
            }
            return res;
        }
    }

    public static class mutiplication implements Operation{
        @Override
        public Poly calculate(Poly a, Poly b) {
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.terms.entrySet()){
                for(Map.Entry<Term,Integer> e2:b.terms.entrySet()){
                    HashMap<String, Integer> vars = new HashMap<>();
                    Term term = new Term(vars);
                    for(Map.Entry<String, Integer> varEntry: e1.getKey().vars.entrySet()){
                        term.update(varEntry.getKey(),varEntry.getValue());
                    }
                    for(Map.Entry<String, Integer> varEntry: e2.getKey().vars.entrySet()){
                        term.update(varEntry.getKey(),varEntry.getValue());
                    }


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
            Poly res =new Poly();
            for(Map.Entry<Term,Integer> e1:a.terms.entrySet()){
                for(Map.Entry<Term,Integer> e2:b.terms.entrySet()){
                    HashMap<String, Integer> vars = new HashMap<>();
                    Term term = new Term(vars);
                    for(Map.Entry<String, Integer> varEntry: e1.getKey().vars.entrySet()){
                        term.update(varEntry.getKey(),varEntry.getValue());
                    }
                    for(Map.Entry<String, Integer> varEntry: e2.getKey().vars.entrySet()){
                        term.update(varEntry.getKey(),-varEntry.getValue());
                    }


                    int coef = e1.getValue()/e2.getValue();
                    res.update(term,coef);
                }
            }
            return res;
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
            else if(c=='+'||c=='-'||c=='*'||c=='/'){
                int curPri = pri;
                if(c=='*'||c=='/') curPri++;

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
        CalculatorV calculator = new CalculatorV();
        String expr = "((a+b)*(c+d))/(b*d)-5*3+2";
        String[] evalvars = new String[]{ "a" };
        int[] vals = new int[]{3};

        System.out.println(calculator.solution(expr,evalvars,vals));


    }
}
