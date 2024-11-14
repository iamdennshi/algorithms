package polylines;

import general.Function;

public class Polylines {

    public static double find(Function f, Function dfx, double a, double b, double eps) {

        double L = Math.abs(dfx.solve(a)); // Константа Липшица
        double step = 0.1;
        double lastL;

        while ( (lastL = Math.abs(dfx.solve(a + step))) > L) {
            L = lastL;
            step += 0.1;
        }

        System.out.println(L);
        return 0.0;
    }
}