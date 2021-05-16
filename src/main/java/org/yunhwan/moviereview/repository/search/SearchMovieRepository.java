package org.yunhwan.moviereview.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yunhwan.moviereview.entity.Movie;

public interface SearchMovieRepository {
    Movie Search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
