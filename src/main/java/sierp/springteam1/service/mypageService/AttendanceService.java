package sierp.springteam1.service.mypageService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sierp.springteam1.model.dao.mypageDao.AttendanceDao;
import sierp.springteam1.model.dto.AttendanceLogDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceDao attendanceDao;

    // 출근 요청
    public boolean attendanceWrite(String eno , String ip){
        // 이미 출근을 찍었는지 체크 ( 찍으면 true , 아니면 false )
        boolean result = attendanceWriteCheck(eno);
        if ( !(result) ){
            AttendanceLogDto attendanceLogDto = AttendanceLogDto.builder()
                    .jday(nowDay())
                    .stat_time(nowTime())
                    .jip(ip)
                    .eno(Integer.parseInt(eno))
                    .build();
            return attendanceDao.attendanceWrite(attendanceLogDto);
        }
        return false; // 이미 출근을 찍었으면 false
    }

    // 퇴근 요청
    public boolean attendanceLeaveWork(String eno){
        System.out.println("AttendanceService.attendanceLeaveWork");
        // 오늘 출근 체크했는지 체크 true 출근 확인
        boolean result = attendanceWriteCheck(eno);
        if (result){
            // 오늘 날짜
            String toDay = nowDay();
            // 현재 시간
            String time = nowTime();
            // 일한 시간 계산 메소드
            String workTime = workingTime(eno ,time,toDay);
            return attendanceDao.attendanceLeaveWork(eno , toDay , time , workTime);
        }
        return result; // false
    }




    public String nowTime(){ // 현재 시간 반환
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        return formattedTime;
    }

    public String nowDay(){ // 현재 날짜 반환
        LocalDate now = LocalDate.now();
        return String.valueOf(now);
    }

    // 출퇴근 출력 1개
    public List<AttendanceLogDto> getEvent(String eno){
        System.out.println("AttendanceService.getEvent");
        String toDay = nowDay();
        return attendanceDao.getEvent(eno , toDay);
    }


    // 내 출근내역 리스트 출력 요청 ( 여러개 )
    public List<AttendanceLogDto> getEvents(String eno){
        System.out.println("AttendanceService.getEvents");
        String toDay = nowDay();
        return attendanceDao.getEvents(eno , toDay);
    }


    // 오늘 날자로 출근을 찍었는지 검사
    public boolean attendanceWriteCheck(String eno){
        System.out.println("AttendanceService.attendanceWriteCheck");
        String toDay = nowDay();
        System.out.println("toDay = " + toDay);
        System.out.println("eno = " + eno);

        return attendanceDao.attendanceWriteCheck(eno ,toDay);
    }

    // 출근 시간 퇴근 시간 계산 후 일한 시간 결과 받기
    public String workingTime(String eno , String time ,String toDay){
        String workTime = null;
        System.out.println("eno = " + eno); // 사원번호
        System.out.println("time = " + time); // 퇴근 현재 시간
        System.out.println("toDay = " + toDay); // 오늘 날짜
        // DAO 에 접근하여 출근 시간 반환
        String goWorkTime = attendanceDao.todayStatTime(eno , toDay);
        System.out.println("goWorkTime = " + goWorkTime);

        // 출근 시간 퇴근 시간 타입 변환
        LocalTime time1 = LocalTime.parse(time);
        System.out.println("time1 = " + time1);
        LocalTime time2 = LocalTime.parse(goWorkTime);
        System.out.println("time2 = " + time2);
        // 출근 시간 퇴근 시간 시 분 으로 계산
        long str1 = time2.until(time1 , ChronoUnit.HOURS);
        System.out.println("str1 = " + str1);
        long str2 = time2.until(time1 , ChronoUnit.MINUTES )% 60;
        System.out.println("str2 = " + str2);
        
        // 출근 퇴근 계산이 시가 2자리 1자리 구분
        if (str1 >= 10){
            workTime = str1+":";
            // 출근 퇴근 계산이 분이 2자리 1자리 구분
            if (str2 >= 10){
                workTime += str2 + ":" + "00";
            }else {
                workTime += "0"+str2 + ":" + "00";
            }
            System.out.println("workTime = " + workTime);
        }else {
            workTime = "0"+ str1 + ":";
            if (str2 >= 10){
                workTime += str2 + ":" + "00";
            }else {
                workTime += "0"+str2 + ":" + "00";
            }
            System.out.println("workTime = " + workTime);
        }
        // 일한 시간 리턴
        return workTime;
    }
}
