package com.shreya.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        PlayerData player1=mongoTemplate.findOne(Query.query(Criteria.where("match_id").is(player.getMatch_id()).and("contactNumber").is(player.getContactNumber())),PlayerData.class);
        player1.setColor(player.getColor());
        player1.setHandCards(player.getHandCards());
        mongoTemplate.save(player1);
    }

    public List<PlayerData> getPlayersByMatchId(int match_id){
        return mongoTemplate.find(Query.query(Criteria.where("match_id").is(match_id)),PlayerData.class);
    }
}


