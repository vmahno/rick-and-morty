package org.ricknmorty.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiInfoDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
