package app.logic.Tracking;

import java.util.ArrayList;
import java.util.List;

public class OperationTracker {

    public List<Operation> operations = new ArrayList<>();

    public void add(Operation op){
        operations.add(op);
    }

    public int size(){
        return operations.size();
    }

    public Operation get(int i){
        return operations.get(i);
    }

    public void clear() {
        operations.clear();
    }
}
