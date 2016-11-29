package com.shreya;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = (Logger) LoggerFactory.getLogger(PlayerRepository.class);
    public static final String COLLECTION_NAME = "Player";

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Player> getAllPlayers() {
        return mongoTemplate.findAll(Player.class);
    }

    public Player getPlayerByContactNumber(String contact) {
        return mongoTemplate.findOne(Query.query(Criteria.where("contactNumber").is(contact)), Player.class);
    }

    public List<Player> getPlayerByName(String name) {
        return mongoTemplate.find(
                Query.query(Criteria.where("name").regex(name, "i")), Player.class);
    }

    public void addPlayer(Player player1) {
        if (!mongoTemplate.collectionExists(Player.class))
            mongoTemplate.createCollection(Player.class);

        mongoTemplate.insert(player1);
    }

    public Player updatePlayer(String number, Player player1){
        Player myPlayer=mongoTemplate.findOne(Query.query(Criteria.where("contactNumber").is(number)),Player.class);

        if (myPlayer != null) {
            logger.info("Inside updateBook(), updating record...");
            myPlayer.setContactNumber(player1.getContactNumber());
            myPlayer.setName(player1.getName());
            myPlayer.setPassword(player1.getPassword());
            myPlayer.setFriends(player1.getFriends());
            myPlayer.setNumberOfDraw(player1.getNumberOfDraw());
            myPlayer.setNumberOfLose(player1.getNumberOfLose());
            myPlayer.setNumberOfWins(player1.getNumberOfWins());
            myPlayer.setOnline(player1.getOnline());

            mongoTemplate.save(myPlayer);
            return myPlayer;

        }
        else
            return null;
    }
    public Player deletePlayer(String number){
        Player player1=mongoTemplate.findOne(Query.query(Criteria.where("contactNumber").is(number)),Player.class);
        mongoTemplate.remove(player1);
        return player1;
}




}
