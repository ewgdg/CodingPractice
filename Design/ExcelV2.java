import java.util.*;

public class ExcelV2 {
    //use topo sort

    Cell[][] buffer;
    public ExcelV2(int H, int W){
        buffer= new Cell[H][W-'A'+1];
    }

    public void set(int r, char c, int v){
        int[] coord = getIndex(r,c);
        Cell cell = buffer[coord[0]][coord[1]];
        cell.f=null;//reset formula

        cell.update(v);


    }

    public int sum(int r, char c, String[] ranges){
        int[] coord = getIndex(r,c);
        Cell cell= buffer[coord[0]][coord[1]];
        cell.setFormula(ranges);
        return cell.val;
    }
    public int[] getIndex(String s){
        int j = s.charAt(0)-'A';
        int i = Integer.parseInt(s.substring(1));
        return new int[]{i,j};
    }
    public int[] getIndex(int r,char c){
        return new int[]{r-1, (int)(c-'A')};
    }
    class RangeIterator implements Iterator<int[]> {
        Iterator<String> ranges;
        int endi,endj;
        int i,j;
        public RangeIterator(String[] ranges){
            this.ranges = Arrays.asList(ranges).iterator();


            setRange();
        }
        public void setRange(){
            String range = ranges.next();
            int split = range.indexOf(":");
            if(split!=-1){
                int[] idxs = getIndex(range.substring(0,split));
                i=idxs[0];
                j=idxs[1];
                idxs = getIndex(range.substring(split+1));
                endi=idxs[0];
                endj=idxs[1];
            }else{
                int[] idxs = getIndex(range);
                i=idxs[0];j=idxs[1];endi=i;endj=j;
            }
        }
        @Override
        public boolean hasNext() {
            return (i<=endi&&j<=endj)||ranges.hasNext();
        }

        @Override
        public int[] next() {
            if(!hasNext()) return null;
            if(i>endi||j>endj){
                setRange();
            }
            return new int[]{i++,j++};

        }
    }
    class Cell{
        int val;
        Formula f;
        HashSet<Cell> children;//dependents cell/f
        public Cell(){
            val=0;
            f=null;
            children= new HashSet<>();
        }
        public void update(int v){
            this.val=v;
            Stack<Cell> ordered = new Stack<>();
            topologicalSort(this,ordered);
            while(!ordered.isEmpty()){
                Cell cur = ordered.pop();
                if(cur.f!=null){
                    cur.val=cur.f.calc();
                }
            }

        }
        public void setFormula(String[] ranges){
            f= new Formula(ranges);
            this.update(f.calc());
        }
    }

    class Formula{
        List<Cell> sources;//parents/references
        String[] ranges;
        //type= sum;
        public Formula(String[] ranges){
//            RangeIterator iter = new RangeIterator(ranges);
//            while(iter.hasNext()){
//                int[] coord = iter.next();
//                sources.add(buffer[coord[0]][coord[1]]);
//            }
            this.ranges = Arrays.copyOf(ranges,ranges.length);
        }
        public int calc(){
            RangeIterator iter = new RangeIterator(ranges);
            int res=0;
            while(iter.hasNext()){
                int[] coord = iter.next();
                Cell cur = buffer[coord[0]][coord[1]];
                res+=cur.val;
            }
            return res;
        }
    }


    public void topologicalSort(Cell cur, Stack<Cell> stack){

        for(Cell child: cur.children){
            topologicalSort(child,stack);
        }
        stack.add(cur);
    }
    public static void main(String[] args){
        Excel excel = new Excel(3,'C');
        excel.set(1,'A',2);
        System.out.println(excel.get(1,'A'));//2
        excel.sum(3,'C',new String[]{"A1","A1:B2"});
        System.out.println(excel.get(3,'C'));//expect 4 , bc we got 2 A1 to add
        excel.set(2,'B',2);
        System.out.println(excel.get(3,'C'));//expect 6
        excel.set(3,'C',9);
        System.out.println(excel.get(3,'C'));//9
//        excel.set(2,'B',8);
        excel.set(1,'A',5);
        System.out.println(excel.get(3,'C'));//9

        excel = new Excel(5,'E');
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
