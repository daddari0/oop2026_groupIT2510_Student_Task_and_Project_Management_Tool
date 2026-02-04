package Config;

public class SettingsManager {

    private static SettingsManager instance;

    private boolean notificationsEnabled;
    private String defaultTaskStatus;

    private SettingsManager() {
        this.notificationsEnabled = true;
        this.defaultTaskStatus = "TODO";
    }

    public static synchronized SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getDefaultTaskStatus() {
        return defaultTaskStatus;
    }

    public void setDefaultTaskStatus(String defaultTaskStatus) {
        this.defaultTaskStatus = defaultTaskStatus;
    }
}