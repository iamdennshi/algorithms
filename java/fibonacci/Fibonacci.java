package fibonacci;
import general.Function;

final public class Fibonacci {
    static public double find(Function f, double a, double b, int n) {
        int i = 0;
        double u, v, fu, fv;

        do {
            i++;

            u = a + fib(n - i - 1)/ fib(n-i+1) * (b - a);
            fu = f.solve(u);
            v = a + fib(n - i)/ fib(n-i+1) * (b - a);
            fv = f.solve(v);

            if (fu > fv) {
                a = u;
            } else {
                b = v;
            }
        } while (n - 2 > i);

        return (b + a) / 2.;
    }
    static private double fib(int n) {
        if (n <= 1) return 1;
        return fib(n - 2) + fib(n - 1);
    }
}