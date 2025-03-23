/**
 * Vending Machine component enhanced methods.
 */
class VendingMachine {
    /**
     * From VendingMachineKernel.
     */
    public VendingMachineKernel vending = new VendingMachineKernel();

    /**
     * @return bought item from vending machine, using String.
     */
    public String buy(String item) {
        String bought = "";
        char column = 'A';
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                if (this.vending.label(column, row).equals(item)) {
                    bought = this.vending.buy(column, row);
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }
        return bought;
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
        boolean exists = false;
        char column = 'A';
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                String label = this.vending.label(column, row);

                //if label exists, add to stock in label
                if (label.equals(stock)) {
                    this.vending.addStock(column, row);
                    exists = true;

                    //exit for loop if stock has been added
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }

        /*
         * If stock is not in machine yet, find first empty rack.
         */
        column = 'A';
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                String label = this.vending.label(column, row);

                //if label is empty, add stock and add label
                if (label.equals("")) {
                    this.vending.addLabel(column, row, stock);
                    this.vending.addStock(column, row);

                    //exit for loop if stock has been added
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }
    }

    /*
     * Restocks all items in all racks.
     */
    public void restockAll() {
        char column = 'A';
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                for (int depth = 0; depth < this.vending.depth(); depth++) {
                    this.vending.addStock(column, row);
                }
            }
            column = column++;
        }
    }

    /*
     * Buys and returns all items from given coords
     */
    public String[] buyAll(char column, int row) {
        String[] rack = new String[this.vending.depth()];
        for (int depth = 0; depth < this.vending.depth(); depth++) {
            rack[depth] = this.vending.buy(column, row);
        }
        return rack;
    }

    /*
     * Buys and returns all items from given item
     */
    public String[] buyAll(String item) {
        char column = 'A';
        String[] rack = new String[this.vending.depth()];
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                if (this.vending.label(column, row).equals(item)) {
                    rack = this.buyAll(column, row);
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }
        return rack;
    }

    /*
     * Swap two given racks, specified by coordinate
     */
    public void swapRacks(char column1, int row1, char column2, int row2) {
        int depth = this.vending.depth();

        //remove racks
        String[] rack1 = this.buyAll(column1, row1);
        String[] rack2 = this.buyAll(column2, row2);

        //find labels
        String label1 = this.vending.label(column1, row1);
        String label2 = this.vending.label(column2, row2);

        //swap labels
        this.vending.addLabel(column1, row1, label2);
        this.vending.addLabel(column2, row2, label1);

        for (int i = 0; rack2[i] != ""; i++) {
            this.vending.addStock(column1, row1);
        }

        for (int i = 0; rack1[i] != ""; i++) {
            this.vending.addStock(column2, row2);
        }
    }

    /**
     * Swap two given racks, specified by names of item.
     */
    public void swapRacks(String item1, String item2) {
        char column1 = 'A';
        int row1 = 0;
        char column = 'A';
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                String label = this.vending.label(column, row);
                if (label.equals(item1)) {
                    column1 = column;
                    row1 = row;
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }
        column = 'A';
        char column2 = 'A';
        int row2 = 0;
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                String label = this.vending.label(column, row);
                if (label.equals(item1)) {
                    column2 = column;
                    row2 = row;
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }
        this.swapRacks(column1, row1, column2, row2);
    }

    public String buyAny() {
        String any = "";
        char column = 'A';
        for (int i = 0; i < this.vending.columns(); i++) {
            for (int row = 0; row < this.vending.rows(); row++) {
                String label = this.vending.label(column, row);
                if (!label.equals(any)) {
                    any = this.vending.buy(column, row);
                    row = this.vending.rows();
                    i = this.vending.columns();
                }
            }
            column = column++;
        }
        return any;
    }
}
