package org.yunhwan.moviereview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSearchRequestDTO {
    private String type;
    private String keyword;
}
