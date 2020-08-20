package dao.abstracts;

import jdo.Topic;

import java.util.List;

/**
 * Dao for work with topic structure
 * */
public interface TopicDao {

    List<Topic> findAll();

    Topic findById(int id);

    Topic save(Topic topic);

    void delete(int id);
}
