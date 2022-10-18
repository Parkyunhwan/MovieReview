package org.yunhwan.moviereview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSearchResponseDTO {

    private MovieDTO movieDTO;
    private MovieImageDTO movieImageDTO;
    private double avg;
    private long cnt;

    public MovieSearchResponseDTO(MovieSearchVO movieSearchVO) {
        this.movieDTO = new MovieDTO(movieSearchVO.getMovie());
        this.avg = movieSearchVO.getAvg();
        this.cnt = movieSearchVO.getReviewCnt();
    }
}
