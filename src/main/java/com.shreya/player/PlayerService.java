package com.shreya.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shreya on 1/12/16.
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService() {
    }

    public List<PlayerData> getAllPlayers() {
        List<PlayerData> allPlayers = playerRepository.getAllPlayers();
        return allPlayers;
    }

    public List<PlayerData> getPlayersByContactNumber(String number) {
        return playerRepository.getPlayersByContactNumber(number);
    }

    public PlayerData createPlayer(String  number,int match_id,char color){
        return playerRepository.createPlayer(number,match_id,color);
    }

    public void updatePlayer(PlayerData player){
        playerRepository.updatePlayer(player);
    }

    public List<PlayerData> getPlayersByMatchId(int match_id){
        return playerRepository.getPlayersByMatchId(match_id);
    }

    public List<PlayerData> deletePlayerByMatchId(int match_id){
        return playerRepository.deletePlayerByMatchId(match_id);
    }

    public PlayerData getPlayerByMatchIdAndContactNumber(int match_id,String number){
        return playerRepository.getPlayerByMatchIdAndContactNumber(match_id,number);
    }
}