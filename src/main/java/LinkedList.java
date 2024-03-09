public class LinkedList {

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
        protected Node getNext() {
            return next;
        }

        // Method to get the previous node
        protected Node getPrev() {
            return prev;
        }

        protected void setNext(Node next) {
            this.next = next;
        }

        protected void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    protected Node head;
    protected Node tail;
    int size = 0;

    public LinkedList() {
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

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    public void prepend(String countryName, long population, String capitalCity, String largestCity, String officialLanguage, String currency) {
        if (population < 0) {
            System.out.println("Error: invalid population size.");
            return;
        }
        Node newHead = new Node(countryName.toUpperCase(), population, capitalize(capitalCity), capitalize(largestCity), capitalize(officialLanguage), currency.toUpperCase());
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
        if (population < 0) {
            System.out.println("Error: invalid population size.");
            return;
        }
        Node newTail = new Node(countryName.toUpperCase(), population, capitalize(capitalCity), capitalize(largestCity), capitalize(officialLanguage), currency.toUpperCase());
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

        queryType = queryType.toLowerCase();
        if (!isValid(queryType) || (sign != '<' && sign != '=' && sign != '>')) {
            System.out.println("Error: invalid query.");
            return;
        }
        if (queryType.equals("population")) {
            Node curr = head;
            boolean flag = false;
            while (curr != null) {
                if (evaluate(curr, Long.parseLong(value.replaceAll("\\.", "")), sign)) {
                    System.out.println(curr.toString());
                    flag = true;
                }
                curr = curr.getNext();
            }
            if (!flag) {
                System.out.println("There was no element that satisfied the condition: population " + sign + " " + value);
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
            System.out.println("Error: invalid sign entered.");
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
            case "official_language" -> evaluate(node.officialLanguage, value, sign);
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
                return comparisonResult < 0 && !nodeVal.equalsIgnoreCase(val);
            }
            case '>' -> {
                return comparisonResult > 0 && !nodeVal.equalsIgnoreCase(val);
            }
            case '=' -> {
                return nodeVal.equalsIgnoreCase(val);
            }
            default -> {
                return false;
            }
        }
    }

    private boolean countryExists(String countryName) {
        Node curr = head;
        while (curr != null) {
            if (curr.countryName.equalsIgnoreCase(countryName)) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    public void add(String[] data) {
        if (data == null) {
            System.out.println("Error: invalid formatted string: " + data.toString());
            return;
        } else if (data.length != 6) {
            System.out.println("Error: incorrect number of elements in the formatted string: " + data.toString());
            return;
        }

        String countryName = data[0].toUpperCase();
        if (countryExists(countryName)) {
            System.out.println("Error: Country '" + countryName + "' already exists.");
            return;
        }

        try {
            long population = Long.parseLong(data[1].replaceAll("\\.", ""));
            append(countryName, population, data[2], data[3], data[4], data[5]);
            System.out.println("A new country has been added.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Unable to parse population value as long: " + data[1]);
            return;
        }

    }

    public void delete(String countryName) {
        if (head == null) {
            System.out.println("Error: list is empty.");
            return;
        }

        Node curr = head;
        while (curr != null) {
            if (curr.countryName.equalsIgnoreCase(countryName)) {
                if (curr == head) {
                    head = curr.getNext();
                    if (head == null) {
                        tail = null; // List is now empty
                    } else {
                        head.setPrev(null);
                    }
                } else if (curr == tail) {
                    if (tail.getPrev() == null) {
                        head = null; // Only one node in the list
                    } else {
                        tail = curr.getPrev();
                        tail.setNext(null);
                    }
                } else {
                    curr.getPrev().setNext(curr.getNext());
                    curr.getNext().setPrev(curr.getPrev());
                }
                size--;
                System.out.println("Deleted: " + curr.countryName);
                return;
            }
            curr = curr.getNext();
        }
        System.out.println("Error: country '" + countryName + "' not found.");
    }

    public void printAll() {
        if (isEmpty()) {
            System.out.println("Error: failed to print as list is empty.");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.toString());
            curr = curr.getNext();
        }
    }

}