package sierp.springteam1.model.dao.projectcontroller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectlogDto;
import sierp.springteam1.model.dto.PsendEmployeeDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class OneprojectDao extends SuperDao {

    public ProjectDto oneProject(int pjno){
        ProjectDto projectDto = null;
        try {
            String sql = "select * from salesproject where spjno in (select spjno from uploadproject where pjno=?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pjno);
            rs = ps.executeQuery();
            if(rs.next()){
                projectDto= ProjectDto.builder()
                        .spjno(rs.getInt("spjno"))
                        .start_date(rs.getString("start_date"))
                        .end_date(rs.getString("end_date"))
                        .rank1_count(rs.getInt("rank1_count"))
                        .rank2_count(rs.getInt("rank2_count"))
                        .rank3_count(rs.getInt("rank3_count"))
                        .title(rs.getString("title"))
                        .request(rs.getString("request"))
                        .note(rs.getString("note"))
                        .compannyname(rs.getString("compannyname"))
                        .state(rs.getInt("state"))
                        .price(rs.getString("price"))
                        .build();
                ;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }

        
        return projectDto;
    }
    public ArrayList<PsendEmployeeDto>[] memberlist(String start_date){
        ArrayList<PsendEmployeeDto>[] result = new ArrayList[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ArrayList<>();
        }
        try {
           String sql = "select a.ename as ename , a.img as img, a.eno as eno, a.평가점수 as score, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime \n" +
                   "from (select e.* , sum(coalesce(score,0)) as 평가점수 \n" +
                   "       from employee as e left outer join projectlog p on e.eno = p.eno \n" +
                   "       group by e.eno \n" +
                   " ) as a left outer join  employeecareer as b on a.eno = b.eno \n" +
                   "where a.eno in( \n" +
                   "       select d.eno \n" +
                   "       from \n" +
                   "       (select b.pjno , end_date, a.eno  from uploadproject b join salesproject d on b.spjno = d.spjno \n" +
                   "       right outer join projectlog a \n" +
                   "       on a.pjno = b.pjno) as c \n" +
                   "       right outer join \n" +
                   "       employee d \n" +
                   "       on c.eno = d.eno \n" +
                   "       group by d.eno \n" +
                   "       having ( coalesce(datediff(?,max(end_date)),1) > 0) \n" +
                   ") \n" +
                   "group by a.eno;";

           ps=conn.prepareStatement(sql);
           ps.setString(1,start_date);
           rs= ps.executeQuery();
           while (rs.next()){
               int score = rs.getInt("score");
               PsendEmployeeDto employeeDto = PsendEmployeeDto.builder()
                       .employeeDto(EmployeeDto.builder()
                               .eno(rs.getInt("eno"))
                               .ename(rs.getString("ename"))
                               .img(rs.getString("img"))
                               .build())
                       .score(score)
                       .build();

               int allday = rs.getInt("alltime");
               System.out.println(allday);

               if(allday < 1459 ){
                   result[0].add(employeeDto);
               }
               else if (allday > 3650) {
                    result[2].add(employeeDto);
               }
               else {
                   result[1].add(employeeDto);
               }

           }
           return result;
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }

        return result;
    }

    public ArrayList<PsendEmployeeDto>[] updatememberlist(int pjno, String start_date){
        ArrayList<PsendEmployeeDto>[] result = new ArrayList[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ArrayList<>();
        }
        try {
            String sql = "select a.ename as ename , a.img as img, a.eno as eno, a.평가점수 as score, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime \n" +
                    "from (select e.* , sum(coalesce(score,0)) as 평가점수  \n" +
                    "\tfrom employee as e left outer join projectlog p on e.eno = p.eno\n" +
                    "    group by e.eno\n" +
                    " )as a left outer join  employeecareer as b on a.eno = b.eno \n" +
                    "where a.eno in(\n" +
                    "\t\tselect d.eno\n" +
                    "\t\tfrom \n" +
                    "\t\t(select b.pjno , end_date, a.eno  from uploadproject b  \n" +
                    "\t\tright outer join projectlog a\n" +
                    "\t\t on a.pjno = b.pjno) as c \n" +
                    "\t\tright outer join\n" +
                    "\t\t employee d \n" +
                    "\t\t on c.eno = d.eno\n" +
                    "\t\t group by d.eno\n" +
                    "\t\t having ( coalesce(datediff(?,max(end_date)),1) > 0)\n" +
                    ")\n" +
                    "or a.eno in(\n" +
                    "\tselect eno from projectlog where pjno = ?\n" +
                    ")\n" +
                    "group by a.eno;";

            ps=conn.prepareStatement(sql);
            ps.setString(1,start_date);
            ps.setInt(2,pjno);
            rs= ps.executeQuery();
            while (rs.next()){
                int score = rs.getInt("score");
                PsendEmployeeDto employeeDto = PsendEmployeeDto.builder()
                        .employeeDto(EmployeeDto.builder()
                                .eno(rs.getInt("eno"))
                                .ename(rs.getString("ename"))
                                .img(rs.getString("img"))
                                .build())
                        .score(score)
                        .build();

                int allday = rs.getInt("alltime");

                if(allday < 1459 ){

                    result[0].add(employeeDto);
                }
                else if (allday > 3650) {
                    result[2].add(employeeDto);
                }
                else {
                    result[1].add(employeeDto);
                }

            }
            return result;
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }

        return result;
    }
    public int findscore(int eno){
        try {
            String sql = "select a.평가점수 as score\n" +
                    "from (select e.* , sum(coalesce(score,0)) as 평가점수  \n" +
                    "\tfrom employee as e left outer join projectlog p on e.eno = p.eno\n" +
                    "    group by e.eno\n" +
                    " )as a left outer join  employeecareer as b on a.eno = b.eno " +
                    " where a.eno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,eno);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return -1;
    }
    public boolean createprojectlog(ProjectlogDto projectlogDto){
        try {
            for (ArrayList<Integer> i : projectlogDto.getEnos()) {
                for(int j : i){
                    String sql = "insert into projectlog(eno, pjno) values(?,?);";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, j);
                    ps.setInt(2, projectlogDto.getPjno());
                    int count = ps.executeUpdate();
                    if (count != 1) {
                        return false;
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
            return false;
        }
        return true;
    }
    public boolean deleteprojectlog(ProjectlogDto projectlogDto){
        try {
            String sql = "delete from projectlog where pjno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,projectlogDto.getPjno());
            ps.executeUpdate();
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return true;
    }

    public ArrayList<Integer>[] findlog( int pjno){//이거 고쳐서 eno와 경력이 같이 나가게 하기
        ArrayList<Integer>[] result = new ArrayList[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ArrayList<>();
        }
        try {
            String sql = "select a.eno as eno, (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime  #프로젝트 로그에 남은 사원들 경력+eno 가져오기\n" +
                    "from (select e.* , sum(coalesce(score,0)) as 평가점수  \n" +
                    "\tfrom employee as e left outer join projectlog p on e.eno = p.eno\n" +
                    "    group by e.eno\n" +
                    " )as a left outer join  employeecareer as b on a.eno = b.eno\n" +
                    " where a.eno in(\n" +
                    "\tselect eno from projectlog where pjno = ?\n" +
                    ")\n" +
                    " group by a.eno;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pjno);
            rs = ps.executeQuery();
            while (rs.next()){
                int allday = rs.getInt("alltime");

                if(allday < 1459 ){

                    result[0].add(rs.getInt("eno"));

                }
                else if (allday > 3650) {
                    result[1].add(rs.getInt("eno"));
                }
                else {
                    result[2].add(rs.getInt("eno"));
                }
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return result;
    }

    public int[] findrankcount(int pjno){
        int[] result = new int[3];
        try {
            String sql = "select rank1_count,rank2_count,rank3_count from uploadproject as a inner join salesproject as b on a.spjno = b.spjno where pjno =?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pjno);
            rs= ps.executeQuery();
            if(rs.next()){
                result[0] = rs.getInt("rank1_count");
                result[1] = rs.getInt("rank2_count");
                result[2] = rs.getInt("rank3_count");
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return result;
    }


    public ArrayList<ProjectlogDto> findlog2( int pjno){
        ArrayList<ProjectlogDto> result = new ArrayList<>();
        try {
            String sql = "select * from projectlog where pjno= ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pjno);
            rs = ps.executeQuery();
            while (rs.next()){
                result.add( ProjectlogDto.builder()
                        .eno(rs.getInt("eno"))
                        .state(rs.getInt("state"))
                        .score(rs.getInt("score"))
                        .note(rs.getString("note"))
                        .build());
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return result;
    }

    public int findspjno(int pjno){
        try {
            String sql ="select spjno from uploadproject where pjno = "+pjno;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);

            }
        }
        catch (Exception e){
            System.out.println("spjno를 찾는 도중 발생한 문제:"+e);
        }

        return -1;
    }

//    public boolean createprojectlog(){
//
//    }
}
