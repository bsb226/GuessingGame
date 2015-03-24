/**
 * File: GuessingGame.java
 * Author: Brian Borowski
 * Date created: May 1999
 * Date last modified: September 6, 2010
 */
import java.util.Random;

public class GuessingGame {
    public static final int TOO_LOW = -1, EQUAL = 0, TOO_HIGH = 1;
    private final int low, high, numberToGuess, maxAttemptsForSuccess;
    private int numberOfAttempts;
    private final Random random = new Random();
    private boolean isGameCompleted;

    public GuessingGame(final int low, final int high) {
        this.low = low;
        this.high = high;
        final int range = high - low + 1;
        numberToGuess = random.nextInt(range) + low;
        maxAttemptsForSuccess = (int)Math.ceil(Math.log10(range)/Math.log10(2));
        numberOfAttempts = 0;
        isGameCompleted = false;
    }

    public int getNumberToGuess() {
        return numberToGuess;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public int getMaxAttemptsForSuccess() {
        return maxAttemptsForSuccess;
    }

    public int guessNumber(final int guess) throws GuessingGameException {
        if (isGameCompleted) {
            throw new GuessingGameException(
                          "Guessing game has been completed.");
        }
        if (guess < low || guess > high) {
            throw new GuessingGameException(
                "Your guess is outside the range (" + low + ".." + high + ").");
        }
        ++numberOfAttempts;
        if (guess < numberToGuess) return TOO_LOW;
        if (guess > numberToGuess) return TOO_HIGH;
        isGameCompleted = true;
        return EQUAL;
    }
}
