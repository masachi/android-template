package ga.cv3sarato.android.im_sdk.utils.event;

public class Event {
    private String eventName;
    private EventHandler eventHandler;

    public Event(String eventName, EventHandler eventHandler) {
        super();
        this.eventName = eventName;
        this.eventHandler = eventHandler;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
