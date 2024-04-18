package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProjectDto3 {
    // 프로젝트 Dto

    int pjno;               // 프로젝트 번호
    String start_date;      // 시작 날짜
    String end_date;        // 끝나는 날짜
    String title;           // 제목
    String request;         // 요구사항
    String note;            // 비고
    String compannyname;    // 회사명
    int price;           // 프로젝트 금액
    int perFormState;    // 평가 완료 여부 , 0평가전 1평가중 2평가완료
}
