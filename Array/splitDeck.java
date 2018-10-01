import java.util.HashMap;

public class splitDeck {
//Return true if and only if you can choose X >= 2 such that it is possible to split the entire deck into 1 or more groups of cards, where:
//
//Each group has exactly X cards.
//All the cards in each group have the same integer.

    public boolean hasGroupsSizeX(int[] deck) {
        if(deck.length==0) return true;
        HashMap<Integer,Integer> counter = new HashMap<>();
        for(int num: deck){
            counter.put(num,counter.getOrDefault(num,0)+1);
        }

        int g=0;
        for(Integer val : counter.values() ){
            g = gcd(g,val);

        }

        return g>1;
    }


    public int gcd(int a,int b){
        if(b==0) return a;

        return gcd(b,a%b);
    }

    public static void main(String[] args){
        splitDeck solver =new splitDeck();

        System.out.println( solver.hasGroupsSizeX(new int[]{1,2,3,4,4,3,2,1}));
    }
}
