
import java.util.*;

import java.util.concurrent.*;


public class TownPopulationSimulation {
    public static volatile int currentYear=2000;

    // Declare a static map to store the population count for each year
    public static  Map<Integer,Integer> populationOfYear=new HashMap<>();

    // Declare a static map to store a list of people who died in each year
    public static  Map<Integer, List<Person>> deathOfYear=new HashMap<>();

    // Define a method to get the population count for a given year
    public static int getPopulation(int year){
        Integer pop=populationOfYear.get(year);
        if (pop==null) return 0;
        return pop;
    }

    public static void main(String[] args)  {


        Town ourTown=new Town();
        // Create a new ExecutorService with a fixed thread pool of 10 threads
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Thread time=new Thread(new TimeLine());
        Thread producerThread = new Thread(new Births(ourTown));
        Thread consumerThread = new Thread(new Deaths(ourTown));

        executor.execute(time);
        executor.execute(producerThread);
        executor.execute(consumerThread);

        Scanner scanner = new Scanner(System.in);

        // Enter a loop that prompts the user to enter a year and prints the population count for that year
        while(true) {
            System.out.println("Enter what year u want OR enter 0 to exit: ");
            int year = scanner.nextInt();
            if (year==0) break;
            System.out.println("Population at " + year + " is: " + getPopulation(year));

        }
        // Shut down the ExecutorService
        executor.shutdownNow();

    }
}
