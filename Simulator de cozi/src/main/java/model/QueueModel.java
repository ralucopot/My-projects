package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class QueueModel {
    private String fileName;

    public QueueModel(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void golireFisier(String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Could not empty file!");
        }
        writer.print("");
        writer.close();
    }

    public void afisare(Scheduler scheduler, int time, ArrayList<Client> clients) {

        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(waitingClientsString(clients) + "\nTime " + time + "\n");
            for (Server s : scheduler.getServers()) {
                bufferedWriter.write(
                        "Queue " + s.getIndex() + ": " + s.toString() + "\n");
            }
            bufferedWriter.write("\n----------------------------------------------\n");
        } catch (IOException e) {
            System.out.println("Could not print to file!");
        }
    }


    public void afisareAnaliza(int peakHour, float avgWaitingTime, float avgServingTime) {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write("Peak hour " + peakHour + "\n" + "Average waiting time: " + String.format("%.2f", avgWaitingTime) + "\n" + "Average serving time: " + String.format("%.2f", avgServingTime) + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Could not print to file!");
        }
    }


    public String waitingClientsString(ArrayList<Client> clients) {
        String result = "";
        if (clients.isEmpty()) {
            result = "No waiting clients left.\n";
        } else {
            result = "Waiting clients: ";
            for (Client c : clients) {
                result = result + c.toString() + "; ";
            }
        }

        return result;
    }

    public int peakHour(Scheduler scheduler) {
        int maxClients = 0;
        for (Server s : scheduler.getServers()) {
            maxClients += s.getSize();
        }
        return maxClients;
    }

}
