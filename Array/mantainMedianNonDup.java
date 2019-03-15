import java.util.Comparator;
import java.util.TreeSet;

public class mantainMedianNonDup {

    public  TreeSet<Integer> tree = new TreeSet<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if(o1==o2) return 0;
            else return o1-o2;
        }
    });//since it is set, only works for unique number, to change it for dup, use treeMap(num->count) ,
    //we can modify the comparator so if a.compareTo(b)==0 return 1/-1 ; thus it will store dup//
    // !!!!doesnt work bc we are not able to find key after modify comparator, plus we need to distinguish diff node with same val treeSet doest  work
    //need to use customized bst

    public  int l,r;
    //treeSet, 2 pointer to mid,  2 heap method doesnt work if we want to support remove op
    public void addNumber(int num){

        if(tree.isEmpty()){
            tree.add(num);
            l=num;
            r=num;
            return;
        }

        tree.add(num);
        int n = tree.size();
        if( (n&1)==1){
            //odd number //here we have 3 cases ,>=r, within middle of >=l  and <r, <l
            //merge into 2 cases
            if(num>=r){
                l=r;
            }else if(num<r){
                r=tree.lower(r);
                l=r;
            }
        }else{
            //even
            if(num>r){

                r=tree.higher(r);
                //notice we cannot use ceiling here, bc ceiling will always find r whatever num>r or num==r
            }else if(num==r){
                r=num;//since it is tree set so it will never happens
            }else{
                l=tree.lower(l);
            }

        }


    }

    public void remove(int num){
        if(!tree.contains(num)) return;

        tree.remove(num);
        int n = tree.size();

        if( (n&1)==1){
            if(num>=r){
//                r=tree.lower(r);// r=l;think r=l??
                r=l;
            }else{
                l=r;
            }
        }else{
            if(num>=r){
                r=tree.ceiling(r);
                l=tree.lower(r);//doesnt really work if all num are dup, unable to find lower, but cannot use floor bc floor might find the same r , need to use node instead of int to find

            }else{
                r=tree.higher(r);
            }
        }


    }
    public double findMedian(){
        return (double)(l+r)/2.0;
    }


    public static void main(String[] args){
        mantainMedianNonDup median_finder = new mantainMedianNonDup();
        median_finder.addNumber(1);
//        System.out.println(median_finder.tree.contains(1));
        System.out.println(median_finder.findMedian());
        median_finder.addNumber(3);
        System.out.println(median_finder.findMedian());
        median_finder.addNumber(4);
        System.out.println(median_finder.findMedian());
        median_finder.addNumber(2);
        System.out.println(median_finder.findMedian());

        median_finder.remove(1);
        System.out.println(median_finder.findMedian());

        median_finder.remove(2);
        System.out.println(median_finder.findMedian());

        median_finder.remove(3);
        System.out.println(median_finder.findMedian());

        median_finder.addNumber(10);
        System.out.println(median_finder.findMedian());

    }
}
