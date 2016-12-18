package com.shreya.player;

import com.shreya.variables.SequenceVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shreya on 25/11/16.
 */
@Repository
public class PlayerRepository {

    public static final String COLLECTION_NAME = "player";

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<PlayerData> getAllPlayers() {
        return mongoTemplate.findAll(PlayerData.class);
    }

    public List<PlayerData> getPlayersByContactNumber(String contact) {
        return mongoTemplate.find(Query.query(Criteria.where("contactNumber").is(contact)), PlayerData.class);
    }

    public List<PlayerData> getPlayerByName(String name) {
        return mongoTemplate.find(
                Query.query(Criteria.where("name").regex(name, "i")), PlayerData.class);
    }

    public PlayerData createPlayer(String number,int match_id,char color){
        if (!mongoTemplate.collectionExists(PlayerData.class))
            mongoTemplate.createCollection(PlayerData.class);
        PlayerData player1=new PlayerData(number,match_id,color);
        mongoTemplate.insert(player1);
        return player1;
    }

    public void updatePlayer(PlayerData player){
        Query query=new Query();
        query.addCriteria(Criteria.where("match_id").is(player.getMatch_id()).and("contactNumber").is(player.getContactNumber()));
        Update u=new Update().set("color",player.getColor()).set("handCards",player.getHandCards());
        PlayerData player1=mongoTemplate.findAndModify(query,u,new FindAndModifyOptions().returnNew(true),PlayerData.class);
    }

    public List<PlayerData> getPlayersByMatchId(int match_id){
        return mongoTemplate.find(Query.query(Criteria.where("match_id").is(match_id)),PlayerData.class);
    }

    public List<PlayerData> deletePlayerByMatchId(int match_id){
        List<PlayerData> players= mongoTemplate.find(Query.query(Criteria.where("match_id").is(match_id)),PlayerData.class);
        for(PlayerData player:players)
            mongoTemplate.remove(player);
        return players;
    }

    public PlayerData getPlayerByMatchIdAndContactNumber(int match_id,String number){
        return mongoTemplate.findOne(Query.query(Criteria.where("match_id").is(match_id).and("contactNumber").is(number)),PlayerData.class);
    }
}


