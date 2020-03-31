package engine.io.source;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import engine.buffer.Loader;
import engine.buffer.VertexArrayObject;
import engine.buffer.VertexBufferObject;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class OBJLoader implements IDataSource {

    private String file = "./models/test.obj";
    private Obj obj;

    public OBJLoader() {}

    public OBJLoader(String file) {
        this.file = file;
    }

    public void load() {
        try {
            obj = ObjReader.read(Files.newInputStream(Path.of(file)));
            obj = ObjUtils.convertToRenderable(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private VertexBufferObject createVerticesVBO() {
        FloatBuffer vertices = ObjData.getVertices(obj);
        return Loader.createVBO(vertices, 3);
    }

    private VertexBufferObject createIndicesVBO() {
        IntBuffer indices = ObjData.getFaceVertexIndices(obj);
        return Loader.createEBO(indices);
    }

    private VertexBufferObject createTexCoordsVBO() {
        FloatBuffer texCoords = ObjData.getTexCoords(obj, 2);
        return Loader.createVBO(texCoords, 2);
    }

    public void fillVertexArrayObject(VertexArrayObject vao) {
        vao.setIndexToVBO(0, false, createVerticesVBO());
        vao.setIndexToVBO(1, false, createTexCoordsVBO());
        vao.bindEBO(createIndicesVBO());
    }

    public VertexArrayObject createVAO() {
        VertexArrayObject vao = new VertexArrayObject();
        fillVertexArrayObject(vao);
        return vao;
    }

    public int getVerticesCount() {
        return obj.getNumVertices();
    }

    public int getIndicesCount() {
        return obj.getNumFaces();
    }
}
