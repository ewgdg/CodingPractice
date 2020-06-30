package others;
import java.util.TreeMap;
import java.util.TreeSet;


public class Calendar {
    TreeSet<Interval> intervals;

    public Calendar() {
        intervals = new TreeSet<>();
        map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Interval cur = new Interval(start, end);

        Interval prev = intervals.floor(cur);
        if (prev != null && prev.end <= end && prev.end > start) {
            return false;
        }
        Interval next = intervals.ceiling(cur);
        if (next != null && next.start >= start && next.start < end) {
            return false;
        }

        intervals.add(cur);
        return true;

    }

    class Interval implements Comparable<Interval> {
        int start, end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int compareTo(Interval that) {
            return start - that.start;
        }
    }


    //method 2 using treemap start to end
    TreeMap<Integer, Integer> map;


    public boolean book2(int start, int end) {
        Integer low = map.lowerKey(end);

        if (low == null || map.get(low) <= start) {
            map.put(start, end);
            return true;
        }
        return false;
    }


    public static void main(String[] args) {

    }
}
