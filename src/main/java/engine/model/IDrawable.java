package engine.model;

import engine.buffer.ICleanable;
import engine.math.ITransformable;

public interface IDrawable extends ITransformable, ICleanable {
    void draw();
}
