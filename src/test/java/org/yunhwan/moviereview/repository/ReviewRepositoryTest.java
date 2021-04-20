package org.yunhwan.moviereview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yunhwan.moviereview.entity.Member;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReviews() throws Exception {
        //given
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long)(Math.random() * 100) + 1; //영화

            Long mid = (long)(Math.random() * 100) + 1; //멤버

            Review review = Review.builder()
                    .movie(Movie.builder().mno(mno).build())
                    .member(Member.builder().mid(mid).build())
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 나의 평가는...?" + i + "번째 리뷰")
                    .build();

            reviewRepository.save(review);
        });
        //when

        //then
    }
}