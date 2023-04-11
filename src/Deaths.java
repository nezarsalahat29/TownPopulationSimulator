import java.util.List;

public class Deaths implements Runnable {

    private final Town town;

    public Deaths(Town town) {
        this.town = town;
    }

    @Override
    public void run() {
        try {
            Death();
        } catch (InterruptedException e) {
            Thread.currentThread().stop(); // restore interrupted status
        }
    }
    public synchronized void  Death() throws InterruptedException {
        while(true) {
            Thread.sleep(900);
            // Get the list of deaths that occurred in the previous year
            List<Person> deaths1 = TownPopulationSimulation.deathOfYear.get(TownPopulationSimulation.currentYear-1);
            // If there were deaths, remove the corresponding people from the town
            if (deaths1 != null)
                town.removeAll(deaths1);

            // Calculate the population of the town and put it into the populationOfYear map
            int population = town.getSize();
            TownPopulationSimulation.populationOfYear.put(TownPopulationSimulation.currentYear-1, population);
            int y=TownPopulationSimulation.currentYear-1;
            System.out.println("citizens " + y + ": "
                   + TownPopulationSimulation.populationOfYear.get(y));
        }

    }

}
