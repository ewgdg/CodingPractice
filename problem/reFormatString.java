public class reFormatString {
    //each line at most k char
    //cannot split word
    //if word.len > k , do not break
    public static String solution(String str, int k){
        StringBuilder sb = new StringBuilder();
        String[] words = str.split(" ");
        if(words.length==0) return sb.toString();

        int line =0;
        for(int i =0; i<words.length; i++){
            String word = words[i];
            int word_len = word.length();



            if(line+word_len<=k || i==0){
                line+=word_len;
                sb.append(word);
            }else{
                line=word_len;
                sb.append("\n");
                sb.append(word);

            }
            if(line<=k) {
                line++;//for space
                sb.append(" ");
            }

        }

        return sb.toString();


    }


    public static void main(String[] args){

        System.out.println(solution("honestlythetruth I am a very smart and competitive man who can live eternally and is immortal",7));
    }
}
