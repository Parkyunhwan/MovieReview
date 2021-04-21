package org.yunhwan.moviereview.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.yunhwan.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles) {

        for(MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일인지 검사!
            if (uploadFile.getContentType().startsWith("image") == false) {
                log.warn("This file is not image type!!");
                return;
            }

            String originalName = uploadFile.getOriginalFilename();
            // http~~~\\ 이후의 문자열을 가져옴
            //String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("[UploadContoller] fileName" + originalName);
            //log.info("fileName: " + fileName);

            String folderPath = makeFolder();
            //UUID
            String uuid = UUID.randomUUID().toString();

            //생성한 UUID를 이름과 함께 붙여서 파일명으로 사용
            String saveName = uploadPath + File.separator + folderPath + File.separator +
                    uuid + "_" + originalName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
