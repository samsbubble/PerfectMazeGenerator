package app.logic;

public class SolutionException extends Throwable {
    public SolutionException(String error_in_solution) {
        super(error_in_solution);
    }
}
