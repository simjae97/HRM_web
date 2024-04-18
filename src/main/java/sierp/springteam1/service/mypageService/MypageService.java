package sierp.springteam1.service.mypageService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import sierp.springteam1.model.dao.EmployeeDao;
import sierp.springteam1.model.dao.mypageDao.MypageDao;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.service.secureservice.SecureService;

@Service
public class MypageService {

    @Autowired
    private MypageDao mypageDao;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SecureService secureService;

    @Autowired
    private EmployeeDao employeeDao;

    // 세션 에서 회원번호 가져오기
    public String sessionEno(){
        String eno = null;
        Object sessionObj = request.getSession().getAttribute("eno");
        if(sessionObj != null){ eno = String.valueOf(sessionObj); }
        // 만약에 로그인했으면(세션에 데이터가 있으면) 강제형변환을 통해 데이터 호출 아니면 0
        return eno;
    }

    // 사원 정보 요청
    public EmployeeDto doGetLoginInfo(String eno){
        System.out.println("MypageService.doGetLoginInfo");
        System.out.println("MypageService eno = " + eno);
        return mypageDao.doGetLoginInfo(eno);
    }



    // 마이페이지 내 개인정보 수정 요청
    public boolean doMypageUpdate( EmployeeDto employeeDto){
        System.out.println("MypageService.doMypageUpdate");
        System.out.println("employeeDto = " + employeeDto);
        // 값을 지우고 넣었을때 기존에 있는 값을 넣어주기 위해 기존 값 가져오기
        EmployeeDto saveInfo = mypageDao.doGetLoginInfo(String.valueOf(employeeDto.getEno()));
        System.out.println("saveInfo = " + saveInfo);
        // 입력값이 빈값이 였을 경우 기존 데이터 DTO 다시 넣고 DAO 전달
        if (employeeDto.getEmail().isEmpty()){
            employeeDto.setEmail(saveInfo.getEmail());
        } else if (employeeDto.getPhone().isEmpty()) {
            employeeDto.setPhone(saveInfo.getPhone());
        } else if (employeeDto.getAddress().isEmpty()) {
            employeeDto.setPhone(saveInfo.getAddress());
        }
        return mypageDao.doMypageUpdate(employeeDto);
    }
    // 마이페이지 비밀번호 수정 요청
    public boolean doMypageUpdatePw(String eno , String pw , String newpw){ // pwc : 변경 패스워드
        System.out.println("MypageService.doMypageUpdatePw");
        // boolean 기본 false 초기화
        boolean result = false;
        // dao 에 접근하여 eno 전달 후 비밀번호 비교
        String existingpw = mypageDao.passwordCheck(eno);
        System.out.println("existingpw = " + existingpw);
        String id = employeeDao.findEmployeeid(Integer.parseInt(eno)).getId();
        pw = secureService.get_UsersPW(id,pw);
        // 입력받은 비밀번호 화 DB 에 저장된 비밀번호 비교
        if (existingpw.equals(pw)){
            System.out.println("비밀번호가 같다");
            // 비밀번호가 같을 경우 model dao 에게 회원번호 뉴 패스워드 전달.
            newpw = secureService.get_UsersPW(id,newpw);
            result = mypageDao.doMypageUpdatePw(eno,newpw);
            return result;
        }else {
            System.out.println("비밀번호가 틀리다.");
        }
        return result;
    }


}
