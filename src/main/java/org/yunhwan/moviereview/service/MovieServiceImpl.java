package org.yunhwan.moviereview.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.MovieSearchRequestDTO;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.dto.MovieSearchVO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;
import org.yunhwan.moviereview.repository.MovieImageRepositroy;
import org.yunhwan.moviereview.repository.MovieRepository;
import org.yunhwan.moviereview.repository.ReviewRepository;


import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepositroy movieImageRepositroy;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public void removeWithReplies(Long mno) {
        // review, movie image 부터 삭제..
        Movie movie = movieRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화는 존재하지 않습니다."));
        reviewRepository.deleteByMovie(movie);
        movieImageRepositroy.deleteByMovie(movie);

        movieRepository.deleteById(mno);
    }

    /**
     * 영화와 영화관련 이미지를 저장 후 (영화 번호) 반환
     *
     * @param movieDTO
     * @return
     */
    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");

        //영화 등록
        movieRepository.save(movie);

        if (entityMap.containsKey("imgList")) {
            List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
            movieImageList.forEach(movieImage -> {
                movieImageRepositroy.save(movieImage);
            });
        }
        return movie.getMno();
    }

    @Override
    public Page<MovieSearchResponseDTO> getList(MovieSearchRequestDTO requestDTO,
            Pageable pageable) {
        Sort sort = pageable.getSort();
        sort.and(Sort.by("mno").descending());

        Page<MovieSearchVO> movieSearchVOS = movieRepository.searchPage(requestDTO.getType(),
                requestDTO.getKeyword(), pageable);
        List<MovieSearchResponseDTO> movieSearchResponseDTOS = movieSearchVOS.stream()
                .map((MovieSearchResponseDTO::new))
                .collect(Collectors.toList());

        return new PageImpl<>(movieSearchResponseDTOS, pageable, pageable.getPageSize());
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> movieWithAll = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) movieWithAll.get(0)[0]; // 어차피 다 똑같은 Movie row

        List<MovieImage> movieImageList = new ArrayList<>();

        movieWithAll.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) movieWithAll.get(0)[2];
        Long reviewCnt = (Long) movieWithAll.get(0)[3]; // 모든 Row가 같으므로 0번째 ROw에서 뽑으면 됨.

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt); // 여러 엔티티
    }

    @Transactional
    @Override
    public void modify(MovieDTO movieDTO) {
        log.info("영화 수정 정보 movieDTO : {}", movieDTO);
        Movie movie = movieRepository.findById(movieDTO.getMno())
                .orElseThrow(() -> new IllegalArgumentException("해당 영화는 존재하지 않습니다."));

        movie.changeTitle(movieDTO.getTitle(), movieDTO.getOpenDate(),
                movieDTO.getRunningTime(), movieDTO.getCountry(),
                movieDTO.getRating());
        // 엔티티매니저가 "변경 감지" 하기 때문에 따로 save 쿼리 X
    }
}
