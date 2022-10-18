package org.yunhwan.moviereview.dto;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

@Getter
public class MovieSearchVO {
    private Movie movie;
    private List<MovieImage> movieImages;
    private double avg;
    private long reviewCnt;
}
