package org.yunhwan.moviereview.entity;

// '회원'이 '영화'에게 '리뷰'를 남긴다. (주어 - 리뷰 - 목적어) -> 매핑 테이블;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"}) // 참조하는 객체 둘 다 제외 시킴.
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private int grade;
    private String text;
}
