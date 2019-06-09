package de.cl.playground.spring.mongodb;


import de.cl.playground.spring.mongodb.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.saveUpdate();
    }

    private void insert() {
        User user = new User();
        user.setName("Jon");
        System.out.println("Save user to database: " + user);
        user = mongoTemplate.insert(user, "user");
        System.out.println("Saved user to database: " + user);
    }

    private void saveInsert() {
        User user = new User();
        user.setName("Albert");
        System.out.println("Save user to database: " + user);
        mongoTemplate.save(user, "user");
        System.out.println("Saved user to database: " + user);
    }

    private void saveUpdate() {
        User user = mongoTemplate.findOne(
            Query.query(Criteria.where("name").is("Albert")), User.class);
        user.setName("Jim");
        System.out.println("Save user to database: " + user);
        mongoTemplate.save(user, "user");
        System.out.println("Saved user to database: " + user);
    }


}
