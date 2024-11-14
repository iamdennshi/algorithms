package tangent;

import general.Function;

final public class Tangent {
    public static double find(Function f, Function dfx, double a, double b, double eps) {
        double startX;
        boolean isEnd = false;
        final Function g = x -> f.solve(x[1]) + dfx.solve(x[1]) * (x[0] - x[1]);

        // Tangent to points a and b
        Line la = new Line(dfx.solve(a), f.solve(a) + dfx.solve(a) * -a);
        Line lb = new Line(dfx.solve(b), f.solve(b) + dfx.solve(b) * -b);
        Line lc;

        do {
            // Finding the intersection point of two tangents
            startX = la.findPointOfIntersection(lb);

            // New tangent to startX
            lc = new Line(dfx.solve(startX), f.solve(startX) + dfx.solve(startX) * -startX);

            // Finding the greatest intersection of old tangents with new ones
            double xla = la.findPointOfIntersection(lc);
            double xlb = lb.findPointOfIntersection(lc);

            isEnd = Math.abs(f.solve(startX) - la.solve(startX)) < eps;
            // Replace the old tangent with the new one
            if (la.solve(xla) > lb.solve(xlb)) {
                la = lc;
            } else {
                lb = lc;
            }
        } while (!isEnd);
        return startX;
    }
}