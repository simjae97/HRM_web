package sierp.springteam1.model.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder@ToString
public class BoardPageDTO {
    //1.현재 페이지
    private int page;
    //2.총 게시물 수
    private int totalpage;
    //3.페이지버튼의 시작 번호
    private int startbtn;
    //4.페이지 버튼의 끝번호
    private int endbtn;
    //5.총 게시물 수
    private int totalBoardSize;
    // 실제 내용 //
    private List<Object> list;
}
