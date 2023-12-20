package Functions;

import java.util.Optional;

public class Functions {

    public static Optional<Double> functionF(Integer x) {
        return x > 0 ? Optional.of(Math.sqrt(x)) : Optional.empty();
    }
    public static Optional<Double> functionG(Integer x) {
        if (x < 0) {
            return Optional.empty();
        }
        double factorial = 1;
        for (int i = 1; i <= x; i++) {
            factorial *= i;
        }
        return Optional.of(factorial);
    }
}
