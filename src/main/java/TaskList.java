import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task>{

    private final List<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    public int getSize(){
        return tasks.size();
    }

    public Task get(int idx){
        return tasks.get(idx);
    }

    public Task delete(int idx){
        return tasks.remove(idx - 1);
    }

    public void add(Task t){
        tasks.add(t);
    }

    @Override
    public Iterator<Task> iterator(){
        return this.tasks.iterator();
    }

}