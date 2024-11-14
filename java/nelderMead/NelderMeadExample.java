package nelderMead;

import general.Function;

import java.util.ArrayList;
import java.util.Arrays;

public class NelderMeadExample {
    public static void main(String[] args) {
        System.out.println("Example of using the Nelder-Mead search:");
        Function f = x -> x[0] * x[0] + 2 * x[1] * x[1] + Math.cos(x[0] + x[1]);
        Double[] startPoint = {0.0, 0.0};
        ArrayList<Double> result = NelderMead.find(f, Arrays.asList(startPoint), 1e-6);
        System.out.printf("f%s = %f\n", result, f.solve(startPoint));
    }
}
