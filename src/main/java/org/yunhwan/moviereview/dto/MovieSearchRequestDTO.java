package org.yunhwan.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MovieSearchRequestDTO {
    private String type;
    private String keyword;
}
