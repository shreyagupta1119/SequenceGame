package com.shreya.game;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by shreya on 30/11/16.
 */
@Data
@Document(collection="deck")
public class Deck {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (int i = 1; i <= 13; i++) {
            cards.add(new Card('s', i));
            cards.add(new Card('h', i));
            cards.add(new Card('d', i));
            cards.add(new Card('c', i));
        }
        deckShuffle();
    }

     public void deckShuffle(){
        Collections.shuffle(cards);
     }

     public Card drawFromDeck(){
         Iterator<Card> itr=cards.iterator();
         if(itr.hasNext())
             return cards.remove(0);
         else
             return null;
     }

     public int getTotalCards(){
      return cards.size();
     }

     @Override
     public String toString(){
         String str="";
         for(Card c:cards)
             str+=c.toString();
         return str;
     }
}



