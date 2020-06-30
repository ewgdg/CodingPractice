package Design;
// import java.util.TreeMap;

public class MedianFinderII {//<T extends Comparable<T>> if we want generic？？wrong!!!! the getmedian return l+r/2 , must be number so T entends Number & Compa
    //use treeMap
    //doesnt work with count, need to know the prefix sum of buckets
    //can use map<int, linkedlist > ~= mutiset
    //the node can be used to find prev, next

    //there are some small mistakes here
    //see FraudulentActivityNotifications for correct implementation

    MultiSet<Integer> set;
    MultiSet<Integer>.Node<Integer> l,r; //for a better naming we can create an interface call bagIterator then node implements it
    int size;
    public MedianFinderII(){
        set = new MultiSet<>();
        size=0;
    }

    public void addNumber(int num){

        if(size==0){
            l=set.add(num);
            r=l;

        }else{
            MultiSet<Integer>.Node<Integer> node= set.add(num);
            if((size&1)==0){ //before add size is even
                if(num>=r.data){
                    l=r;
                }else if(num>=l.data && num<r.data){
                    r=node; //r = set.prev(r);
                    l=node;
                }else{
                    r=l;
                }
            }else{
                if(num>=r.data){
                    r= set.next(r);
                }
                else{
                    l=set.prev(r);
                }
            }


        }

        size++;


    }

    public double findMedian(){
        return (l.data+r.data)/2.0;
    }

    public void remove(int num){
        if(!set.contains(num)) return;
        if((size&1)==0){ //before remove it is even
            if(num>=r.data){//corner case , size=2 .  l = r = same data, remove same data, so it will remove l then wrong!!!!!!!!
                //need to test if (num==r.data) { remove(r); instead of num}
                r=l;
                set.remove(num);//need to remove after modify l and r other wise might lost trace of l and r
            }else if( num<=l.data){
                l=r;
                set.remove(num);
            }

        }else{
            if(num==r.data){
                r=set.next(r);
                l=set.prev(r);
                set.remove(r);//!!!!!!!!wrong!!!!!!!!!! need to remove old r not cur r!!!!!!
            }else if(num> r.data){
                l=set.prev(r);
                set.remove(num);
            }else if(num<r.data){
                r= set.next(r);
                set.remove(num);
            }
        }
        size--;
    }

    public static void main(String[] args){
        MedianFinder median_finder = new MedianFinder();
        median_finder.addNumber(1);
        median_finder.addNumber(2);
        median_finder.addNumber(2);
        median_finder.addNumber(3);
        median_finder.addNumber(4);
        System.out.println(median_finder.findMedian());

        median_finder.addNumber(3);
        System.out.println(median_finder.findMedian());

        median_finder.remove(2);
        System.out.println(median_finder.findMedian());
        median_finder.remove(3);
        System.out.println(median_finder.findMedian());
        median_finder.remove(2);
        System.out.println(median_finder.findMedian());
        median_finder.remove(4);
        System.out.println(median_finder.findMedian());
        //2.0
        //2.5
        //3.0
        //2.5
        //3.0
        //2.0

//        median_finder.addNumber(1);
////        System.out.println(median_finder.tree.contains(1));
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(3);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(4);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(2);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.remove(1);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.remove(2);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.remove(3);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        System.out.println("==================");
//
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.addNumber(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());
//        median_finder.remove(10);
//        System.out.println(median_finder.findMedian());

    }

//1.0
//2.0
//3.0
//2.5
//3.0
//3.5
//4.0
//7.0

}
