package com.example.demo.domain.repository;

import com.example.demo.domain.entity.ImageBoardFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageBoardFileInfoRepository extends JpaRepository<ImageBoardFileInfo,Long> {


    List<ImageBoardFileInfo> findByImageBoardId(Long imageBoardId); // findBy 찾는다 ImageBoard 이미지보드의 Id 아이디
}
