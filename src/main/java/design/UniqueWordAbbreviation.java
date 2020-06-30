package design;
import java.util.*;

public class UniqueWordAbbreviation {

    HashMap<String, Set<String>> abbrDict;
    public UniqueWordAbbreviation(String[] dictionary) {
        //preprocess the dict to store the abbr
        abbrDict= new HashMap<>();
        for(String w:dictionary){
            abbrDict.computeIfAbsent(compress(w), k->new HashSet<>()).add(w) ;
        }
    }

    public boolean isUnique(String word) {
        String compressed = compress(word);
        Set<String> set = abbrDict.get(compressed);
        if(set==null || (set.size()==1&&set.contains(word))  ){ //abbreviation is unique if no other word from the dictionary has the same abbreviation.
            return true;
        }
        return false;

    }

    public String compress(String word){
        if(word.length()<=2) return word;
        StringBuilder sb= new StringBuilder();
        sb.append(word.charAt(0));
        sb.append( String.valueOf(word.length()-2) );
        sb.append(word.charAt(word.length()-1));
        return sb.toString();

    }


/**
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr obj = new ValidWordAbbr(dictionary);
 * boolean param_1 = obj.isUnique(word);
 */
}
