/**
 * Vending Machine component Kernel methods.
 */
class VendingMachineKernel {

    /*
     * Variables------------------------------------------------------------
     */

    /**
     * Vending Machine.
     */
    private String[][][] vendingMachine;

    /**
     * Number of rows in vending machine.
     */
    private int rows;

    /**
     * Number of columns in vending machine.
     */
    private int columns;

    /**
     * Depth of vending machine.
     */
    private int depth;

    /**
     * Keeps tabs on number of elements in vending machine.
     */
    private int stock;

    /**
     * Possible columns of vending machine. NOTE: CURRENTLY THE MAX NUMBER OF
     * COLUMNS IS 24.
     */
    private static final String COLUMNS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /*
     * Kernel Methods -------------------------------------------------------
     */

    /**
     * @return number of items in vending machine
     */
    public int stock() {
        return this.stock;
    }

    /**
     * @return depth of vending machine
     */
    public int depth() {
        return this.depth;
    }

    /**
     * @return number of columns in vending machine
     */
    public int columns() {
        return this.columns;
    }

    /**
     * @return number of rows in vending machine
     */
    public int rows() {
        return this.rows;
    }

    /**
     * @return bought item from vending machine.
     * @param column
     *            column of requested item
     * @param row
     *            row of requested item
     */
    public String buy(char column, int row) {
        String bought = "";
        int columnIndex = COLUMNS.indexOf(Character.toUpperCase(column));

        assert columnIndex > -1 : "Invalid Arguments!";

        /*
         * Find nonempty index in rack to remove item (remove from back to
         * front, acting as if item was taken from front and rest of stock slid
         * down rack). End at i - 1 = 1, since index 0 is label.
         */
        for (int i = this.depth; i > 1; i--) {

            //finds nonempty index
            if (this.vendingMachine[columnIndex][row][i - 1] != "") {

                //replace bought item with empty string
                bought = this.vendingMachine[columnIndex][row][i - 1];
                this.vendingMachine[columnIndex][row][i - 1] = "";

                //reduce stock accordingly
                this.stock--;

                i = 1;
            }
        }

        return bought;
    }

    /**
     * Adds 1 stock to given location if not full.
     *
     * @param column
     *            column of requested add
     * @param row
     *            row of requested add
     */
    public void addStock(char column, int row) {
        int columnIndex = COLUMNS.indexOf(Character.toUpperCase(column));

        assert columnIndex != -1 : "Invalid Arguments!";

        /*
         * Find closest to front index in rack to add item.
         */
        for (int i = 1; i < this.depth; i++) {

            //finds empty index
            if (this.vendingMachine[columnIndex][row][i] == "") {

                //restock item with stock that belongs
                String rackItem = this.vendingMachine[columnIndex][row][0];
                this.vendingMachine[columnIndex][row][i] = rackItem;

                //increase stock accordingly
                this.stock++;

                i = this.depth;
            }
        }
    }

    /**
     * @return label at coordinates.
     * @param column
     *            column of requested label
     * @param row
     *            row of requested label
     */
    public String label(char column, int row) {
        int columnIndex = COLUMNS.indexOf(Character.toUpperCase(column));
        return this.vendingMachine[columnIndex][row][0];
    }

    /**
     * Adds label at coordinates.
     *
     * @param column
     *            column of requested add
     * @param row
     *            row of requested add
     * @param label
     *            label to be added
     */
    public void addLabel(char column, int row, String label) {
        int columnIndex = COLUMNS.indexOf(Character.toUpperCase(column));
        this.vendingMachine[columnIndex][row][0] = label;
    }
}
