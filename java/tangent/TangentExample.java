package tangent;

import general.Function;

public class TangentExample {
    public static void main(String[] args) {
        System.out.println("Example of using the Tangent search:");
        Function f = x -> -Math.pow(x[0],3) + 3 * (1 + x[0]) * (Math.log1p(x[0]) - 1);
        Function dfx = x -> -3 * x[0] * x[0] + 3 * Math.log1p(x[0]);
        double x = Tangent.find(f, dfx, -0.5, 0.5, 1e-6);
        System.out.printf("f(%.2f) = %.2f\n\n", x, f.solve(x));
    }
}
