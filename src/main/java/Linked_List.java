import java.util.Scanner;

public class Linked_List {

    public static class Node {
        protected Node next;
        protected Node prev;
        protected String countryName;
        protected long population;
        protected String capitalCity;
        protected String largestCity;
        protected String officialLanguage;
        protected String currency;

        public Node(String countryName, long population, String capitalCity,
                    String largestCity, String officialLanguage, String currency) {
            this.countryName = countryName;
            this.population = population;
            this.capitalCity = capitalCity;
            this.largestCity = largestCity;
            this.officialLanguage = officialLanguage;
            this.currency = currency;
        }

        public Node(Node node) {
            this.countryName = node.countryName;
            this.population = node.population;
            this.capitalCity = node.capitalCity;
            this.largestCity = node.largestCity;
            this.officialLanguage = node.officialLanguage;
            this.currency = node.currency;
        }

        @Override
        public String toString() {
            return countryName + " " + population + " " + capitalCity + " " + largestCity + " " +
                    officialLanguage + " " + currency;
        }

        // Method to get the next node
        public Node getNext() {
            return next;
        }

        // Method to get the previous node
        public Node getPrev() {
            return prev;
        }
    }


    protected Node head;
    protected Node tail;
    int size = 0;

    public Linked_List() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void prepend(String countryName, long population, String capitalCity, String largestCity, String officialLanguage, String currency) {
        Node newHead = new Node(countryName, population, capitalCity, largestCity, officialLanguage, currency);
        if (head == null) {
            this.head = newHead;
            this.tail = newHead;
            this.size = 1;
        } else {
            newHead.next = head;
            head.prev = newHead;
            head = newHead;
            this.size++;
        }
    }

    public void append(String countryName, long population, String capitalCity, String largestCity, String officialLanguage, String currency) {
        Node newTail = new Node(countryName, population, capitalCity, largestCity, officialLanguage, currency);
        if (tail == null) {
            this.tail = newTail;
            this.head = newTail;
            this.size = 1;
        } else {
            tail.next = newTail;
            newTail.prev = tail;
            tail = newTail;
            this.size++;
        }
    }

    private boolean isValid(String queryType) {
        if (queryType == null) {
            return false;
        }


        return queryType.equals("country") ||
                queryType.equals("population") ||
                queryType.equals("capital_city") ||
                queryType.equals("largest_city") ||
                queryType.equals("official_language") ||
                queryType.equals("currency");
    }

    public void query(String queryType, char sign, String value) {
        //check queryType
        //queryType = queryType.toLowerCase();
        if (!isValid(queryType) || (sign != '<' && sign != '=' && sign != '>')) {
            System.out.println("Invalid query entered.");
            return;
        }
        if (queryType.equals("population")) {
            Node curr = head;
            boolean flag = false;
            while (curr != null) {
                if (evaluate(curr, Long.parseLong(value), sign)) {
                    System.out.println(curr.toString());
                    flag = true;
                }
                curr = curr.getNext();
            }
            if (!flag) {
                System.out.println("There was no element with a population " + sign + " " + value);
            }
        } else {
            Node curr = head;
            boolean flag = false;
            while (curr != null) {
                if (evaluate(curr, queryType, value, sign)) {
                    System.out.println(curr.toString());
                    flag = true;
                }
                curr = curr.getNext();
            }
            if (!flag) {
                System.out.println("There was no element that satisfied the condition: " + queryType + " " + sign + " " + value);
            }
        }
    }

    private boolean evaluate(Node node, long population, char sign) {
        if (sign != '<' && sign != '=' && sign != '>') {
            return false;
        }
        switch (sign) {
            case '<' -> {
                return node.population < population;
            }
            case '>' -> {
                return node.population > population;
            }
            case '=' -> {
                return node.population == population;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean evaluate(Node node, String queryType, String value, char sign) {
        if (sign != '<' && sign != '=' && sign != '>') {
            return false;
        }

        return switch (queryType) {
            case "capital_city" -> evaluate(node.capitalCity, value, sign);
            case "currency" -> evaluate(node.currency, value, sign);
            case "country" -> evaluate(node.countryName, value, sign);
            case "official_Language" -> evaluate(node.officialLanguage, value, sign);
            case "largest_city" -> evaluate(node.largestCity, value, sign);
            default -> false;
        };
    }

    private boolean evaluate(String nodeVal, String val, char sign) {
        if (sign != '<' && sign != '=' && sign != '>') {
            return false;
        }

        int comparisonResult = nodeVal.compareToIgnoreCase(val);

        switch (sign) {
            case '<' -> {
                return comparisonResult < 0;
            }
            case '>' -> {
                return comparisonResult > 0;
            }
            case '=' -> {
                return comparisonResult == 0;
            }
            default -> {
                return false;
            }
        }
    }



    public static void main(String[] args) {
        Linked_List countryMaintenanceSystem = new Linked_List();

        // Manually add some initial countries
        countryMaintenanceSystem.append("TURKIYE", 85000000, "Ankara", "Istanbul", "Turkish", "TRY");
        countryMaintenanceSystem.append("GERMANY", 84000000, "Berlin", "Berlin", "German", "EUR");
        countryMaintenanceSystem.append("USA", 335000000, "Washington", "NewYork", "English", "USD");

        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Enter your queries (type 'exit' to quit):");
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            String[] queryParts = input.split(" ");
            String inputType = queryParts[0];
            if (inputType.equalsIgnoreCase("Add")) {
                // Add a country
                String countryName = queryParts[1];
                long population = Long.parseLong(queryParts[2].replace(".", ""));
                String capitalCity = queryParts[3];
                String largestCity = queryParts[4];
                String officialLanguage = queryParts[5];
                String currency = queryParts[6];
                countryMaintenanceSystem.prepend(countryName, population, capitalCity, largestCity, officialLanguage, currency);
                System.out.println("A new country has been added.");
            } else if (inputType.equalsIgnoreCase("Query")) {
                // Perform a query
                //Query country > HUNGARY
                String queryType = queryParts[1];
                char sign = queryParts[2].charAt(0);
                String value = queryParts[3];
                countryMaintenanceSystem.query(queryType, sign, value);
            }
        }
        scanner.close();
    }

}
