package com.shreya.game;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by shreya on 29/11/16.
 */
@Data
@Document(collection="card")
public class Card {
    private char suit;
    private int value;

    public final static int ACE = 1;      // Codes for the non-numeric cards.
    public final static int JACK = 11;    //   Cards 2 through 10 have their
    public final static int QUEEN = 12;   //   numerical values for their codes.
    public final static int KING = 13;

    public Card(){
        suit='j';
        value=0;
    }

    public Card(char suit,int value){
        if(suit!='s'||suit!='h'||suit!='c'||suit!='d')
            throw new IllegalArgumentException("Illegal playing card suit");
        if(value<1 ||value>13)
            throw new IllegalArgumentException("Illegal playing card value");
        this.suit=suit;
        this.value=value;
    }

    public String toString(){
        String str;
        switch(value){
            case 1:   str= "Ace";
            case 2:   str= "2";
            case 3:   str= "3";
            case 4:   str= "4";
            case 5:   str= "5";
            case 6:   str= "6";
            case 7:   str= "7";
            case 8:   str= "8";
            case 9:   str= "9";
            case 10:  str= "10";
            case 11:  str= "Jack";
            case 12:  str= "Queen";
            case 13:  str= "King";
            default:  str="joker";
        }
        return str +" of "+suit;
    }

}
