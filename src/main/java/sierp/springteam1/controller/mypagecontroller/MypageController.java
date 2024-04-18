package sierp.springteam1.controller.mypagecontroller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.service.mypageService.MypageService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService mypageService;
    @Autowired
    private HttpServletRequest request;

    // 마이페이지 페이지 요청
    @GetMapping("")
    public String mypageView(){
        System.out.println("MypageController.mypageView");
        return "/mypages/mypage";
    }

    // 사원번호로 사원 정보 가져오기.
    @GetMapping("/info")
    @ResponseBody
    public EmployeeDto doGetLoginInfo(){
        System.out.println("MypageController.doGetLoginInfo");
        String eno = mypageService.sessionEno();
        EmployeeDto employeeDto = mypageService.doGetLoginInfo(eno);
        System.out.println("employeeDto = " + employeeDto);
        return employeeDto;
    }



    // 마이페이지 수정 페이지 요청
    @GetMapping("/updateView")
    public String doGetUpdateView(){
        System.out.println("MypageController.doGetUpdateView");
        return "/mypages/mypageupdate";
    }


    // 마이페이지 내 개인정보 수정 요청 ( 이메일 , 전화번호 , 주소  )
    @PutMapping("/update.do")
    @ResponseBody
    public boolean doMypageUpdate(String email , String phone , String address){
        System.out.println("MypageController.doMypageUpdate");
        // 세션에서 사원번호 가져오기
        String eno = mypageService.sessionEno();
        // 빌더 패턴 생성자 객체화
        EmployeeDto employeeDto = EmployeeDto.builder()
                .eno(Integer.parseInt(eno))
                .email(email)
                .phone(phone)
                .address(address)
                .build();
//        System.out.println("employeeDto = " + employeeDto);
//        if (employeeDto.getEmail().trim().isEmpty()){
//            System.out.println("빈문자 입니다.");
//        }
        return mypageService.doMypageUpdate(employeeDto);
    }
    // 마이페이지 비밀번호 수정 요청
    @PutMapping("/updatepw.do")
    @ResponseBody
    public boolean doMypageUpdatePw(String pw , String newpw){ // pwc : 변경 패스워드
        System.out.println("MypageController.doMypageUpdatePw");
        // 세션에서 사원번호 가져오기
        String eno = mypageService.sessionEno();
        // 사원번호 와 입력받은 값 서비스 전달
        return mypageService.doMypageUpdatePw(eno, pw , newpw);
    }
}
/*
    - 내정보 보기 ( 사원번호 , 아이디 , 이름 , 이메일 , 전화번호 , 주소 , 성별 , 자기이미지 , 입사일 , 파트 ,  )
    GET 방식


*/
