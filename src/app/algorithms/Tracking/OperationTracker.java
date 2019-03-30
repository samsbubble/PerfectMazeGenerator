package app.algorithms.Tracking;

import java.util.ArrayList;
import java.util.List;

public class OperationTracker {

    public List<Operation> operations = new ArrayList<>();

    public void add(Operation op){
        operations.add(op);
    }

}
