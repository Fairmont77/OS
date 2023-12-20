package Functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

public class FunctionsTest {

    @Test
    public void testFunctionFReturnsCorrectSquareRoot() {
        Integer x = 16; // Квадратний корінь з 16 повинен бути 4
        Optional<Double> result = Functions.functionF(x);
        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(4.0, result.get(), 0.001, "Square root of 16 should be 4");
    }

    @Test
    public void testFunctionFHandlesZero() {
        Integer x = 0; // Корінь з нуля має повернути порожній Optional
        Optional<Double> result = Functions.functionF(x);
        assertFalse(result.isPresent(), "Result should not be present for zero");
    }

    @Test
    public void testFunctionFHandlesNegative() {
        Integer x = -1; // Негативні числа не мають кореня у дійсних числах
        Optional<Double> result = Functions.functionF(x);
        assertFalse(result.isPresent(), "Result should not be present for negative numbers");
    }

    @Test
    public void testFunctionGReturnsCorrectFactorial() {
        Integer x = 3; // Факторіал 3 повинен бути 6
        Optional<Double> result = Functions.functionG(x);
        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(6.0, result.get(), "Factorial of 3 should be 6");
    }

    @Test
    public void testFunctionGHandlesZero() {
        Integer x = 0; // Факторіал 0 повинен бути 1
        Optional<Double> result = Functions.functionG(x);
        assertTrue(result.isPresent(), "Result should be present for zero");
        assertEquals(1.0, result.get(), "Factorial of 0 should be 1");
    }
}

