import java.util.PriorityQueue;

public class Ugly {

    class Node implements Comparable<Node>{
        int prime;
        int index;//index in dp
        int val;
        public Node(int p, int i){
            prime=p; index=i; val = Ugly.this.dp[index]*prime;
        }
        public int compareTo(Node o){
            return this.val-o.val;
        }
    }
    int[] dp;
    public int nthUglyNumber(int n) {
        if(n==0) return 0;
        //view it as 2d matrix use multi-way merge
        //dp 0 1 2 3 4 5
        //prime
        //2
        //3
        //5


        dp = new int[n];
        dp[0]=1;
        // int[] primes = new primes[3];
        // prime[0]= 2;
        // prime[1]= 3;
        // prime[2]= 5;




        PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.add(new Node(2,0));
        heap.add(new Node(3,0));
        heap.add(new Node(5,0));


        for(int i =1;i<n;i++){
            Node cur = heap.poll();
            dp[i]=cur.val;
            heap.add(new Node(cur.prime,cur.index+1));
            if(dp[i]==dp[i-1]) i--;//remove dup
        }

        return dp[n-1];



    }
}
