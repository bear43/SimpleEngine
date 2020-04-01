package engine.io.source;

import engine.model.IModel;
import engine.texture.source.ITextureSource;

import java.util.List;

public interface IDataSource {
    void load();
    List<IModel> createModel(ITextureSource textureSource);
    int getVerticesCount();
    int getIndicesCount();
}
