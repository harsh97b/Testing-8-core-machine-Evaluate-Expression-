import java.util.HashMap;
import java.util.Map;

public class StoreInMapToGroupBy implements Runnable{
    private int iterateLoopStart = 0;
    private int iterateLoopEnd = 0;
    public static Map<String,Double> map = new HashMap<>();

    StoreInMapToGroupBy(int iterateLoopStart, int iterateLoopEnd){
        this.iterateLoopStart = iterateLoopStart;
        this.iterateLoopEnd = iterateLoopEnd;
    }

    @Override
    public void run() {
        storeInMap(iterateLoopStart, iterateLoopEnd);
    }
    public static void storeInMap(int iterateLoopStart, int iterateLoopEnd){
        long start = 0, end =0;

        if(ReadInputs.noOfLines != 0) {


            //starting time
            start = System.currentTimeMillis();

            //Task
            //************************************************************************************

//            System.out.println("        by thread: "+Thread.currentThread().getName()+ "       [StoreInMapToGroupBy]        iterateLoopStart: "+iterateLoopStart+"        iterateLoopEnd: "+iterateLoopEnd);

            for (int i = iterateLoopStart; i <= iterateLoopEnd; i++) {
//                System.out.println("i: "+i);
                if (ReadInputs.region[i] == null) {
                    System.out.println("------------------------------------>>>>>>>>>>>>>>>>>here one region value is null");
                    break;
                }
                String str = ReadInputs.region[i] + " " + ReadInputs.country[i];
                double count = StoreInMapToGroupBy.map.getOrDefault(str, 0.0);
//                System.out.println("For: "+ str + "   count: "+count+"     result: "+ReadInputs.result[i]);
                StoreInMapToGroupBy.map.put(str, count + ReadInputs.result[i]);
            }
//            StoreInMapToGroupBy.map.forEach((key, value) -> System.out.println(key + " " + value));


            //ending time
            end = System.currentTimeMillis();
            System.out.println("        by thread: "+Thread.currentThread().getName()+ "       [StoreInMapToGroupBy]    Loop("+iterateLoopStart+" -> "+iterateLoopEnd+")    Storing in map "+(iterateLoopEnd-iterateLoopStart+1)+" inputs takes: " + (end - start) + "ms");
//            System.out.println("   "+start+"  "+end);
        }
    }
}
