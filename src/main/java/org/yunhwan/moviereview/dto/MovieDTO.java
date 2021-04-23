package org.yunhwan.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
