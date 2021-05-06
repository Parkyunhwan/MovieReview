package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.yunhwan.moviereview.entity.Member;
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

    // void deleteByMember(Member member); ------> 삭제 성능을 좀 더 개선하기 위해 @Query 사용.

    @Modifying // update나 delete 시에는 @Modifying 어노테이션이 필요하다.
    @Query("delete from Review mr where mr.member = :member") // -> 멤버의 모든 댓글을 한 쿼리로 삭제! (효율적)
    void deleteByMember(Member member);

//    @Modifying
//    void deleteByMovie_Mno(Long mno);

    @Modifying
    @Query("delete from Review mr where mr.movie = :movie") // -> 영화의 모든 댓글을 한 쿼리로 삭제! (효율적)
    void deleteByMovie(Movie movie);

}
