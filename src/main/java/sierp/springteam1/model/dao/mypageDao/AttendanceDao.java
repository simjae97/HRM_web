package sierp.springteam1.model.dao.mypageDao;


import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.AttendanceLogDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AttendanceDao extends SuperDao {

    // 출석 요청
    public boolean attendanceWrite(AttendanceLogDto attendanceLogDto){
        try {
            String sql = "INSERT INTO attendance_log(jday, stat_time,jip, eno) VALUES(?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,attendanceLogDto.getJday());
            ps.setString(2,attendanceLogDto.getStat_time());
            ps.setString(3,attendanceLogDto.getJip());
            ps.setString(4, String.valueOf(attendanceLogDto.getEno()));
            int count = ps.executeUpdate();
            if(count == 1){return true;}
        }catch (Exception e){
            System.out.println("attendanceWrite : e = " + e);
        }
        return false;
    }

    // 퇴근 요청
    public boolean attendanceLeaveWork(String eno , String toDay , String time, String workTime){
        System.out.println("AttendanceDao.attendanceLeaveWork");
        System.out.println("attendanceLeaveWork : eno = " + eno);
        try {
            String sql = "update attendance_log set end_time = ? , working_time = ? where eno = ? and jday = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,time);
            ps.setString(2,workTime);
            ps.setString(3,eno);
            ps.setString(4,toDay);
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("attendanceLeaveWork : e = " + e);
        }
        return false;
    }

    // 출 퇴근 출력 값 가져오기 ( 1개 )
    public List<AttendanceLogDto> getEvent(String eno , String toDay){
        System.out.println("AttendanceDao.getEvent");
        List<AttendanceLogDto> list = new ArrayList<>();
        try {
            String sql = "select jday , stat_time , end_time , eno from attendance_log where eno = ? and jday = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            ps.setString(2,toDay);
            rs = ps.executeQuery();
            if(rs.next()){
                AttendanceLogDto attendanceLogDto = AttendanceLogDto.builder()
                        .stat_time(rs.getString("stat_time"))
                        .end_time(rs.getString("end_time"))
                        .build();
                list.add(attendanceLogDto);
            }
        }catch (Exception e){
            System.out.println("AttendanceDao.getEvent : e = " + e);
        }
        System.out.println("getEvent : list = " + list);
        return list;
    }


    // 내 출근내역 리스트 출력 요청 ( 여러개 )
    public List<AttendanceLogDto> getEvents(String eno , String toDay){
        System.out.println("AttendanceDao.getEvents");
        List<AttendanceLogDto> list = new ArrayList<>();
        try {
            String sql = "select jday , stat_time , end_time , eno , working_time from attendance_log where eno = ? and jday != ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            ps.setString(2,toDay);
            rs = ps.executeQuery();
            while (rs.next()){
                AttendanceLogDto attendanceLogDto = AttendanceLogDto.builder()
                        .jday(rs.getString("jday"))
                        .stat_time(rs.getString("stat_time"))
                        .end_time(rs.getString("end_time"))
                        .working_time(rs.getString("working_time"))
                        .build();
                list.add(attendanceLogDto);
            }
        }catch (Exception e){
            System.out.println("AttendanceDao.getEvents : e = " + e);
        }
        System.out.println("getEvents : list = " + list);
        return list;
    }

    // 오늘 날자로 출근을 찍었는지 검사
    public boolean attendanceWriteCheck(String eno , String toDay){
        System.out.println("AttendanceService.attendanceWriteCheck");
        try {
            String sql = "select jday , stat_time , end_time , eno from attendance_log where eno = ? and jday = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            ps.setString(2,toDay);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            System.out.println("attendanceWriteCheck : e = " + e);
        }
        return false;
    }


    // 사원의 오늘 출근 시간 가져오기
    public String todayStatTime(String eno , String toDay){
        System.out.println("AttendanceDao.todayStatTime");
        System.out.println("eno = " + eno);
        try {
            String sql = "select stat_time from attendance_log where eno = ? and jday = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, eno);
            ps.setString(2, toDay);
            rs = ps.executeQuery();
            if (rs.next()){
                // 사원번호 eno 와 오늘날짜 toDay 를 검색하여 출근 시간을 반환
                return rs.getString("stat_time");

            }
        }catch (Exception e){
            System.out.println("todayStatTime : e = " + e);
        }
        return null;
    }


}



// event.put("start", LocalDate.now());
//        event.put("title", now+"출근");
//        System.out.println("formatter = " + now);
//        event.put("end",LocalDate.now());
//        eventList.add(event);
//        event = new HashMap<String, Object>();
//        event.put("start", LocalDate.now().plusDays(3));
//        event.put("title", "test2");
//        event.put("end",LocalDate.now().plusDays(4));
//        eventList.add(event);
