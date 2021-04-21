package org.yunhwan.moviereview.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yunhwan.moviereview.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.yunhwan.upload.path}")
    private String uploadPath;

    /**
     * 멀티파트 파일을 받아와서 각각의 파일을 폴더에 저장하는 기능
     *
     * !!주의할 점!!
     * 1. 업로드된 파일의 확장자가 image인 것만 가능하도록 필터링
     * 2. 동일한 이름이 업로드 되면 기존 파일을 덮어쓰므로 유일한 이름을 부여할 것
     * 3. 한 폴더만 사용하지 말 것 (날짜별 폴더 사용)
     * @param uploadFiles
     * @return ResponseEntity로 "반환 값과 상태 값을 함께 반환합니다."
     */
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일인지 검사!
            // 1. 업로드된 파일의 확장자가 image인 것만 가능하도록 필터링
            if (!uploadFile.getContentType().startsWith("image")) {
                log.warn("This file is not image type!!");

                // 예외 처리 대용으로 "FORBIDDEN" 설정 해놈
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();

            log.info("[UploadContoller] fileName : " + originalName);

            // 3. 한 폴더만 사용하지 말 것 (날짜별 폴더 사용)
            String folderPath = makeFolder();

            //UUID
            // 2. 동일한 이름이 업로드 되면 기존 파일을 덮어쓰므로 유일한 이름을 부여할 것
            String uuid = UUID.randomUUID().toString();

            //생성한 UUID를 이름과 함께 붙여서 파일명으로 사용
            String saveName = uploadPath + File.separator + folderPath + File.separator +
                    uuid + "_" + originalName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);
                resultDTOList.add(new UploadResultDTO(originalName, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // json 형태의 결과 반환
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("//", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {
            log.info("Make folder : " + uploadPathFolder);
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
