package engine.event.struct;

public interface IEventRoot {
    default void registerAsEventRoot() {
        EventManager.registerEventRoot(this.getClass());
    }
    default void routeEventHandlers(Object... args) {
        EventManager.routeEvents(this.getClass(), args);
    }
}
