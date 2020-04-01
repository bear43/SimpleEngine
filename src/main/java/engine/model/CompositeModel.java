package engine.model;

import engine.MemoryManager;
import engine.buffer.ICleanable;
import lombok.Data;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;


@Data
public class CompositeModel implements IModel, ICleanable {
    protected String name;
    protected List<IModel> meshes = new ArrayList<>();
    protected int verticesCount;
    protected int indicesCount;
    protected Matrix4f transformation;

    public CompositeModel(String name, List<? extends IModel> meshes, int verticesCount, int indicesCount, boolean register) {
        this.name = name;
        this.meshes.addAll(meshes);
        this.verticesCount = verticesCount;
        this.indicesCount = indicesCount;
        this.transformation = new Matrix4f();
        if(register) {
            MemoryManager.getModels().putIfAbsent(this.getClass(), new ArrayList<>());
            MemoryManager.getModels().get(this.getClass()).add(this);
        }
    }

    public CompositeModel(String name, List<? extends IModel> meshes, int verticesCount, int indicesCount) {
        this(name, meshes, verticesCount, indicesCount, true);
    }

    public void draw() {
        meshes.forEach(IDrawable::draw);
    }

    @Override
    public void clean() {
        meshes.forEach(ICleanable::clean);
        MemoryManager.removeModelInstance(this);
    }

}
