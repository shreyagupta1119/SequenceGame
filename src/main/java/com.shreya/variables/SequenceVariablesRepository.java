package com.shreya.variables;

import com.shreya.game.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shreya on 8/12/16.
 */
@Repository
public class SequenceVariablesRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Card> showDeck(int match_id){
        SequenceVariables obj= mongoTemplate.findOne(Query.query(Criteria.where("match_id").is(match_id)),SequenceVariables.class);
        return obj.getGad().getDeck().getCards();
    }

    public SequenceVariables createSequenceVariables(int match_id){
        if (!mongoTemplate.collectionExists(SequenceVariables.class))
            mongoTemplate.createCollection(SequenceVariables.class);
        SequenceVariables sv=new SequenceVariables(match_id);
        mongoTemplate.insert(sv);
        return sv;
    }

    public void updateSequenceVariables(SequenceVariables sv){
        SequenceVariables sv1=mongoTemplate.findOne(Query.query(Criteria.where("match_id").is(sv.getMatch_id())),SequenceVariables.class);
        sv1.setGad(sv.getGad());
        sv1.setCounter(sv.getCounter());
        sv1.setLockedpos(sv.getLockedpos());
        mongoTemplate.save(sv1);
    }

    public List<SequenceVariables> getAllSequenceVariables(){
        return mongoTemplate.findAll(SequenceVariables.class);
    }

    public SequenceVariables getSequenceVariableByMatchId(int match_id){
        return mongoTemplate.findOne(Query.query(Criteria.where("match_id").is(match_id)),SequenceVariables.class);
    }
}
