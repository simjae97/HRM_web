package sierp.springteam1.model.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class EmployeeDto {
    // 사원 Dto

    int eno;
    String eeducation;  // 학력 ( 고졸 , 초대졸 , 대졸 )
    String id;          // 아이디
    String pw;          // 비밀번호
    String ename;       // 이름
    String email;       // 이메일
    String phone;       // 전화번호
    String address;     // 주소
    boolean sex;        // 성별 0 남성 1 여성
    String img;         // 업로드한 이미지 이름
    String edate;       // 입사일 ( 등록일 )
    int pno;            // 파트 ( 업무 부서 )
    MultipartFile mfile; // 파일 업로드
    //====== 추가한 필드
    String salt; //암호화용
    String pname;       // 부서 이름

}
