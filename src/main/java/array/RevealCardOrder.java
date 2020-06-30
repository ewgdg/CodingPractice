package array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class RevealCardOrder {
    //In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.
    //
    //Initially, all the cards start face down (unrevealed) in one deck.
    //
    //Now, you do the following steps repeatedly, until all cards are revealed:
    //
    //Take the top card of the deck, reveal it, and take it out of the deck.
    //If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
    //If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
    //Return an ordering of the deck that would reveal the cards in increasing order.
    //
    //The first entry in the answer is considered to be the top of the deck.
    //
    //
    //
    //Example 1:
    //
    //Input: [17,13,11,2,3,5,7]
    //Output: [2,13,3,11,5,17,7]
    //Explanation:
    //We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
    //After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
    //We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
    //We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
    //We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
    //We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
    //We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
    //We reveal 13, and move 17 to the bottom.  The deck is now [17].
    //We reveal 17.
    //Since all the cards revealed are in increasing order, the answer is correct.



     public int[] deckRevealedIncreasing(int[] deck) {
         //reverse the op , thinking from the final state

         Arrays.sort(deck);

         Deque<Integer> queue = new ArrayDeque<Integer>();
         int n = deck.length;
         for(int i = n-1;i>0;i--){

             queue.addFirst(deck[i]);
             //put bottom onto top
             queue.addFirst(queue.removeLast());

         }
         queue.addFirst(deck[0]);

         return queue.stream().mapToInt(x->x).toArray();

     }
//     !!!!
//general method for all kind of manipulation simulation ordering question
     public int[] method2(int[] deck){
         //another simulation method
         //with index order to find the correct position,
         // find the desired order
         // find the executing order
         // read num from desired order and move into new result array based on executing order index

         Arrays.sort(deck);

         Deque<Integer> index = new ArrayDeque<Integer>();
         int n = deck.length;
         for(int i =0 ; i<n;i++){
             index.addLast(i);
         }

        int[] res = new int[n];
         for(int card: deck){
             int i = index.removeFirst();//we know the first polled i is 0 which we need to fill the smallest item at this index
             res[i]=card;

             //simulate the process
             if (!index.isEmpty())
                 index.add(index.removeFirst());
         }


         return res;



     }
}
