import components.queue.Queue;

class VendingMachine1L {
    /**
     * Vending Machine
     */
    private Queue<String>[][] vendingMachine;

    /*
     * Keeps tabs on number of elements in vending machine
     */
    private int stock;

    /**
     * Default rows of vending machine
     */
    private static final int DEFAULT_ROWS = 6;

    /**
     * Default columns of vending machine.
     */
    private static final int DEFAULT_COLUMNS = 6;

    /**
     * Depth of vending machine, set to default value
     */
    private static int depth;

    /**
     * Initial representation
     */
    private void createNewRep(int columns, int rows, int depth) {
        this.vendingMachine = new Queue[columns][rows];
        this.depth = depth;
        this.stock = 0;
    }

    public VendingMachine1L() {
        this.createNewRep(DEFAULT_COLUMNS, DEFAULT_ROWS, depth);
    }

    public VendingMachine1L(int columns, int rows, int depth) {
        assert columns != 0 : "Error: Columns is 0";
        assert rows != 0 : "Error: Rows is 0";
        assert depth != 0 : "Error: Depth is 0";

        this.createNewRep(columns, rows, depth);
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

    }
}