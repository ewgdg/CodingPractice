package design;

import java.util.*;


//Design a Leaderboard class, which has 3 functions:

// addScore(playerId, score): Update the leaderboard by adding score to the given player's score. If there is no player with such id in the leaderboard, add him to the leaderboard with the given score.

// top(K): Return the score sum of the top K players.
// reset(playerId): Reset the score of the player with the given id to 0 (in other words erase it from the leaderboard). It is guaranteed that the player was added to the leaderboard before calling this function.
// Initially, the leaderboard is empty.

public class DesignALeaderBoard {

  // my first try
  class Solution1 {
    // binarySearchTree
    // Ologn for adding
    // O n for top //this is wrong check sol2

    // heap
    // OlogN adding
    // O klogn top

    // quickselection
    // O1 for adding
    // O n for top

    class Leaderboard {
      class Player {

        int score;

        public Player() {
          score = 0;
        }

        public void setScore(int score) {
          this.score = score;
        }

        public void addScore(int score) {
          this.score += score;
        }
      }

      Map<Integer, Player> players;

      public Leaderboard() {
        players = new HashMap<>();
      }

      public void addScore(int playerId, int score) {

        players.computeIfAbsent(playerId, (k) -> new Player()).addScore(score);

      }

      public int top(int K) {
        // Collection<Player> values = players.values();
        Integer[] array = players.values().stream().map((p) -> p.score).toArray(Integer[]::new);
        int left = 0;
        int right = array.length - 1;
        if (K > array.length) {
          K = array.length;
        }

        while (left < right) {
          int pivot = partition(array, left, right);
          if (pivot == K - 1)
            break;
          if (pivot < K) {
            left = pivot + 1;
          } else {
            right = pivot - 1;
          }
        }

        int sum = 0;
        for (int i = 0; i < K; i++) {
          sum += array[i];
        }
        return sum;

      }

      int partition(Integer[] array, int left, int right) {
        int pivot = new Random().nextInt(right - left + 1) + left;
        int pivotalValue = array[pivot];

        swap(array, pivot, left);

        int writeIndex = left + 1;
        for (int i = left + 1; i <= right; i++) {
          if (array[i] >= pivotalValue) {
            swap(array, writeIndex++, i);
          }

        }
        writeIndex--;
        swap(array, writeIndex, left);
        return writeIndex;

      }

      void swap(Integer[] array, int a, int b) {
        // array[a]= array[b]^(array[b]=array[a])^array[a];
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
      }

      public void reset(int playerId) {
        players.get(playerId).setScore(0);
      }
    }
  }

  class Solution2 {
    class Leaderboard {
      // binarySearchTree
      // Ologn for adding
      // key to note : traverse a java treeMap top k elem is klogn(or maybe k+logn??)  I think it is logn+k!!!!
      // instead of k or n
      // think about tree iterator

      class Player {

        public int score;

        public Player() {
          score = 0;
        }

        public void setScore(int score) {
          this.score = score;
        }

        public void addScore(int score) {
          this.score += score;
        }
      }

      Map<Integer, Player> players;
      Map<Integer, Integer> sorted;

      public Leaderboard() {
        players = new HashMap<>();
        sorted = new TreeMap<>(Collections.reverseOrder());
      }

      public void addScore(int playerId, int score) {

        if (players.containsKey(playerId)) {
          // existed player

          Player player = players.get(playerId);
          // count--
          sorted.put(player.score, sorted.get(player.score) - 1);

          player.addScore(score);
          sorted.put(player.score, sorted.getOrDefault(player.score, 0) + 1);
        } else {
          Player player = new Player();
          player.setScore(score);
          players.put(playerId, player);

          sorted.put(player.score, sorted.getOrDefault(player.score, 0) + 1);
        }

      }

      public int top(int K) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
          int score = entry.getKey();
          int count = entry.getValue();

          if (K <= count) {
            count = K;
          }

          sum += count * score;
          K -= count;

          if (K <= 0) {
            break;
          }

        }
        return sum;

      }

      public void reset(int playerId) {
        Player player = players.get(playerId);
        players.remove(playerId);
        int count = sorted.get(player.score);
        sorted.put(player.score, count - 1);

      }
    }
  }

}
