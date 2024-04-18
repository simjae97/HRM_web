package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PartDto {
    // 부서 Dto

    int pno;        // 부서 번호 1 : 인사과 2 : 영업 3: 프로그래머
    String pname;   // 부서명
}
