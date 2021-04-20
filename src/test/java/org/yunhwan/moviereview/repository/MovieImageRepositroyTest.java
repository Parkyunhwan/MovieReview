package org.yunhwan.moviereview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieImageRepositroyTest {

    @Autowired
    private MovieImageRepositroy movieImageRepositroy;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void insertMovies() throws Exception {
        //given
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder().title("Movie....." + i).build();

            System.out.println("--------Movie insert---------------");

            movieRepository.save(movie);

            // 특정 영화에만 많은 수의 이미지가 들어갈 수 도 있다.
            int count = (int)(Math.random() * 5) + 1; // 1 ~ 4 랜덤 갯수의 사진

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())  // uuid 값 랜덤 할당
                        .movie(movie) // movie와 연결 시켜줌 (movie를 미리 생성한 이유)
                        .imgName("test" + j + ".jpg").build();

                movieImageRepositroy.save(movieImage);
            }
            System.out.println("===============End insertMovies===============");
        });
        //when

        //then
    }


}