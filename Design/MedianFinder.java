public class MedianFinder {
    //simple way use 2 priority queue
    //issue unable to remove

    //better use bst , if c++ use multiset
    BST<Integer> tree;
    BST<Integer>.Node l,r;
    public MedianFinder(){
        tree = new BST<>();
    }
    public void addNumber(int num){

        if(tree.root==null){
            tree.insert(num);
            l = tree.root;
            r=l;
        }else{
            tree.insert(num);
            if( (tree.size&1) ==0){//even
                if(num>=r.data){
                    r=tree.getSucc(r);
                }else{
                    l = tree.getPred(l);
                }

            }else{//odd
                if(num>=r.data){
                    l= r;
                }else if(num <r.data && num>=l.data){
                    r= tree.getPred(r);
                    l=r;
                }else if(num <l.data){
                    r=l;
                }

            }

        }
    }
    public double findMedian(){
        if(tree.size==0) return 0;
        return (l.data+r.data)/2.0;
    }

    public void remove(int num){
        if(!tree.contains(num)) return;

        if( (tree.size&1) ==0){//even before delete
            if(num>r.data){
                r=l;
                tree.delete(num);
            }else if(num==r.data){
                tree.deleteNode(r);
                r=l;
            }else if(num <l.data){
                l=r;
                tree.delete(num);
            }else if(num==l.data){
                tree.deleteNode(l);
                l=r;
            }

        }else{
            if(num==r.data){
                BST<Integer>.Node old = r;
                r=tree.getSucc(r);
                l= tree.getPred(l);
                tree.deleteNode(old);//delete r
            }else if(num>r.data){
                l=tree.getPred(l);
                tree.delete(num);
            }else if(num<l.data){
                r=tree.getSucc(r);
                tree.delete(num);
            }
        }


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
