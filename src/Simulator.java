import java.io.IOException;

import java.util.Random;

public class Simulator implements Runnable {
    Town ourTown;
    public Simulator(Town town){
        ourTown=town;
    }
    @Override
    public void run() {
        while (true) {
            try {
                simulate();
            } catch (InterruptedException e) {
                Thread.currentThread().stop(); // Force interrupted thread to stop
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void simulate() throws InterruptedException, IOException {

        birthManager();
        deathManager();

        // Calculate the population of the town and put it into the populationOfYear map
        int population = ourTown.getCitizens();
        TownPopulationSimulator.populationOfYear
                .put(TownPopulationSimulator.currentYear, population);

        // Go to next year
        TownPopulationSimulator.currentYear++;

        // Every year represent as a sec
        Thread.sleep(1000);
    }

    // Method that simulates births in the town's population growth
    private void birthManager() throws IOException {
        // Generate a random number of births
        Random random=new Random();
        int randomNumberOfBirths=random.nextInt(10);
        int counterOfAlive=0;

        // Loops through the random number of births and adds new Person objects to the town
        while (randomNumberOfBirths--!=0) {
            Random age = new Random();
            Person person = new Person(TownPopulationSimulator.currentYear, age.nextInt(3));
            // If person died in the same year of birth don't add it or count it
            if (person.getAge() != 0) {
                counterOfAlive++;
                int deathYearCounter = TownPopulationSimulator.getDeathOfYear(person.getDeathYear())+ 1;
                TownPopulationSimulator.deathOfYear
                        .put(person.getDeathYear(), deathYearCounter);
            }
        }
        // Write logs in file
        TownPopulationSimulator.bw.write("year: "+ TownPopulationSimulator.currentYear+"\n");
        TownPopulationSimulator.bw.write("added: "+counterOfAlive+"\n");
        ourTown.add(counterOfAlive);//add to citizens
    }

    // Method that simulates deaths in the town's population growth
    private void deathManager() throws IOException {

        int deathsCount = TownPopulationSimulator.getDeathOfYear(TownPopulationSimulator.currentYear);

        // If there were deaths, remove the corresponding people from the town
        if (deathsCount != 0)
            ourTown.remove(deathsCount);

        // Write logs in file
        TownPopulationSimulator.bw.write("deaths: "+deathsCount+"\n\n");
    }
}
