package org.ricknmorty.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterDto[] results;
}
