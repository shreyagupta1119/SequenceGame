package com.shreya.player;

import java.util.ArrayList;

import com.shreya.game.Card;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by shreya on 25/11/16.
 */
@Data
@Document(collection="player")
@CompoundIndexes({
@CompoundIndex(name = "number_matchid", def = "{'contactNumber': 1, 'match_id': 1}",unique = true)})

public class PlayerData {
    @Indexed
    private String contactNumber;
    @Indexed
    private int match_id;
    private ArrayList<Card> handCards;
    private char color;


    public PlayerData(){}

    public PlayerData(String contactNumber,int match_id,char color){
        this.contactNumber=contactNumber;
        this.handCards=null;
        this.match_id=match_id;
        this.color=color;
    }

    public void addCard(Card c){
        handCards.add(c);
    }

    public void removeCard(Card c){
        if(c==null)
            return;
        else {
            for (int i = 0; i < handCards.size(); i++) {
                if (handCards.get(i).getSuit() == c.getSuit() && handCards.get(i).getValue() == c.getValue()) ;
                {
                    handCards.remove(i);
                    return;
                }
            }
        }
    }

    @Override
    public String toString(){
        return  "player [ contactnumber= " + contactNumber +"handCards= "+ handCards + " Match_Id= "+ match_id+
                "color= "+color+" ]";
    }
}
