import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) {
        //counting the cores present in the system to decide the number of threads

        String path;
        long start=0, end=0;
        String expr = "a1+a2+a3+a4";

        if(args.length == 2){
            System.out.println("Taking Arguments from CLI");
            expr = args[0];
            path = args[1];
            System.out.println("Expression: "+expr);
            System.out.println("Path: "+path);
        }
        else{
//            path = "C:\\Users\\veer5\\IdeaProjects\\Evaluate Expression\\Inputs\\Inputs.txt";
            path = "C:\\Users\\veer5\\IdeaProjects\\Evaluate Expression\\Inputs\\Inputs_1_Million.txt";
//            path = "C:\\Users\\veer5\\IdeaProjects\\Evaluate Expression\\Inputs\\Inputs_5_Million.txt";
//            path = "C:\\Users\\veer5\\IdeaProjects\\Evaluate Expression\\Inputs\\Inputs_10_Million.txt";
//            path = "C:\\Users\\veer5\\IdeaProjects\\Evaluate Expression\\Inputs\\Inputs_1_Million_Single.txt";
        }


        //starting time for the whole program
        long startOfProgram = System.currentTimeMillis();


        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("core count: "+coreCount);



        System.out.println("---------------------------------Proceeding to the ReadInputs------------------------------------");

        //starting time for ReadInputs
        start = System.currentTimeMillis();

        //Task
        ReadInputs ri = new ReadInputs(expr,path);
        ReadInputs.readInputs();

        //ending time for ReadInputs
        end = System.currentTimeMillis();
        System.out.println("Running ReadInputs takes: " + (end - start) + "ms");

        System.out.println("-------------------------Proceeding to the StoreInArray and Evaluating---------------------------");
        //This section will use multithreading

        //starting time for StoreInArray
        start = System.currentTimeMillis();

        int k = ReadInputs.noOfLines;
        int iterateLoopStart = 0;
        int iterateLoopEnd = 0;
        StoreInArray[] storeInArray = new StoreInArray[coreCount];
        StoreInMapToGroupBy[] storeInMapToGroupBy = new StoreInMapToGroupBy[coreCount];

        ExecutorService service = Executors.newFixedThreadPool(coreCount);
        List<Future> allFutures= new ArrayList<>();
        Future<?> future;

        //If number of lines are greater than 3 then only we need to divide work across multiple threads
        // otherwise single thread can handle 3 lines
        if(k>3){
            iterateLoopStart = 0;
            int batchSize = ReadInputs.noOfLines/4;
            int j=0;
            for (j = 1; j < coreCount; j++) {
                iterateLoopEnd = j * batchSize;
//                System.out.println("iterateLoopStart: "+iterateLoopStart+"        iterateLoopEnd: "+iterateLoopEnd);

                storeInArray[j-1] = new StoreInArray(iterateLoopStart,iterateLoopEnd);
                iterateLoopStart = iterateLoopEnd+1;
            }
            iterateLoopEnd = ReadInputs.noOfLines-1;
//            System.out.println("iterateLoopStart: "+iterateLoopStart+"        iterateLoopEnd: "+iterateLoopEnd);
            storeInArray[j-1] = new StoreInArray(iterateLoopStart,iterateLoopEnd);

            for (int i = 0; i < coreCount; i++) {
                future = service.submit(storeInArray[i]);
                allFutures.add(future);
            }
//            service.execute(new StoreInArrayFirstPart());
//            service.execute(new StoreInArraySecondPart());
//            service.execute(new StoreInArrayThirdPart());
//            service.execute(new StoreInArrayFourthPart());
        }
        else {
            System.out.println("here in else : input lines<=3");
            storeInArray[0] = new StoreInArray(0, k - 1);
            future = service.submit(storeInArray[0]);
            allFutures.add(future);
//            service.execute(new StoreInArrayFull());
        }

        int noOfTasks = allFutures.size();
        try {
            for (int i = 0; i < noOfTasks; i++) {
                allFutures.get(i).get();
                System.out.println("Task "+(i+1)+" is completed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //allFutures.clear();
//        StoreInArray.print();

        //ending time for StoreInArray
        end = System.currentTimeMillis();
        System.out.println("Running StoreInArray and Evaluating takes: " + (end - start) + "ms");


        System.out.println("-----------------------------Proceeding to the StoreInMapToGroupBy-------------------------------");

        //starting time for StoreInMapToGroupBy
        start = System.currentTimeMillis();

        //doing StoreInMapToGroupBy by multithreading was giving wrong result, so decided to do it with single thread;
        future = service.submit(new StoreInMapToGroupBy(0,k-1));
        allFutures.add(future);
//        if(k>3){
////            service.execute(new StoreInArray(0,k-1));
//            iterateLoopStart = 0;
//            int batchSize = ReadInputs.noOfLines/4;
//            int j=0;
//
//            for (j = 1; j < coreCount; j++) {
//                iterateLoopEnd = j * batchSize;
//                storeInMapToGroupBy[j-1] = new StoreInMapToGroupBy(iterateLoopStart,iterateLoopEnd);
//                iterateLoopStart = iterateLoopEnd+1;
//            }
//            iterateLoopEnd = ReadInputs.noOfLines-1;
//            storeInMapToGroupBy[j-1] = new StoreInMapToGroupBy(iterateLoopStart,iterateLoopEnd);
//            System.out.println("storeInMapToGroupBy.length: "+storeInMapToGroupBy.length);
//
//            for (int i = 0; i < coreCount; i++) {
//                future = service.submit(storeInMapToGroupBy[i]);
//                allFutures.add(future);
//            }
//        }
//        else {
//            System.out.println("here in else : input lines<=3");
//            storeInMapToGroupBy[0] = new StoreInMapToGroupBy(0, k - 1);
//            future = service.submit(storeInMapToGroupBy[0]);
//            allFutures.add(future);
//
//        }

        // For haulting the program till previous tasks execute
        int noOfTasksNew = allFutures.size();
        try {
//            System.out.println("noOfTasks: "+noOfTasks+"    noOfTasksNew: "+noOfTasksNew);
            for (int i = noOfTasks; i < noOfTasksNew; i++) {
                allFutures.get(i).get();
                System.out.println("Task "+(i+1)+" is completed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //ending time for StoreInMapToGroupBy
        end = System.currentTimeMillis();
        System.out.println("Running StoreInMapToGroupBy takes: " + (end - start) + "ms");

        System.out.println("-------------------------------Proceeding to the PrintResult------------------------------------");

        //starting time for PrintResult
        start = System.currentTimeMillis();

        //submiting last task
        future = service.submit(new PrintResult());
        allFutures.add(future);

        service.shutdown();

        //haulting the program till the printresult is completed
        try {
            allFutures.get(noOfTasksNew).get();
            System.out.println("Task "+(noOfTasksNew+1)+" is completed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //ending time for PrintResult
        end = System.currentTimeMillis();
        System.out.println("Running PrintResult takes: " + (end - start) + "ms");

        //ending time for the whole program
        long endOfProgram = System.currentTimeMillis();
        System.out.println("-------------------------------< Whole program took: " + (endOfProgram - startOfProgram) + "ms >-----------------------------------");

//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//
//        executorService.schedule(new StoreInMapToGroupBy(), 10, TimeUnit.SECONDS);
//        executorService.shutdown();
//
//        ExecutorService service1 = Executors.newFixedThreadPool(coreCount);
//        service1.execute(new StoreFromArrayToObject());
//        service1.shutdown();
//
//        ExecutorService service2 = Executors.newFixedThreadPool(coreCount);
//        service2.execute(new GroupBy());
//        service2.shutdown();
//
//        StoreFromArrayToObject.doItNow();
//        GroupBy.print();
//
//        System.out.println("no of values in array : "+ReadInputs.noOfLines);
//        System.out.println("last value of arr1: "+ReadInputs.arr1[ReadInputs.noOfLines-1]);
    }
}
