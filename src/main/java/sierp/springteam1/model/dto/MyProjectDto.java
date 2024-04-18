package sierp.springteam1.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MyProjectDto {
    // 마이프로젝트 에서 사원번호로 현재 자기가 진행 중인 프로젝트 조인 DTO

    private int pjno;               // 프로젝트 번호 int
    private String start_date;      // 시작 날짜 string
    private String end_date;        // 끝 날짜 string
    private String title;           // 프로젝트 이름 string
    private String compannyname;    // 회사이름 string
    private int eno;                // 사원번호   int
    private String id;              // 사원 아아디
    private int state;              // 현재상태  int


}
