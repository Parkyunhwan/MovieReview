package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yunhwan.moviereview.dto.ReviewDTO;
import org.yunhwan.moviereview.service.ReviewService;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}")
    public ResponseEntity<List<ReviewDTO>> getReviewList(
            @PathVariable("mno") Long mno
    ) {
        log.info("getReviewList Controller ---------------------------- MNO : " + mno);

        List<ReviewDTO> listOfMovie = reviewService.getListOfMovie(mno);

        return new ResponseEntity<>(listOfMovie, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(
            @RequestBody ReviewDTO reviewDTO
    ) {
        log.info("addReview Controller -------- ReviewDTO: " + reviewDTO);

        Long regNum = reviewService.register(reviewDTO);

        // PRG패턴을 컨트롤러 단에서 만들려 했으나 ajax 요청 시 응답 처리해줘야하므로 응답 처리에서 redirect처리 해줘야 함
        //return "redirect:/movie/" + reviewDTO.getMno();
        return new ResponseEntity<>(regNum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(
            @PathVariable Long reviewnum,
            @RequestBody ReviewDTO reviewDTO
    ) {
        log.info("ModifyReview Controller -------- ReviewDTO: " + reviewDTO);

        reviewService.modify(reviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(
            @PathVariable Long reviewnum
    ) {
        log.info("------------delete review ---- ReviewNum : " + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
