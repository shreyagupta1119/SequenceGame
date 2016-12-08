package com.shreya.variables;

import com.shreya.game.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shreya on 8/12/16.
 */
@Service
public class SequenceVariablesService {

    @Autowired
    private SequenceVariablesRepository sequenceVariablesRepository;

    public SequenceVariablesService(){}

    public List<Card> showDeck(int match_id){
        return sequenceVariablesRepository.showDeck(match_id);
    }

    public SequenceVariables createSequenceVariables(int match_id){
        return sequenceVariablesRepository.createSequenceVariables(match_id);
    }

    public void updateSequenceVariables(SequenceVariables sv){
        sequenceVariablesRepository.updateSequenceVariables(sv);
    }

    public List<SequenceVariables> getAllSequenceVariables(){
        return sequenceVariablesRepository.getAllSequenceVariables();
    }

    public SequenceVariables getSequenceVariableByMatchId(int match_id){
        return sequenceVariablesRepository.getSequenceVariableByMatchId(match_id);
    }

}
