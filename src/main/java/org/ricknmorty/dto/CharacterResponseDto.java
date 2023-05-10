package org.ricknmorty.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ricknmorty.model.Gender;
import org.ricknmorty.model.Status;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
