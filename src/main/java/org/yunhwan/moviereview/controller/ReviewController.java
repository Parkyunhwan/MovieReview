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
@RequestMapping("/movies")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<List<ReviewDTO>> findAll (
            @PathVariable("movieId") Long movieId
    ) {
        log.info("[FIND ALL REVIEWS] movieId={}", movieId);
        return ResponseEntity.ok(reviewService.findAll(movieId));
    }

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<Long> create (
            @PathVariable("movieId") Long movieId,
            @RequestBody ReviewDTO reviewDTO
    ) {
        log.info("[CREATE REVIEW] movieId={}, reviewDTO={}", movieId, reviewDTO);
        reviewService.create(movieId, reviewDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{movieId}/reviews/{reviewnum}")
    public ResponseEntity<Void> update (
            @PathVariable("movieId") Long movieId,
            @PathVariable Long reviewnum,
            @RequestBody ReviewDTO reviewDTO
    ) {
        log.info("[UPDATE REVIEW] movieId={}, reviewDTO={}", movieId, reviewDTO);
        reviewService.update(movieId, reviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}/{reviewnum}")
    public ResponseEntity<Void> delete (
            @PathVariable Long reviewnum
    ) {
        log.info("------------delete review ---- ReviewNum : " + reviewnum);
        reviewService.delete(reviewnum);
        return ResponseEntity.ok().build();
    }
}
