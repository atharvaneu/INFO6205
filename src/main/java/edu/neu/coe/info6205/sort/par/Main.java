package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    private static int PARALLELISM_LEVEL = 8;

    public static void main(String[] args) {
        processArgs(args);
//        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        System.out.println("(CUSTOM) Degree of parallelism: " + PARALLELISM_LEVEL);
        Random random = new Random();
        int[] array = new int[800000];
        ArrayList<Long> timeList = new ArrayList<>();

        ForkJoinPool pool = new ForkJoinPool(PARALLELISM_LEVEL);

        int[] cutoffValues = {500, 1000, 5000, 10000, 50000, 100000, 500000};

        for (int cutoff : cutoffValues) {
            ParSort.cutoff = cutoff;

            long startTime = System.currentTimeMillis();
            // Run 10 times for each cutoff
            for (int t = 0; t < 10; t++) {
                // Generate new random array
                for (int i = 0; i < array.length; i++) {
                    array[i] = random.nextInt(10000000);
                }

                int[] arrayCopy = array.clone();
                pool.submit(() -> ParSort.sort(arrayCopy, 0, arrayCopy.length, pool));
            }
            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime);
            timeList.add(time);

            System.out.println("Cutoff: " + cutoff + "\t10 times total Time: " + time + "ms");
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 0;
            for (long i : timeList) {
                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }


    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) {
                //noinspection ResultOfMethodCallIgnored
//                ForkJoinPool.getCommonPoolParallelism();

                PARALLELISM_LEVEL = Integer.parseInt(y);
            }

    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}