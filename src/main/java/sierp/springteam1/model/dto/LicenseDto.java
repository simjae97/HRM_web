package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LicenseDto {
    // 자격증 테이블 Dto
    int lno;        // 자격증 번호 1 : 정보처리기사 2 : 정보보안기사 3: 전자계산기조직응용기사 4 : 전자계산기기사
    String lname;    // 자격증 명
    int lprice;     // 자격증 보유 시 돈

}
