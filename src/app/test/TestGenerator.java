package app.test;

import app.logic.Algorithm;
import app.logic.SolutionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestGenerator {
    // Run every algorithm 10 times and save the data for each algorithm in different files
    private static final int ITERATION = 200;
    private static final int ITERATIONTIME = 200;
    private static final int DIM_X = 50, DIM_Y = 50;

    private String folder = "tests/";

    public TestGenerator(){
    }

    public void generatePropertyTests() throws IOException, SolutionException {
        System.out.println("Tests starting...");
        testButtomUp();
        testRecursiveBacktracking();
        testPrim();
        testWilson();
        testRandom();
        testRBBottomUp();
        System.out.println("All tests are finished");
    }

    public void generateTimeTests() throws IOException {
        System.out.println("Tests starting...");
        testRecursiveBacktrackingTime();
        System.out.println("RB finish");
        testButtomUpTime();
        System.out.println("Bottom Up finish");
        testPrimTime();
        System.out.println("Prim finish");
        testWilsonTime();
        System.out.println("Wilson finish");
        testRandomTime();
        System.out.println("Random finish");
        testRBBottomUpTime();
        System.out.println("RB Bottom Up finish");
        System.out.println("All tests are finished");
    }

    private void testRBBottomUp() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("RB Bottom Up");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println("RB Bottom Up done " + i);
        }
        Files.write(Paths.get(folder+"RBBottomUp.txt"), lines);
    }

    private void testRecursiveBacktracking() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println("Recursive backtracking done " + i);
        }
        Files.write(Paths.get("RecursiveBacktracking.txt"), lines);
    }

    private void testPrim() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Prim's Algorithm");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println("Prim done " + i);
        }
        Files.write(Paths.get(folder+"Prim.txt"), lines);
    }

    private void testWilson() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Wilson's Algorithm");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println("Wilson done " + i);
        }
        Files.write(Paths.get(folder+"Wilson.txt"), lines);
    }

    private void testRandom() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Random Choice");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println("Random done " + i);
        }
        Files.write(Paths.get(folder+"Random.txt"), lines);
    }

    private void testButtomUp() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Bottom Up");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println(deadEnds + " " + rivers + " " + length + " " + turns);
            //System.out.println("Bottom up done " + i);
        }
        Files.write(Paths.get(folder+"BottomUp.txt"), lines);
    }

    private void testRandomTime() throws IOException {
        Algorithm algorithm = new Algorithm();
        ArrayList<String> lines = new ArrayList<String>();

        long start0 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(10, 10);
            algorithm.runAlgorithm("Random Choice");
        }
        long time0 = (System.nanoTime() - start0)/ITERATIONTIME;
        double elapsedTimeInSecond0 = (double) time0 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond0 + " seconds for (10x10)");
        lines.add("" + elapsedTimeInSecond0);


        long start1 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(20, 20);
            algorithm.runAlgorithm("Random Choice");
        }
        long time1 = (System.nanoTime() - start1)/ITERATIONTIME;
        double elapsedTimeInSecond1 = (double) time1 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond1 + " seconds for (20x20) ");
        lines.add(""+elapsedTimeInSecond1);

        long start2 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(30, 30);
            algorithm.runAlgorithm("Random Choice");
        }
        long time2 = (System.nanoTime() - start2)/ITERATIONTIME;
        double elapsedTimeInSecond2 = (double) time2 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond2 + " seconds for (30x30)");
        lines.add(""+elapsedTimeInSecond2);


        long start3 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(40, 40);
            algorithm.runAlgorithm("Random Choice");
        }
        long time3 = (System.nanoTime() - start3)/ITERATIONTIME;
        double elapsedTimeInSecond3 = (double) time3 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond3 + " seconds for (40x40)");
        lines.add(""+elapsedTimeInSecond3);

        long start4 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(50, 50);
            algorithm.runAlgorithm("Random Choice");
        }
        long time4 = (System.nanoTime() - start4)/ITERATIONTIME;
        double elapsedTimeInSecond4 = (double) time4 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond4 + " seconds for (50x50)");
        lines.add(""+elapsedTimeInSecond4);


        long start5 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(60, 60);
            algorithm.runAlgorithm("Random Choice");
        }
        long time5 = (System.nanoTime() - start5)/ITERATIONTIME;
        double elapsedTimeInSecond5 = (double) time5 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond5 + " seconds for (60x60)");
        lines.add(""+elapsedTimeInSecond5);


        long start6 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(70, 70);
            algorithm.runAlgorithm("Random Choice");
        }
        long time6 = (System.nanoTime() - start6)/ITERATIONTIME;
        double elapsedTimeInSecond6 = (double) time6 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond6 + " seconds for (70x70)");
        lines.add(""+elapsedTimeInSecond6);

        long start7 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(80, 80);
            algorithm.runAlgorithm("Random Choice");
        }
        long time7 = (System.nanoTime() - start7)/ITERATIONTIME;
        double elapsedTimeInSecond7 = (double) time7 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond7 + " seconds for (80x80)");
        lines.add(""+elapsedTimeInSecond7);


        long start8 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(100, 100);
            algorithm.runAlgorithm("Random Choice");
        }
        long time8 = (System.nanoTime() - start8)/ITERATIONTIME;
        double elapsedTimeInSecond8 = (double) time8 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond8 + " seconds for (100x100)");
        lines.add(""+elapsedTimeInSecond8);


        long start9 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(120, 120);
            algorithm.runAlgorithm("Random Choice");
        }
        long time9 = (System.nanoTime() - start9)/ITERATIONTIME;
        double elapsedTimeInSecond9 = (double) time9 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond9 + " seconds for (120x120)");
        lines.add(""+elapsedTimeInSecond9);


        long start10 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(140, 140);
            algorithm.runAlgorithm("Random Choice");
        }
        long time10 = (System.nanoTime() - start10)/ITERATIONTIME;
        double elapsedTimeInSecond10 = (double) time10 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond10 + " seconds for (140x140)");
        lines.add(""+elapsedTimeInSecond10);

        long start11 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(160, 160);
            algorithm.runAlgorithm("Random Choice");
        }
        long time11 = (System.nanoTime() - start11)/ITERATIONTIME;
        double elapsedTimeInSecond11 = (double) time11 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond11 + " seconds for (160x160)");
        lines.add(""+elapsedTimeInSecond11);

        long start12 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(180, 180);
            algorithm.runAlgorithm("Random Choice");
        }
        long time12 = (System.nanoTime() - start12)/ITERATIONTIME;
        double elapsedTimeInSecond12 = (double) time12 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond12 + " seconds for (180x180)");
        lines.add(""+elapsedTimeInSecond12);

        long start13 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(200, 200);
            algorithm.runAlgorithm("Random Choice");
        }
        long time13 = (System.nanoTime() - start13)/ITERATIONTIME;
        double elapsedTimeInSecond13 = (double) time13 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond13 + " seconds for (200x200)");
        lines.add(""+elapsedTimeInSecond13);

        long start14 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(220, 220);
            algorithm.runAlgorithm("Random Choice");
        }
        long time14 = (System.nanoTime() - start14)/ITERATIONTIME;
        double elapsedTimeInSecond14 = (double) time14 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond14 + " seconds for (220x220)");
        lines.add(""+elapsedTimeInSecond14);

        long start15 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(260, 260);
            algorithm.runAlgorithm("Random Choice");
        }
        long time15 = (System.nanoTime() - start15)/ITERATIONTIME;
        double elapsedTimeInSecond15 = (double) time15 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond15 + " seconds for (260x260)");
        lines.add(""+elapsedTimeInSecond15);

        long start16 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(290, 290);
            algorithm.runAlgorithm("Random Choice");
        }
        long time16 = (System.nanoTime() - start16)/ITERATIONTIME;
        double elapsedTimeInSecond16 = (double) time16 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond16 + " seconds for (290x290)");
        lines.add(""+elapsedTimeInSecond16);

        long start17 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(320, 320);
            algorithm.runAlgorithm("Random Choice");
        }
        long time17 = (System.nanoTime() - start17)/ITERATIONTIME;
        double elapsedTimeInSecond17 = (double) time17 / 1000000000;
        System.out.println("Each RC run took an average of " + elapsedTimeInSecond17 + " seconds for (320x320)");
        lines.add(""+elapsedTimeInSecond17);

        Files.write(Paths.get(folder+"RandomChoiceTimeTest.txt"), lines);
    }

    private void testRBBottomUpTime() throws IOException {
        Algorithm algorithm = new Algorithm();
        ArrayList<String> lines = new ArrayList<String>();

        long start0 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(10, 10);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time0 = (System.nanoTime() - start0)/ITERATIONTIME;
        double elapsedTimeInSecond0 = (double) time0 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond0 + " seconds for (10x10)");
        lines.add(""+elapsedTimeInSecond0);


        long start1 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(20, 20);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time1 = (System.nanoTime() - start1)/ITERATIONTIME;
        double elapsedTimeInSecond1 = (double) time1 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond1 + " seconds for (20x20)");
        lines.add(""+elapsedTimeInSecond1);

        long start2 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(30, 30);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time2 = (System.nanoTime() - start2)/ITERATIONTIME;
        double elapsedTimeInSecond2 = (double) time2 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond2 + " seconds for (30x30)");
        lines.add(""+elapsedTimeInSecond2);


        long start3 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(40, 40);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time3 = (System.nanoTime() - start3)/ITERATIONTIME;
        double elapsedTimeInSecond3 = (double) time3 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond3 + " seconds for (40x40)");
        lines.add(""+elapsedTimeInSecond3);


        long start4 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(50, 50);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time4 = (System.nanoTime() - start4)/ITERATIONTIME;
        double elapsedTimeInSecond4 = (double) time4 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond4 + " seconds for (50x50)");
        lines.add(""+elapsedTimeInSecond4);

        long start5 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(60, 60);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time5 = (System.nanoTime() - start5)/ITERATIONTIME;
        double elapsedTimeInSecond5 = (double) time5 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond5 + " seconds for (60x60)");
        lines.add(""+elapsedTimeInSecond5);

        long start6 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(70, 70);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time6 = (System.nanoTime() - start6)/ITERATIONTIME;
        double elapsedTimeInSecond6 = (double) time6 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond6 + " seconds for (70x70)");
        lines.add(""+elapsedTimeInSecond6);

        long start7 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(80, 80);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time7 = (System.nanoTime() - start7)/ITERATIONTIME;
        double elapsedTimeInSecond7 = (double) time7 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond7 + " seconds for (80x80)");
        lines.add(""+elapsedTimeInSecond7);


        long start8 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(100, 100);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time8 = (System.nanoTime() - start8)/ITERATIONTIME;
        double elapsedTimeInSecond8 = (double) time8 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond8 + " seconds for (100x100)");
        lines.add(""+elapsedTimeInSecond8);


        long start9 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(120, 120);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time9 = (System.nanoTime() - start9)/ITERATIONTIME;
        double elapsedTimeInSecond9 = (double) time9 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond9 + " seconds for (120x120)");
        lines.add(""+elapsedTimeInSecond9);


        long start10 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(140, 140);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time10 = (System.nanoTime() - start10)/ITERATIONTIME;
        double elapsedTimeInSecond10 = (double) time10 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond10 + " seconds for (140x140)");
        lines.add(""+elapsedTimeInSecond10);

        long start11 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(160, 160);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time11 = (System.nanoTime() - start11)/ITERATIONTIME;
        double elapsedTimeInSecond11 = (double) time11 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond11 + " seconds for (160x160)");
        lines.add(""+elapsedTimeInSecond11);

        long start12 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(180, 180);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time12 = (System.nanoTime() - start12)/ITERATIONTIME;
        double elapsedTimeInSecond12 = (double) time12 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond12 + " seconds for (180x180)");
        lines.add(""+elapsedTimeInSecond12);

        long start13 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(200, 200);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time13 = (System.nanoTime() - start13)/ITERATIONTIME;
        double elapsedTimeInSecond13 = (double) time13 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond13 + " seconds for (200x200)");
        lines.add(""+elapsedTimeInSecond13);

        long start14 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(220, 220);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time14 = (System.nanoTime() - start14)/ITERATIONTIME;
        double elapsedTimeInSecond14 = (double) time14 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond14 + " seconds for (220x220)");
        lines.add(""+elapsedTimeInSecond14);

        long start15 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(260, 260);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time15 = (System.nanoTime() - start15)/ITERATIONTIME;
        double elapsedTimeInSecond15 = (double) time15 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond15 + " seconds for (260x260)");
        lines.add(""+elapsedTimeInSecond15);

        long start16 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(290, 290);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time16 = (System.nanoTime() - start16)/ITERATIONTIME;
        double elapsedTimeInSecond16 = (double) time16 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond16 + " seconds for (290x290)");
        lines.add(""+elapsedTimeInSecond16);

        long start17 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(320, 320);
            algorithm.runAlgorithm("RB Bottom Up");
        }
        long time17 = (System.nanoTime() - start17)/ITERATIONTIME;
        double elapsedTimeInSecond17 = (double) time17 / 1000000000;
        System.out.println("Each RB Bottom Up run took an average of " + elapsedTimeInSecond17 + " seconds for (320x320)");
        lines.add(""+elapsedTimeInSecond17);

        Files.write(Paths.get(folder+"RBBottomUpTimeTest.txt"), lines);
    }

    private void testWilsonTime() throws IOException {
        Algorithm algorithm = new Algorithm();
        ArrayList<String> lines = new ArrayList<String>();

        long start0 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(10, 10);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time0 = (System.nanoTime() - start0)/ITERATIONTIME;
        double elapsedTimeInSecond0 = (double) time0 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond0 + " seconds for (10x10)");
        lines.add(""+elapsedTimeInSecond0);


        long start1 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(20, 20);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time1 = (System.nanoTime() - start1)/ITERATIONTIME;
        double elapsedTimeInSecond1 = (double) time1 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond1 + " seconds for (20x20)");
        lines.add(""+elapsedTimeInSecond1);


        long start2 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(30, 30);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time2 = (System.nanoTime() - start2)/ITERATIONTIME;
        double elapsedTimeInSecond2 = (double) time2 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond2 + " seconds for (30x30)");
        lines.add(""+elapsedTimeInSecond2);


        long start3 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(40, 40);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time3 = (System.nanoTime() - start3)/ITERATIONTIME;
        double elapsedTimeInSecond3 = (double) time3 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond3 + " seconds for (40x40)");
        lines.add(""+elapsedTimeInSecond3);


        long start4 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(50, 50);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time4 = (System.nanoTime() - start4)/ITERATIONTIME;
        double elapsedTimeInSecond4 = (double) time4 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond4 + " seconds for (50x50)");
        lines.add(""+elapsedTimeInSecond4);

        long start5 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(60, 60);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time5 = (System.nanoTime() - start5)/ITERATIONTIME;
        double elapsedTimeInSecond5 = (double) time5 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond5 + " seconds for (60x60)");
        lines.add(""+elapsedTimeInSecond5);

        long start6 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(70, 70);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time6 = (System.nanoTime() - start6)/ITERATIONTIME;
        double elapsedTimeInSecond6 = (double) time6 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond6 + " seconds for (70x70)");
        lines.add(""+elapsedTimeInSecond6);

        long start7 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(80, 80);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time7 = (System.nanoTime() - start7)/ITERATIONTIME;
        double elapsedTimeInSecond7 = (double) time7 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond7 + " seconds for (80x80)");
        lines.add(""+elapsedTimeInSecond7);


        long start8 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(100, 100);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time8 = (System.nanoTime() - start8)/ITERATIONTIME;
        double elapsedTimeInSecond8 = (double) time8 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond8 + " seconds for (100x100)");
        lines.add(""+elapsedTimeInSecond8);


        long start9 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(120, 120);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time9 = (System.nanoTime() - start9)/ITERATIONTIME;
        double elapsedTimeInSecond9 = (double) time9 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond9 + " seconds for (120x120)");
        lines.add(""+elapsedTimeInSecond9);


        long start10 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(140, 140);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time10 = (System.nanoTime() - start10)/ITERATIONTIME;
        double elapsedTimeInSecond10 = (double) time10 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond10 + " seconds for (140x140)");
        lines.add(""+elapsedTimeInSecond10);

        long start11 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(160, 160);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time11 = (System.nanoTime() - start11)/ITERATIONTIME;
        double elapsedTimeInSecond11 = (double) time11 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond11 + " seconds for (160x160)");
        lines.add(""+elapsedTimeInSecond11);

        long start12 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(180, 180);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time12 = (System.nanoTime() - start12)/ITERATIONTIME;
        double elapsedTimeInSecond12 = (double) time12 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond12 + " seconds for (180x180)");
        lines.add(""+elapsedTimeInSecond12);

        long start13 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(200, 200);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time13 = (System.nanoTime() - start13)/ITERATIONTIME;
        double elapsedTimeInSecond13 = (double) time13 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond13 + " seconds for (200x200)");
        lines.add(""+elapsedTimeInSecond13);

        long start14 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(220, 220);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time14 = (System.nanoTime() - start14)/ITERATIONTIME;
        double elapsedTimeInSecond14 = (double) time14 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond14 + " seconds for (220x220)");
        lines.add(""+elapsedTimeInSecond14);

        long start15 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(260, 260);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time15 = (System.nanoTime() - start15)/ITERATIONTIME;
        double elapsedTimeInSecond15 = (double) time15 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond15 + " seconds for (260x260)");
        lines.add(""+elapsedTimeInSecond15);

        long start16 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(290, 290);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time16 = (System.nanoTime() - start16)/ITERATIONTIME;
        double elapsedTimeInSecond16 = (double) time16 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond16 + " seconds for (290x290)");
        lines.add(""+elapsedTimeInSecond16);

        long start17 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(320, 320);
            algorithm.runAlgorithm("Wilson's Algorithm");
        }
        long time17 = (System.nanoTime() - start17)/ITERATIONTIME;
        double elapsedTimeInSecond17 = (double) time17 / 1000000000;
        System.out.println("Each Wilson run took an average of " + elapsedTimeInSecond17 + " seconds for (320x320)");
        lines.add(""+elapsedTimeInSecond17);


        Files.write(Paths.get(folder+"WilsonTimeTest.txt"), lines);
    }

    private void testPrimTime() throws IOException {
        Algorithm algorithm = new Algorithm();
        ArrayList<String> lines = new ArrayList<String>();

        long start0 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(10, 10);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time0 = (System.nanoTime() - start0)/ITERATIONTIME;
        double elapsedTimeInSecond0 = (double) time0 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond0 + " seconds for (10x10)");
        lines.add(""+elapsedTimeInSecond0);


        long start1 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(20, 20);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time1 = (System.nanoTime() - start1)/ITERATIONTIME;
        double elapsedTimeInSecond1 = (double) time1 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond1 + " seconds for (20x20)");
        lines.add(""+elapsedTimeInSecond1);


        long start2 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(30, 30);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time2 = (System.nanoTime() - start2)/ITERATIONTIME;
        double elapsedTimeInSecond2 = (double) time2 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond2 + " seconds for (30x30)");
        lines.add(""+elapsedTimeInSecond2);


        long start3 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(40, 40);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time3 = (System.nanoTime() - start3)/ITERATIONTIME;
        double elapsedTimeInSecond3 = (double) time3 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond3 + " seconds for (40x40)");
        lines.add(""+elapsedTimeInSecond3);


        long start4 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(50, 50);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time4 = (System.nanoTime() - start4)/ITERATIONTIME;
        double elapsedTimeInSecond4 = (double) time4 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond4 + " seconds for (50x50)");
        lines.add(""+elapsedTimeInSecond4);

        long start5 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(60, 60);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time5 = (System.nanoTime() - start5)/ITERATIONTIME;
        double elapsedTimeInSecond5 = (double) time5 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond5 + " seconds for (60x60)");
        lines.add(""+elapsedTimeInSecond5);

        long start6 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(70, 70);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time6 = (System.nanoTime() - start6)/ITERATIONTIME;
        double elapsedTimeInSecond6 = (double) time6 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond6 + " seconds for (70x70)");
        lines.add(""+elapsedTimeInSecond6);

        long start7 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(80, 80);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time7 = (System.nanoTime() - start7)/ITERATIONTIME;
        double elapsedTimeInSecond7 = (double) time7 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond7 + " seconds for (80x80)");
        lines.add(""+elapsedTimeInSecond7);


        long start8 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(100, 100);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time8 = (System.nanoTime() - start8)/ITERATIONTIME;
        double elapsedTimeInSecond8 = (double) time8 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond8 + " seconds for (100x100)");
        lines.add(""+elapsedTimeInSecond8);


        long start9 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(120, 120);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time9 = (System.nanoTime() - start9)/ITERATIONTIME;
        double elapsedTimeInSecond9 = (double) time9 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond9 + " seconds for (120x120)");
        lines.add(""+elapsedTimeInSecond9);


        long start10 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(140, 140);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time10 = (System.nanoTime() - start10)/ITERATIONTIME;
        double elapsedTimeInSecond10 = (double) time10 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond10 + " seconds for (140x140)");
        lines.add(""+elapsedTimeInSecond10);

        long start11 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(160, 160);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time11 = (System.nanoTime() - start11)/ITERATIONTIME;
        double elapsedTimeInSecond11 = (double) time11 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond11 + " seconds for (160x160)");
        lines.add(""+elapsedTimeInSecond11);

        long start12 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(180, 180);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time12 = (System.nanoTime() - start12)/ITERATIONTIME;
        double elapsedTimeInSecond12 = (double) time12 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond12 + " seconds for (180x180)");
        lines.add(""+elapsedTimeInSecond12);

        long start13 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(200, 200);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time13 = (System.nanoTime() - start13)/ITERATIONTIME;
        double elapsedTimeInSecond13 = (double) time13 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond13 + " seconds for (200x200)");
        lines.add(""+elapsedTimeInSecond13);

        long start14 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(220, 220);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time14 = (System.nanoTime() - start14)/ITERATIONTIME;
        double elapsedTimeInSecond14 = (double) time14 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond14 + " seconds for (220x220)");
        lines.add(""+elapsedTimeInSecond14);

        long start15 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(260, 260);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time15 = (System.nanoTime() - start15)/ITERATIONTIME;
        double elapsedTimeInSecond15 = (double) time15 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond15 + " seconds for (260x260)");
        lines.add(""+elapsedTimeInSecond15);

        long start16 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(290, 290);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time16 = (System.nanoTime() - start16)/ITERATIONTIME;
        double elapsedTimeInSecond16 = (double) time16 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond16 + " seconds for (290x290)");
        lines.add(""+elapsedTimeInSecond16);

        long start17 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(320, 320);
            algorithm.runAlgorithm("Prim's Algorithm");
        }
        long time17 = (System.nanoTime() - start17)/ITERATIONTIME;
        double elapsedTimeInSecond17 = (double) time17 / 1000000000;
        System.out.println("Each Prim run took an average of " + elapsedTimeInSecond17 + " seconds for (320x320)");
        lines.add(""+elapsedTimeInSecond17);


        Files.write(Paths.get(folder+"PrimTimeTest.txt"), lines);
    }

    private void testRecursiveBacktrackingTime() throws IOException {
        Algorithm algorithm = new Algorithm();
        ArrayList<String> lines = new ArrayList<String>();

        long start0 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(10, 10);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time0 = (System.nanoTime() - start0)/ITERATIONTIME;
        double elapsedTimeInSecond0 = (double) time0 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond0 + " seconds for (10x10)");
        lines.add(""+elapsedTimeInSecond0);


        long start1 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(20, 20);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time1 = (System.nanoTime() - start1)/ITERATIONTIME;
        double elapsedTimeInSecond1 = (double) time1 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond1 + " seconds for (20x20)");
        lines.add(""+elapsedTimeInSecond1);

        long start2 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(30, 30);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time2 = (System.nanoTime() - start2)/ITERATIONTIME;
        double elapsedTimeInSecond2 = (double) time2 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond2 + " seconds for (30x30)");
        lines.add(""+elapsedTimeInSecond2);

        long start3 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(40, 40);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time3 = (System.nanoTime() - start3)/ITERATIONTIME;
        double elapsedTimeInSecond3 = (double) time3 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond3 + " seconds for (40x40)");
        lines.add(""+elapsedTimeInSecond3);


        long start4 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(50, 50);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time4 = (System.nanoTime() - start4)/ITERATIONTIME;
        double elapsedTimeInSecond4 = (double) time4 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond4 + " seconds for (50x50)");
        lines.add(""+elapsedTimeInSecond4);

        long start5 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(60, 60);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time5 = (System.nanoTime() - start5)/ITERATIONTIME;
        double elapsedTimeInSecond5 = (double) time5 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond5 + " seconds for (60x60)");
        lines.add(""+elapsedTimeInSecond5);

        long start6 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(70, 70);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time6 = (System.nanoTime() - start6)/ITERATIONTIME;
        double elapsedTimeInSecond6 = (double) time6 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond6 + " seconds for (70x70)");
        lines.add(""+elapsedTimeInSecond6);

        long start7 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(80, 80);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time7 = (System.nanoTime() - start7)/ITERATIONTIME;
        double elapsedTimeInSecond7 = (double) time7 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond7 + " seconds for (80x80)");
        lines.add(""+elapsedTimeInSecond7);


        long start8 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(100, 100);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time8 = (System.nanoTime() - start8)/ITERATIONTIME;
        double elapsedTimeInSecond8 = (double) time8 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond8 + " seconds for (100x100)");
        lines.add(""+elapsedTimeInSecond8);


        long start9 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(120, 120);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time9 = (System.nanoTime() - start9)/ITERATIONTIME;
        double elapsedTimeInSecond9 = (double) time9 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond9 + " seconds for (120x120)");
        lines.add(""+elapsedTimeInSecond9);


        long start10 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(140, 140);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time10 = (System.nanoTime() - start10)/ITERATIONTIME;
        double elapsedTimeInSecond10 = (double) time10 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond10 + " seconds for (140x140)");
        lines.add(""+elapsedTimeInSecond10);

        long start11 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(160, 160);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time11 = (System.nanoTime() - start11)/ITERATIONTIME;
        double elapsedTimeInSecond11 = (double) time11 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond11 + " seconds for (160x160)");
        lines.add(""+elapsedTimeInSecond11);

        long start12 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(180, 180);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time12 = (System.nanoTime() - start12)/ITERATIONTIME;
        double elapsedTimeInSecond12 = (double) time12 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond12 + " seconds for (180x180)");
        lines.add(""+elapsedTimeInSecond12);

        long start13 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(200, 200);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time13 = (System.nanoTime() - start13)/ITERATIONTIME;
        double elapsedTimeInSecond13 = (double) time13 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond13 + " seconds for (200x200)");
        lines.add(""+elapsedTimeInSecond13);

        long start14 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(220, 220);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time14 = (System.nanoTime() - start14)/ITERATIONTIME;
        double elapsedTimeInSecond14 = (double) time14 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond14 + " seconds for (220x220)");
        lines.add(""+elapsedTimeInSecond14);

        long start15 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(260, 260);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time15 = (System.nanoTime() - start15)/ITERATIONTIME;
        double elapsedTimeInSecond15 = (double) time15 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond15 + " seconds for (260x260)");
        lines.add(""+elapsedTimeInSecond15);

        long start16 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(290, 290);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time16 = (System.nanoTime() - start16)/ITERATIONTIME;
        double elapsedTimeInSecond16 = (double) time16 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond16 + " seconds for (290x290)");
        lines.add(""+elapsedTimeInSecond16);

        long start17 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(320, 320);
            algorithm.runAlgorithm("Recursive Backtracking Algorithm");
        }
        long time17 = (System.nanoTime() - start17)/ITERATIONTIME;
        double elapsedTimeInSecond17 = (double) time17 / 1000000000;
        System.out.println("Each RB run took an average of " + elapsedTimeInSecond17 + " seconds for (320x320)");
        lines.add(""+elapsedTimeInSecond17);


        Files.write(Paths.get(folder+"RBTimeTest.txt"), lines);
    }

    private void testButtomUpTime() throws IOException {
        Algorithm algorithm = new Algorithm();
        ArrayList<String> lines = new ArrayList<String>();

        long start0 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(10, 10);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time0 = (System.nanoTime() - start0)/ITERATIONTIME;
        double elapsedTimeInSecond0 = (double) time0 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond0 + " seconds for (10x10)");
        lines.add(""+elapsedTimeInSecond0);


        long start1 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(20, 20);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time1 = (System.nanoTime() - start1)/ITERATIONTIME;
        double elapsedTimeInSecond1 = (double) time1 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond1 + " seconds for (20x20)");
        lines.add(""+elapsedTimeInSecond1);

        long start2 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(30, 30);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time2 = (System.nanoTime() - start2)/ITERATIONTIME;
        double elapsedTimeInSecond2 = (double) time2 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond2 + " seconds for (30x30)");
        lines.add(""+elapsedTimeInSecond2);

        long start3 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(40, 40);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time3 = (System.nanoTime() - start3)/ITERATIONTIME;
        double elapsedTimeInSecond3 = (double) time3 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond3 + " seconds for (40x40)");
        lines.add(""+elapsedTimeInSecond3);

        long start4 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(50, 50);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time4 = (System.nanoTime() - start4)/ITERATIONTIME;
        double elapsedTimeInSecond4 = (double) time4 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond4 + " seconds for (50x50)");
        lines.add(""+elapsedTimeInSecond4);

        long start5 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(60, 60);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time5 = (System.nanoTime() - start5)/ITERATIONTIME;
        double elapsedTimeInSecond5 = (double) time5 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond5 + " seconds for (60x60)");
        lines.add(""+elapsedTimeInSecond5);

        long start6 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(70, 70);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time6 = (System.nanoTime() - start6)/ITERATIONTIME;
        double elapsedTimeInSecond6 = (double) time6 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond6 + " seconds for (70x70)");
        lines.add(""+elapsedTimeInSecond6);

        long start7 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(80, 80);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time7 = (System.nanoTime() - start7)/ITERATIONTIME;
        double elapsedTimeInSecond7 = (double) time7 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond7 + " seconds for (80x80)");
        lines.add(""+elapsedTimeInSecond7);


        long start8 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(100, 100);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time8 = (System.nanoTime() - start8)/ITERATIONTIME;
        double elapsedTimeInSecond8 = (double) time8 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond8 + " seconds for (100x100)");
        lines.add(""+elapsedTimeInSecond8);


        long start9 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(120, 120);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time9 = (System.nanoTime() - start9)/ITERATIONTIME;
        double elapsedTimeInSecond9 = (double) time9 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond9 + " seconds for (120x120)");
        lines.add(""+elapsedTimeInSecond9);


        long start10 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(140, 140);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time10 = (System.nanoTime() - start10)/ITERATIONTIME;
        double elapsedTimeInSecond10 = (double) time10 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond10 + " seconds for (140x140)");
        lines.add(""+elapsedTimeInSecond10);

        long start11 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(160, 160);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time11 = (System.nanoTime() - start11)/ITERATIONTIME;
        double elapsedTimeInSecond11 = (double) time11 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond11 + " seconds for (160x160)");
        lines.add(""+elapsedTimeInSecond11);

        long start12 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(180, 180);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time12 = (System.nanoTime() - start12)/ITERATIONTIME;
        double elapsedTimeInSecond12 = (double) time12 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond12 + " seconds for (180x180)");
        lines.add(""+elapsedTimeInSecond12);

        long start13 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(200, 200);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time13 = (System.nanoTime() - start13)/ITERATIONTIME;
        double elapsedTimeInSecond13 = (double) time13 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond13 + " seconds for (200x200)");
        lines.add(""+elapsedTimeInSecond13);

        long start14 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(220, 220);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time14 = (System.nanoTime() - start14)/ITERATIONTIME;
        double elapsedTimeInSecond14 = (double) time14 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond14 + " seconds for (220x220)");
        lines.add(""+elapsedTimeInSecond14);

        long start15 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(260, 260);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time15 = (System.nanoTime() - start15)/ITERATIONTIME;
        double elapsedTimeInSecond15 = (double) time15 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond15 + " seconds for (260x260)");
        lines.add(""+elapsedTimeInSecond15);

        long start16 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(290, 290);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time16 = (System.nanoTime() - start16)/ITERATIONTIME;
        double elapsedTimeInSecond16 = (double) time16 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond16 + " seconds for (290x290)");
        lines.add(""+elapsedTimeInSecond16);

        long start17 = System.nanoTime();
        for(int i = 0; i < ITERATIONTIME; i++){
            algorithm.generateMaze(320, 320);
            algorithm.runAlgorithm("Bottom Up");
        }
        long time17 = (System.nanoTime() - start17)/ITERATIONTIME;
        double elapsedTimeInSecond17 = (double) time17 / 1000000000;
        System.out.println("Each Bottom Up run took an average of " + elapsedTimeInSecond17 + " seconds for (320x320)");
        lines.add(""+elapsedTimeInSecond17);

        Files.write(Paths.get(folder+"BottomUpTimeTest.txt"), lines);
    }

}
