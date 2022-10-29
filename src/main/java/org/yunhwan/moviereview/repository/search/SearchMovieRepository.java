package org.yunhwan.moviereview.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;

public interface SearchMovieRepository {
    Page<MovieSearchResponseDTO> searchPageBy(String type, String keyword, Pageable pageable);
}
