package org.yunhwan.moviereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.repository.search.SearchMovieRepository;

public interface MovieRepository extends JpaRepository<Movie, Long>, SearchMovieRepository {

    // -> 영화의 이미지를 하나만 가져오게 된다. (가장 먼저 입력된 이미지 번호!) [N + 1 문제 해결]
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) from Movie m "
            + "left outer join MovieImage mi on mi.movie = m "
            + "left outer join Review r on r.movie = m group by m ")
    Page<Object[]> getListPage(Pageable pageable);
}
