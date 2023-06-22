package Utils;

import org.openqa.selenium.TimeoutException;

import java.util.function.Supplier;

public class Utils {
    public static <T> T waiter(Supplier<T> method, int timeout, long interval) throws InterruptedException {
        long started = System.currentTimeMillis();
        Exception lastException = null;
        while (System.currentTimeMillis() - started < timeout){
            try {
                return method.get();
            } catch (Exception e) {
                lastException = e;
            }
            Thread.sleep(interval*1000);
        }
        throw new TimeoutException("Method " + method.getClass().getName() +
                " timed out in " + timeout + "sec with exception: " + lastException);
    }

}
