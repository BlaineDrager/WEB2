package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ImageBoardFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 마이바티스의 오토인크리먼트 역할이다
    private Long id;

    @ManyToOne // 외래키 연결 설정으로 1:다 형식
    @JoinColumn(name = "imageboard_id",
            foreignKey = @ForeignKey(name = "FK_imageFileInfo_imageBoard",
                    foreignKeyDefinition = "FOREIGN KEY(imageboard_id) REFERENCES image_board(id) ON DELETE CASCADE ON UPDATE CASCADE"))
    // @JoinColumn 테이블에서 만들어지는 열 이름 설정 세부설정 가능 // foreignKey = @ForeignKey() 외래키에 대한 설정
    private ImageBoard imageBoard;
    private String dir;
    private String filename;
}
