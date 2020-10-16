package interval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static interval.EmployeeFreeTime.Interval;

public class EmploeeFreeTimeTest {

    EmployeeFreeTime solver;

    @BeforeEach
    public void setup() {
        solver = new EmployeeFreeTime();
    }

    @Test
    public void test() {
        List<List<Interval>> schedule = new ArrayList<>();
        schedule.add(new ArrayList<>() {

            {
                add(new Interval(1, 2));
                add(new Interval(5, 6));

            }
        });

        schedule.add(new ArrayList<>() {

            {
                add(new Interval(1, 3));

            }
        });
        schedule.add(new ArrayList<>() {

            {
                add(new Interval(4, 10));

            }
        });

        List<Interval> sol = solver.employeeFreeTime(schedule);
        System.out.println(sol);
        
        assertTrue(sol.equals(new ArrayList<>() {
            {

                add(new Interval(3, 4));

            }

        }));

    }

    @Test
    /*
    [[[0,1],[12,19],[42,54],[78,80],[89,93]],[[5,10],[30,62],[69,73],[80,83],[90,100]],[[13,21],[23,29],[42,43],[86,91],[97,100]],[[0,11],[24,25],[30,58],[76,93],[94,97]],[[1,3],[46,50],[56,58],[71,73],[77,99]]]
    */
    public void test2(){
        List<List<Interval>> schedule = new ArrayList<>();
        int[][][] input = new int[][][]{{{0,1},{12,19},{42,54},{78,80},{89,93}},{{5,10},{30,62},{69,73},{80,83},{90,100}},{{13,21},{23,29},{42,43},{86,91},{97,100}},{{0,11},{24,25},{30,58},{76,93},{94,97}},{{1,3},{46,50},{56,58},{71,73},{77,99}}};

        for(int[][] intervals_raw: input){
            List<Interval> intervals = new ArrayList<>();
            for(int[] interval_raw: intervals_raw ){
                intervals.add(new Interval(interval_raw[0],interval_raw[1]));
            }
            schedule.add(intervals);
        }
        List<Interval> sol = solver.employeeFreeTime(schedule);
        System.out.println(sol);
       
        //[[11,12],[21,23],[29,30],[62,69],[73,76]]
        assertTrue(sol.equals(new ArrayList<Interval>() {
            {

                add(new Interval(11, 12));
                add(new Interval(21, 23));
                add(new Interval(29, 30));
                add(new Interval(62, 69));
                add(new Interval(73, 76));

            }

        }));
    }
}
