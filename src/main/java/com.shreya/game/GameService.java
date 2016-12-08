package com.shreya.game;

import com.shreya.player.PlayerData;
import com.shreya.player.PlayerService;
import com.shreya.user.UserData;
import com.shreya.user.UserService;
import com.shreya.variables.SequenceVariables;
import com.shreya.variables.SequenceVariablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shreya on 1/12/16.
 */
@Service
public class GameService {

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SequenceVariablesService sequenceVariablesService;


    public UserData findRandomOpponent(String number){
            List<UserData> users=userService.getOnlineUsers();
            if(users.size()==0 ||users.size()==1)
                return null;

            else {
                while (true) {
                    int i = (int) (Math.random() * (users.size() + 1));
                    UserData user = users.get(i);
                    if (!user.getContactNumber().equals( number)) {
                         return user;
                    }
                }
            }
    }

    public UserData sequenceFriend(String number){
        List<UserData> users1=userService.getUserFriendsList(number);
        List<UserData> users2=userService.getOnlineUsers();
        List<UserData> users=new ArrayList<UserData>();
        for(UserData user:users1){
            if(users2.contains(user))
                users.add(user);
        }
        if(users.size()==0)
            return null;

        else {
            while (true) {
                int i = (int) (Math.random() * (users.size() + 1));
                UserData user = users.get(i);
                if (!user.getContactNumber().equals(number)) {
                     return user;
                }
            }
        }

    }

    public List<UserData> findOpponentByName(String name){
        List<UserData> users1=userService.getUserByName(name);
        List<UserData> users2=userService.getOnlineUsers();
        List<UserData> users=new ArrayList<UserData>();
        for(UserData user:users1){
            if(users2.contains(user))
                users.add(user);
        }
        return users;
    }

    public List<Card> showDeck(int match_id){
        return sequenceVariablesService.showDeck(match_id);
    }

    public void match(UserData user1,UserData user2){
        int match_id=generateMatchId();
        PlayerData player1=createPlayer(user1,match_id,'g');
        PlayerData player2=createPlayer(user2,match_id,'b');
        Deck deck=new Deck();
        for(int i=0;i<6;i++){
            player1.addCard(deck.drawFromDeck());
            player2.addCard(deck.drawFromDeck());
        }
        playerService.updatePlayer(player1);
        playerService.updatePlayer(player2);
        SequenceVariables sv=sequenceVariablesService.createSequenceVariables(match_id);
        GameboardAndDeck gad=new GameboardAndDeck(new GameBoard(),deck);
        sv.setGad(gad);
        sequenceVariablesService.updateSequenceVariables(sv);
    }

    public Integer generateMatchId(){
        List<SequenceVariables> svs=sequenceVariablesService.getAllSequenceVariables();
        Collections.sort(svs,SequenceVariables.match_idComparator);
        int length=svs.size();
        int last_match_id=svs.get(length-1).getMatch_id();
        return last_match_id+1;
    }

    public PlayerData createPlayer(UserData user,int match_id,char color){
        return playerService.createPlayer(user.getContactNumber(),match_id,color);
    }

    public String putCardOnGameBoard(Card c, PlayerData player, int pos){
        int match_id=player.getMatch_id();
        SequenceVariables temp=sequenceVariablesService.getSequenceVariableByMatchId(match_id);
        GameboardAndDeck gad=temp.getGad();
        GameBoard gb=temp.getGad().getGb();
        Deck deck=temp.getGad().getDeck();

        if(c.getValue()!=11) {
            for (int i:gb.getGameboard().keySet()){
                if(((gb.getGameboard().get(i).getSuit())==(c.getSuit())) &&
                        (gb.getGameboard().get(i).getValue())==(c.getValue())){
                    if(gb.getFill().get(i)==null) {
                        gb.getFill().put(i,player.getColor());
                        gad.setGb(gb);
                        temp.setGad(gad);
                        sequenceVariablesService.updateSequenceVariables(temp);
                        return "Card is placed on gameboard";
                    }
                    else {
                        String str=deadCardReplacement(player,c)  ;
                        return str;
                    }
                }
            }
        }
        else if(c.getSuit()=='h' ||c.getSuit()=='s'){
            if(gb.getFill().get(pos)!=null && !temp.getLockedpos().contains(pos)){
                gb.getFill().remove(pos);
                gad.setGb(gb);
                temp.setGad(gad);
                sequenceVariablesService.updateSequenceVariables(temp);
                return "Card is removed from position "+pos+" using single eyed jack";
            }
        }
        else {
            if(gb.getFill().get(pos)==null){
                gb.getFill().put(pos,player.getColor());
                gad.setGb(gb);
                temp.setGad(gad);
                sequenceVariablesService.updateSequenceVariables(temp);
                return "Card is put on position "+pos +" using double eyed jack";
            }
        }
        return "card is not put, please choose other position";
    }


    public PlayerData whoseTurnToPlayNext(int match_id){
        List<PlayerData> players=playerService.getPlayersByMatchId(match_id);
        int curr_counter=sequenceVariablesService.getSequenceVariableByMatchId(match_id).getCounter();
        if(gameActive(match_id)){
            ++curr_counter;
            if(curr_counter%2==0)
                return players.get(1);
            else
                return players.get(0);
        }
        else
            return null;
    }


    public Boolean gameActive(int match_id){
        ArrayList<Integer> lpos=sequenceVariablesService.getSequenceVariableByMatchId(match_id).getLockedpos();
        if(lpos.size()>0)
            return false;
        return true;
    }

    public String deadCardReplacement(PlayerData player,Card c){
        player.removeCard(c);
        int match_id=player.getMatch_id();
        SequenceVariables temp=sequenceVariablesService.getSequenceVariableByMatchId(match_id);
        GameboardAndDeck gad=temp.getGad();
        Deck deck=gad.getDeck();
        player.addCard(deck.drawFromDeck());
        playerService.updatePlayer(player);
        gad.setDeck(deck);
        temp.setGad(gad);
        sequenceVariablesService.updateSequenceVariables(temp);
        return "deadcard is replaced with new card";
    }

    public PlayerData gameWinner(int match_id){
        List<PlayerData> players=playerService.getPlayersByMatchId(match_id);
        SequenceVariables temp=sequenceVariablesService.getSequenceVariableByMatchId(match_id);
        GameBoard gb=temp.getGad().getGb();
        ArrayList<Integer> lpos=temp.getLockedpos();
        for(int i=1;i<49;i++){
            if(gb.getFill().get(i)!=null){
                char color=gb.getFill().get(i);
                int count=1;
                for(int j=i+1;j<i+4;j++){
                    if(color==gb.getFill().get(j) &&(j%8!=0||count==3))
                        count++;
                    else{
                        count=1;
                        break;
                    }
                }
                if(count==4){
                    lpos.add(i);lpos.add(i+1);lpos.add(i+2);lpos.add(i+3);
                    temp.setLockedpos(lpos);
                    sequenceVariablesService.updateSequenceVariables(temp);
                    for(PlayerData player:players){
                        if(player.getColor()==color)
                            return player;
                    }
                }

                for(int j=i+8;j<i+25;j=j+8){
                    if(color==gb.getFill().get(j) &&(j<41||count==3))
                        count++;
                    else{
                        count=1;
                        break;
                    }
                }
                if(count==4){
                    lpos.add(i);lpos.add(i+8);lpos.add(i+16);lpos.add(i+24);
                    temp.setLockedpos(lpos);
                    sequenceVariablesService.updateSequenceVariables(temp);
                    for(PlayerData player:players){
                        if(player.getColor()==color)
                            return player;
                    }
                }

                for(int j=i+9;j<i+28;j=j+9){
                    if(color==gb.getFill().get(j) &&(j<41||count==3))
                        count++;
                    else{
                        count=1;
                        break;
                    }
                }
                if(count==4){
                    lpos.add(i);lpos.add(i+9);lpos.add(i+18);lpos.add(i+27);
                    temp.setLockedpos(lpos);
                    sequenceVariablesService.updateSequenceVariables(temp);
                    for(PlayerData player:players){
                        if(player.getColor()==color)
                            return player;
                    }
                }

                for(int j=i+7;j<i+22;j=j+7){
                    if(color==gb.getFill().get(j) &&(j<41||count==3))
                        count++;
                    else{
                        count=1;
                        break;
                    }
                }
                if(count==4){
                    lpos.add(i);lpos.add(i+7);lpos.add(i+14);lpos.add(i+21);
                    temp.setLockedpos(lpos);
                    sequenceVariablesService.updateSequenceVariables(temp);
                    for(PlayerData player:players){
                        if(player.getColor()==color)
                            return player;
                    }
                }
            }
        }

        return null;
    }

}
