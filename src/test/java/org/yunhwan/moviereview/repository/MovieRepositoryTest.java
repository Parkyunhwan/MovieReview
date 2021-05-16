package org.yunhwan.moviereview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void 영화_리뷰_조인을_통해_영화평점평균리뷰갯수_조회() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")); // size 10 이므로 10개 조회
        //when
        Page<Object[]> result = movieRepository.getListPage(pageRequest);
        //then
        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void 영화의_모든_정보를_제대로_가져오는지() throws Exception {
        //given
        List<Object[]> result = movieRepository.getMovieWithAll(73L);
        //when
        System.out.println(result);
        //then
        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void Movie와SearchBoardRepository간연결테스트() throws Exception {
        movieRepository.Search1();
    }
}