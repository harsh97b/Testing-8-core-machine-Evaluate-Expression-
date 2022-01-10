import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class StoreInArray implements Runnable{
    private int iterateLoopStart = 0;
    private int iterateLoopEnd = 0;
    StoreInArray(int iterateLoopStart, int iterateLoopEnd){
        this.iterateLoopStart = iterateLoopStart;
        this.iterateLoopEnd = iterateLoopEnd;
    }

    @Override
    public void run() {
        doIt(iterateLoopStart,iterateLoopEnd);
    }

    public static void doIt(int iterateLoopStart, int iterateLoopEnd) {
        long start = 0, end =0;

        //-------------------------------------------------------------------------------------------------

        if(ReadInputs.noOfLines != 0) {
            //------------------------------------------------------------------------------------------

            //starting time
            start = System.currentTimeMillis();

            //Task
            //************************************************************************************

//            System.out.println("        by thread: "+Thread.currentThread().getName()+ "       [StoreInArray]        iterateLoopStart: "+iterateLoopStart+"        iterateLoopEnd: "+iterateLoopEnd);

            for (int i = iterateLoopStart; i <= iterateLoopEnd; i++) {
                String[] strArray = new String[6];
                int strArrayPointer = 0;
                String str = ReadInputs.stringList.get(i);
                if(str.equals(""))// if any line in input file is empty then don't do anything for it
                    continue;
                char[] c = str.toCharArray();
                for (int j = 0; j < c.length; j++) {
                    if( c[j] == ' '){
                        strArrayPointer++;
                        continue;
                    }
                    if(strArray[strArrayPointer] == null)
                        strArray[strArrayPointer] = ""+c[j];
                    else
                        strArray[strArrayPointer] += c[j];
                }
//                System.out.println("charaArray, i: "+i+"   "+ Arrays.toString(strArray));
                ReadInputs.arr1[i] = Float.parseFloat(strArray[0]);
                ReadInputs.arr2[i] = Float.parseFloat(strArray[1]);
                ReadInputs.arr3[i] = Float.parseFloat(strArray[2]);
                ReadInputs.arr4[i] = Float.parseFloat(strArray[3]);
                ReadInputs.region[i] = strArray[4];
                ReadInputs.country[i] = strArray[5];
                ReadInputs.result[i] = Evaluate.evalString(ReadInputs.expr,ReadInputs.arr1[i], ReadInputs.arr2[i],ReadInputs.arr3[i],ReadInputs.arr4[i]);
            }

            //ending time
            end = System.currentTimeMillis();
            System.out.println("        by thread: "+Thread.currentThread().getName()+ "       [StoreInArray]    Loop("+iterateLoopStart+" -> "+iterateLoopEnd+")    Storing in Array and Evaluating "+(iterateLoopEnd-iterateLoopStart+1)+" inputs takes: " + (end - start) + "ms");
//            System.out.println("   "+start+"  "+end);
            //-------------------------------------------------------------------------------------------------
        }
    }
    public static void print(){
        if(ReadInputs.noOfLines != 0) {
            System.out.println("arr1: " + "   " + Arrays.toString(ReadInputs.arr1));
            System.out.println("arr2: " + "   " + Arrays.toString(ReadInputs.arr2));
            System.out.println("arr3: " + "   " + Arrays.toString(ReadInputs.arr3));
            System.out.println("arr4: " + "   " + Arrays.toString(ReadInputs.arr4));
            System.out.println("region: " + "   " + Arrays.toString(ReadInputs.region));
            System.out.println("country: " + "   " + Arrays.toString(ReadInputs.country));
            System.out.println("resut: " + "   " + Arrays.toString(ReadInputs.result));
        }
    }
}
//class StoreInArrayFirstPart extends StoreInArray implements Runnable{
//    public static int iterateLoopStart = 0;
//    public static int iterateLoopEnd = 0;
//
//    @Override
//    public void run() {
//        iterateLoopEnd = ReadInputs.noOfLines/4;
//        doIt(iterateLoopStart,iterateLoopEnd);
//    }
//}
//class StoreInArraySecondPart extends StoreInArray implements Runnable{
//    public static int iterateLoopStart = 0;
//    public static int iterateLoopEnd = 0;
//
//    @Override
//    public void run() {
//        int batchSize = ReadInputs.noOfLines/4;
//        iterateLoopStart =  batchSize;
//        iterateLoopEnd = 2 * batchSize;
//        doIt(iterateLoopStart,iterateLoopEnd);
//    }
//}
//class StoreInArrayThirdPart extends StoreInArray implements Runnable{
//    public static int iterateLoopStart = 0;
//    public static int iterateLoopEnd = 0;
//
//    @Override
//    public void run() {
//        int batchSize = ReadInputs.noOfLines/4;
//        iterateLoopStart =  2 * batchSize;
//        iterateLoopEnd = 3 * batchSize;
//        doIt(iterateLoopStart,iterateLoopEnd);
//    }
//}
//class StoreInArrayFourthPart extends StoreInArray implements Runnable{
//    public static int iterateLoopStart = 0;
//    public static int iterateLoopEnd = 0;
//
//    @Override
//    public void run() {
//        int batchSize = ReadInputs.noOfLines/4;
//        iterateLoopStart = 3 * batchSize;
//        iterateLoopEnd = ReadInputs.noOfLines-1;
//        doIt(iterateLoopStart,iterateLoopEnd);
//    }
//}
//class StoreInArrayFull extends StoreInArray implements Runnable{
//    public static int iterateLoopStart = 0;
//    public static int iterateLoopEnd = 0;
//
//    @Override
//    public void run() {
//        iterateLoopStart = 0;
//        iterateLoopEnd = ReadInputs.noOfLines-1;
//        doIt(iterateLoopStart,iterateLoopEnd);
//    }
//}
//
