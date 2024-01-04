package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ImageBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 마이바티스의 오토인크리먼트 역할이다
    private Long id;
    private String seller;
    private String productname;
    private String price;
    private String category;
    private String brandname;
    private String itemdetals;
    private String amount;
    private String size;

    private LocalDateTime createAt;


    private List<String> files; // List<String> 는 데이터 타입이 바이너리임 그렇기에 위의 @ElementCollection 을 추가 해줘서 파일명만 따로 저장시킬 수 있게 함
}
