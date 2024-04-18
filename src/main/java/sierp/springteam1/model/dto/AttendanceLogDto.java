package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AttendanceLogDto {     // 출근 체크 DTO
    private String jday;            // 날짜 2024-03-13
    private String stat_time;       // 시간 15:43:10
    private String end_time;        // 시간 15:43:10
    private String working_time;    // 시간 00:00:00
    private String jip;             // 출근 ip
    private String jnote;           // 비고
    private int eno;                // 출근 체크 한 사원 번호

}
