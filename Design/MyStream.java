import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface MyStream {
    //stream is a buffer??
    //benefit is the buffer size is limit so we can save some memory so cache will be efficient
    //also IO fetch data can be parallel with processing the data
    //parallel stream can use multiple cores

    // most of implementation of intermediate op(map, filter, flatMap, peek)  are fully fused(stateless)
    // we build a chain of cascading  Consumer objects and pour the data in, process single elem each time. compose the stages into a big function, and feed the data to that

    //(distinct, sorted, limit) are stateful ops, more complicated, and vary more in their behavior.
    //Each stateful operation gets to choose how it wants to implement itself
    // For example, distinct (under some circumstances), lets elements come out as they are vetted, whereas sorted is a full barrier.
    //(The difference is in how much laziness is possible, and how well they handle things like infinite sources with a limit operation downstream.)


    // fusion: in addition to the value of short-circuiting, (can still proceed under short-circuiting???)
    // additional big wins from fusion include (a) you don't have to populate intermediate result containers between stages,(less memory??)
    // and (b) the data you are dealing with is always "hot" in cache.(cache locality)

    //stateful op implementation
    //https://stackoverflow.com/questions/28363323/partition-a-stream-by-a-discriminator-function/28363324#28363324
    //convert to the stream.spliterator then wrap it with another new spliterator with new tryAdvance then back to stream with StreamSupport.stream(spliterator)




    public static void main(String[] args) throws Exception{
        //some stream examples
        System.out.println(IntStream.range(1,5).sum());//1+2+3+4=10

        IntStream.range(1,10).skip(5).forEach(System.out::print);//skip first 5 elem, IntStream for primitive int type?
        IntStream.range(1,10).skip(5).forEach(x->System.out.print(x)); //use lambda action

        Stream.of("Ava","Aneri","Alberto").sorted().findFirst().ifPresent(System.out::println);

        String[] names = {"Ava","Aneri","Alberto","Sara","Sb"};
        //Arrays.stream(names)
        Stream.of(names)
                .filter(x->x.startsWith("S"))
                .sorted()
                .forEach(System.out::println);

        //avg of squares
        Arrays.stream(new int[]{2,4,6,8,10})
                .map(x->x*x)
                .average()
                .ifPresent(System.out::println);

        //list to stream
        List<String> people = Arrays.asList(names);
        people.stream()
                .map(String::toLowerCase)
                .forEach(System.out::println);
//        Stream.of(people)

        //from file
        List<String> test = Files.lines(Paths.get("FileIO\\in.txt"))
                .filter(x->x.contains("1"))
                .collect(Collectors.toList());
        test.forEach(x-> System.out.println(x));

        //csv
        Stream<String> rows1= Files.lines(Paths.get("FileIO\\test.csv"));
        int rowCount = (int)rows1
                .map(x->x.split(","))//split into stream of array
                .filter(x->x.length==3)//skip invalid row
                .count();
        rows1.close();//close file stream

        //to hashmap
        Map<String,Integer> map = Files.lines(Paths.get("FileIO\\test.csv"))
                .map(x->x.split(","))//split into stream of array
                .filter(x->x.length==3)//skip invalid row
                .filter(x-> Integer.parseInt(x[1])>2 )
                .collect(Collectors.toMap(x->x[0],x->Integer.parseInt(x[1])));


        //reduction
        double total = Stream.of(7.3,1.5,4.8)
                .reduce(0.0,(Double a,Double b)->(a+b)); //0.0 is init val
        System.out.println(total);

        //reduction--summary statistics
        IntSummaryStatistics summary = IntStream.of(7,2,19,88,73,4,10)
                .summaryStatistics();
        System.out.println(summary);







    }
}
