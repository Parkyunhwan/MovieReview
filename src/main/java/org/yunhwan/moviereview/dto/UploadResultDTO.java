package org.yunhwan.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

// 업로드 결과 처리 dto
@Data
@AllArgsConstructor
public class UploadResultDTO {

    private String fileName;
    private String uuid;
    private String folderPath;

    // 파일이름 uuid 경로를 합쳐서 이미지 전체 경로 반환
    // ajax 실행 시 아래 메서드가 실행되면서 ImageURL값을 만들어 가져감.
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
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
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
