package com.shreya.variables;

import com.shreya.game.GameboardAndDeck;
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
    private GameboardAndDeck gad;
    private ArrayList<Integer> lockedpos;
    private int counter;


    public SequenceVariables(int match_id){
        this.match_id=match_id;
        this.gad=new GameboardAndDeck();
        lockedpos=null;
        counter=0;
     }

    public SequenceVariables(int match_id, GameboardAndDeck gad,ArrayList<Integer> lockedpos,int counter){
        this.match_id=match_id;
        this.gad=gad;
        this.lockedpos=lockedpos;
        this.counter=counter;
    }

    @Override
    public String toString(){
        return "SequenceVariables: [match_id: "+match_id+", GameboardandDeck: "+ gad.toString()+", lockedpos: "+lockedpos+
                ", counter: "+counter+" ]";
    }

    public static Comparator<SequenceVariables> match_idComparator = new Comparator<SequenceVariables>() {
        @Override
        public int compare(SequenceVariables sv1, SequenceVariables sv2) {
            return (sv2.getMatch_id() < sv1.getMatch_id() ? -1 :
                    (sv2.getMatch_id() == sv1.getMatch_id() ? 0 : 1));
        }
    };
}
