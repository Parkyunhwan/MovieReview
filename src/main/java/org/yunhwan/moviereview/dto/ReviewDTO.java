package org.yunhwan.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yunhwan.moviereview.entity.Member;
import org.yunhwan.moviereview.entity.Movie;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 리뷰를 화면에 표현하기위해 필요한 모든 내용을 가지고 있어야만 한다.
 * 회원의 아이디 + 닉네임/이메일
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDTO {

    private Long id;

    private Long movieId; // Moive

    // 작성자 Member 정보.
    private Long mid;
    private String nickname;
    private String email;

    // 평점
    private int grade;

    // 내용.
    private String text;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
