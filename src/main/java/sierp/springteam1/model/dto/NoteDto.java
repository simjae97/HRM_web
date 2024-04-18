package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NoteDto {
    private int nno;
    private int posteno;
    private int sendeno;
    private String ncontent;
    private String ndate;
    int reply;

    String postid;
    String sendid;
}
