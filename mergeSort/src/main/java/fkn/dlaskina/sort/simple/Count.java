package fkn.dlaskina.sort.simple;

/**
 * Created by Дарья on 24.04.2016.
 */
public class Count {

    private long value = 1;

    public void increment() {
        this.value++;
    }
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
