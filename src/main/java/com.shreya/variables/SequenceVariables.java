package com.shreya.variables;

import com.shreya.game.Deck;
import com.shreya.game.GameBoard;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by shreya on 8/12/16.
 */
@Data
@Document(collection = "sequenceVariables")
public class SequenceVariables {
    @Id
    private int match_id;
    private GameBoard gb;
    private Deck deck;
    private ArrayList<Integer> lockedpos;
    private int counter;


    public SequenceVariables(){
    }

    public SequenceVariables(int match_id){
        this.match_id=match_id;
        this.gb=new GameBoard();
        this.deck=new Deck();
        lockedpos=new ArrayList<Integer>();
        counter=0;
     }

    public SequenceVariables(int match_id, GameBoard gb,Deck deck,ArrayList<Integer> lockedpos,int counter){
        this.match_id=match_id;
        this.gb=gb;
        this.deck=deck;
        this.lockedpos=lockedpos;
        this.counter=counter;
    }

    public static Comparator<SequenceVariables> match_idComparator = new Comparator<SequenceVariables>() {
        @Override
        public int compare(SequenceVariables sv1, SequenceVariables sv2) {
            return (sv2.getMatch_id() < sv1.getMatch_id() ? -1 :
                    (sv2.getMatch_id() == sv1.getMatch_id() ? 0 : 1));
        }
    };
}
