package com.example.demo.controller;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.entity.ImageBoardFileInfo;
import com.example.demo.domain.service.ImageBoardService;
import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/imageboard")
public class ImageBoardController {

    @Autowired
    private ImageBoardService imageBoardService;

    @GetMapping("/add")
    public void add(){
        log.info("GET /imageBoard/add");
    }


    @PostMapping("/add")
    public @ResponseBody ResponseEntity<String> add_post(ImageBoardDto dto) throws Exception { // 여러개를 받을거기 때문에 배열형으로
        log.info("POST /imageBoard/add file : " + dto); // 파일 경로가 잘 잡히는지

        // 유효성 체크(생략)

        // 서비스 실행
        boolean isuploaded = imageBoardService.addImageContents(dto); // 서비스 연결 완

        // 뷰처리
        if (isuploaded)
            return new ResponseEntity("message", HttpStatus.OK);
        return new ResponseEntity("fail...", HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        log.info("GET /imageBoard/list");
        List<ImageBoardFileInfo> filelist = imageBoardService.getAllitems(); // 서비스의 함수명은 무슨용도인지 알 수 있어야한다 // 뷰로 내용을 던져줌
//        filelist.forEach(item->System.out.println(item)); // filelist 출력
        // ImageBoard의 id를 기준으로 중복을 제거하여 Map을 생성합니다.
        Map<Long, ImageBoardFileInfo> uniqueItemsById = filelist.stream()
                .collect(Collectors.toMap(item -> item.getImageBoard().getId(), Function.identity(), (existing, replacement) -> existing));
        // 중복이 제거된 값들을 다시 List로 변환합니다.
        List<ImageBoardFileInfo> uniqueFileList = uniqueItemsById.values().stream().collect(Collectors.toList());

        // 결과를 출력합니다.
        uniqueFileList.forEach(item -> System.out.println(item));

        model.addAttribute("list",uniqueFileList);
    }

    @GetMapping("/read")
    public void read(Long id, Model model) throws Exception { // () 파라미터(인자) 받는곳
        log.info("GET /imageboard/read...id : " + id);
        // 서비스로 던지기
        List<ImageBoardFileInfo> list = imageBoardService.getImageBoardItem(id); // 여기서 걸러낸 리스트를
        model.addAttribute("list", list); // 모델로 보냄 // 이후 read에 뿌림 (read.html로)
    }

}
