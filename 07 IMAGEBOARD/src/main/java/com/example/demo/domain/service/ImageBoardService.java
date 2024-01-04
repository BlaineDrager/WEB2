package com.example.demo.domain.service;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.entity.ImageBoardFileInfo;
import com.example.demo.domain.repository.ImageBoardFileInfoRepository;
import com.example.demo.domain.repository.ImageBoardRepository;
import com.example.demo.properties.UploadInfoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ImageBoardService {
    // 저장위치를 먼저 잡아야함 윈도우OS의 경우.

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private ImageBoardFileInfoRepository imageBoardFileInfoRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean addImageContents(ImageBoardDto dto) throws Exception{ // throws Exception 예외 처리 컨트롤러로./

        // Dto >> Entity로 변환시키기
        ImageBoard imageBoard = ImageBoard.builder()
                .seller(dto.getSeller())
                .productname(dto.getProductname())
                .brandname(dto.getBrandname())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .size(dto.getSize())
                .createAt(LocalDateTime.now())
                .itemdetals(dto.getItemdetals())
                .build();

        imageBoardRepository.save(imageBoard); // imageBoard 위치에 테이블에 저장된 ID 값을 같이 넣어줌

        List<String> files = new ArrayList<>();

        // 파일 정보 확인하기 전에 저장 폴더를 지정()
        String uploadPath = UploadInfoProperties.ROOTPATH + UploadInfoProperties.UPLOADPATH + File.separator+dto.getSeller() + File.separator+dto.getCategory() + File.separator+imageBoard.getId(); // 저장하고나서 ID도 같이 넣어줘야함
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
            
            File fileobj = new File(dir,file.getOriginalFilename()); // 파일 객체 생성 // dir,file.getOriginalFilename() 디렉토리 경로와 파일의 이름을 연결

            file.transferTo(fileobj); // 저장

            // 섬네일 생성
            File thumbnailFile = new File(dir, "s_"+file.getOriginalFilename()); // 실제 파일 경로
            // 파일 객체로 부터 이미지 받아오기
            BufferedImage bo_image = ImageIO.read(fileobj); // 받아온 이미지 파일 객체
            //
            BufferedImage bt_image = new BufferedImage(250,250,BufferedImage.TYPE_3BYTE_BGR); // 이미지 형식 크기와 색상조절 // 이미지 객체 새롭게 생성
            Graphics2D graphic = bt_image.createGraphics(); // 이미지 객체를 만들기
            graphic.drawImage(bo_image,0,0,250,250,null); // null 위치에 있는 observer 는 3D용 이미지를 만들때에 사용되는 것이다 // 그래픽 객체 만들기
            ImageIO.write(bt_image,"png",thumbnailFile); // "jpg" 확장자 명 // thumbnailFile 위치정보 // 썸네일 데이터 정보

            // DB에 파일경로 저장
//            files.add(fileobj.getPath());
            // 외래키가 포함되어있는 상태에서 저장 // 파일 정보만 따로 저장
            ImageBoardFileInfo imageBoardFileInfo = new ImageBoardFileInfo();
            imageBoardFileInfo.setImageBoard(imageBoard);
            String dirPath = UploadInfoProperties.UPLOADPATH
                    + File.separator+dto.getSeller()
                    + File.separator+dto.getCategory()
                    + File.separator+imageBoard.getId()
                    + File.separator;
            imageBoardFileInfo.setDir(dirPath);
            imageBoardFileInfo.setFilename(file.getOriginalFilename());
            imageBoardFileInfoRepository.save(imageBoardFileInfo);
        }

        imageBoard.setFiles(files);
        imageBoardRepository.save(imageBoard);
        // 이미지 파일 저장하기

        // 이미지 DTO DB 로 저장
        // 1.dto를 entity로 바꾸는 작업 즉 entity를 만들어야 함

        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    public List<ImageBoardFileInfo> getAllitems() throws Exception {
        return imageBoardFileInfoRepository.findAll(); //일단은 전체 받아오기
    }
    @Transactional(rollbackFor = Exception.class)
    public List<ImageBoardFileInfo> getImageBoardItem(Long id) throws Exception {
        return imageBoardFileInfoRepository.findByImageBoardId(id);
    }
}
