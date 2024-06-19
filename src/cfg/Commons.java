package cfg;

public interface Commons {
    int[] level={0,1,2,3,4};
int[] diffScaler = {4,3,2,1,0};
    int[] maxSolverTries={6,5,4,3,2};
    int[] removeShapes = {3,4,5,6,7};
    int levelUpTime = 60;
     int levelDownTime = 240;

    enum Difficulty {
         UNERFAHREN,
         ANFAENGER,
         FORTGESCHRITTEN,
         EXPERTE,
         PROFI
     }

}
