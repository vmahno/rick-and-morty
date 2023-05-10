package org.ricknmorty.service;


import org.ricknmorty.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
