package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhwan.moviereview.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
