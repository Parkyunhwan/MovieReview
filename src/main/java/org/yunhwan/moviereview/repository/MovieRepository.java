package org.yunhwan.moviereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yunhwan.moviereview.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {


    /**
     * 영화와 리뷰의 조인을 통해 영화의 (영화, 평점 평균, 리뷰 갯수)를 조회
     * coalesce()로 -> null이면 평점평균 0을 반환하게 됨.
     *
     * 중간에 무비이미지 조인을 추가해주면 영화의 이미지도 함께 가져올 수 있다. (이미지 최대 갯수)
     * 아래 메서드는 max(mi)로 인해 N + 1 문제가 발생한다. (1번의 쿼리로 N개의 데이터를 가져왔는데 N개의 데이터를 처리하기위해 필요한 추가 쿼리가 각N개에 대해서 수행되는 상황)
     */
    @Query("select m, max(mi), avg(coalesce(r.grade, 0)), count(distinct r) from Movie m "
            + "left outer join MovieImage mi on mi.movie = m "
            + "left outer join Review r on r.movie = m group by m ")
    Page<Object[]> getListPageDeprecated(Pageable pageable);

    // -> 영화의 이미지를 하나만 가져오게 된다. (가장 먼저 입력된 이미지 번호!) [N + 1 문제 해결]
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m "
            + "left outer join MovieImage mi on mi.movie = m "
            + "left outer join Review r on r.movie = m group by m ")
    Page<Object[]> getListPage(Pageable pageable);

}
