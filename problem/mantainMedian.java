import java.util.TreeSet;

public class mantainMedian {

    public  TreeSet<Integer> tree = new TreeSet<>();
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
            //odd number
            if(num>=r){
                l=r;
            }else if(num<r){
                r=tree.lower(r);
                l=r;
            }
        }else{
            //even
            if(num>=r){
                r=tree.higher(r);
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
                r=tree.lower(r);
            }else{
                l=r;
            }
        }else{
            if(num>=r){
                r=tree.ceiling(r);
                l=tree.lower(r);

            }else{
                r=tree.higher(r);
            }
        }


    }
    public double findMedian(){
        return (double)(l+r)/2.0;
    }


    public static void main(String[] args){
        mantainMedian median_finder = new mantainMedian();
        median_finder.addNumber(1);
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
