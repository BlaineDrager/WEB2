package com.example.demo.domain.service;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.repository.ImageBoardRepository;
import com.example.demo.properties.UploadInfoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
public class ImageBoardService {
    // 저장위치를 먼저 잡아야함 윈도우OS

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean addImageContents(ImageBoardDto dto) throws Exception{ // throws Exception 예외 처리 컨트롤러로./
        // 파일 정보 확인하기 전에 저장 폴더를 지정()
        String uploadPath = UploadInfoProperties.uploadPath + File.separator+dto.getSeller()+File.separator+dto.getCategory();
        // 파일 객체로 빼기
        File dir = new File(uploadPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        
        // 파일 정보 확인 하는 작업
        for(MultipartFile file : dto.getFiles()) { // dto에서 파일을 하나씩 꺼내기
            System.out.println("filename : " + file.getName());
            System.out.println("filename(origin) : " + file.getOriginalFilename()); // 파일 이름
            System.out.println("filesize : " + file.getSize()); // 파일의 사이즈
            System.out.println("-------------------------");
            
            File fileobj = new File(dir,file.getOriginalFilename()); // 파일 객체 생성
            file.transferTo(fileobj); // 저장
        }

        // 이미지 파일 저장하기

        // 이미지 DTO DB 로 저장
        // 1.dto를 entity로 바꾸는 작업 즉 entity를 만들어야 함

        return false;
    }
}
