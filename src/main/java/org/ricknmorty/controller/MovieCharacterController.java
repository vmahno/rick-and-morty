package org.ricknmorty.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ricknmorty.dto.CharacterResponseDto;
import org.ricknmorty.dto.mapper.MovieCharacterMapper;
import org.ricknmorty.model.MovieCharacter;
import org.ricknmorty.service.MovieCharacterService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService characterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService characterService,
                                    MovieCharacterMapper mapper) {
        this.characterService = characterService;
        this.mapper = mapper;
    }

    @GetMapping("/random")
    @ApiOperation(value = "get random character")
    public Object getRandom() {
        MovieCharacter randomCharacter = characterService.getRandomCharacter();
        return mapper.toResponseDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "get any character by name or it's part")
    public List<CharacterResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return characterService.findAllByNameContains(namePart).stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
