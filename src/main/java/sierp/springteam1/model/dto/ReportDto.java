package sierp.springteam1.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReportDto {
    // 보고 Dto

    int rno;                        // 리포트 번호
    int settoeno;                   // 받는사람
    int setfromeno;                 // 보낸사람
    boolean state;                  // 상태
    String title;                   // 제목
    String content;                 // 내용
    String img;                     // 이미지 명
    String note;                    // 비고
    String rdate;                   // 등록일
    private MultipartFile rawimg;   // 파일 업로드



}
