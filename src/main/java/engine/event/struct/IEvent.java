package engine.event.struct;

public interface IEvent {

    default void registerAsEventHandler(Class<? extends IEventRoot> eventClass) {
        EventManager.registerEventHandler(eventClass, this);
    }

    void onEvent(Object... args);
}
