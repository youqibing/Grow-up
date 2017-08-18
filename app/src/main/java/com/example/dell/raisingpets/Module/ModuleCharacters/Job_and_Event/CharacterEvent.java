package com.example.dell.raisingpets.Module.ModuleCharacters.Job_and_Event;

import com.example.dell.raisingpets.NetWork.Result.CharactersResult;

import java.util.List;

/**
 * Created by root on 16-11-17.
 */

public class CharacterEvent {
    public List<CharactersResult.CharacterList> characterLists;

    public CharacterEvent(List<CharactersResult.CharacterList> characterLists){
        this.characterLists = characterLists;

    }
}
