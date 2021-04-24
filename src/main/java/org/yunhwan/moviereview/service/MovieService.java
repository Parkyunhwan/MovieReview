package org.yunhwan.moviereview.service;

import org.yunhwan.moviereview.dto.MovieDTO;
import org.yunhwan.moviereview.dto.MovieImageDTO;
import org.yunhwan.moviereview.dto.PageRequestDTO;
import org.yunhwan.moviereview.dto.PageResultDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    /**
     * Movie, MovieImage 리스트, 평균, 리뷰갯수를 조회해서 DTO값에 넣어주는 작업.
     * @param movie
     * @param movieImages
     * @param avg
     * @param reviewCnt
     * @return
     */
    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getRegDate())
                .build();

        // List<MovieImage>를 stream map을 통해 List<MovieImageDTO>로 변환..
        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);

        // 화면 출력을 위해 DTO에 데이터를 넣어줌
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

    /**
     * Movie객체와 MovieImage객체가 함께 처리되므로 Map타입으로 두 객체를 반환해야한다.
     * @param movieDTO
     * @return
     */
    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {

        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie", movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if (imageDTOList != null && imageDTOList.size() > 0) {

            // imageDTOList를 스트림을 통해 -> movieImageList로 변환시킴.
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO ->{

                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;

            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }
}
