import java.util.concurrent.Semaphore;

public class ThreadAccumulator {
    private final int threshold;
    private final Semaphore semaphore;
    private int currentCount = 0;

    public ThreadAccumulator(int threshold) {
        this.threshold = threshold;
        this.semaphore = new Semaphore(1); // Дозволяємо одному потоку ввійти
    }

    public void enter() throws InterruptedException {
        semaphore.acquire(); // Запитуємо дозвіл на вхід
        synchronized (this) {
            currentCount++;
            if (currentCount >= threshold) {
                semaphore.release(); // Якщо досягли порогу, дозволяємо наступному потоку ввійти
            } else {
                semaphore.release(); // Якщо поріг не досягнуто, дозволяємо наступному потоку ввійти
            }
        }
    }

    public void leave() {
        synchronized (this) {
            currentCount--;
            if (currentCount < threshold) {
                semaphore.release(); // Якщо кількість потоків під порогом, дозволяємо наступному потоку ввійти
            }
        }
    }
}
