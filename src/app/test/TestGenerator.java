package app.test;

import app.logic.Algorithm;
import app.logic.SolutionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestGenerator {
    // Run every algorithm 10 times and save the data for each algorithm in different files
    private static final int ITERATION = 15;
    private static final int DIM_X = 50, DIM_Y = 50;

    public TestGenerator(){
    }

    public void generateTests() throws IOException, SolutionException {
   /*    testRecursiveBacktracking();
       System.gc();
       testPrim();
       System.gc();
       testButtomUp();
       System.gc();
       testWilson();
       System.gc();
       testRandom();
       System.gc();*/
       testRBBottomUp();
    }

    private void testRBBottomUp() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("RB Bottom Up Algo");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            System.out.println("RB Bottom Up done " + i);
        }
        Files.write(Paths.get("RBBottomUp.txt"), lines);
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
            System.out.println("Recursive backtracking done " + i);
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
            System.out.println("Prim done " + i);
        }
        Files.write(Paths.get("Prim.txt"), lines);
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
            System.out.println("Wilson done " + i);
        }
        Files.write(Paths.get("Wilson.txt"), lines);
    }

    private void testRandom() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Random Algo");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            System.out.println("Random done " + i);
        }
        Files.write(Paths.get("Random.txt"), lines);
    }

    private void testButtomUp() throws SolutionException, IOException {
        int deadEnds, rivers, length, turns;
        ArrayList<String> lines = new ArrayList<String>();

        //lines.add("50 50");
        for (int i = 0; i < ITERATION; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.generateMaze(DIM_X, DIM_Y);
            algorithm.runAlgorithm("Bottom up Algo");
            algorithm.getSolution();

            deadEnds = algorithm.getNumberOfDeadEnds();
            rivers = algorithm.getRiverFactor();
            length = algorithm.lengthOfSolution();
            turns = algorithm.getNumberOfTurnsInSolution();

            lines.add(deadEnds + " " + rivers + " " + length + " " + turns);
            System.out.println("Bottom up done " + i);
        }
        Files.write(Paths.get("BottomUp.txt"), lines);
    }

}
