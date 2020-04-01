package engine.io.source;

import de.javagl.obj.*;
import engine.buffer.Loader;
import engine.buffer.VertexArrayObject;
import engine.buffer.VertexBufferObject;
import engine.model.CompositeModel;
import engine.model.IModel;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.texture.source.ITextureSource;
import engine.util.TripleConsumer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader implements IDataSource {

    private final String DEFAULT_MODELS_DIRECTORY = "./models/";
    private String file;
    private Obj obj;

    public OBJLoader() {}

    public OBJLoader(String file) {
        this.file = file;
    }

    public void load() {
        try {
            obj = ObjReader.read(Files.newInputStream(Path.of(DEFAULT_MODELS_DIRECTORY + file)));
            obj = ObjUtils.convertToRenderable(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private VertexBufferObject createVerticesVBO(Obj obj) {
        FloatBuffer vertices = ObjData.getVertices(obj);
        return Loader.createVBO(vertices, 3);
    }

    private VertexBufferObject createIndicesVBO(Obj obj) {
        IntBuffer indices = ObjData.getFaceVertexIndices(obj);
        return Loader.createEBO(indices);
    }

    private VertexBufferObject createTexCoordsVBO(Obj obj) {
        FloatBuffer texCoords = ObjData.getTexCoords(obj, 2);
        return Loader.createVBO(texCoords, 2);
    }

    private void fillVertexArrayObject(VertexArrayObject vao, Obj obj) {
        vao.setIndexToVBO(0, false, createVerticesVBO(obj));
        vao.setIndexToVBO(1, false, createTexCoordsVBO(obj));
        vao.bindEBO(createIndicesVBO(obj));
    }

    private VertexArrayObject createVAO(Obj obj) {
        VertexArrayObject vao = new VertexArrayObject();
        fillVertexArrayObject(vao, obj);
        return vao;
    }

    private IModel createModel(String name, VertexArrayObject vao, Obj meshObj, ITextureSource textureSource) {
        return meshObj.getNumTexCoords() == 0 || textureSource == null ?
                new RawModel(name, vao, meshObj.getNumVertices(), meshObj.getNumFaces()*3, false) :
                new TexturedModel(name, vao, meshObj.getNumVertices(), meshObj.getNumFaces()*3, textureSource, false);
    }

    private List<IModel> processOBJGroup(Obj obj, ITextureSource textureSource) {
        List<IModel> modelList = new ArrayList<>();
        for(int index = 0; index < obj.getNumGroups(); index++) {
            var group = obj.getGroup(index);
            var meshObj = ObjUtils.groupToObj(obj, group, null);
            meshObj = ObjUtils.convertToRenderable(meshObj);
            var createdVao = createVAO(meshObj);
            modelList.add(createModel(group.getName(), createdVao, meshObj, textureSource));
        }
        obj = ObjUtils.convertToRenderable(obj);
        modelList.add(createModel("Root", createVAO(obj), obj, textureSource));
        return modelList;
    }

    public List<IModel> createModel(ITextureSource textureSource) {
        return processOBJGroup(obj, textureSource);
    }

    public int getVerticesCount() {
        return obj.getNumVertices();
    }

    public int getIndicesCount() {
        return obj.getNumFaces();
    }
}
