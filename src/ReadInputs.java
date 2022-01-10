import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadInputs{
    public static int noOfLines;
    public static float[] arr1;
    public static float[] arr2;
    public static float[] arr3;
    public static float[] arr4;
    public static float[] result;
    public static String[] region;
    public static String[] country;
    public static String expr= null;
    public static String filePath= null;

//    public static double[] arr1;
//    public static double[] arr2;
//    public static double[] arr3;
//    public static double[] arr4;

    public static List<String> stringList;

    ReadInputs(String expr, String filePath){
        this.expr = expr;
        this.filePath = filePath;
    }


    //    long noOfLines=0;
    public static void readInputs(){

        long start = 0, end =0;
        String path = null;
        path = filePath;

        //-------------------------------------------------------------------------------------------------------

        //starting time
        start = System.currentTimeMillis();

        //Task
        stringList = bufferReaderToArrayList(path);

        //ending time
        end = System.currentTimeMillis();
        System.out.println("        Storing "+noOfLines+" lines of input in List takes: " + (end - start) + "ms");

        //-------------------------------------------------------------------------------------------------------
        int k = noOfLines;
        System.out.println("        ReadInputs.noOfLines: "+noOfLines);
        //starting time
        start = System.currentTimeMillis();

        //Task
        //Initializing the arrays;
        arr1 = new float[k];
        arr2 = new float[k];
        arr3 = new float[k];
        arr4 = new float[k];
        result = new float[k];
        region = new String[k];
        country = new String[k];

        //ending time
        end = System.currentTimeMillis();
        System.out.println("        creating 6 arrays with size "+k+"  takes: " + (end - start) + "ms");
    }
    private static List<String> bufferReaderToArrayList(String path) {
        return bufferReaderToList(path, new ArrayList<>());
    }
    private static List<String> bufferReaderToList(String path, List<String> list) {
        try {
            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            noOfLines=0;
            while ((line = in.readLine()) != null) {
                list.add(line);
                noOfLines++;
            }
            in.close();
        } catch (final IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void printCharArray(char[] c) {
        for (int i = 0; i < c.length; i++) {
            System.out.print(c[i]);
        }
        System.out.println();
    }
}
