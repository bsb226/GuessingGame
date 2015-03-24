/**
 * File: GuessingGameException.java
 * Author: Brian Borowski
 * Date created: May 1999
 * Date last modified: January 30, 2011
 */
public class GuessingGameException extends Exception {
    private static final long serialVersionUID = 1L;

    public GuessingGameException(final String msg) {
        super(msg);
    }

    public GuessingGameException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
