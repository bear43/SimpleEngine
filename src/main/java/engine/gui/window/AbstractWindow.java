package engine.gui.window;

import engine.model.IDrawable;
import lombok.Data;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractWindow implements IDrawable {
    private List<IDrawable> children = new ArrayList<>();
    private AbstractWindow parent;
    private Vector2f absoluteDisplayPosition;
    private Vector2f absoluteCoordinatePosition;
    private Vector2f relativeDisplayPosition;
    private Vector2f relativeCoordinatePosition;


}
