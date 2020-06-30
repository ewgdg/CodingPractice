package Design;
import java.util.*;

class Twitter {
    //the key is ood!!! so we need to abstract some obj!!! : user and tweet
    /** Initialize your data structure here. */
    int curTime;
    HashMap<Integer,User> userMap;
    public Twitter() {
        curTime=0;
        userMap = new HashMap<>();

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        addUser(userId);
        User user = userMap.get(userId);
        Tweet newTweet = new Tweet(tweetId);
        newTweet.next = user.head;
        user.head = newTweet;
    }
    //extra helper
    public void addUser(int userId){
        userMap.putIfAbsent(userId, new User(userId));
    }
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    static int NewsFeedLimit=10;
    public List<Integer> getNewsFeed(int userId) {
        addUser(userId);
        User user = userMap.get(userId);
        PriorityQueue<Tweet> queue = new PriorityQueue<>( (a, b)-> b.time-a.time  );

        for(Integer followeeId: user.followed){
            User followee = userMap.get(followeeId);
            if(followee.head!=null)
                queue.add(followee.head);//most recent
        }
        if(user.head!=null) queue.add(user.head);
        int count = 0;
        List<Integer> res= new ArrayList<>();
        while(count <NewsFeedLimit &&  !queue.isEmpty()){
            Tweet t = queue.poll();
            if(t!=null){
                res.add(t.data);
                if(t.next!=null)
                    queue.add(t.next);
                count++;
            }
        }
        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        addUser(followerId); addUser(followeeId);
        User user = userMap.get(followerId);
        if(followerId!=followeeId)
            user.followed.add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        addUser(followerId);  addUser(followeeId);
        User user = userMap.get(followerId);
        user.followed.remove(followeeId);
    }

    class User{
        int id;
        Tweet head;
        Set<Integer> followed;

        public User(int id ){
            this.id= id;
            followed = new HashSet<>();
        }

    }
    //lnked list
    class Tweet{
        Tweet next;
        int data;
        int time;
        public Tweet(int d){
            data =d;
            time = curTime;
            curTime++;
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
