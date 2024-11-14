package fibonacci;

import general.Function;

public class FibonacciExample {
    public static void main(String[] args) {
        System.out.println("Example of using the Fibonacci serach:");
        Function f = x -> -Math.pow(x[0],3) + 3 * (1 + x[0]) * (Math.log1p(x[0]) - 1);
        double x = Fibonacci.find(f, -0.5, 0.5,10);
        System.out.printf("f(%.2f) = %.2f\n\n", x, f.solve(x));
    }
}
