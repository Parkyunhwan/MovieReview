package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.PageRequestDTO;
import org.yunhwan.moviereview.dto.PageResultDTO;
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

    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageReqestDTO: " + pageRequestDTO);
        PageResultDTO<MovieDTO, Object[]> list = movieService.getList(pageRequestDTO);
        model.addAttribute("result", list);
    }

    @GetMapping("{mno}")
    public String readMovie(@PathVariable long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);

        return "movie/read";
    }

    @GetMapping("{mno}/modify")
    public String modifyPage(@PathVariable long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);

        return "movie/modify";
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<String> removeMovie(@PathVariable("mno") Long mno) {
        log.info("delete mno " + mno);
        movieService.removeWithReplies(mno);

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @PutMapping("/{mno}")
    @ResponseBody
    public ResponseEntity<String> modifyMovie(@PathVariable("mno") Long mno, @RequestBody MovieDTO movieDTO) {
        log.info("modify mno " + mno);
        movieService.modify(movieDTO);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
