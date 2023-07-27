import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {

        TollService data = new TollService();
        Thread timeThread = new Thread(new Time(data));
        timeThread.start();
        System.out.println("Program running, please wait...");
        while(timeThread.isAlive()) {
            Thread roadThread = new Thread(new Road(data));
            roadThread.start();
            try {
                roadThread.join();
            } catch (InterruptedException ignored) {
            }
            while(data.availableTolls > 0) {
                Thread tollThread = new Thread(new Toll(data));
                tollThread.start();
                data.availableTolls -= 1;
            }
        }
        if (data.stop) {
            data.Results();
        }
    }

    static class TollService {
        Queue<String> cars = new LinkedList<>();
        int availableTolls = 8; // how many tolls are available
        int timer = 60000; // how long the emulation runs
        int carQueueingTime = 1000; // how long it takes for a new car to enter the queue
        boolean stop = false;
        int chargedCars = 0;
        int highestCarLine = 0;

        void Results(){
            System.out.println(
                    "Toll Emulation Results\n" +
                    "----------------------------------------------------------------\n" +
                    "Duration: " + timer / 1000 + "s\n"+
                    "Charged Cars: " + chargedCars + "\n"+
                    "Highest line width: " + highestCarLine + "\n"+
                    "Current line width: " + cars.size() + "\n" +
                    "----------------------------------------------------------------"
            );
        }
    }

    static class Toll implements Runnable {
        TollService data;
        public Toll(TollService data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (!data.cars.isEmpty() && !data.stop) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10000, 25000)); // This makes the toll service time random
                    //Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                }
                data.cars.remove();
                data.chargedCars += 1;
            }
        }
    }

    static class Road implements Runnable {
        TollService data;
        public Road(TollService data) {
            this.data = data;
        }

        @Override
        public void run() {
            data.cars.add("car");
            if (data.cars.size() > data.highestCarLine) {
                data.highestCarLine = data.cars.size();
            }
            try {
                Thread.sleep(data.carQueueingTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
    }

    static class Time implements Runnable {
        TollService data;
        public Time(TollService data) {
            this.data = data;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(data.timer);
            } catch (InterruptedException ignored) {
            }
            data.stop = true;
        }
    }
}
