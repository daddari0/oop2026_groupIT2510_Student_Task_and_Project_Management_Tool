package components;

import Config.NotificationCenter;

import java.util.function.Consumer;

public class NotificationComponentImpl implements NotificationComponent {

    private final NotificationCenter center;

    public NotificationComponentImpl(NotificationCenter center) {
        this.center = center;
    }

    @Override
    public void subscribe(Consumer<String> listener) {
        center.subscribe(listener);
    }

    @Override
    public void notify(String message) {
        center.notifyAll(message);
    }
}
