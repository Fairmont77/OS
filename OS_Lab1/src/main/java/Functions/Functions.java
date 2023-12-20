package Functions;

import java.util.Optional;

public class Functions {

    // calculating sqrt(x). Result if x < x = empty
    public static Optional<Double> functionF(Integer x) {
        return x > 0 ? Optional.of(Math.sqrt(x)) : Optional.empty();
    }
    //calc factorial(x). Result if x < 0 = empty
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
