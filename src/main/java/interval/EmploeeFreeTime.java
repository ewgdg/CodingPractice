package interval;

import java.util.*;

//759

/*
We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

(Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).  Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

 

Example 1:

Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation: There are a total of three employees, and all common
free time intervals would be [-inf, 1], [3, 4], [10, inf].
We discard any intervals that contain inf as they aren't finite.
Example 2:

Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
Output: [[5,6],[7,9]]
 

Constraints:

1 <= schedule.length , schedule[i].length <= 50
0 <= schedule[i].start < schedule[i].end <= 10^8

*/

class EmployeeFreeTime {
  // Definition for an Interval.
  static class Interval {
    public int start;
    public int end;

    public Interval() {
    }

    public Interval(int _start, int _end) {
      start = _start;
      end = _end;
    }

    public String toString() {
      return "[" + start + "," + end + "]";

    }

    public boolean equals(Object o) {
      if (o == null)
        return false;
      if (o.getClass() != getClass())
        return false;

      Interval that = (Interval) o;

      return that.start == this.start && that.end == end;
    }
  };

  // my first solution , use binary search tree to store intervals , ClogC where C
  // is number of intervals
  final static int NEGATIVE_INF = -1;
  final static int POSITIVE_INF = (int) Math.pow(10, 8) + 1;

  public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
    // [[1,2],[3,4]],[[1,2],[3,4]] -> [2,3]
    // [2,3] [2,3]

    // segment tree?

    // simple tree structure
    Tree tree = new Tree();

    int employeeCount = schedule.size();

    List<List<Interval>> freeIntervals = findFreeIntervals(schedule);
    // early terminate
    if (freeIntervals.size() < employeeCount) {
      // System.out.println(freeIntervals.size());
      return new ArrayList<>();
    }

    for (List<Interval> intervals : freeIntervals) {
      for (Interval interval : intervals) {
        tree.addInterval(interval);
      }
    }

    return tree.findIntervals(employeeCount);

  }

  public List<List<Interval>> findFreeIntervals(List<List<Interval>> schedule) {
    List<List<Interval>> res = new ArrayList<>();
    for (List<Interval> intervals : schedule) {
      List<Interval> freeIntervals = new ArrayList<Interval>();

      // -1 is -inf , -2 is +inf
      Interval prev = new Interval(NEGATIVE_INF, NEGATIVE_INF);
      for (Interval interval : intervals) {
        if (prev == null) {
          prev = interval;
        } else {
          if (interval.start > prev.end) {
            freeIntervals.add(new Interval(prev.end, interval.start));
          }

          prev = interval;
        }
      }
      freeIntervals.add(new Interval(prev.end, POSITIVE_INF));
      if (freeIntervals.size() > 0)
        res.add(freeIntervals);
    }
    return res;

  }

  class Tree {
    TreeNode root;

    public Tree() {
      root = null;
    }

    public void addInterval(Interval interval) {
      Interval copy = new Interval(interval.start, interval.end);
      if (root == null) {
        root = new TreeNode(copy, 1);
        return;
      }

      addInterval(copy, root, 1);

    }

    public void addInterval(Interval interval, TreeNode cur, int count) {

      // compare interval , right side interval
      if (interval.start >= cur.interval.end) {
        if (cur.right == null) {
          cur.right = new TreeNode(interval, count);
        } else
          addInterval(interval, cur.right, count);
      }
      // left side interval
      else if (interval.end <= cur.interval.start) {
        if (cur.left == null)
          cur.left = new TreeNode(interval, count);
        else
          addInterval(interval, cur.left, count);
      }
      // intersect
      else {

        // trim and split cur node
        if (interval.start > cur.interval.start) {

          // we need to also pass the cur count down to the new interval
          Interval newInterval = new Interval(cur.interval.start, interval.start);
          cur.interval.start = interval.start;

          if (cur.left == null)
            cur.left = new TreeNode(newInterval, cur.count);
          else
            addInterval(newInterval, cur.left, cur.count);
        }
        // split target interval
        else if (interval.start < cur.interval.start) {
          Interval newInterval = new Interval(interval.start, cur.interval.start);

          if (cur.left == null)
            cur.left = new TreeNode(newInterval, count);
          else
            addInterval(newInterval, cur.left, count);

        }
        // same procedure for interval end
        if (interval.end < cur.interval.end) {
          Interval newInterval = new Interval(interval.end, cur.interval.end);
          cur.interval.end = interval.end;

          if (cur.right == null)
            cur.right = new TreeNode(newInterval, cur.count);
          else
            addInterval(newInterval, cur.right, cur.count);
        } else if (interval.end > cur.interval.end) {
          Interval newInterval = new Interval(cur.interval.end, interval.end);

          if (cur.right == null)
            cur.right = new TreeNode(newInterval, count);
          else
            addInterval(newInterval, cur.right, count);

        }
        cur.count++;

      }
    }

    public List<Interval> findIntervals(int count) {
      // traverse tree in a sorted order , in-order traversal
      List<Interval> res = new ArrayList<Interval>();
      findIntervalsHelper(res, count, this.root);
      return mergeIntervals(res);
    }

    public void findIntervalsHelper(List<Interval> intervals, int count, TreeNode cur) {
      if (cur == null)
        return;
      findIntervalsHelper(intervals, count, cur.left);
      if (cur.count == count) {
        intervals.add(cur.interval);
      }
      findIntervalsHelper(intervals, count, cur.right);

    }

    public boolean validateInterval(Interval interval) {
      return (interval.start > NEGATIVE_INF && interval.end < POSITIVE_INF);
    }

    public List<Interval> mergeIntervals(List<Interval> intervals) {
      List<Interval> res = new ArrayList<>();

      for (Interval interval : intervals) {
        if (res.isEmpty()) {
          if (validateInterval(interval))
            res.add(interval);
        } else if (validateInterval(interval)) {
          Interval lastInterval = res.get(res.size() - 1);
          if (interval.start <= lastInterval.end && interval.end >= lastInterval.end) {
            lastInterval.end = interval.end;
          } else {
            // if (validateInterval(interval))
            res.add(interval);
          }
        }
      }
      return res;
    }

  }

  class TreeNode {
    Interval interval;
    int count;
    TreeNode left;
    TreeNode right;

    public TreeNode(Interval interval, int count) {
      this.interval = interval;
      this.count = count;
    }

  }

  // suggested solution 1, check problem merge intervals also
  // ClogC
  // line sweep
  // record 2 events for each interval , interval start and interval end event.
  // start event means balance++ , end event means balance --

  /*
   * If some interval overlaps any interval (for any employee), then it won't be
   * included in the answer. So we could reduce our problem to the following:
   * given a set of intervals, find all places where there are no intervals.
   * 
   * To do this, we can use an "events" approach present in other interval
   * problems. For each interval [s, e], we can think of this as two events:
   * balance++ when time = s, and balance-- when time = e. We want to know the
   * regions where balance == 0.
   * 
   * Algorithm
   * 
   * For each interval, create two events as described above, and sort the events.
   * Now for each event occuring at time t, if the balance is 0, then the
   * preceding segment [prev, t] did not have any intervals present, where prev is
   * the previous value of t.
   */
  public List<Interval> employeeFreeTime2(List<List<Interval>> avails) {
    int OPEN = 0, CLOSE = 1;

    List<int[]> events = new ArrayList<>();
    for (List<Interval> employee : avails)
      for (Interval iv : employee) {
        events.add(new int[] { iv.start, OPEN });
        events.add(new int[] { iv.end, CLOSE });
      }

    // sort in an order that open is before close if at the same time to avoid case
    // whree free interval length is 0 . e.g.[24,24]
    Collections.sort(events, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    List<Interval> ans = new ArrayList<>();

    int prev = -1, bal = 0;
    for (int[] event : events) {
      // event[0] = time, event[1] = command
      if (bal == 0 && prev >= 0)
        ans.add(new Interval(prev, event[0]));
      bal += event[1] == OPEN ? 1 : -1;
      prev = event[0];
    }

    return ans;
  }

  // suggested sol2
  // we use an priority queue to store the first intervals of all employees.
  // pop the first interval then insert a new one from that employee owns the
  // poped one.
  // in this way the generated interval is ordered and sorted.
  // given sorted interval we can easily find the gap by keep recording the
  // lastest possible target time.

  // Intuition

  // Say we are at some time where no employee is working. That work-free period
  // will last until the next time some employee has to work.
  // So let's maintain a heap of the next time an employee has to work, and it's
  // associated job.
  // When we process the next time from the heap, we can add the next job for that
  // employee.

  // Algorithm
  // Keep track of the latest time anchor that we don't know of a job overlapping
  // that time.
  // When we process the earliest occurring job not yet processed,
  // it occurs at time t, by employee e_id, and it was that employee's e_jx'th
  // job.
  // If anchor < t, then there was a free interval Interval(anchor, t).

  public List<Interval> employeeFreeTime3(List<List<Interval>> avails) {
    List<Interval> ans = new ArrayList<>();
    PriorityQueue<Job> pq = new PriorityQueue<Job>(
        (a, b) -> avails.get(a.eid).get(a.index).start - avails.get(b.eid).get(b.index).start);
    int ei = 0, anchor = Integer.MAX_VALUE;

    for (List<Interval> employee : avails) {
      pq.offer(new Job(ei++, 0));
      anchor = Math.min(anchor, employee.get(0).start);
    }

    while (!pq.isEmpty()) {
      Job job = pq.poll();
      Interval iv = avails.get(job.eid).get(job.index);
      if (anchor < iv.start)
        ans.add(new Interval(anchor, iv.start));
      anchor = Math.max(anchor, iv.end);
      if (++job.index < avails.get(job.eid).size())
        //notice that we dont need to create new job just update the old job indexS
        pq.offer(job);
    }

    return ans;
  }

  class Job {
    int eid, index;

    Job(int e, int i) {
      eid = e;
      index = i;
    }
  }
}