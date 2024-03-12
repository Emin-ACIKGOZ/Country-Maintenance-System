public class LinkedList {

    /**
     * Inner class that is a node within the list that represents a country
     */
    public static class Node {
        protected Node next;
        protected Node prev;
        protected String countryName;
        protected long population;
        protected String capitalCity;
        protected String largestCity;
        protected String officialLanguage;
        protected String currency;

        /**
         * Constructs a new node with the following data fields.
         *
         * @param countryName      The name of the country.
         * @param population       The population of the country.
         * @param capitalCity      The capital city of the country.
         * @param largestCity      The largest city of the country.
         * @param officialLanguage The official language of the country.
         * @param currency         The currency of the country.
         */
        public Node(String countryName, long population, String capitalCity,
                    String largestCity, String officialLanguage, String currency) {
            this.countryName = countryName;
            this.population = population;
            this.capitalCity = capitalCity;
            this.largestCity = largestCity;
            this.officialLanguage = officialLanguage;
            this.currency = currency;
        }

        /**
         * An unused copy constructor that copies the data fields of an existing node
         * without copying its links
         *
         * @param node The Node to be copied.
         */
        public Node(Node node) {
            this.countryName = node.countryName;
            this.population = node.population;
            this.capitalCity = node.capitalCity;
            this.largestCity = node.largestCity;
            this.officialLanguage = node.officialLanguage;
            this.currency = node.currency;
        }

        /**
         * Returns the data of the node in string form.
         *
         * @return A string that represents the node.
         */
        @Override
        public String toString() {
            return countryName + " " + population + " " + capitalCity + " " + largestCity + " " +
                    officialLanguage + " " + currency;
        }

        /**
         * Returns the next node after the specified node.
         *
         * @return The node after this node.
         */
        protected Node getNext() {
            return next;
        }

        /**
         * Returns the previous node that precedes the specified node.
         *
         * @return The node before this node.
         */
        protected Node getPrev() {
            return prev;
        }

        /**
         * Sets the next node of the specified node.
         *
         * @param next The node to be set as next.
         */
        protected void setNext(Node next) {
            this.next = next;
        }

        /**
         * Sets the previous node of the specified node.
         *
         * @param prev The node to be set as prev.
         */
        protected void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    protected Node head;
    protected Node tail;
    int size = 0;

    /**
     * Constructs an empty LinkedList.
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Returns the size of the linked list.
     *
     * @return The size of the linked list.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return True if the linked list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param str The string to be capitalized.
     * @return The capitalized string.
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * Prepends a node with the provided information to the list.
     *
     * @param countryName      The name of the country.
     * @param population       The population of the country.
     * @param capitalCity      The capital city of the country.
     * @param largestCity      The largest city of the country.
     * @param officialLanguage The official language of the country.
     * @param currency         The currency of the country.
     */
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

    /**
     * Appends a node with the provided information to the list.
     *
     * @param countryName      The name of the country.
     * @param population       The population of the country.
     * @param capitalCity      The capital city of the country.
     * @param largestCity      The largest city of the country.
     * @param officialLanguage The official language of the country.
     * @param currency         The currency of the country.
     */
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

    /**
     * Checks if the queryType string is a valid query type.
     *
     * @param queryType The query type to be validated.
     * @return True if the query type is valid, false otherwise.
     */
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

    /**
     * Performs a query on the linked list based on the provided queryType.
     *
     * @param queryType The type of query (e.g., population, country, capital_city).
     * @param sign      The comparison sign (>, <, =).
     * @param value     The value to compare against.
     */
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


    /**
     * Evaluates if the population of a node satisfies the given condition.
     *
     * @param node       The node containing population information.
     * @param population The population value to compare against.
     * @param sign       The comparison sign (>, <, =).
     * @return True if the population satisfies the condition, false otherwise.
     */
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

    /**
     * Evaluates if the specified attribute of a node satisfies the given condition.
     *
     * @param node       The node containing attribute information.
     * @param queryType  The type of attribute to be evaluated (e.g., capital_city, currency).
     * @param value      The value to compare against.
     * @param sign       The comparison sign (>, <, =).
     * @return True if the attribute satisfies the given condition, false otherwise.
     */
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

    /**
     * Evaluates if the specified string value satisfies the given condition.
     *
     * @param nodeVal The string value to be evaluated.
     * @param val     The value to compare against.
     * @param sign    The comparison sign (>, <, =).
     * @return True if the string value satisfies the condition, false otherwise.
     */
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


    /**
     * Checks if a country with the provided name already exists in the linked list.
     *
     * @param countryName The name of the country to check for existence.
     * @return True if the country exists, false otherwise.
     */
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

    /**
     * Adds a new node to the linked list if it is valid
     * and does not already exist within the list.
     *
     * @param data A string array that contains the data of the new node.
     */
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


    /**
     * Deletes a node with the specified country name
     * from the linked list if it exists within the list.
     *
     * @param countryName The name of the country to delete.
     */
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

    /**
     * Prints all nodes in the linked list.
     */
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