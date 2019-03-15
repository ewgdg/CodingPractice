import java.util.HashSet;

public class Dominos {


    //method1 simulation
    //O n time , O n space
    //bfs with hashSet as open for fast check existence
    public String pushDominoes(String dominoes) {
        HashSet<Integer> level_open  = new HashSet<>();//to fast check if node exist in a level open
        char[] res = dominoes.toCharArray();
        HashSet<Integer> visited = new HashSet<>();

        int n = dominoes.length();

        for(int i=0;i<n;i++){
            char c = res[i];
            if(c!='.'){
                visited.add(i);
                level_open.add(i);
            }
        }

        while(level_open.size()>0){


            HashSet<Integer> next_level  = new HashSet<>();
            for(int pos : level_open){
                int next = pos+1;
                char val = res[pos];
                if(res[pos]=='L'){
                    next =pos-1;
                }
                if(next<0 || next>=n){//boundary check!!!!!!!
                    continue;
                }

                if(next_level.contains(next)){ // it is vertical， met second time 2 forces canceled out
                    next_level.remove(next);
                    res[next]  =  '.';
                }else if(!visited.contains(next)){
                    next_level.add(next);
                    res[next]=val;
                    visited.add(next);
                }


            }
            level_open = next_level;


        }


        return new String(res);

    }

    //O n time, in place , observe the pattern
    //if we see L.... R  do nothing in between
    //R..........R ,   everything set to R in between
    //L..........L, set to L
    //R...........L, approach from lo and hi to mid set LLLL.RRRR


    public String solution2(String dominos){
        char[] res = dominos.toCharArray();
        int n = dominos.length();

        char prev='L';
        int prevIdx = -1;
        for(int i =0; i<=n ;i++){

            char cur=0;
            if(i==n){//corner case
                cur = 'R';
            }
            else
                cur = res[i];
            if(cur=='L'){
                if(prev=='L'){
                    for(int j = prevIdx+1; j<i; j++){
                        res[j]='L';
                    }
                }else if(prev=='R'){
                    int lo=prevIdx+1;
                    int hi = i-1;
                    while(lo<hi){
                        res[lo]='R';
                        res[hi]='L';
                        lo++; hi--;
                    }
                }
                //update prev
                prev=cur;
                prevIdx=i;

            }else if(cur=='R'){
                if(prev=='R'){
                    for(int j = prevIdx+1; j<i; j++){
                        res[j]='R';
                    }
                }
                //update prev!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! only when L or R
                prev=cur;
                prevIdx=i;
            }


        }

        return new String(res);

    }



}
