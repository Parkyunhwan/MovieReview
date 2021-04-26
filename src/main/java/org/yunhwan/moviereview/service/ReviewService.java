package org.yunhwan.moviereview.service;


import org.yunhwan.moviereview.dto.ReviewDTO;
import org.yunhwan.moviereview.entity.Member;
import org.yunhwan.moviereview.entity.Review;

import java.util.List;

public interface ReviewService {
    //C
    Long register(ReviewDTO reviewDTO);

    //R
    List<ReviewDTO> getListOfMovie(Long mno);

    //U
    void modify(ReviewDTO reviewDTO);

    //D
    void remove(Long reviewNum);

    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .text(reviewDTO.getText())
                .grade(reviewDTO.getGrade())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();

        return review;
    }

    default ReviewDTO entityToDto(Review movieReview){
        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;
    }
}
