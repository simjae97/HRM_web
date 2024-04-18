package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProjectLikeDto {
    // 관심 프로젝트 등록
    int eno;        // 사원 번호
    int pjno;       // 프로젝트 번호
}
