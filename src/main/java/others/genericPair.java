package others;

public class genericPair<T,K> {
    public T member1;
    public K member2;

    public genericPair(T t, K k){
        member1=t;
        member2=k;
    }


    public static void main(String[] args){
        genericPair<Integer,String> test =new genericPair<>(1,"sss");

        System.out.println(test.member1);
        System.out.println(test.member2);
    }
}


