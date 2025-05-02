package net.engineeringdisgest.journalApp.repository;

import net.engineeringdisgest.journalApp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntry> getUserForSA() {
        Query query = new Query();
//        query.addCriteria(Criteria.where("userName").is("rahulsinha036"));

//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));

        // we can also check using requalr exp
//        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"));

        // Another way to write above query by creating obj of Criteria
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.andOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("email").ne(null).ne(""),
//                Criteria.where("sentimentAnalysis").exists(true))
//        );

        // if you don't want to send email to someone
//        query.addCriteria(Criteria.where("email").nin("rahulsinha036","Ajit"));

        // we can also play with the type of datatype
//        query.addCriteria(Criteria.where("sentimentAnalysis").type(JsonSchemaObject.Type.BsonType.BOOLEAN));

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));
        List<UserEntry> users = mongoTemplate.find(query, UserEntry.class);
        return users;
    }
}
