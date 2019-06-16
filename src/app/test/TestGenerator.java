package app.test;

import app.logic.Algorithm;
import app.logic.domain.SolutionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestGenerator {
    private String propertyFolder = "tests/property/";
    private String timeFolder = "tests/time/";

    // Empty constructor
    public TestGenerator(){ }

    // Method testing all algorithms. It runs all algorithms 200 times with the size 50x50 maze
    // and calculates the values of the properties. These values are saved in one .txt file pr algorithm and
    // saved under tests/property/ .
    public void generatePropertyTests() throws IOException, SolutionException {
        System.out.println("Tests starting...");
        String[] algorithms = new String[]{"Recursive Backtracking Algorithm", "Prim's Algorithm", "Wilson's Algorithm", "Random Choice", "Bottom Up", "RB Bottom Up"};

        for(int j = 0; j < algorithms.length; j++) {
            int deadEnds, rivers, length, turns;
            ArrayList<String> lines = new ArrayList<String>();
            Algorithm algorithm = new Algorithm();

            for (int i = 0; i < 200; i++) {
                algorithm.generateMaze(50, 50);
                algorithm.runAlgorithm(algorithms[j]);
                algorithm.getSolution();

                deadEnds = algorithm.getNumberOfDeadEnds();
                rivers = algorithm.getRiverFactor();
                length = algorithm.lengthOfSolution();
                turns = algorithm.getNumberOfTurnsInSolution();

                lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
                System.out.println(algorithms[j] +" done " + i);
            }
            Files.write(Paths.get(propertyFolder + algorithms[j]+".txt"), lines);
        }
        System.out.println("All tests are finished");
    }

    // Method testing the all algorithms. It runs all algorithms with different sizes, and depending on the sizes a different amount of times.
    // All times are save in one .txt file pr algorithm and saved under tests/time/ .
    public void generateTimeTests() throws IOException {
        Algorithm algorithm = new Algorithm();
        //String[] algorithms = new String[]{"Recursive Backtracking Algorithm", "Prim's Algorithm", "Wilson's Algorithm", "Random Choice", "Bottom Up", "RB Bottom Up"};
        String[] algorithms = new String[]{"Random Choice", "Wilson's Algorithm"};
        System.out.println("Tests starting...");

        int[] sizes = new int[]       {   10,    15,    20,    25,    30,    35,    40,   50,   60,   70,  80,  90, 100, 120, 140, 160, 180, 200, 220, 250, 280, 320, 360, 400, 450};
        int[] noIterations = new int[]{10000, 10000, 10000, 10000, 10000, 10000, 10000, 1000, 1000, 1000, 800, 800, 800, 600, 600, 600, 500, 500, 400, 400, 100, 100,  50,  50,  50};

        for(int j = 0; j < algorithms.length; j++) {
            ArrayList<String> lines = new ArrayList<String>();
            for (int i = 0; i < sizes.length; i++) {
                lines.add(testTime(algorithm, algorithms[j], sizes[i], noIterations[i]));
            }
            Files.write(Paths.get(timeFolder + algorithms[j]+" Time Test.txt"), lines);
            System.out.println(algorithms[j] + " finish");
        }
        System.out.println("All tests are finished");
    }

    // This method creates a time test of a single algorithm with the given type, dimension and number of iterations.
    private String testTime(Algorithm algorithm, String nameOfAlgo, int dimension, int noIteration){
        long start = System.nanoTime();
        for(int i = 0; i < noIteration; i++){
            algorithm.generateMaze(dimension, dimension);
            algorithm.runAlgorithm(nameOfAlgo);
        }
        long time = (System.nanoTime() - start)/noIteration;

        long start2 = System.nanoTime();
        for(int i=0; i < noIteration; i++){
            algorithm.generateMaze(dimension, dimension);
        }
        long time2 = time - (System.nanoTime()-start2)/noIteration;

        double elapsedTimeInSecond = (double) time2 * 1e-9;
        System.out.println("Each " + nameOfAlgo + " run took an average of " + elapsedTimeInSecond + " seconds for (" + dimension + "x" + dimension +")");
        return "" + elapsedTimeInSecond;
    }
}
