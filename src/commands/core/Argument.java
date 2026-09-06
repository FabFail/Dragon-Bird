package commands.core;

/**
 * .
 * @author ulprv
 * @param name
 * @param type
 * @param required
 */
public record Argument(String name, Class<?> type, boolean required) {

    /**
     * Constructs an Argument schema definition.
     *
     * @param name     argument name
     * @param type     expected type
     * @param required whether the argument is optional
     */
    public Argument {
    }

}
