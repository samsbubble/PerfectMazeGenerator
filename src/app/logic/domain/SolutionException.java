package app.logic.domain;

public class SolutionException extends Throwable {
    // Custom made exception for when the solution to the maze cannot be found. This will only happen if the maze isn't perfect.
    public SolutionException(String error_in_solution) {
        super(error_in_solution);
    }
}
