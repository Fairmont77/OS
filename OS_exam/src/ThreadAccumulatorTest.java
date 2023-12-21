import org.junit.Test;
import static org.junit.Assert.*;

public class ThreadAccumulatorTest {

    @Test
    public void testThreadAccumulator() throws InterruptedException {
        final int threshold = 5;
        final int numberOfThreads = 100;
        ThreadAccumulator accumulator = new ThreadAccumulator(threshold);
        Thread[] threads = new Thread[numberOfThreads];

        // Створюємо та запускаємо потоки
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                try {
                    accumulator.enter();
                    // Симулюємо деяку обробку у критичній секції
                    Thread.sleep(100);
                    accumulator.leave();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[i].start();
        }

        // Чекаємо на завершення всіх потоків
        for (Thread thread : threads) {
            thread.join();
        }

        // Перевіряємо, чи всі потоки успішно завершили своє виконання
        for (Thread thread : threads) {
            assertFalse(thread.isAlive());
        }
    }
}
