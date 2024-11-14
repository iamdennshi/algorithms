package nelderMead;

import general.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NelderMead {
    private static final boolean DEBUG = false;
    // Calculate the function at the p point
    private static double calc(Function f, ArrayList<Double> p) {
        return f.solve(p.toArray(new Double[]{}));
    }
    // Sorting by the function value for points
    private static void sortByFunctionResult(Function f, ArrayList<ArrayList<Double>> points) {
        points.sort((o1, o2) -> {
            if (calc(f, o1) - calc(f, o2) > 0) {
                return 1;
            }
            return -1;
        });
    }
    /**
     * @param f          Function of any arguments
     * @param pointStart Starting point of the search
     * @param eps        Accuracy of result, i.e. 1e-6
     */
    public static ArrayList<Double> find(Function f, List<Double> pointStart, double eps) {
        final double A = 1.0;
        final double B = 0.5;
        final double C = 2.0;
        int iter = 0;

        // Number of points
        final int n = pointStart.size() + 1;

        // Indexes of points
        // xh - highest point, xl - lowest point, xg - closest to xh
        final int xhIndex = n - 1;
        final int xlIndex = 0;
        final int xgIndex = xhIndex - 1;

        // Array of points
        ArrayList<ArrayList<Double>> x = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            x.add(new ArrayList<Double>(n - 1));
        }

        // Initialize array of point coordinates
        for (int i = 0; i < n; i++) {
            int usedRand = i;
            for (int j = 0; j < n - 1; j++) {
                if (usedRand > 0) {
                    x.get(i).add(pointStart.get(j) + Math.random());
                    usedRand--;
                } else {
                    x.get(i).add(pointStart.get(j));
                }
            }
        }

        // To stop the algorithm
        double sigma = 0.0;
        double fi = 0.0;

        // To store function values
        ArrayList<Double> fx;

        do {
            fx = new ArrayList<>(n);

            // Sorting points by function value
            // To find: xh, xl, xg
            sortByFunctionResult(f, x);

            // Calculate function values
            for (int i = 0; i < n; i++) {
                fx.add(calc(f, x.get(i)));
            }

            if (DEBUG) {
                System.out.println("x:");
                for (int i = 0; i < n; i++) {
                    System.out.println(" " + (i + 1) + ". " + x.get(i));
                }
                System.out.println("fx: " + fx);
            }

            // xc - center of gravity
            ArrayList<Double> xc = new ArrayList<>(n - 1);

            // Exclude xh and finding center of gravity among other points
            for (int i = 0; i < n - 1; i++) {
                double tmp = 0.0;
                for (int j = 0; j < x.size() - 1; j++) {
                    tmp += x.get(j).get(i);
                }
                xc.add(tmp / (n - 1));
            }

            double fxc = calc(f, xc);
            if (DEBUG) {
                System.out.println("xc: " + xc);
                System.out.println("fxc: " + fxc);
            }

            // Reflecting xh relative to xc to finding xr
            ArrayList<Double> xr = new ArrayList<>(n - 1);

            final double Ap1 = A + 1.0;
            for (int i = 0; i < n - 1; i++) {
                xr.add(Ap1 * xc.get(i) - x.get(xhIndex).get(i) * A);
            }

            double fxr = calc(f, xr);
            if (DEBUG) {
                System.out.println("xr: " + xr);
                System.out.println("fxr: " + fxr);
            }

            if (fxr < fx.get(xlIndex)) {
                // Stretching
                ArrayList<Double> xe = new ArrayList<>(n - 1);
                for (int i = 0; i < n - 1; i++) {
                    xe.add(xc.get(i) * (1 - C) + C * xr.get(i));
                }
                double fxe = calc(f, xe);

                if (fxe < fxr) {
                    x.set(xhIndex, xe);
                } else {
                    x.set(xhIndex, xr);
                }
                continue;

            } else if (fxr < fx.get(xgIndex)) {
                fx.set(xhIndex, fxr);
                x.set(xhIndex, xr);
                continue;

            } else if (fxr < fx.get(xhIndex)) {
                fx.set(xhIndex, fxr);
                x.set(xhIndex, xr);
            }

            ArrayList<Double> xk = new ArrayList<>(n - 1);
            for (int i = 0; i < n - 1; i++) {
                xk.add((B * x.get(xhIndex).get(i) + B * xc.get(i)));
            }
            double fxk = calc(f, xk);

            if (fxk < fx.get(xhIndex)) {
                // Compression
                x.set(xhIndex, xk);
            } else {
                // Reduction
                ArrayList<Double> xl = new ArrayList<>(x.get(xlIndex));
                ArrayList<Double> xi = new ArrayList<>(Collections.nCopies(n - 1, 0.0));
                ArrayList<Double> xm;

                for (int i = 0; i < n; i++) {
                    xm = new ArrayList<>(x.get(i));

                    for (int j = 0; j < n - 1; j++) {
                        xi.set(j, xl.get(j) + (xm.get(j) - xl.get(j)) / 2.0);
                    }
                    x.set(i, xi);
                }
            }
            // Calculating the end of the algorithm
            fi = 0.0;
            for (int i = 0; i < n; i++) {
                fi += fx.get(i) / n;
            }
            sigma = 0.0;
            for (int i = 0; i < n; i++) {
                sigma +=  Math.pow(fx.get(i) - fi, 2) / n;
            }
            iter++;
        } while (Math.sqrt(sigma) > eps);

        if (DEBUG) {
            System.out.println("Iteration " + iter);
            System.out.printf("f%s = %f\n", x.get(0).toString(), fx.get(0));
        }
        return x.get(0);
    }
}