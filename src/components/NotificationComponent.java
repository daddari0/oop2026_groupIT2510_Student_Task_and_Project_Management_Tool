package components;

import java.util.function.Consumer;

public interface NotificationComponent {
    void subscribe(Consumer<String> listener);

    void notify(String message);
}
