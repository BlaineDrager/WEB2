package com.example.demo.properties;

import java.io.File;

public class UploadInfoProperties {
    public static final String ROOTPATH = "c:\\";
    public static final String UPLOADPATH = File.separator+"imageboard"; // 특정 이미지 보드 // 내부에서 변경 불가능하게 final을 추가함 나중에 변경할 경우엔 직접 수정해야함
    // 맥스사이즈를 여기서 진행시키면됨
}
