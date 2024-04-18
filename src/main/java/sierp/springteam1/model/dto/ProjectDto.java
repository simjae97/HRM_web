package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProjectDto {
    // 프로젝트 Dto

    int spjno;              // 프로젝트 번호
    int pjno;               // 프로젝트 번호 ( 실제 진행 되는 프로젝트 번호 )
    String start_date;      // 시작 날짜
    String end_date;        // 끝나는 날짜
    int rank1_count;        // 초급 요청 수
    int rank2_count;        // 중급 요청 수
    int rank3_count;        // 고급 요청 수
    String title;           // 제목
    String request;         // 요구사항
    String note;            // 비고
    String compannyname;    // 회사명
    int state;              // 현재 프로젝트 상태
    String price;           // 프로젝트 금액
}
