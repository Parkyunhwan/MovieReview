package org.yunhwan.moviereview.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.yunhwan.moviereview.entity.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MovieDTO {

    private Long id;

    private String title;

    // 빌더에서 따로 설정하지 않으면 null or 0이 나오게 함.
    @Builder.Default
    private List<MovieImageDTO> imageDTOS = new ArrayList<>();

    // 영화의 평균 평점
    private double avg;

    private long reviewCnt;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    private String rating;

    private Long runningTime;

    private String country;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.regDate = movie.getRegDate();
        this.modDate = movie.getModDate();
        this.openDate = movie.getOpenDate();
        this.rating = movie.getRating();
        this.runningTime = movie.getRunningTime();
        this.country = movie.getCountry();
    }
}
