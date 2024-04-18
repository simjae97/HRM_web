package sierp.springteam1.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class ProjectPageDto {
    private int startPage;  //시작 페이지
    private int endPage;  //마지막 페이지
    private int totalPage;  //총 페이지
    private int page;   //현재 페이지



    private List<Object> objectList; // 출퇴근 조회 리스트

}
