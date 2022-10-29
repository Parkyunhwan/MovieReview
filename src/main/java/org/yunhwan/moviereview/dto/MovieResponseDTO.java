package org.yunhwan.moviereview.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.yunhwan.moviereview.entity.Movie;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MovieResponseDTO {

    private Long id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    private String rating;

    private Long runningTime;

    private String country;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    @Builder.Default
    private List<MovieImageDTO> imageDTOS = new ArrayList<>();

    private double avg;

    private Long reviewCnt;

    public MovieResponseDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.openDate = movie.getOpenDate();
        this.rating = movie.getRating();
        this.runningTime = movie.getRunningTime();
        this.country = movie.getCountry();
    }

    public MovieResponseDTO(
            Movie movie,
            double avg,
            long reviewCnt
    ) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.regDate = movie.getRegDate();
        this.modDate = movie.getModDate();
        this.openDate = movie.getOpenDate();
        this.rating = movie.getRating();
        this.runningTime = movie.getRunningTime();
        this.country = movie.getCountry();
        this.avg = avg;
        this.reviewCnt = reviewCnt;
    }

    public void setImageDTOS(List<MovieImageDTO> imageDTOS) {
        this.imageDTOS = imageDTOS;
    }
}
