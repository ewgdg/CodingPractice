import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//Input:
//S = "heeellooo"
//words = ["hello", "hi", "helo"]
//Output: 1
//Explanation:
//We can extend "e" and "o" in the word "hello" to get "heeellooo".
//We can't extend "helo" to get "heeellooo" because the group "ll" is not extended.
// Formally, we are allowed to repeatedly choose a group (as defined above) of characters c,
// and add some number of the same character c to it so that the length of the group is 3 or more.

public class Expressive_Words {
    public int expressiveWords(String S, String[] words) {
        //if the dict is very large we can reduce S and check dict if it exists
        //if the s is very large we can compare it one by one

        //check size of reducedSet
        List<Node> encodedS = getTransition(S);
        int max =0;
        for(Node node : encodedS){
            max = Math.max(max,node.count);
        }

        double possibleReducedSetSize = Math.pow(encodedS.size(),max);
        int count=0;
        //method 1
        if(possibleReducedSetSize<words.length){
            List<String> reducedSet = reduce(S,0,encodedS);


            HashSet<String> dict = new HashSet<>();
            for(String word:words){
                dict.add(word);
            }


            for(String reduced:reducedSet){
                if(dict.contains(reduced)){
                    count++;
                }
            }
        }

        //method2
        else{
            for(String word:words){
                List<Node> encodedW = getTransition(word);
                if(stretchy(encodedW,encodedS)){
                    count++;
                }
            }


        }


        return count;

    }


    public boolean stretchy(List<Node> encodeW, List<Node> encodedS){
        int n = encodedS.size();
        if(encodeW.size()!=n) return false;

        for(int i =0; i< n; i++){
            Node s= encodedS.get(i);
            Node w = encodeW.get(i);
            if(s.c!=w.c) return false;
            else if(s.count<w.count) return false;
            else if(s.count<3 && s.count!=w.count) return false;
        }
        return true;
    }

    class Node{
        char c;
        int count;
        public Node(char c, int count){
            this.c=c;
            this.count=count;
        }
    }
    public List<Node> getTransition(String s){


        int n=s.length();
        List<Node> res =new ArrayList<>();
        int i=0;
        while(i<n){
            int count=0;
            char c = s.charAt(i);
            while(i<n && s.charAt(i)==c){
                count++;i++;
            }
            res.add(new Node(c,count) ) ;
        }
        return res;

    }
    public List<String> reduce(String s, int start, List<Node> transition){
//        if(mem.containsKey(start)) return mem.get(start);

        List<String> res =new ArrayList<>();
        if(start>=transition.size()){
            res.add("");
            return res;
        }


        Node cur = transition.get(start);

        List<String> sub = reduce(s,start+1,transition);

        if(cur.count>=3){
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<cur.count;i++){
                sb.append(cur.c);
                for(String str:sub){
                    String _new = sb.toString()+(str) ;
                    res.add(_new);
                }
            }

        }else{
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<cur.count;i++){
                sb.append(cur.c);
            }

            for(String str:sub){
                String _new = sb.toString()+(str) ;
                res.add(_new);
            }

        }


//        mem.put(start,res);
        return res;
    }

    public static void main(String[] args){
        Expressive_Words solver =new Expressive_Words();

//        String s = "dddiiiinnssssssoooo";
//        String[] words= new String[]{ "dinnssoo","ddinso","ddiinnso","ddiinnssoo","ddiinso","dinsoo","ddiinsso","dinssoo","dinso" };


        String s = "yyrrrrrjaappoooyybbbebbbbriiiiiyyynnnvvwtwwwwwooeeexxxxxkkkkkaaaaauuuu";

        String[] words= new String[]{"yrrjjappooybbebriiyynvvwtwwoeexkauu","yrjjappooybbebrriyynnvwwttwoeexkaauu","yyrrjapoybbebriiynnvvwwtwoeexkaauu","yyrjappooyybebbrriyynnvwttwwooeexxkkau","yrjaapooybbebrriyynnvvwwttwooexkaau","yyrjjapooyybeebbrriiyynvwwttwoexxkau","yrrjaappoyybbeebbriynnvwwtwooexxkauu","yrrjjaapooybebriynnvvwwttwwooexkaau","yyrrjjappooyybebriiyynvvwttwoeexxkkaau","yrrjaappooybbebrriynvwwtwooeexkau","yyrjjaapooyybebrriiynvvwttwwooeexxkkaau","yyrrjappooyybbebriyynnvwwttwwoeexkkauu","yyrrjjaapoyybbeebriiyynnvwwtwwooexkkaau","yrjjaappooybbeebriiyynnvwwtwwoexkau","yrrjjappoyybbeebbrriiyynnvwttwwooexxkkaauu","yyrrjjapooyybbebbrriyynvwtwoexxkkaauu","yyrrjappoybebrriynvwwttwooeexkkauu","yrrjaappooybbeebriiyynnvvwwttwoexxkauu","yrrjapoybebbrriyynvvwwttwwoexkaau","yyrrjjapoybbeebbrriynnvwwtwwooexkaauu","yyrrjjapooyybbeebbriyynnvwtwwoexkaau","yrjjaapooyybebriynnvwwttwooeexxkkaauu","yyrjjaapooybbebbriiynvvwttwwoexxkkauu","yrjjaapooyybeebbriiyynvvwwttwoeexxkau","yrjjappooyybbebbrriiynvvwtwooeexxkkau","yyrrjjapoyybbebbrriiyynvwwtwwoexxkkaau","yrjjapooyybbeebriyynnvvwwtwoeexkkau","yrjapooyybebriiynnvvwwtwwooeexkauu","yyrjaapoyybbebbrriynnvwtwwoeexkauu","yrrjjappoybeebrriiynvvwwtwwoeexxkkaau","yrrjjapoybbeebrriiyynnvwwttwwoexxkaau","yyrrjaapoybeebrriiyynvwttwwooeexkauu","yyrjapoybbeebbrriyynnvvwwttwwooeexkaauu","yyrjappooybebrriiynvwtwwoeexxkaauu","yrrjjappooybebrriynnvvwttwooexkau","yrjjaapoybbeebbriiynnvvwttwooexkauu","yyrrjapooyybbeebriiyynnvvwtwwoeexxkaauu","yyrjjaappooybeebbrriiyynnvvwwtwwoeexkkau","yrrjappoyybbeebrriiynvvwwtwwoeexxkauu","yrjapooyybeebriiyynvvwttwwooeexxkaauu","yrjjappooyybbebbriiynnvwwtwooeexxkauu","yyrrjjappooybbeebbriyynnvwtwwooexxkkau","yyrrjjaapooybebriiyynvwwtwooeexxkkaauu","yrjjappooyybbeebbriiyynvwwtwwoeexkkau","yrrjjappooybbebrriiynvvwwtwwoexxkkaau","yrjjapooybebbriyynnvvwwttwwooeexxkkaau","yyrjjapoyybebbrriynvvwwttwoexkauu","yyrjappoyybebriiynnvvwttwwoexxkaauu","yyrjaapoybbeebriyynvvwwttwoeexkau","yrjjaappooyybbebbriiynnvvwtwooexxkau","yyrjjaappooyybbebrriiyynvvwttwooexkau","yrjjappoybbeebriyynnvvwwttwwooexxkkaau","yyrrjaapooybbebbriiyynnvwwtwwooexxkkaauu","yrrjaapooybbeebrriynnvvwwtwoeexxkkauu","yrjjaappooyybeebbrriyynnvvwttwwoexxkkauu","yrrjapooyybebriyynnvwwttwooeexkau","yyrjjaapooyybeebrriiynnvvwwttwoeexxkkau","yrjappooybebriyynnvvwttwwooeexkau","yrrjjaappoyybebbrriiyynvwwtwooexxkauu","yrjjappooybeebriynnvwwtwoeexkaauu","yrjaappoybbebbriiynnvwwttwooexxkaau","yyrrjappooyybeebbriiyynvwwttwwoexxkau","yyrjappoyybbeebrriynvwtwoeexkaau","yrrjjaapooybbeebbriyynvwwtwooeexkkaau","yrjapoybebbrriiynvwttwwoeexxkaau","yrjapooybebbrriiynnvwwtwwoexxkaau","yrrjjaappoybeebbriiyynvwwtwooexxkkaauu","yrjappooybeebrriynvwwtwooeexkaauu","yrrjaapooybeebbriiynvvwtwwoexxkkaauu","yyrrjaappooyybebbrriiyynvwwtwwooexxkkau","yyrjaappoybbeebriynnvvwwtwwooeexkaauu","yyrjaappooyybbebbriynvvwwttwwooexkauu","yrjappooybeebbrriiynnvwttwwooexkkau","yrrjjappooyybebbriiyynnvvwttwwoexkkau","yrrjjaapooybeebbriynnvvwwtwooexkaau","yyrjjappoybeebbrriiynnvwtwwoexkaauu","yyrjjaapoybbebbrriiyynnvvwtwwoexkaau","yyrrjjaappoyybbebbriyynvwwtwwooeexkkaau","yrrjjaappooybbebriiyynvvwttwwooexxkau","yyrjjaapoyybebriiynnvwtwwooeexkauu","yrrjjappoyybeebbriiyynnvwttwoexkkau","yrjjappoyybbebbrriynnvvwttwwooeexkkaauu","yyrjappooybeebrriiynnvwwttwwooexxkkaauu","yrrjaappoybbeebrriyynnvvwwtwwooeexxkaauu","yyrjaappooybeebbriiynvwttwoexxkkauu","yyrrjjapooyybbeebbrriyynvwttwwooeexxkkau","yrrjapoybbebbrriiynvwtwwoeexxkaau","yyrrjapoybbeebbriiyynnvvwttwooexkkauu","yyrjaapooyybebbrriiyynnvvwwtwooeexkkauu","yyrrjjaappoybbeebrriyynnvwwtwwoexkkaauu","yyrjappooybbeebrriiyynvwwttwwoexkkau","yyrjaapooyybebbriiyynnvvwwtwoeexkkaau","yyrrjjappoyybbeebbriiyynvwtwooexxkaauu","yrrjjaapoyybbeebriynvvwtwwoexxkaau","yyrrjjapoybbebbrriyynnvwwtwoeexxkkaau","yyrrjapooyybebrriiyynvwttwwooeexxkkauu","yrjappooyybebriiynnvwwtwoeexkkaauu","yrjjaapooyybeebriiynvwtwooexkauu","yyrrjjapoybeebbrriiynnvwttwwoexkaau","yyrrjaappoyybebbrriiyynvwwtwooeexkaau"};
        System.out.println(solver.expressiveWords(s,words));



    }

}
