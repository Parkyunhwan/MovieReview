package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.MovieSearchRequestDTO;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.service.MovieService;

@RestController
@RequestMapping("movie")
@RequiredArgsConstructor
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Long> createMovie (
            @RequestBody MovieDTO movieDTO
    ) {
        log.info("movieDTO: " + movieDTO);
        return ResponseEntity.ok(movieService.createMovie(movieDTO));
    }

    @GetMapping
    public ResponseEntity<Page<MovieSearchResponseDTO>> findAllMovies (
            MovieSearchRequestDTO movieSearchRequestDTO,
            Pageable pageable
    ) {
        // 어떤 타입과 keyword로 검색이 되는지 로깅
        log.info("MovieList Read Event : type:{}, keyword:{}", movieSearchRequestDTO.getType(), movieSearchRequestDTO.getKeyword());
        return ResponseEntity.ok(movieService.findAllMovies(movieSearchRequestDTO, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> findMovie (
            @PathVariable long id
    ) {
        log.info("Movie Read Event : movieNo:{}", id);
        return ResponseEntity.ok(movieService.findMovie(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMovie (
            @PathVariable("id") Long id,
            @RequestBody MovieDTO movieDTO
    ) {
        log.info("Movie Update Event: movieId : {}", id);
        movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie (
            @PathVariable("id") Long id
    ) {
        log.info("Movie Delete Event: movieId : {}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
