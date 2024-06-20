package cfg;

public interface Commons {
    int[] diffScaler = {4, 3, 2, 1, 0};
    int[] removeShapes = {3, 4, 5, 6, 7};
    int levelUpTime = 60;
    int levelDownTime = 240;
    int requiredLevelUpStreak = 3;
    int requiredLevelDownStreak = 5;

    enum Difficulty {
        UNERFAHREN,
        ANFAENGER,
        FORTGESCHRITTEN,
        EXPERTE,
        PROFI
    }

}
