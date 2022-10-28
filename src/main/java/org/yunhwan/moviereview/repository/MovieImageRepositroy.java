package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yunhwan.moviereview.dto.MovieImageDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

import java.util.List;

public interface MovieImageRepositroy extends JpaRepository<MovieImage, Long> {

    @Modifying
    @Query("delete from MovieImage mi where mi.movie=:movie")
    void deleteByMovie(Movie movie);

    @Query("select mi from MovieImage mi " +
            "where mi.movie.id = :movieId")
    List<MovieImageDTO> findAllMovieImageByMovieId(@Param("movieId") Long movieId);

}
