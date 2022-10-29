package org.yunhwan.moviereview.service;


import org.yunhwan.moviereview.dto.ReviewDTO;
import org.yunhwan.moviereview.entity.Member;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;

import java.util.List;

public interface ReviewService {
    //C
    Long create(Long movieId, ReviewDTO reviewDTO);

    //R
    List<ReviewDTO> findAll(Long mno);

    //U
    void update(Long movieId, Long reviewnum, ReviewDTO reviewDTO);

    //D
    void delete(Long movieId, Long reviewNum);

    default Review dtoToEntity(long movieId, ReviewDTO reviewDTO) {
        return Review.builder()
                .id(reviewDTO.getId())
                .text(reviewDTO.getText())
                .grade(reviewDTO.getGrade())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .movie(Movie.builder().id(movieId).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();
    }

    default ReviewDTO entityToDto(Review movieReview){
        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .id(movieReview.getId())
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
