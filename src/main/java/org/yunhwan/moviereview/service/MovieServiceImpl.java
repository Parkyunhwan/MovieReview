package org.yunhwan.moviereview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.PageRequestDTO;
import org.yunhwan.moviereview.dto.PageResultDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;
import org.yunhwan.moviereview.repository.MovieImageRepositroy;
import org.yunhwan.moviereview.repository.MovieRepository;
import org.yunhwan.moviereview.repository.ReviewRepository;


import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepositroy movieImageRepositroy;
    private final ReviewRepository reviewRepository;
    @Override
    public void removeWithReplies(Long mno) {

        // review, movie image 부터 삭제..
        reviewRepository.deleteByMovie_Mno(mno);
        movieImageRepositroy.deleteByMovie_Mno(mno);

        // movie 삭제..
        movieRepository.deleteById(mno);
    }

    /**
     * 영화와 영화관련 이미지를 저장 후 (영화 번호) 반환
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
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {

        //넘겨줄 페이지 정보 제작 (파라미터 -> 소트 정보)
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        // 여러 엔티티가 섞여있는 데이터이므로 Object[]로 받도록 함.
        Page<Object[]> result = movieRepository.getListPage(pageable);

        // <파라미터, 리턴값>
        Function<Object[], MovieDTO> fn = (arr -> {
            if (arr[1] != null) {
                return entitiesToDTO(
                        (Movie) arr[0],
                        (List<MovieImage>) (Arrays.asList((MovieImage) arr[1])),
                        (Double) arr[2],
                        (Long) arr[3]);
            } else {
                return entitiesToDTO(
                        (Movie) arr[0],
                        Collections.emptyList(),
                        (Double) arr[2],
                        (Long) arr[3]);
            }
        }
        );

        // 데이터 + 적용할 함수.
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> movieWithAll = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) movieWithAll.get(0)[0]; // 어차피 다 똑같은 Movie row

        List<MovieImage> movieImageList = new ArrayList<>();

        movieWithAll.forEach(arr ->{
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
        Movie movie = movieRepository.getOne(movieDTO.getMno());
        log.info(movieDTO);
        movie.changeTitle(movieDTO.getTitle(), movieDTO.getOpenDate(), movieDTO.getRunningTime()
                , movieDTO.getCountry(), movieDTO.getRating());
        // 엔티티매니저가 "변경 감지" 하기 때문에 따로 save 쿼리 안날렷음.
    }
}
