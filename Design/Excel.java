//Excel(3,"C");
//// construct a 3*3 2D array with all zero.
////   A B C
//// 1 0 0 0
//// 2 0 0 0
//// 3 0 0 0
//
//Set(1, "A", 2);
//// set C(1,"A") to be 2.
////   A B C
//// 1 2 0 0
//// 2 0 0 0
//// 3 0 0 0
//
//Sum(3, "C", ["A1", "A1:B2"]);
//// set C(3,"C") to be the sum of value at C(1,"A") and the values sum of the rectangle range whose top-left cell is C(1,"A") and bottom-right cell is C(2,"B"). Return 4.
////   A B C
//// 1 2 0 0
//// 2 0 0 0
//// 3 0 0 4
//
//Set(2, "B", 2);
//// set C(2,"B") to be 2. Note C(3, "C") should also be changed.
////   A B C
//// 1 2 0 0
//// 2 0 2 0
//// 3 0 0 6

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Excel {
    //a formula can be viewed as target cell = op(reference cells) , and a target cell could be reference of another cell e.g D2, (D2 is dependent on the target cell)
    // so a cell should contains dependent formula(the formula is dependent on this cell) and target formula
    //formula update only need to calc the diff so O 1,only works for sum op , if multiply then we need to re-cal the whole formula

    //each time we set , we update up to O N formula, and each formula could affect other N-1 formula
    //so totally take O(N^2) for set ,

    //method2// if we need a fully re-calc a formula we need to have a proper order (topological sort), each re-calc takes O N(n reference cells) ,
    // and we have up to N formulas, so the re-calc method also takes O N^2, view it as a DAG graph!!!!!!!!!
    //A=B*C e.g ,A is a Cell which contains Formula A=B*C, B is a Cell contains Formula B=B
    //   A
    //  ^ ^
    // /  \
    //B   C
    //topo order B -> C -> A , calc B with formula within B first then C then A


    //method3, set formula for each cell in O1 without calc
    //only calc if we call get, so O N^2 for get (with cache??? without cache could be N!??)


    //could improve by remove hashmap but use class Cell


    //use topological sort for better extensibility

    int[][] buffer;
    HashMap<String, HashMap<Formula,Integer>> map;//map input pos/reference cells to formula
    HashMap<String,Formula> formulas;//map target cell/ouput pos to formula
    public Excel(int H, char W) {
        buffer= new int[H][W-'A'+1];
        map = new HashMap<>();
        formulas = new HashMap<>();
    }

    public void set(int r, char c, int v) {
        set(r,c,v,true);

    }
    public void set(int r, char c, int v, boolean clean) {
        if(clean)
            clean( r,  c);
        int col = c-'A';
        int old = buffer[r-1][col];
        buffer[r-1][col]=v;
        String key = getKey(r,c);
        // System.out.println(key);
        if(map.containsKey(key)){
            int update = v-old;
            Map<Formula,Integer> fs  = map.get(key);
            for(Map.Entry<Formula,Integer> f : fs.entrySet()){
                f.getKey().update(update*f.getValue());
            }

        }



    }

    public String getKey(int r, char c){
        return ""+c+r;
    }

    public void clean(int r, char c){
        String key = getKey(r,c);

        if(formulas.containsKey(key)){

            Formula f = formulas.get(key);
            f.delete();
        }
    }

    public int get(int r, char c) {
        return buffer[r-1][c-'A'];
    }

    public int sum(int r, char c, String[] strs) {
        clean(r,c);
        Formula f = new Formula(r,c,strs);
        return f.getSum();
    }

    class Formula{
        int r;
        int c;
        String key;
        String[] ranges;
        //type = sum
        public Formula(int r, char cchar, String[] strs){
            this.r=r;
            this.c =cchar-'A';
            this.key = getKey(r,cchar);
            this.ranges = Arrays.copyOf(strs, strs.length);

            formulas.put(key,this);
            int sum=0;
            for(String range: ranges){
                String[] range2 = range.split(":");
                int startr = Integer.parseInt(range2[0].substring(1,range2[0].length()));
                int startc = range2[0].charAt(0)-'A';

                int endr = startr;
                int endc = startc;
                if(range2.length>1){
                    endr = Integer.parseInt(range2[1].substring(1,range2[1].length()));
                    endc = range2[1].charAt(0)-'A';
                }
                for(int i=startr ; i<=endr; i++){
                    for(int j = startc;j<=endc;j++){
                        String key = getKey(i,(char)(j+'A'));
                        // System.out.println(key);
                        HashMap<Formula,Integer> formulaCounter = map.computeIfAbsent(key,k->new HashMap<>());
                        formulaCounter.put(this,formulaCounter.getOrDefault(this,0)+1);//store mapping relation
                        sum+=buffer[i-1][j];
//                        System.out.println("Sum "+buffer[i-1][j]);
                    }

                }
            }
            buffer[r-1][c] = sum;
        }
        //just use obj default hashCode and equals should be enough
        // public int hashCode(){
        //     return r*31+c;
        // }

        public int getSum(){
            return  buffer[r-1][c];
        }
        public void update(int update){
//            buffer[r-1][c]+=update;
            Excel.this.set(r,(char)(c+'A'),Excel.this.get(r,(char)(c+'A')) + update , false);//might change another formula, but dont clean the formula here, so use set func
        }
        public void delete(){
            for(String range: ranges){
                String[] range2 = range.split(":");
                int startr = Integer.parseInt(range2[0].substring(1,range2[0].length()));
                int startc = range2[0].charAt(0)-'A';

                int endr = startr;
                int endc = startc;
                if(range2.length>1){
                    endr = Integer.parseInt(range2[1].substring(1,range2[1].length()));
                    endc = range2[1].charAt(0)-'A';
                }
                for(int i=startr ; i<=endr; i++){
                    for(int j = startc;j<=endc;j++){
                        String key = getKey(i,(char)(j+'A'));
                        HashMap<Formula,Integer> formulaCounter = map.get(key);
                        if(formulaCounter!=null) {//could delete twice if add twice, but need to check null
                            formulaCounter.remove(this);

                            if (formulaCounter.isEmpty()) {
                                map.remove(key);
                            }
                        }

                    }

                }
            }

            formulas.remove(this.key);



        }

    }
    public static void main(String[] args){
//        Excel excel = new Excel(3,'C');
//        excel.set(1,'A',2);
//        System.out.println(excel.get(1,'A'));//2
//        excel.sum(3,'C',new String[]{"A1","A1:B2"});
//        System.out.println(excel.get(3,'C'));//expect 4 , bc we got 2 A1 to add
//        excel.set(2,'B',2);
//        System.out.println(excel.get(3,'C'));//expect 6
//        excel.set(3,'C',9);
//        System.out.println(excel.get(3,'C'));//9
////        excel.set(2,'B',8);
//        excel.set(1,'A',5);
//        System.out.println(excel.get(3,'C'));

        Excel excel = new Excel(5,'E');
        excel.set(1,'A',5);
        excel.set(1,'B',3);
        excel.set(1,'C',2);
        excel.sum(1,'C',new String[]{"A1","A1:B1"});
        excel.set(1,'B',5);
        excel.sum(1,'B',new String[]{"A1:A5"});
        excel.set(5,'A',10);
        System.out.println(excel.get(1,'C'));//expect 25



        //["Excel","set","set","set","sum","get",  sdf"set","get","sum",sdf"set","get",dd"get","sum","set","get","get","get","get"]
        //[[5,"E"],[1,"A",5],[1,"B",3],[1,"C",2],[1,"C",["A1","A1:B1"]],[1,"C"],sdf[1,"B",5],[1,"C"],[1,"B",["A1:A5"]],sdf[5,"A",10],ff[1,"B"],dd[1,"C"],[3,"C",["A1:C1","A1:A5"]],[3,"A",3],[1,"B"],[1,"C"],[3,"C"],[5,"A"]]

    }
}

/**
 * Your Excel object will be instantiated and called as such:
 * Excel obj = new Excel(H, W);
 * obj.set(r,c,v);
 * int param_2 = obj.get(r,c);
 * int param_3 = obj.sum(r,c,strs);
 */
