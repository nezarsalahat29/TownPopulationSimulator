import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Births implements Runnable {
    private final Town town;

    public Births(Town town) {
        this.town = town;
    }

    @Override
    public void run() {
        try {
            birth();
        } catch (InterruptedException e) {
            Thread.currentThread().stop(); // restore interrupted status
        }
    }

    // Define a synchronized method to simulate births
    public synchronized void birth() throws InterruptedException {
        Random random=new Random();
        while (true) {
            Thread.sleep(900);
            int randomNumberOfBirths=random.nextInt(500);
            personGenerator(randomNumberOfBirths);

        }
    }
    private synchronized void personGenerator(int random){
        while (random--!=0){
            Random random1=new Random();
            Person person = new Person(TownPopulationSimulation.currentYear, random1.nextInt(50));
            town.add(person);
            // Add the new Person to the deathOfYear map with their death year as the key
            List<Person> list = TownPopulationSimulation.deathOfYear.get(person.getDeathYear());
            if (list == null) list = new ArrayList<>();
            list.add(person);
            TownPopulationSimulation.deathOfYear.put(person.getDeathYear(), list);
            System.out.println(person );
        }

    }
}
