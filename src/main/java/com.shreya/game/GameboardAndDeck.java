package com.shreya.game;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Data
@Document(collection = "gameboardAndDeck")
public class GameboardAndDeck {
    GameBoard gb;
    Deck deck;

    public GameboardAndDeck(){
        this.gb=new GameBoard();
        this.deck=new Deck();
    }

    public GameboardAndDeck(GameBoard gb, Deck deck){
        this.gb=gb;
        this.deck=deck;
    }

    @Override
    public String toString(){
        return " Gameboard and Deck states: [ Gameboard: "+ gb.toString()+", Deck: "+deck.toString();
    }
}