
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

import java.util.concurrent.*;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
public class TownPopulationSimulation {
    public static int currentYear=2000;
    public static BufferedWriter bw ;

    // Declare a static map to store the population count for each year
    public static  ConcurrentHashMap<Integer,Integer> populationOfYear=new ConcurrentHashMap<>();

    // Declare a static map to store a number of people who died in each year
    public static ConcurrentHashMap<Integer, Integer> deathOfYear = new ConcurrentHashMap<>();

    // Define a method to get the population count for a given year
    public static int getPopulation(int year){
        if (populationOfYear.get(year)==null)
            return 0;
        return populationOfYear.get(year);
    }
    // Define a method to get the death count for a given year

    public static int getDeathOfYear(int year){
        if (deathOfYear.get(year)==null)
            return 0;
        return deathOfYear.get(year);
    }

    public static void main(String[] args) throws IOException {
        FileWriter logFile= new FileWriter("log.txt");
        bw = new BufferedWriter(logFile);


        //initialize the population of town
        Town ourTown=new Town(0);

        // Create a thread of simulation and start it
        Thread simulator=new Thread(new Simulator(ourTown));
        simulator.start();
        Scanner scanner = new Scanner(System.in);

        // Enter a loop that prompts the user to enter a year and prints the population count for that year
        while(true) {
            System.out.println("Enter what year u want OR enter 0 to exit: ");
            int year = scanner.nextInt();
            if (year==0) break;
            System.out.println("Population at " + year + " is: " + getPopulation(year));
        }
        // Shut down the ExecutorService
        simulator.stop();
        bw.close();
    }
}
