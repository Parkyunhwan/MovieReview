package org.yunhwan.moviereview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<List<ReviewDTO>> findAllReviews (
            @PathVariable("mno") Long mno
    ) {
        log.info("getReviewList Controller ---------------------------- MNO : " + mno);
        return ResponseEntity.ok(reviewService.findAllReviews(mno));
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> createReview (
            @RequestBody ReviewDTO reviewDTO
    ) {
        log.info("addReview Controller -------- ReviewDTO: " + reviewDTO);
        reviewService.createReview(reviewDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Void> updateReview (
            @PathVariable Long reviewnum,
            @RequestBody ReviewDTO reviewDTO
    ) {
        log.info("ModifyReview Controller -------- ReviewDTO: " + reviewDTO);
        reviewService.updateReview(reviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Void> deleteReview (
            @PathVariable Long reviewnum
    ) {
        log.info("------------delete review ---- ReviewNum : " + reviewnum);
        reviewService.deleteReview(reviewnum);
        return ResponseEntity.ok().build();
    }
}
