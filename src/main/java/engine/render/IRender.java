package engine.render;

import engine.MemoryManager;

public interface IRender {
    void render();
    String getName();
    default void register() {
        MemoryManager.getRenders().add(this);
    }
}
