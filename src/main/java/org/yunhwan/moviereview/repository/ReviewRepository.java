package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhwan.moviereview.entity.Review;

// '회원' 과 '영화'를 댓글로 연결하는 매핑 테이블!
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
