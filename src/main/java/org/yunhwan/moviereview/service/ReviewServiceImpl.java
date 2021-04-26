package org.yunhwan.moviereview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yunhwan.moviereview.dto.ReviewDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;
import org.yunhwan.moviereview.repository.MemberRepository;
import org.yunhwan.moviereview.repository.MovieRepository;
import org.yunhwan.moviereview.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;


    @Override
    public Long register(ReviewDTO reviewDTO) {
        log.info("Review register-----------------!! " + reviewDTO);
        Review review = dtoToEntity(reviewDTO);
        log.info(review);
        reviewRepository.save(review);

        return review.getReviewnum();
    }

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        log.info("Review List Read-----------------!! ");

        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void modify(ReviewDTO reviewDTO) {
        log.info("Review Modify---------------!!");

        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());

        if (result.isPresent()) {
            Review review = result.get();
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());
        }
    }

    @Override
    public void remove(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);
    }
}
