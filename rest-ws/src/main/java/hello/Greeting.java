package hello;

import lombok.Data;

/**
 * The Class Greeting.
 * @author Vladimir Laskin
 * @version 1.0
 */
@Data
public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

}