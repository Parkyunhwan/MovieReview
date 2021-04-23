package org.yunhwan.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * UploadImageDTO와 동일한 코드 사용. 변수명만 교체
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    // 파일이름 uuid 경로를 합쳐서 이미지 전체 경로 반환
    // ajax 실행 시 아래 메서드가 실행되면서 ImageURL값을 만들어 가져감.
    public String getImageURL() {
        try {
            return URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json으로 전달되는 UploadResultDTO에 썸네일 링크를 처리하기 위한 함수
     * @return
     */
    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
