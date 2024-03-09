import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CountryManagementSystem {
    private LinkedList list;

    private String[] formatLine(String line) {
        line = line.trim().toLowerCase().replaceAll("\\s+", " ");
        return line.split(" ");
    }

    private boolean isValidPopulation(String populationStr) {
        try {
            long population = Long.parseLong(populationStr.replaceAll("\\.", ""));
            return population > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void processInputLine(String line) {
        String[] data = formatLine(line);

        if (data.length != 6) {
            System.out.println("Error: incorrect input.\n");
            return;
        }

        if (!isValidPopulation(data[1])) {
            System.out.println("Error: invalid population size.\n");
            return;
        }

        long population = Long.parseLong(data[1].replaceAll("\\.", ""));
        list.prepend(data[0].toUpperCase(), population, data[2], data[3], data[4], data[5].toUpperCase());    }


    public void loadInput() {
        list = new LinkedList();
        File inputFile = new File("input.txt");

        try (Scanner reader = new Scanner(inputFile)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                processInputLine(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }

    public void processQueries() {
        File file = new File("query.txt");

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                processQueryLine(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }

    private void processQueryLine(String line) {
        String[] data = formatLine(line);

        if (data.length < 2) {
            System.out.println("Error: query is missing parameters.");
        }else{
            String queryType = data[0].toLowerCase();
            switch (queryType) {
                case "query" -> processQuery(data);
                case "delete" -> processDelete(data);
                case "add" -> processAdd(data);
                default -> System.out.println("Invalid query type: " + queryType);
            }
        }

        System.out.println("");
    }

    private void processQuery(String[] data) {
        if (data.length != 4 && data.length != 2) {
            System.out.println("Error: query has invalid parameters.");
            return;
        }

        String queryType = data[1];
        if (queryType.equalsIgnoreCase("print_all")) {
            list.printAll();
        } else if (data.length == 4) {
            String sign = data[2];
            if (sign.length() != 1) {
                System.out.println("Error: invalid sign.");
                return;
            }

            list.query(queryType, sign.charAt(0), data[3]);
        } else {
            System.out.println("Error: query has invalid parameters.");
        }
    }

    private void processDelete(String[] data) {
        if (data.length != 2) {
            System.out.println("Error: unable to delete due to invalid input format.");
            return;
        }

        list.delete(data[1]);
    }

    private void processAdd(String[] data) {
        if (data.length != 7 || !isValidPopulation(data[2])) {
            System.out.println("Error: unable to add element due to invalid input format or non-positive population.");
            return;
        }

        list.add(Arrays.copyOfRange(data, 1, data.length));
    }


    public void checkFiles() {
        try {
            File inputFile = new File("input.txt");
            File queryFile = new File("query.txt");

            if (!inputFile.exists()) {
                inputFile.createNewFile();
                System.out.println("Input file created.");
            }

            if (!queryFile.exists()) {
                queryFile.createNewFile();
                System.out.println("Query file created.");
            }

        } catch (IOException e) {
            System.out.println("Error: an IO exception has occurred.");
            e.printStackTrace();
        }
    }

    public void process() {
        checkFiles();
        loadInput();
        processQueries();
    }

    public static void main(String[] args) {
        CountryManagementSystem countryManagementSystem = new CountryManagementSystem();
        countryManagementSystem.process();

    }
}

