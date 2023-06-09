package org.ricknmorty.service;

import lombok.extern.log4j.Log4j2;
import org.ricknmorty.dto.external.ApiCharacterDto;
import org.ricknmorty.dto.external.ApiResponseDto;
import org.ricknmorty.dto.mapper.MovieCharacterMapper;
import org.ricknmorty.model.MovieCharacter;
import org.ricknmorty.repository.MovieCharacterRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private static final String API_URL = "https://rickandmortyapi.com/api/character";
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper mapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository,
                                     MovieCharacterMapper mapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 7 * * * ?")
    private void syncExternalCharacters() {
        log.info("syncExternalCharacters method was invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get(API_URL,
                ApiResponseDto.class);
        saveDtoToDB(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtoToDB(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count) + 1;
        return movieCharacterRepository.findById(randomId).orElseThrow(() ->
                 new RuntimeException("Can't get Movie Character by id " + randomId));
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    List<MovieCharacter> saveDtoToDB(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters =
                movieCharacterRepository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> mapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .collect(Collectors.toList());

        return movieCharacterRepository.saveAll(charactersToSave);
    }
}
