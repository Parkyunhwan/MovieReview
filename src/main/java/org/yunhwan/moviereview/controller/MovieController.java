package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.PageRequestDTO;
import org.yunhwan.moviereview.dto.PageResultDTO;
import org.yunhwan.moviereview.service.MovieService;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO: " + movieDTO); // 넘어온 movieDTO

        Long mno = movieService.register(movieDTO);

        redirectAttributes.addFlashAttribute("msg", mno); // 저장후 생성된 mno를 특성에 넣어서 반환

        return "redirect:/movie/list"; // 등록 후 영화 목록 페이지로 리다이렉트
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageReqestDTO: " + pageRequestDTO);
        PageResultDTO<MovieDTO, Object[]> list = movieService.getList(pageRequestDTO);
        model.addAttribute("result", list);
    }

    @GetMapping("/{mno}")
    public String readMovie(@PathVariable long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);

        return "/movie/read";
    }

    @GetMapping("/{mno}/modify")
    public String modifyPage(@PathVariable long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mno: " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);

        return "/movie/modify";
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<String> removeMovie(@PathVariable("mno") Long mno) {
        log.info("delete mno " + mno);
        movieService.removeWithReplies(mno);

        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @PutMapping("/{mno}")
    public RedirectView modifyMovie(@PathVariable("mno") Long mno, MovieDTO movieDTO) {
        log.info("modify mno " + mno);
        movieService.modify(movieDTO);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/movie/" + mno);
        return redirectView;
    }
}
