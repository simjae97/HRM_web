package sierp.springteam1.controller.mypagecontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sierp.springteam1.model.dto.AttendanceLogDto;
import sierp.springteam1.service.mypageService.AttendanceService;
import sierp.springteam1.service.mypageService.MypageService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private MypageService mypageService;

    @GetMapping("/attendance")
    public String AttendanceView(){
        System.out.println("AttendanceController.AttendanceView");
        return "/mypages/attendance";
    }

    // 출근 요청
    @GetMapping("/attendance/goToWork")
    @ResponseBody
    public boolean attendanceWrite(HttpServletRequest request){
        // HttpServletRequest request ip 가져오기에 필요.
        String eno = mypageService.sessionEno();
        // 세션에 사원 번호 없으면 false
        if (eno == null){return false;}
        // 클라이언트 ip 가져온 후 서비스 전달
        String ip = null;
        ip = request.getRemoteAddr();
        return attendanceService.attendanceWrite(eno , ip);
    }


    // 퇴근 요청
    @GetMapping("/attendance/leaveWork")
    @ResponseBody
    public boolean attendanceLeaveWork(){
        System.out.println("AttendanceController.attendanceLeaveWork");
        String eno = mypageService.sessionEno();
        if (eno == null){return false;}
        System.out.println("eno = " + eno);
        return attendanceService.attendanceLeaveWork(eno);
    }

    // 출근 체크 값 가져오기
    @GetMapping("/event")
    @ResponseBody
    public List<AttendanceLogDto> getEvent() {
        System.out.println("AttendanceController.getEvent");
        String eno = mypageService.sessionEno();
        return attendanceService.getEvent(eno);

    }
    
    // 내 출근내역 리스트 사이트 요청
    @GetMapping("/attendance/list")
    public String attendanceListView(){
        System.out.println("AttendanceController.attendanceListView");
        return "/mypages/attendanceList";
    }

    // 내 출근내역 리스트 출력 요청 ( 여러개 )
    @GetMapping("/attendance/list.do")
    @ResponseBody
    public List<AttendanceLogDto> getEvents(){
        System.out.println("AttendanceController.getEvents");
        String eno = mypageService.sessionEno();
        return attendanceService.getEvents(eno);
    }



}
