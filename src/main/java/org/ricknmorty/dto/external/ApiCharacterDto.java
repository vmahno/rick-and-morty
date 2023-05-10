package org.ricknmorty.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiCharacterDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
