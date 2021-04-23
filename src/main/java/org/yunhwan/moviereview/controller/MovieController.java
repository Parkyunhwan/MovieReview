package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.service.MovieService;

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
}
