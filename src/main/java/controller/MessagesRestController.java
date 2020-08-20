package controller;

import org.springframework.web.bind.annotation.*;
import aspect.ControllerLogs;
import dao.abstracts.MessageDao;
import dao.abstracts.TopicDao;
import jdo.Message;
import jdo.Topic;

import javax.inject.Inject;
import java.util.List;

/**
 * Handling rest requests from message page
 * */
@RestController
@ControllerLogs
@RequestMapping(path="/messages", produces="application/json")
@CrossOrigin(origins="*")
public class MessagesRestController {

    private final MessageDao messageDao;
    private final TopicDao topicDao;

    @Inject
    public MessagesRestController(MessageDao messageDao, TopicDao topicDao) {
        this.messageDao = messageDao;
        this.topicDao = topicDao;
    }

    @GetMapping("/findTotalNumberOfPages")
    public int findTotalNumberOfPages(int topicId) {
        return messageDao.findTotalNumberOfPages(topicId);
    }

    @GetMapping("/findForPage")
    public List<Message> findForPage(int topicId, int pageNumber) {
        return messageDao.findForPage(topicId, pageNumber);
    }

    @GetMapping("/findTopicById")
    public Topic findTopicById(int topicId){
        return topicDao.findById(topicId);
    }

}
