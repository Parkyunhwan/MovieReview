package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yunhwan.moviereview.dto.MovieResponseDTO;
import org.yunhwan.moviereview.entity.Movie;

import java.util.Optional;

public interface MovieQueryRepository extends JpaRepository<Movie, Long> {
    @Query("select distinct new org.yunhwan.moviereview.dto.MovieResponseDTO(m, avg(coalesce(r.grade, 0)), count(r))"
            + "from Movie m "
            + "left join fetch Review r on r.movie = m "
            + "group by r")
    Optional<MovieResponseDTO> findMovieWithReviewAvgCnt(@Param("id") Long id);
}
