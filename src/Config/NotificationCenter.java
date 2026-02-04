package Config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationCenter {

    private static NotificationCenter instance;

    private final List<Consumer<String>> listeners = new ArrayList<>();

    private NotificationCenter() {
    }

    public static synchronized NotificationCenter getInstance() {
        if (instance == null) {
            instance = new NotificationCenter();
        }
        return instance;
    }

    public void subscribe(Consumer<String> listener) {
        listeners.add(listener);
    }

    public void notifyAll(String message) {
        for (Consumer<String> listener : listeners) {
            listener.accept(message);
        }
    }
}