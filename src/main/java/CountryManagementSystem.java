import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CountryManagementSystem {
    private Linked_List cms;

    public void loadInput() {
        cms = new Linked_List();
        try {
            File file = new File("input.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Input file created.");
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().strip().split(" ");
                if (data.length != 6) {
                    System.out.println("Incorrect input");
                    continue;
                }
                if (Long.parseLong(data[1].replaceAll("\\.", "")) <= 0) {
                    System.out.println("Invalid population size");
                    continue;
                }
                cms.append(data[0], Long.parseLong(data[1].replaceAll("\\.", "")), data[2], data[3], data[4], data[5]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while creating the input file.");
            e.printStackTrace();
        }
    }

    public void processQueries() {
        try {
            File file = new File("query.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Query file created.");
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().strip().split(" ");
                if (data.length < 2) {
                    System.out.println("Invalid query: query has malformed input");
                    continue;
                }
                if (data[0].equalsIgnoreCase("Query")) {
                    if (data[1].equalsIgnoreCase("print_all")) {
                        cms.printAll();
                    } else {
                        if (data.length != 4) {
                            System.out.println("Invalid query type: query has incorrect input");
                            continue;
                        }
                        cms.query(data[1], data[2].charAt(0), data[3]);
                    }

                } else if (data[0].equalsIgnoreCase("Delete")) {
                    if (data.length != 2) {
                        System.out.println("Unable to delete due to malformed delete query");
                        continue;
                    }
                    cms.delete(data[1]);
                } else if (data[0].equalsIgnoreCase("Add")) {
                    if (data.length != 7 || Long.parseLong(data[2].replaceAll("\\.", "")) <= 0) {
                        System.out.println("Unable to add due to malformed Add query");
                        continue;
                    }
                    cms.prepend(data[1], Long.parseLong(data[2].replaceAll("\\.", "")), data[3], data[4], data[5], data[6]);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while creating the query file.");
            e.printStackTrace();
        }
    }

    public void createInputFilesIfNeeded() {
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

            if (inputFile.exists() && queryFile.exists()) {
                System.out.println("Both input and query files already exist.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating input and query files.");
            e.printStackTrace();
        }
    }

    public void process() {
        createInputFilesIfNeeded();
        loadInput();
        processQueries();
    }

    public static void main(String[] args) {
        CountryManagementSystem countryManagementSystem = new CountryManagementSystem();
        countryManagementSystem.process();
    }
}

