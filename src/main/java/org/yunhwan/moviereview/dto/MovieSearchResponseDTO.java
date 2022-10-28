package org.yunhwan.moviereview.dto;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

@Getter
@Setter
public class MovieSearchResponseDTO {

    private MovieResponseDTO movieResponseDTO;
    private MovieImageDTO movieImageDTO;
    private double avg;
    private long cnt;

    public MovieSearchResponseDTO(Movie movie, MovieImage movieImage, double avg, long cnt) {
        this.movieResponseDTO = new MovieResponseDTO(movie);
        Optional.ofNullable(movieImage)
                .ifPresent((image) ->
                        this.movieImageDTO = new MovieImageDTO(image.getUuid(), image.getImgName(), image.getPath())
                );
        this.avg = avg;
        this.cnt = cnt;
    }
}
