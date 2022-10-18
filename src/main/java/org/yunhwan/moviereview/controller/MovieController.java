package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.MovieSearchRequestDTO;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.dto.PageRequestDTO;
import org.yunhwan.moviereview.service.MovieService;

@RestController
@RequestMapping("movie")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public Long register(@RequestBody MovieDTO movieDTO) {
        log.info("movieDTO: " + movieDTO);      // 넘어온 movieDTO
        return movieService.register(movieDTO);
    }

    @GetMapping
    public Page<MovieSearchResponseDTO> list(MovieSearchRequestDTO movieSearchRequestDTO, Pageable pageable) {
        return movieService.getList(movieSearchRequestDTO, pageable);
    }

    @GetMapping("{mno}")
    public MovieDTO readMovie(@PathVariable long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        log.info("mno: " + mno);
        return movieService.getMovie(mno);
    }

    @GetMapping("{mno}/modify")
    public String modifyPage(@PathVariable long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);

        return "movie/modify";
    }

    @PutMapping("/{mno}")
    public ResponseEntity<String> modifyMovie(
            @PathVariable("mno") Long mno, @RequestBody MovieDTO movieDTO) {
        log.info("modify mno " + mno);
        movieDTO.setMno(mno);
        movieService.modify(movieDTO);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<String> removeMovie(@PathVariable("mno") Long mno) {
        log.info("delete mno " + mno);
        movieService.removeWithReplies(mno);

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }
}
