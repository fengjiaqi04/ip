package harden;

/**
 * Represents user-facing errors in the Harden application.
 */
public class HardenException extends Exception {

    /**
     * Constructs a HardenException.
     *
     * @param message Error message.
     */
    public HardenException(String message) {
        super(message);
    }
}
