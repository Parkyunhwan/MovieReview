package org.yunhwan.moviereview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yunhwan.moviereview.dto.*;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;
import org.yunhwan.moviereview.repository.MovieImageRepositroy;
import org.yunhwan.moviereview.repository.MovieQueryRepository;
import org.yunhwan.moviereview.repository.MovieRepository;
import org.yunhwan.moviereview.repository.ReviewRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepositroy movieImageRepositroy;
    private final ReviewRepository reviewRepository;
    private final MovieQueryRepository movieQueryRepository;

    @Transactional
    @Override
    public void delete(Long id) {
        // review, movie image 부터 삭제..
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화는 존재하지 않습니다."));
        reviewRepository.deleteByMovie(movie);
        movieImageRepositroy.deleteByMovie(movie);

        movieRepository.deleteById(id);
        log.info("[DELETE MOVIE] : movieId={}", id);
    }

    /**
     * 영화와 영화관련 이미지를 저장 후 (영화 번호) 반환
     *
     * @param movieDTO
     * @return
     */
    @Transactional
    @Override
    public Long createMovie(MovieDTO movieDTO) {
        Movie movie = Movie.builder()
                .id(movieDTO.getId())
                .title(movieDTO.getTitle())
                .country(movieDTO.getCountry())
                .openDate(movieDTO.getOpenDate())
                .runningTime(movieDTO.getRunningTime())
                .rating(movieDTO.getRating())
                .build();
        movieRepository.save(movie);
        log.info("[CREATE MOVIE] 영화 등록 완료 : movieDTO={}", movieDTO);

        List<MovieImage> movieImages = Optional.of(movieDTO.getImageDTOS())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(movieImageDTO -> MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build()
                ).collect(Collectors.toList());
        if (!movieImages.isEmpty()) {
            movieImageRepositroy.saveAll(movieImages);
            log.info("[CREATE MOVIE_IMAGES] 영화 이미지 생성 완료 movieImages={}", movieImages);
        }

        return movie.getId();
    }

    @Override
    public Page<MovieSearchResponseDTO> findAll(MovieSearchRequestDTO requestDTO, Pageable pageable) {
        return movieRepository.searchPageBy(requestDTO.getType(), requestDTO.getKeyword(), pageable);
    }

    @Override
    public MovieResponseDTO findOne(Long id) {
        MovieResponseDTO movieResponseDTO = movieQueryRepository.findMovieWithReviewAvgCnt(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
        List<MovieImageDTO> movieImageDTOS = movieImageRepositroy.findAllMovieImageByMovieId(id);
        movieResponseDTO.setImageDTOS(movieImageDTOS);
        return movieResponseDTO; // 여러 엔티티
    }

    @Transactional
    @Override
    public void update(Long mno, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화는 존재하지 않습니다."));

        movie.changeTitle(
                movieDTO.getTitle(),
                movieDTO.getOpenDate(),
                movieDTO.getRunningTime(),
                movieDTO.getCountry(),
                movieDTO.getRating()
        );
    }
}
