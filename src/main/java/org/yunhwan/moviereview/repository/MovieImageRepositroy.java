package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

public interface MovieImageRepositroy extends JpaRepository<MovieImage, Long> {

    @Modifying
    @Query("delete from MovieImage mi where mi.movie=:movie")
    void deleteByMovie(Movie movie);
}
