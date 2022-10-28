package org.yunhwan.moviereview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.MovieImageDTO;
import org.yunhwan.moviereview.dto.MovieResponseDTO;
import org.yunhwan.moviereview.dto.MovieSearchRequestDTO;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    // 댓글과 함께 영화 삭제
    void delete(Long mno);

    Long createMovie(MovieDTO movieDTO);

    Page<MovieSearchResponseDTO> findAll(MovieSearchRequestDTO movieSearchRequestDTO, Pageable pageable);

    MovieResponseDTO findOne(Long mno);

    void update(Long mno, MovieDTO movieDTO);
}
