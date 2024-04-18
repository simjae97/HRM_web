package sierp.springteam1.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PriceDto {
    // 초중고 연봉 테이블 Dto
    int pno;                 //1이면 초급, 2이면 중급 3이면고급
    int startyear;           //0,6,11
    int endyear;             //3,5,7
    int pprice;              // 연봉

}
