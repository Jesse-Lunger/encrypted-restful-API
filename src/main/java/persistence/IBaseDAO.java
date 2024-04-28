package persistence;

import java.util.List;

public interface IBaseDAO<Entity> {

    List<Entity> getAll();

    Entity getEntityById(int id);

    void saveEntity(Entity entity);

    void updateEntity(Entity entity);

    void removeEntityById(int id);
}

