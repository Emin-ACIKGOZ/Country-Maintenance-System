import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CountryManagementSystem {
    private Linked_List cms;

    public void loadInput() {
        cms = new Linked_List();
        try {
            File file = new File("input.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().strip().split(" ");
                if (data.length != 6) {
                    System.out.println("Incorrect input");
                    continue;
                }
                if (Long.parseLong(data[1]) <= 0) {
                    System.out.println("Invalid population size");
                    continue;
                }
                cms.append(data[0], Long.parseLong(data[1]), data[2], data[3], data[4], data[5]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void processQueries() {
        try {
            File file = new File("query.txt");
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
                    if (data.length != 7 || Long.parseLong(data[2]) <= 0) {
                        System.out.println("Unable to add due to malformed Add query");
                        continue;
                    }
                    cms.prepend(data[1], Long.parseLong(data[2]), data[3], data[4], data[5], data[6]);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void process(){
        loadInput();
        processQueries();
    }
    public static void main(String[] args){
        CountryManagementSystem countryManagementSystem = new CountryManagementSystem();
        countryManagementSystem.process();
    }
}
