package config;

import org.springframework.context.annotation.Configuration;
import dao.abstracts.AccountDao;
import dao.abstracts.MessageDao;
import dao.abstracts.TopicDao;
import enums.Authority;
import enums.Gender;
import enums.gender;
import jdo.Account;
import jdo.Message;
import jdo.Topic;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Initial data for forum is added here. This is not necessary, but without it forum would be empty.
 * */
@Configuration
public class AddInitDataToDbConfig {

    private static final String DREAM_TRUCKER = "Dream Trucker";
    private static final String MORELLI = "Morelli";
    private static final String MADAM = "Madam";
    
    private AccountDao accountDao;
    private TopicDao topicDao;
    private MessageDao messageDao;

    @Inject
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Inject
    public void setTopicDao(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Inject
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @PostConstruct
    public void init(){
        Account account = new Account();
        account.setUsername(DREAM_TRUCKER);
        account.setPassword("bingo");
        account.setEmail("truck@mail.ru");
        account.setGender(Gender.MALE);
        account.setBirthDate(LocalDate.of(1990, 10,28));
        account.addAuthority(Authority.USER);
        account.addAuthority(Authority.ADMIN);
        accountDao.register(account);

        account = new Account();
        account.setUsername(MORELLI);
        account.setPassword("123");
        account.setEmail("mc_bomfunc@mail.ru");
        account.setGender(Gender.MALE);
        account.setBirthDate(LocalDate.of(1975, 1,28));
        account.addAuthority(Authority.USER);
        accountDao.register(account);

        account = new Account();
        account.setUsername(MADAM);
        account.setPassword("qwerty");
        account.setEmail("dj_go@yandex.ya");
        account.setGender(Gender.FEMALE);
        account.setBirthDate(LocalDate.of(1991, 2,3));
        account.addAuthority(Authority.USER);
        accountDao.register(account);
        
        Topic topic = new Topic();
        topic.setId(1);
        topic.setTitle("News");
        topicDao.save(topic);

        topic = new Topic();
        topic.setTitle("About forum");
        topicDao.save(topic);

        topic = new Topic();
        topic.setTitle("Night chat");
        topicDao.save(topic);

        topic = new Topic();
        topic.setTitle("Empty topic");
        topicDao.save(topic);

        Message message = new Message();
        message.setTopicId(2);
        message.setAccountUsername(DREAM_TRUCKER);
        message.setText("Hello, friends! This is a little forum, where you can write anything!");
        messageDao.save(message);

        message = new Message();
        message.setTopicId(3);
        message.setAccountUsername(MADAM);
        message.setText("Hi, Boys! Where are you from?");
        messageDao.save(message);

        message = new Message();
        message.setTopicId(3);
        message.setAccountUsername(MORELLI);
        message.setText("I am from Huston, and you?");
        messageDao.save(message);

        message = new Message();
        message.setTopicId(3);
        message.setAccountUsername(MADAM);
        message.setText("I am from Boston.");
        messageDao.save(message);

        message = new Message();
        message.setTopicId(3);
        message.setAccountUsername(MORELLI);
        message.setText("Call me when you will be in Huston.");
        messageDao.save(message);

        message = new Message();
        message.setTopicId(3);
        message.setAccountUsername(MADAM);
        message.setText("Maybe you will call me, I have not your phone.");
        messageDao.save(message);

        message = new Message();
        message.setTopicId(1);
        message.setAccountUsername(DREAM_TRUCKER);
        message.setText("We moved to a localhost and it is very exciting!");
        messageDao.save(message);
    }

}
