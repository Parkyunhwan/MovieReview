package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhwan.moviereview.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}


