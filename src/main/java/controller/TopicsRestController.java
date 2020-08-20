package controller;

import org.springframework.web.bind.annotation.*;
import aspect.ControllerLogs;
import dao.abstracts.TopicDao;
import jdo.Topic;

import javax.inject.Inject;
import java.util.List;

/**
 * Handling rest requests from topics page
 * */
@RestController
@ControllerLogs
@RequestMapping(path="/topics", produces="application/json")
@CrossOrigin(origins="*")
public class TopicsRestController {

    private final TopicDao topicDao;

    @Inject
    public TopicsRestController(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @GetMapping("/findAll")
    public List<Topic> findAll(){
        return topicDao.findAll();
    }

    @GetMapping("/smth/save")
    public String smth(){
        return "ok";
    }
}
