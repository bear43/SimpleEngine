package engine.event.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private static Map<Class<? extends IEventRoot>, List<IEvent>> eventMap = new HashMap<>();

    public static void registerEventRoot(Class<? extends IEventRoot> eventClass) {
        eventMap.put(eventClass, new ArrayList<>());
    }

    public static void routeEvents(Class<? extends IEventRoot> eventClass, Object... args) {
        List<IEvent> eventHandlers = eventMap.get(eventClass);
        check(eventHandlers);
        eventHandlers.forEach(handler -> {
            handler.onEvent(args);
        });
    }

    private static void check(List<IEvent> events) {
        if(events == null) {
            throw new RuntimeException("That type of events is not registered");
        }
    }

    private static int searchEventHandlerIndex(Class<? extends IEventRoot> eventClass, IEvent handler) {
        List<IEvent> events = eventMap.get(eventClass);
        check(events);
        if(handler == null) {
            throw new RuntimeException("Cannot search null event handler");
        }
        return events.indexOf(handler);
    }

    public static void registerEventHandler(Class<? extends IEventRoot> eventClass, IEvent handler) {
        List<IEvent> events = eventMap.get(eventClass);
        check(events);
        events.add(handler);
    }

    public static void registerEventHandlerBefore(Class<? extends IEventRoot> eventClass, IEvent beforeHandler, IEvent handler) {
        List<IEvent> events = eventMap.get(eventClass);
        int index = searchEventHandlerIndex(eventClass, beforeHandler);
        if(index == -1) {
            throw new RuntimeException("There is no such before handler");
        } else {
            events.add(index, handler);
        }

    }

    public static void registerEventHandlerAfter(Class<? extends IEventRoot> eventClass, IEvent afterHandler, IEvent handler) {
        List<IEvent> events = eventMap.get(eventClass);
        int index = searchEventHandlerIndex(eventClass, afterHandler);
        if(index == -1) {
            throw new RuntimeException("There is no such after handler");
        } else {
            events.add(index+1, handler);
        }
    }
}
