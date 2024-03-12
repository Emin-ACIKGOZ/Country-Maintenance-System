import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CountryManagementSystem {
    /**
     * This is the doubly linked list that our system uses to handle countries.
     */
    private LinkedList list;

    /**
     * Formats a line by removing extra whitespaces and making all letters lowercase
     * in order to reduce the risk of invalid data being entered or edge cases.
     *
     * @param line This is the input line to be formatted.
     * @return A string array representing the data the line contains.
     */
    private String[] formatLine(String line) {
        line = line.trim().toLowerCase().replaceAll("\\s+", " ");
        return line.split(" ");
    }

    /**
     * Returns true if the population string
     * can be properly parsed into a non-negative long
     *
     * @param populationStr This is the population string.
     * @return true if the population string is valid, false otherwise.
     */
    private boolean isValidPopulation(String populationStr) {
        try {
            long population = Long.parseLong(populationStr.replaceAll("\\.", ""));
            return population > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Formats a line from the input file
     * and adds it to the doubly linked list if it is valid.
     *
     * @param line The input line to be processed.
     */
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
        list.prepend(data[0].toUpperCase(), population, data[2], data[3], data[4], data[5].toUpperCase());
    }

    /**
     * Loads input from the input file and processes each line.
     */
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

    /**
     * Handles queries where a search will be performed.
     *
     * @param data A string array containing one line of data from the query line.
     */
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

    /**
     * Deletes a country if the provided data is correct.
     *
     * @param data A string array containing one line of data from the query line.
     */
    private void processDelete(String[] data) {
        if (data.length != 2) {
            System.out.println("Error: unable to delete due to invalid input format.");
            return;
        }

        list.delete(data[1]);
    }

    /**
     * Adds a country to the doubly linked list if it is valid.
     *
     * @param data A string array containing one line of data from the query line.
     */
    private void processAdd(String[] data) {
        if (data.length != 7 || !isValidPopulation(data[2])) {
            System.out.println("Error: unable to add element due to invalid input format or non-positive population.");
            return;
        }

        list.add(Arrays.copyOfRange(data, 1, data.length));
    }

    /**
     * This method processes the query line
     * and calls the corresponding method based on its keyword
     *
     * @param line This is the query line to be processed.
     */
    private void processQueryLine(String line) {
        String[] data = formatLine(line);

        if (data.length < 2) {
            System.out.println("Error: query is missing parameters.");
        } else {
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

    /**
     * This method reads each line of the query file and handles each line accordingly
     */
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

    /**
     * This method checks if the input and output files exist and creates them if they do not exist.
     */
    private void checkFiles() {
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

    /**
     * This method is to be called in main
     * it loads the input and output files then processes them
     */
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

