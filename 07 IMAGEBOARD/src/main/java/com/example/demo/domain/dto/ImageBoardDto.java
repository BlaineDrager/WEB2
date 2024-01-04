package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ImageBoardDto {
    private Long id;
    private String seller;
    private String productname;
    private String category;
    private String brandname;
    private String price;
    private String itemdetals;
    private String amount;
    private String size;
    private MultipartFile[] files; // 해당 파일 정보를 여기에 저장
}
