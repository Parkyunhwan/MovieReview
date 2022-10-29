package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.MovieResponseDTO;
import org.yunhwan.moviereview.dto.MovieSearchRequestDTO;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.service.MovieService;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Long> create (
            @RequestBody MovieDTO movieDTO
    ) {
        log.info("[CREATE MOVIE] 영화 등록 요청 : movieDTO={}",movieDTO);
        return ResponseEntity.ok(movieService.createMovie(movieDTO));
    }

    @GetMapping
    public ResponseEntity<Page<MovieSearchResponseDTO>> findAll (
            MovieSearchRequestDTO movieSearchRequestDTO,
            @PageableDefault Pageable pageable
    ) {
        // 어떤 타입과 keyword로 검색이 되는지 로깅
        log.info("[SEARCH MOVIE LIST] : type:{}, keyword:{}", movieSearchRequestDTO.getType(), movieSearchRequestDTO.getKeyword());
        return ResponseEntity.ok(movieService.findAll(movieSearchRequestDTO, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieResponseDTO> findOne (
            @PathVariable long id
    ) {
        return ResponseEntity.ok(movieService.findOne(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update (
            @PathVariable("id") Long id,
            @RequestBody MovieDTO movieDTO
    ) {
        log.info("[UPDATE MOVIE] : movieId={}, updateMovieInfo={}", id, movieDTO);
        movieService.update(id, movieDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (
            @PathVariable("id") Long id
    ) {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }
}
