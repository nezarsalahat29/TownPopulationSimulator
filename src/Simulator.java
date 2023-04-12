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
        TownPopulationSimulation.populationOfYear
                .put(TownPopulationSimulation.currentYear, population);
//            System.out.println("citizens " + TownPopulationSimulation.currentYear + ": "
//                   + TownPopulationSimulation.populationOfYear.get(TownPopulationSimulation.currentYear));
        TownPopulationSimulation.currentYear= TownPopulationSimulation.currentYear+1;
        Thread.sleep(1000);
    }

    // Method that simulates births in the town's population growth
    private void birthManager() throws IOException {
        Random random=new Random();
        int randomNumberOfBirths=random.nextInt(1000000);
        int counterOfAlive=0;

        // Loops through the random number of births and adds new Person objects to the town
        while (randomNumberOfBirths--!=0) {
            Random age = new Random();
            Person person = new Person(TownPopulationSimulation.currentYear, age.nextInt(90));
            if (person.getAge() != 0) {
                counterOfAlive++;
                int deathYearCounter = TownPopulationSimulation.getDeathOfYear(person.getDeathYear())+ 1;
                TownPopulationSimulation.deathOfYear
                        .put(person.getDeathYear(), deathYearCounter);
            }
            //System.out.println(person);
        }
        //write logs in file
        TownPopulationSimulation.bw.write("year: "+TownPopulationSimulation.currentYear+"\n");
        TownPopulationSimulation.bw.write("added: "+counterOfAlive+"\n");

        //System.out.println("added: "+counterOfAlive);
        ourTown.add(counterOfAlive);//add to citizens
    }

    // Method that simulates deaths in the town's population growth
    private void deathManager() throws IOException {
        int deathsCount = TownPopulationSimulation.getDeathOfYear(TownPopulationSimulation.currentYear);
        // If there were deaths, remove the corresponding people from the town
        if (deathsCount != 0)
            ourTown.remove(deathsCount);
        //write logs in file
        TownPopulationSimulation.bw.write("deaths: "+deathsCount+"\n\n");

        //System.out.println("deaths: "+deathsCount);

    }

}
