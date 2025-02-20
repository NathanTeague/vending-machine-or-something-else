
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Vending Machine component V1.
 */
class VendingMachine {

    /*
     * Variables------------------------------------------------------------
     */

    /**
     * Vending Machine.
     */
    private String[][][] vendingMachine;

    /**
     * Keeps tabs on number of elements in vending machine.
     */
    private int stock;

    /**
     * Default rows of vending machine.
     */
    private static final int DEFAULT_ROWS = 6;

    /**
     * Default columns of vending machine.
     */
    private static final int DEFAULT_COLUMNS = 6;

    /**
     * Default depth of vending machine.
     */
    private static final int DEFAULT_DEPTH = 6;

    /**
     * Possible columns of vending machine. NOTE: CURRENTLY THE MAX NUMBER OF
     * COLUMNS IS 24.
     */
    private static final String COLUMNS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /*
     * Helper Methods----------------------------------------------------------
     */

    /**
     * @param char
     *            column representation of column.
     * @return the index of the inputted column.
     */
    private int column(char column) {
        return COLUMNS.indexOf(Character.toUpperCase(column));
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Initial representation.
     */
    private void createNewRep(int columns, int rows, int depth) {
        this.vendingMachine = new String[columns][rows][depth];
        this.stock = 0;

        //fill all of arrays with empty string
        for (int i = 0; i < this.vendingMachine.length; i++) {
            for (int j = 0; j < this.vendingMachine[i].length; j++) {
                for (int k = 0; k < this.vendingMachine[i][j].length; k++) {
                    this.vendingMachine[i][j][k] = "";
                }
            }
        }
    }

    public VendingMachine() {
        this.createNewRep(DEFAULT_COLUMNS, DEFAULT_ROWS, DEFAULT_DEPTH);
    }

    public VendingMachine(int columns, int rows, int depth) {
        assert columns > 0 : "Error: Columns is <= 0";
        assert rows > 0 : "Error: Rows is <= 0";
        assert depth > 0 : "Error: Depth is <= 0";

        this.createNewRep(columns, rows, depth);
    }

    /*
     * Kernel Methods -------------------------------------------------------
     */

    /**
     * @return array with coordinates of item, "!", "-1" if item not in machine
     *
     *         TODO: I just realized this should be a Map.Pair and would make so
     *         much of my code so much easier to write, so this is definitely
     *         one of the first things I'm going to change.
     */
    public String[] coordinates(String item) {
        String[] coordinates = { "!", "-1" };

        /*
         * Figure out if stock belongs to existing rack, report coords if so.
         */
        for (int i = 0; i < this.vendingMachine.length; i++) {
            for (Integer j = 0; j < this.vendingMachine[0].length; j++) {
                if (this.vendingMachine[i][j][0] == item) {
                    coordinates[0] = "" + COLUMNS.charAt(i);
                    coordinates[1] = j.toString();

                    i = this.vendingMachine.length;
                    j = i;
                }
            }
        }

        return coordinates;
    }

    /**
     * @return bought item from vending machine.
     */
    public String buy(char column, int row) {
        String bought = "";
        int columnIndex = this.column(column);
        int rackLength = this.vendingMachine[columnIndex][row].length;

        assert columnIndex != -1 : "Invalid Arguments!";

        /*
         * Find nonempty index in rack to remove item (remove from back to
         * front, acting as if item was taken from front and rest of stock slid
         * down rack)
         */
        for (int i = rackLength; i > 0; i--) {

            //finds nonempty index
            if (this.vendingMachine[columnIndex][row][i - 1] != "") {

                //replace bought item with empty string
                bought = this.vendingMachine[columnIndex][row][i - 1];
                this.vendingMachine[columnIndex][row][i - 1] = "";

                //reduce stock accordingly
                this.stock--;

                i = -1;
            }
        }

        return bought;
    }

    /**
     * @return number of items in vending machine
     */
    public int stock() {
        return this.stock;
    }

    /**
     * Adds 1 stock to given location.
     */
    public void addStock(char column, int row) {
        int columnIndex = this.column(column);
        int rackLength = this.vendingMachine[columnIndex][row].length;

        assert columnIndex != -1 : "Invalid Arguments!";

        /*
         * Find closest to front index in rack to add item.
         */
        for (int i = 1; i < rackLength; i++) {

            //finds empty index
            if (this.vendingMachine[columnIndex][row][i] == "") {

                //restock item with stock that belongs
                String rackItem = this.vendingMachine[columnIndex][row][0];
                this.vendingMachine[columnIndex][row][i] = rackItem;

                //increase stock accordingly
                this.stock++;

                i = rackLength;
            }
        }
    }

    /*
     * Secondary methods------------------------------------------------------
     */

    /**
     * @return bought item from vending machine.
     */
    public String buy(String item) {
        String[] coordinates = this.coordinates(item);
        return this.buy(coordinates[0].charAt(0),
                Integer.parseInt(coordinates[1]));
    }

    /**
     * Adds 1 given stock to appropiate location, adding it to new rack if no
     * matching stock is found and there is room.
     */
    public void addStock(String stock) {
        /*
         * Figure out if stock belongs to existing rack, add to that rack if so,
         * add to first empty rack if not. If no empty racks exist, report.
         */
        String[] coordinates = this.coordinates(stock);
        char letterIndex = coordinates[0].charAt(0);
        int row = Integer.parseInt(coordinates[1]);

        /*
         * If stock is not in machine yet, find first empty rack.
         */
        if (row < 0) {
            coordinates = this.coordinates("");
            letterIndex = coordinates[0].charAt(0);
            row = Integer.parseInt(coordinates[1]);

            if (row >= 0) {
                //labeling rack
                this.vendingMachine[this.column(letterIndex)][row][0] = stock;
            }
        }

        /*
         * Add to appropiate rack if room in machine
         */
        if (row >= 0) {
            this.addStock(letterIndex, row);
        }
    }

    /*
     * Restocks all items in all racks.
     */
    public void restockAll() {
        for (int i = 0; i < this.vendingMachine.length; i++) {
            for (int j = 0; j < this.vendingMachine[i].length; j++) {
                char letterIndex = COLUMNS.charAt(i);
                for (int k = 0; i < this.vendingMachine[i][j].length; k++) {
                    this.addStock(letterIndex, j);
                }
            }
        }
    }

    /*
     * Buys and returns all items from given coords
     */
    public String[] buyAll(char column, int row) {
        int rackLength = this.vendingMachine[0][0].length;
        String[] rack = new String[rackLength];
        for (int i = 0; i < rackLength; i++) {
            rack[i] = this.buy(column, row);
        }
        return rack;
    }

    /*
     * Buys and returns all items from given item
     */
    public String[] buyAll(String item) {
        String[] coordinates = this.coordinates(item);
        int row = Integer.parseInt(coordinates[1]);
        assert row >= 0 : "Stock not in system";
        char letterIndex = coordinates[0].charAt(0);
        return this.buyAll(letterIndex, row);
    }

    /*
     * Swap two given racks, specified by coordinate
     */
    public void swapRacks(char column1, int row1, char column2, int row2) {
        int rackLength = this.vendingMachine[0][0].length;

        //Buy and store everything available from first coords
        String[] rack1 = this.buyAll(column1, row1);

        //Buy and store everything available from second coords
        String[] rack2 = this.buyAll(column2, row2);

        //change identities of each rack
        String placeholder = this.vendingMachine[this.column(column1)][row1][0];

        this.vendingMachine[this
                .column(column1)][row1][0] = this.vendingMachine[this
                        .column(column2)][row2][0];

        this.vendingMachine[this.column(column2)][row2][0] = placeholder;

        //Readd racks to appropiate positions
        for (int i = 0; i < rackLength; i++) {
            this.addStock(rack1[i]);
            this.addStock(rack2[i]);
        }
    }

    /**
     * Swap two given racks, specified by names of item.
     */
    public void swapRacks(String item1, String item2) {
        String[] coordinates1 = this.coordinates(item1);
        String[] coordinates2 = this.coordinates(item2);

        int row1 = Integer.parseInt(coordinates1[1]);
        int row2 = Integer.parseInt(coordinates2[1]);

        assert row1 >= 0 : "Stock1 not in system";
        assert row2 >= 0 : "Stock2 not in system";

        char letterIndex1 = coordinates1[0].charAt(0);
        char letterIndex2 = coordinates1[0].charAt(0);

        this.swapRacks(letterIndex1, row1, letterIndex2, row2);
    }

    public String buyAny() {
        String any = "";

        for (int i = 0; i < this.vendingMachine.length; i++) {
            for (int j = 0; j < this.vendingMachine[i].length; j++) {
                if (this.vendingMachine[i][j][1] != "") {
                    any = this.buy(COLUMNS.charAt(i), j);
                }
            }
        }

        return any;
    }
}

/**
 * Proof of Concept for Vending Machine Component
 *
 * @author Nathan Teague
 *
 */
public final class ProofOfConcept {
    /**
     * Private Class.
     */
    private ProofOfConcept() {
    }

    /**
     * Demonstrates some qualities of the vending machine component
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        out.println("Vending Machine of default size: 6 columns, rows, depth");
        VendingMachine vending = new VendingMachine();

        out.println(vending.stock());//should be 0

        //adding stock
        vending.addStock("Cheetos");
        vending.addStock("Doritos");
        vending.addStock("Peanuts");

        out.println(vending.stock()); //should be 3

        //finding pos of cheetos, should be "A", "0"
        String[] cheetos = vending.coordinates("Cheetos");
        out.println(cheetos[0] + cheetos[1]);

        //should be buying at A0
        String yum = vending.buy("Cheetos");
        out.println(yum); //should be cheetos

        //A0 should still be reserved for cheetos, so should be in A3
        vending.addStock("M&Ms");

        //vending.restockAll(); //full stock of all elements should be 6*4
        out.println(vending.stock()); //should be 24

        String[] doritos = vending.buyAll("Doritos");
        int i = 0;
        while (i < doritos.length) {
            out.println(doritos[i]);
            i++;
        }

        out.println(vending.stock()); //should be 18

        vending.swapRacks("Cheetos", "Peanuts");
        cheetos = vending.coordinates("Cheetos");
        out.println(cheetos); //Should be A2

        out.close();
    }
}
