package Design;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class FraudulentActivityNotifications {
    //For Integer compare.The JVM is caching small Integer values. == only works for numbers between -128 and 127 http://www.owasp.org/index.php/Java_gotchas#Immutable_Objects_.2F_Wrapper_Class_Caching
    //use equals for Integer vs Integer
    //HackerLand National Bank has a simple policy for warning clients about possible fraudulent account activity.
    // If the amount spent by a client on a particular day is greater than or equal to 2* the client's median spending for a trailing number of days,
    // they send the client a notification about potential fraud. The bank doesn't send the client any notifications until they have at least that trailing number of prior days'
    // transaction data.


    // Complete the activityNotifications function below.

    //method 1 : for small number range  O n*maxNumber
    //store num into bucket and count the freq
    //find the median using freq table


    //method 2 : multiset/bag
    //nlogn


    // n<10^5
    // elem < 10^6  --> max Number
    // d< n

    //so method 2 is better

    static int activityNotifications(int[] expenditure, int d) {
        MedianFinder medianFinder = new MedianFinder();
        int count=0;
        int n = expenditure.length;
        for(int i=0;i<n;i++){


            int num = expenditure[i];

            if(medianFinder.size()==d){
                double median = medianFinder.getMedian();
                if(num>=median*2){
                    count++;
                }
            }


            medianFinder.add(num);

            if(medianFinder.size()>d){
                medianFinder.remove(expenditure[i-d]);
            }
        }
        return count;

    }

    static class MedianFinder{
        Bag<Integer> bag;
        public MedianFinder(){
            bag =  new Bag<Integer>();
        }
        Bag<Integer>.Node l,r;
        public void add(Integer data){
            int size = size();
            Bag<Integer>.Node node  =bag.add(data);
            if(size==0){
                l=node;
                r=node;
            }else{
                if( (size&1) ==0  ){
                    if(data>=r.data){
                        l=r;
                    }else if(data<r.data && data>=l.data){
                        l=node;
                        r=node;
                    }else{
                        r=l;
                    }
                }else{ // l==r
                    if(data>=r.data){
                        r=bag.next(r);
                    }else{
                        l=bag.prev(l);
                    }
                }
            }

        }

        public void remove(Integer data){
            if(!bag.contains(data)) return;
            int size=size();
            if( (size&1) == 0 ){
                if(data.equals(r.data)){ //wrong with == !!!!!!!!!!!!!!!!!!//You can't compare two Integer with a simple == they're objects so most of the time references won't be the same.

//                    There is a trick, with Integer between -128 and 127, references will be the same as autoboxing uses  Integer.valueOf() which caches small integers.
                    bag.remove(r);
                    r=l;
                }else if(data>r.data){
                    bag.remove(data);
                    r=l;
                }else if(data.equals(l.data) ){
                    bag.remove(l);
                    l=r;
                }else{
                    l=r;
                    bag.remove(data);
                }
            }else{
                if(data.equals(r.data) ){
                    Bag<Integer>.Node old = r;
                    r = bag.next(r);
                    l= bag.prev(l);
                    bag.remove(old);
                }else if(data>r.data){
                    l= bag.prev(l);
                    bag.remove(data);
                }else{
                    r=bag.next(r);
                    bag.remove(data);
                }
            }


        }
        public double getMedian(){
            return (l.data+r.data)/2.0;
        }
        public int size(){
            return bag.size;
        }

    }
    static class Bag<T extends Comparable<T>>{
        TreeMap<T,DLL> map;
        public int size;

        public Bag(){
            map= new TreeMap<>();
            size=0;
        }
        public Node add(T data){
            size++;
            Node node =new Node(data);
            map.computeIfAbsent(data,k->new DLL()).addLast(node);
            return node;
        }

        public Node next(Node node){
            if(node.isTail()){
                if(map.higherEntry(node.data)==null){
                    System.out.println("ss");
                }
                return map.higherEntry(node.data).getValue().peekFirst();
            }else{
                return node.next;
            }
        }

        public Node prev(Node node){
            if(node.isHead()){
                return map.lowerEntry(node.data).getValue().peekLast();
            }else{
                return node.prev;
            }
        }
        public boolean contains(T data){
            return map.containsKey(data);
        }
        public void remove(Node node){
            if(!contains(node.data)) return;
            size--;
            DLL dll = map.get(node.data);
            node.removeFromList();
            if(dll.isEmpty()){
                map.remove(node.data);
            }
        }
        public void remove(T data){
            if(contains(data)){
                size--;
                DLL dll = map.get(data);
                dll.removeFirst();
                if(dll.isEmpty()){
                    map.remove(data);
                }
            }
        }

        class DLL{
            Node dummyHead,dummyTail;
            public DLL(){
                dummyHead=new Node(null,true);
                dummyTail=new Node(null,true);
                dummyHead.next=dummyTail;
                dummyTail.prev=dummyHead;
            }
            public void addLast(Node node){
                dummyTail.prev.next = node;
                node.prev= dummyTail.prev;

                dummyTail.prev=node;
                node.next=dummyTail;
            }
            public void removeFirst(){
                if(dummyHead.next==dummyTail) return;
                dummyHead.next.removeFromList();
            }
            public Node peekFirst(){
                return dummyHead.next;
            }
            public Node peekLast(){
                return dummyTail.prev;
            }
            public boolean isEmpty(){
                return dummyHead.next==dummyTail;
            }

        }

        class Node{
            T data;
            Node prev;
            Node next;
            boolean isDummy;
            public Node(T data){
                this.data=data;
            }
            public Node(T data, boolean isDummy){
                this(data);
                this.isDummy=isDummy;
            }
            public Node next(){
                return next;
            }
            public Node prev(){
                return prev;
            }
            public boolean isTail(){
                if(this.next==null) return true;
                return this.next.isDummy;
            }

            public boolean isHead(){
                if(this.prev==null) return true;
                return this.prev.isDummy;
            }
            public void removeFromList(){
                if(this.prev!=null){
                    this.prev.next=this.next;
                }
                if(this.next!=null){
                    this.next.prev=this.prev;
                }
                this.next=null;
                this.prev=null;
            }
        }
    }

    public static void main(String[] args){
        int d=10000;
        int[] expenditure = new int[200000];
        Random rand = new Random();

        try(
                BufferedReader in = new BufferedReader(new FileReader(  Paths.get("FileIO/test.txt").toFile() ));
//                BufferedWriter out =  new BufferedWriter(new FileWriter(output));
        ){
            String line = null;

            int i=0;
            while( (line=in.readLine())!=null){
                String[] content = line.split(" ");
//                int index = line.indexOf(' ');
//                String first = index>-1?line.substring(0, index):line;
//                out.write(first+"\n");
                for(int j=0;j<content.length;j++){
                    expenditure[i+j]=Integer.parseInt(content[j] );
                }

            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
//        for(int i =0 ;i<20000;i++){
//            expenditure[i]=rand.nextInt(1000);
//        }
//        int[] ex2 = new int[]{
//                492,274,20,990,69,132,804,8,19,285,603,354,627,406,597,8,332,519,67,811,277,319,412,863,873,551,170,130,989,860,887,140,24,408,709,671,769,228,668,628,615,876,631,967,713,695,232,633,230,401,490,880,70,143,829,509,624,702,883,825,197,48,281,397,697,580,674,660,162,496,613,63,936,126,303,294,101,297,747,686,910,259,190,635,807,996,751,467,765,129,51,548,373,614,695,443,354,841,142,802,628,637,435,357,827,999,296,618,882,705,52,514,113,361,908,992,499,837,104,389,927,477,518,142,121,236,844,523,528,269,358,644,611,926,936,186,772,347,94,367,62,210,167,875,371,607,239,492,62,948,35,409,368,774,440,619,310,30,636,976,703,530,486,758,795,800,418,993,6,46,740,529,764,345,654,34,823,385,789,985,796,845,21,672,732,144,308,127,62,895,412,320,193,477,778,260,272,190,924,966,14,732,888,380,464,481,255,462,378,402,624,565,993,284,289,393,728,479,368,238,759,595,423,60,117,978,54,594,470,412,170,735,199,225,388,980,678,599,21,289,295,40,181,243,217,886,75,62,320,910,246,173,331,735,433,907,29,885,557,277,353,287,177,326,110,145,829,38,266,230,540,64,635,509,64,927,192,975,311,660,15,779,794,878,31,822,263,471,61
//        };
        System.out.println(activityNotifications(expenditure,d) );


    }


}
