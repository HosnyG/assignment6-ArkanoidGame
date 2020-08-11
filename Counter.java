/**
 * A Counter class.
 * simple class that is used for counting things
 */
public class Counter {
    private int count = 0;

    /**
     * add number to current count.
     *
     * @param number : to be added to current count.
     */
    public void increase(int number) {
        this.count = this.count + number;
    }

    /**
     * subtract number from current count.
     *
     * @param number : to be subtract from the current count.
     */
    public void decrease(int number) {
        this.count = this.count - number;
    }

    /**
     * @return current count.
     */
    public int getValue() {
        return this.count;
    }
}