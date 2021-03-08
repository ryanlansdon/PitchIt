package dev.lansdon.exceptions;

@SuppressWarnings("serial")
public class NonUniqueUsernameException extends Exception {
    public NonUniqueUsernameException() {
        super("The requested username is taken");
    }
}
