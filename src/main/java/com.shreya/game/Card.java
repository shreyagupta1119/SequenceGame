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
        if(suit!='s'&&suit!='h'&&suit!='c'&&suit!='d')
            throw new IllegalArgumentException("Illegal playing card suit");
        if(value<1 ||value>13)
            throw new IllegalArgumentException("Illegal playing card value");
        this.suit=suit;
        this.value=value;
    }

    public String toString(){
        String str;
        switch(value){
            case 1:   str= "Ace";break;
            case 2:   str= "2";break;
            case 3:   str= "3";break;
            case 4:   str= "4";break;
            case 5:   str= "5";break;
            case 6:   str= "6";break;
            case 7:   str= "7";break;
            case 8:   str= "8";break;
            case 9:   str= "9";break;
            case 10:  str= "10";break;
            case 11:  str= "Jack";break;
            case 12:  str= "Queen";break;
            case 13:  str= "King";break;
            default:  str="joker";
        }
        return str +" of "+suit;
    }

}
