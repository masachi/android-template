package ga.cv3sarato.android.im_sdk.utils.event;

import java.util.ArrayList;
import java.util.List;

public class EventEmitter {
    private List<Event> eventList = new ArrayList<>();

    public void on(String eventName, EventHandler eventHandler) {
        eventList.add(new Event(eventName, eventHandler));
    }

    public void emit(String eventName, Object... params) {
        for(Event event : eventList) {
            if(event.getEventName().equals(eventName)) {
                event.getEventHandler().handleEvent(params);
            }
        }
    }
}
