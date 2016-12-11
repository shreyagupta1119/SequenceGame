package com.shreya.game;

import lombok.Data;

/**
 * Created by shreya on 11/12/16.
 */
@Data
public class MoveRequest {
    private int match_id;
    private String number;
    private Card c;
    private int pos;

    public MoveRequest(){}

    public MoveRequest(int match_id,String number,Card c,int pos){
        this.match_id=match_id;
        this.number=number;
        this.c=c;
        this.pos=pos;
    }
}
