package sierp.springteam1.model.dao.mypageDao;


import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.MyProjectDto;
import sierp.springteam1.model.dto.ProjectDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyProjectDao extends SuperDao {


    // 진행 중인 프로젝트 출력
    public MyProjectDto myProjectList(String eno){
        System.out.println("MyProjectDao.myProjectList");
        MyProjectDto myProjectDto = new MyProjectDto();
        try {
            String sql =
                    "SELECT p.pjno, p.eno , p.state , s.state , s.start_date , s.end_date , s.title , s.compannyname\n" +
                    "FROM projectlog p\n" +
                    "INNER JOIN salesproject s ON p.pjno = s.spjno\n" +
                    "INNER JOIN employee e ON p.eno = e.eno\n" +
                    "WHERE p.eno = ? AND s.state = 1;";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            rs = ps.executeQuery();
            if (rs.next()){
                myProjectDto = new MyProjectDto(
                rs.getInt("pjno") ,
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getString("title") ,
                rs.getString("compannyname"),
                rs.getInt("eno") ,
                null,   // 회원 아이디  서비스 에서 mypageDao 에 연결 후 가져올것.
                rs.getInt("state")
            );
        }
            System.out.println("myProjectDto = " + myProjectDto);
            return myProjectDto;
        }catch (Exception e){
            System.out.println("MyProjectDao.myProjectList : e = " + e);
        }
        return myProjectDto;
    }


    // 즐겨찾기한 프로젝트 전체 출력
    public List<ProjectDto> myProjectLikeView(String eno){
        System.out.println("MyProjectDao.myProjectLikeView");
        System.out.println("MyProjectDao.myProjectLikeView : eno = " + eno);
        List<ProjectDto> list = new ArrayList<>();
        ProjectDto projectDto = null;

        try {
            String sql =
                    "SELECT DISTINCT  p.pjno , s.title , s.compannyname , s.price\n" +
                            "FROM projectlike p\n" +
                            "INNER JOIN salesproject s ON p.pjno = s.spjno\n" +
                            "INNER JOIN employee e ON p.eno = e.eno\n" +
                            "WHERE p.eno = ? and s.state != 2;";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            rs = ps.executeQuery();
            while (rs.next()){
                projectDto = ProjectDto.builder()
                        .pjno(rs.getInt("pjno"))
                        .title(rs.getString("title"))
                        .compannyname(rs.getString("compannyname"))
                        .price(rs.getString("price"))
                        .build();
                list.add(projectDto);
            }
            System.out.println("list = " + list);
            return list;
        }catch (Exception e){
            System.out.println("MyProjectDao.myProjectLikeView : e = " + e);
        }

        return null;
    }

    // 내 이전 프로젝트 전체 출력
    public  List<ProjectDto> myProjectPreviousView(String eno){
        System.out.println("MyProjectDao.myProjectPreviousView");
        List<ProjectDto> list = new ArrayList<>();
        ProjectDto projectDto = null;
        try {
            String sql = "SELECT DISTINCT b.state, b.title, a.pjno, b.start_date, b.end_date \n" +
                    "FROM uploadproject a \n" +
                    "JOIN salesproject b ON a.spjno = b.spjno \n" +
                    "JOIN projectlog j ON a.pjno = j.pjno \n" +
                    "WHERE j.eno = ? AND j.state = 2;";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            rs = ps.executeQuery();
            while (rs.next()){
                projectDto = ProjectDto.builder()
                        .pjno(rs.getInt("pjno"))
                        .title(rs.getString("title"))
                        .start_date(rs.getString("start_date"))
                        .end_date(rs.getString("end_date"))
                        .state(rs.getInt("state"))
                        .build();
                list.add(projectDto);
            }
            System.out.println("list = " + list);
            return list;
        }catch (Exception e){
            System.out.println("MyProjectDao.myProjectPreviousView : e = " + e);
        }
        return null;
    }


} // c e
