package app.logic.tracking;

import java.util.ArrayList;
import java.util.List;

// Class holding a list of operations, where it is possible to add to the list, get the size or a single element of the list.
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
}
