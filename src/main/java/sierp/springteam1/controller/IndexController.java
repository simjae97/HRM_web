package sierp.springteam1.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sierp.springteam1.model.dao.EmployeeDao;
import sierp.springteam1.model.dao.mypageDao.MypageDao;
import sierp.springteam1.service.j_projectPage.J_projectPageService;
import sierp.springteam1.service.secureservice.SecureService;

@Controller
public class IndexController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MypageDao mypageDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private J_projectPageService j_projectPageService;

    @Autowired
    private SecureService secureService;

    @GetMapping("/")
    public String indexview(){
        /*request.getSession().setAttribute("eno" ,1);*/
        return "index";
    }
    // 2. 로그인 처리 요청
    @PostMapping("/login")
    @ResponseBody
    public boolean login(String id, String pw){
        System.out.println("id = " + id);

        int result= secureService.login(id,pw);
        if(result !=-1){
            request.getSession().setAttribute("eno",result);
            return true;
        }
        return false;
    }

    //========== 로그아웃
    @GetMapping("/logout")
    @ResponseBody
    public boolean doGetLogout(){
        request.getSession().invalidate(); // 현재 요청 보낸 브라우저의 모든 세션 초기화
        return true;
    }
    // 로그인 여부 확인
    @GetMapping("employee/login/check")
    @ResponseBody
    public String getLoginCheck(){
        String loginId = null;
        Object sessionObj = request.getSession().getAttribute("eno");
        if(sessionObj != null){ loginId = String.valueOf(sessionObj); }
        // 만약에 로그인했으면(세션에 데이터가 있으면) 강제형변환을 통해 데이터 호출 아니면 0
        System.out.println("여기여기loginId = " + loginId);
        return loginId;
    } // getLoginCheck end

    // 로그인 후 사원이름 가져오기
    @GetMapping("employee/login/checkname")
    @ResponseBody
    public String doGetLoginName(String eno){
        System.out.println("IndexController.doGetLoginName");
        return mypageDao.doGetNameInfo(eno); // 세션에 사원번호로 DAO 접근 후 아이디 표시로 전환
    }

    //로그인 페이지 요청
    @GetMapping("/login")
    public String login(){
        return "/employee/login";
    }

    //관리자 식별 메소드(return type -> true : 관리자(인사과) , false : 일반사원)
    @GetMapping("/managerCheck")
    @ResponseBody
    public boolean indexManager(){
        System.out.println("J_ProjectPageController.indexManager");
        System.out.println("request.getSession().getAttribute(\"eno\") = " + request.getSession().getAttribute("eno"));
        Object eno=request.getSession().getAttribute("eno");
        //만약 비회원일경우 false 출력
        if(eno==null){
            return false;
        }

        return j_projectPageService.indexManager((int)eno);
    }//m end

} // c e
