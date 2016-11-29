package com.shreya;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by shreya on 25/11/16.
 */
@Data
@Document(collection="player")
public class Player {

    @Id
    private String contactNumber;
    private String name;
    private String password;
    private int numberOfWins;
    private int numberOfLose;
    private int numberOfDraw;
    private Boolean online;
    private List<String> friends;

    public Player(){}

    public Player(String contactNumber, String name, String password){
        this.contactNumber=contactNumber;
        this.name=name;
        this.password=password;
        this.numberOfWins=0;
        this.numberOfLose=0;
        this.numberOfDraw=0;
        this.online=true;
        this.friends=null;
    }

    @Override
    public String toString(){
        return  "Player [ contactnumber=" + contactNumber + ", name =" + name + ", password=" + password +", numberOfWins=" +
                numberOfWins + ", numberOflose="+ numberOfLose +", numberOfdraws=" +numberOfDraw +", total match played=" +
                numberOfWins+numberOfLose+numberOfDraw + ", Player online status=" + online+ ", friends="+ friends;
    }
}
