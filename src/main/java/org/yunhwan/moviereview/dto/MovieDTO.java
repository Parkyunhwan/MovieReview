package org.yunhwan.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long mno;

    private String title;

    // 빌더에서 따로 설정하지 않으면 null or 0이 나오게 함.
    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();


    // 아래 필드들은 무비 테이블에는 저장되지 않지만 화면 출력에 있어 필요하기 때문에 추가된 데이터.
    // 영화의 평균 평점
    private double avg;

    // 리뷰 숫자
    private int reviewCnt;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private LocalDateTime openDate;

    private String rating;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private Long runningTime;

    private String country;
}
