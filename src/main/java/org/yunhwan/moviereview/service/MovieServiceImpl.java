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


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepositroy movieImageRepositroy;

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

        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        //영화 등록
        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            movieImageRepositroy.save(movieImage);
        });

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {

        //넘겨줄 페이지 정보 제작 (파라미터 -> 소트 정보)
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        // 여러 엔티티가 섞여있는 데이터이므로 Object[]로 받도록 함.
        Page<Object[]> result = movieRepository.getListPage(pageable);

        // <파라미터, 리턴값>
        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie)arr[0],
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long) arr[3]
        ));

        // 데이터 + 적용할 함수.
        return new PageResultDTO<>(result, fn);
    }
}
