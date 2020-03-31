package engine.gui.window;

import engine.model.IModel;
import lombok.Data;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractWindow implements IModel {
    private List<IModel> children = new ArrayList<>();
    private AbstractWindow parent;
    private Vector2f absoluteDisplayPosition;
    private Vector2f absoluteCoordinatePosition;
    private Vector2f relativeDisplayPosition;
    private Vector2f relativeCoordinatePosition;


}
