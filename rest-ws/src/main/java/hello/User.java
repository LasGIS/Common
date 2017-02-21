package hello;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import lombok.Data;

/**
 * DB model User.
 * @author Vladimir Laskin
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 4009321252357225076L;

    private int userId;
    private String firstName;
    private String midName;
    private String lastName;
    private String login;
    private String passwordHash;
    private String email;
    private String note;
    private boolean enabled;
    private Collection<String> userRoles = new LinkedList<String>();

}
