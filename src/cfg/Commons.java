package cfg;

/**
 * The Commons interface contains constants and an enum that are used in the Tangram game.
 * It includes an array of difficulty scalers, an array of shapes to remove, a time to level up, a time to level down, a required streak to level up, a required streak to level down, and an enum of difficulty levels.
 */
public interface Commons {
    /**
     * An array of difficulty scalers. The index of the array corresponds to the difficulty level, and the value at that index is the difficulty scaler for that level.
     */
    int[] diffScaler = {4, 3, 2, 1, 0};

    /**
     * An array of shapes to remove. The index of the array corresponds to the difficulty level, and the value at that index is the number of shapes to remove for that level.
     */
    int[] removeShapes = {3, 4, 5, 6, 7};

    /**
     * The time in seconds to level up. If the player completes the puzzle within this time, they level up.
     */
    int levelUpTime = 60;

    /**
     * The time in seconds to level down. If the player does not complete the puzzle within this time, they level down.
     */
    int levelDownTime = 240;

    /**
     * The required streak to level up. If the player completes the puzzle within the level up time this many times in a row, they level up.
     */
    int requiredLevelUpStreak = 3;

    /**
     * The required streak to level down. If the player does not complete the puzzle within the level down time this many times in a row, they level down.
     */
    int requiredLevelDownStreak = 5;

    /**
     * An enum of difficulty levels. The levels are, in order from easiest to hardest: UNERFAHREN, ANFAENGER, FORTGESCHRITTEN, EXPERTE, PROFI.
     */
    enum Difficulty {
        UNERFAHREN,
        ANFAENGER,
        FORTGESCHRITTEN,
        EXPERTE,
        PROFI
    }
}