public class PrintResult implements Runnable{
    @Override
    public void run() {
        printResult();
    }
    public static void printResult(){
        //starting time
        long start = System.currentTimeMillis();

        //Task
        //printing the map
        StoreInMapToGroupBy.map.forEach((key, value) -> System.out.println(key + " " + value));

        //ending time
        long end = System.currentTimeMillis();
        System.out.println("       Printing Output from map takes: " + (end - start) + "ms");

    }
}
