package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeeLicenseDto {
    // 사원 자격증 로그 Dto
    int eno;            // 사원 번호
    int lno;            // 자격증 테이블 번호
    String ldate;       // 등록 날짜.

    // 추가 필드
    String lname;   // 자격증 이름
}
