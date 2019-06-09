package de.cl.playground.spring.mongodb;


import de.cl.playground.spring.mongodb.model.User;
import de.cl.playground.spring.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.insert();
    }

    //#################### Using the mongo template ####################
    private void insert() {
        User user = new User();
        user.setName("Heinz");
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

    private void updateFirst() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Alex"));
        Update update = new Update();
        update.set("name", "James");
        mongoTemplate.updateFirst(query, update, User.class);
    }

    private void updateMulti() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Eugen"));
        Update update = new Update();
        update.set("name", "Victor");
        mongoTemplate.updateMulti(query, update, User.class);
    }

    private void findAndModify() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Markus"));
        Update update = new Update();
        update.set("name", "Nick");
        User user = mongoTemplate.findAndModify(query, update, User.class);
    }

    private void upsert() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Markus"));
        Update update = new Update();
        update.set("name", "Nick");
        mongoTemplate.upsert(query, update, User.class);
    }

    private void remove() {
        User user = new User();
        user.setId("5cfd5cdcd8a5abafec548b32");
        mongoTemplate.remove(user, "user");
    }

    //#################### Using the mongo repository ####################

    private void insertByRepo() {
        User user = new User();
        user.setName("Peter");
        userRepository.insert(user);
    }

    private void saveInsertByRepo() {
        User user = new User();
        user.setName("Aaron");
        userRepository.save(user);
    }

    private void saveUpdateByRepo() {
        User user = mongoTemplate.findOne(
            Query.query(Criteria.where("name").is("Jack")), User.class);
        user.setName("Jim");
        userRepository.save(user);
    }

    private void deleteByRepo() {
        User user = new User();
        user.setId("5cfd59ced8a5ab375441482e");
        userRepository.delete(user);
    }

    private void findOneByRepo() {
        String id = "5cfd59ced8a5ab375441482e";
        Optional<User> opt = userRepository.findById(id);
        boolean exists = opt.isPresent();
        if (exists) {
            User user = opt.get();
            System.out.println("User with ID (" + id + ") could be found.");
            System.out.println(user.getName());
        } else
            System.out.println("User with ID (" + id + ") could not be found.");

    }

    private void existsByRepo() {
        String id = "5cfd59ced8a5ab375441482e";
        boolean exists = userRepository.existsById(id);
        System.out.println((exists) ? "User exists":"User does not exist");
    }

    private void findAllByRepo() {
        List<User> users = userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        users.forEach((user -> System.out.println(user.getName())));
    }

    private void findAllPageableByRepo() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<User> pages = userRepository.findAll(pageableRequest);
        List<User> users = pages.getContent();
        users.forEach((user -> System.out.println(user.getName())));
    }
}
