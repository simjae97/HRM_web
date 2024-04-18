package sierp.springteam1.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeCareerDto {
    // 사원 경력 테이블 Dto

    int eno;                // 사원 번호
    String companyname;     // 회사 이름
    String note;            // 무슨 업무를 했는지.
    String eimg;            // 경력증명서 제출 시 이미지 이름
    String start_date;      // 근무 시작일
    String end_date;        // 근무 종료 일
    MultipartFile cimg;     // 파일 업로드
}
