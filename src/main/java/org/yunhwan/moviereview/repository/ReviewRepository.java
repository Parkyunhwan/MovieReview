package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;

import java.util.List;

// '회원' 과 '영화'를 댓글로 연결하는 매핑 테이블!
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * 해당 영화에 대한 모든 리뷰 정보를 조회하는 메서드
     *
     * +주의할점+ : @EntityGraph를 사용하지 않으면 멤버 객체를 가져올 수 없다 (LAZY 상태 이므로) path에 명시한 것은 Eager로 가져오게 됨.
     * @param movie
     * @return 해당 영화의 '리뷰'를 모두 리스트형태로 반환
     */
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);
}
