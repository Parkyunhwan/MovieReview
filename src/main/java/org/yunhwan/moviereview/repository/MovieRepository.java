package org.yunhwan.moviereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.repository.search.SearchMovieRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, SearchMovieRepository {

    // -> 영화의 이미지를 하나만 가져오게 된다. (가장 먼저 입력된 이미지 번호!) [N + 1 문제 해결]
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) from Movie m "
            + "left outer join MovieImage mi on mi.movie = m "
            + "left outer join Review r on r.movie = m group by m ")
    Page<Object[]> getListPage(Pageable pageable);


    /**
     * 영화 조회 시 영화와 해당 영화의 모든 이미지, 평균 평점 + 리뷰 개수를 함께 조회해오기 위한 기능
     *
     * group by를 통해 '영화 이미지'별로 묶고
     * where를 통해 특정 영화만을 조회한다.
     *
     */
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) from Movie m "
            + "left outer join MovieImage mi on mi.movie = m "
            + "left outer join Review r on r.movie = m "
            + "where m.id = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);


}
