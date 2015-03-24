/**
 * File: Application.java
 * Author: Brian Borowski
 * Date created: May 1999
 * Date last modified: January 30, 2011
 */

/**
 * Main class for starting the application.
 */
public class Application {

    /**
     * Default constructor.
     * Creates an instance of the gui.
     */
    public Application() {
        new GUI(null);
    }

    /**
     * Main method.
     * Creates an instance of the application.
     */
    public static void main(final String[] args) {
        new Application();
    }
}
