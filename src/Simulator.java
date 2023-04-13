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
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void simulate() throws IOException, InterruptedException {

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
        int randomNumberOfBirths=random.nextInt(1000000);

        // Loops through the random number of births and adds new Person objects to the town
        while (randomNumberOfBirths--!=0) {
            // +1 to avoid 0 age
            int age = random.nextInt(90) + 1;
            // If person died in the same year of birth don't add it or count it
            //if (age != 0) {
            int personDeathYear=TownPopulationSimulator.currentYear + age;
            int deathYearCounter = TownPopulationSimulator.getDeathOfYear(personDeathYear)+ 1;
            TownPopulationSimulator.deathOfYear
                        .put(personDeathYear, deathYearCounter);
            //}
        }
        // Add to citizens
        ourTown.add(randomNumberOfBirths);

        // Write logs in file
        TownPopulationSimulator.bw.write("year: "+ TownPopulationSimulator.currentYear+"\n");
        TownPopulationSimulator.bw.write("added: "+randomNumberOfBirths+"\n");

    }

    // Method that simulates deaths in the town's population growth
    private void deathManager() throws IOException {

        int deathsCounter = TownPopulationSimulator.getDeathOfYear(TownPopulationSimulator.currentYear);

        // If there were deaths, remove the corresponding people from the town
        if (deathsCounter != 0)
            ourTown.remove(deathsCounter);

        // Write logs in file
        TownPopulationSimulator.bw.write("deaths: "+deathsCounter+"\n\n");
    }
}
