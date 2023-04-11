public class TimeLine implements Runnable {


    @Override
    public void run() {
        while (true) {
            try {
                nextYear();
            } catch (InterruptedException e) {
                Thread.currentThread().stop(); // restore interrupted status
            }
        }
    }
    private static synchronized void nextYear() throws InterruptedException{
        Thread.sleep(1000);
        TownPopulationSimulation.currentYear= TownPopulationSimulation.currentYear+1;
    }
}