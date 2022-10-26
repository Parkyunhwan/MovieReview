package org.yunhwan.moviereview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yunhwan.moviereview.dto.ReviewDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;
import org.yunhwan.moviereview.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public Long create(Long movieId, ReviewDTO reviewDTO) {
        Review review = dtoToEntity(movieId, reviewDTO);
        reviewRepository.save(review);
        return review.getId();
    }

    @Override
    public List<ReviewDTO> findAll(Long mno) {
        Movie movie = Movie.builder().id(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(Long movieId, ReviewDTO reviewDTO) {
        List<Review> existReviews = reviewRepository.findByMovie(Movie.builder().id(movieId).build());
        Review review = existReviews.stream()
                .filter(existReview -> existReview.isSameId(reviewDTO.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰번호입니다."));
        review.changeGrade(reviewDTO.getGrade());
        review.changeText(reviewDTO.getText());
    }

    @Override
    public void delete(Long movieId, Long id) {
        List<Review> existReviews = reviewRepository.findByMovie(Movie.builder().id(movieId).build());
        Review review = existReviews.stream()
                .filter(existReview -> existReview.isSameId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 영화에 해당 리뷰가 존재하지 않습니다."));
        reviewRepository.delete(review);
    }
}
