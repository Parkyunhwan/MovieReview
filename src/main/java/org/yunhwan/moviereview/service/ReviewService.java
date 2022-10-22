package org.yunhwan.moviereview.service;


import org.yunhwan.moviereview.dto.ReviewDTO;
import org.yunhwan.moviereview.entity.Member;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;

import java.util.List;

public interface ReviewService {
    //C
    Long createReview(ReviewDTO reviewDTO);

    //R
    List<ReviewDTO> findAllReviews(Long mno);

    //U
    void updateReview(ReviewDTO reviewDTO);

    //D
    void deleteReview(Long reviewNum);

    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .text(reviewDTO.getText())
                .grade(reviewDTO.getGrade())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .movie(Movie.builder().id(reviewDTO.getMovieId()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();

        return review;
    }

    default ReviewDTO entityToDto(Review movieReview){
        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .movieId(movieReview.getMovie().getId())
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
