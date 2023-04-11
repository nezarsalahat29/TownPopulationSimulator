import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Town{
    private final List<Person> citizens;
    ReentrantLock lock = new ReentrantLock();
    public Town(){
        citizens=new ArrayList<>();
    }
    public int getSize(){
        return citizens.size();
    }
    public void add(Person person){
        lock.lock();
        citizens.add(person);
        lock.unlock();
    }
    public void removeAll(List<Person> persons){
        lock.lock();
        citizens.removeAll(persons);
        lock.unlock();
    }
}