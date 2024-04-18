package sierp.springteam1.model.dao.j_projectPageDao;

import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectDto3;
import sierp.springteam1.model.dto.ProjectPageDto;

import java.util.*;

@Component
public class J_projectPageDao extends SuperDao {
    //프로젝트 전체 리스트 출력
    public List<Object> printProjectList(int startRow, int pageBoardSize,
                                          int sortkey,
                                          String key, String keyword,
                                          int startPrice, int endPrice){
        System.out.println("J_projectPageDao.printProjectList");
        List<Object> projectDtos=new ArrayList<>();
        try{
            String sql="select * from salesproject ";

            //------------- 검색기준을 선택한 경우 -------------------
            if(!key.equals("")){
                if(key.equals("price")){ //검색기준이 규모인 경우
                    sql+=" where price between "+startPrice*10000 +" and "+endPrice*10000;
                }
                else if(key.equals("state")){//검색기준이 진행상태인경우
                    sql += " where state= " + keyword;
                }
                else {
                    sql += " where " + key + " like '%" + keyword + "%' ";
                }
            }//if end
            //------------------------------------------------------



            //----------------------- 정렬기준 -------------------------
            if(sortkey==1) {
                sql += " order by price limit ? , ? ";
            }
            else if(sortkey==2){
                sql+=" order by rank1_count+rank2_count+rank3_count limit ? , ? ";
            }
            else{
                sql+=" order by start_date limit ? , ?";
            }
            //----------------------------------------------------------

            ps=conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardSize);
            rs=ps.executeQuery();
            while(rs.next()){
                ProjectDto projectDto=ProjectDto.builder()
                        .spjno(rs.getInt("spjno"))
                        .start_date(rs.getString("start_date"))
                        .end_date(rs.getString("end_date"))
                        .rank1_count(rs.getInt("rank1_count"))
                        .rank2_count(rs.getInt("rank2_count"))
                        .rank3_count(rs.getInt("rank3_count"))
                        .title(rs.getString("title"))
                        .compannyname(rs.getString("compannyname"))
                        .state(rs.getInt("state"))
                        .price(rs.getString("price"))
                        .build();

                projectDtos.add(projectDto);
            }//w end
            return projectDtos;
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }//m end

    //전체 영업 프로젝드 수 추출
    public int projectCount(String key, String keyword, int startPrice, int endPrice){
        System.out.println("J_projectPageDao.projectCount");
        try{
            String sql="select count(*) from salesproject as pj ";
            //------------- 검색기준을 선택한 경우 -------------------
            if(!key.equals("")){
                if(key.equals("price")){ //검색기준이 규모인 경우
                    sql+=" where price between "+startPrice*10000 +" and "+endPrice*10000;
                }
                else if(key.equals("state")){//검색기준이 진행상태인경우
                    sql += " where state= " + keyword;
                }
                else {
                    sql += " where " + key + " like '%" + keyword + "%' ";
                }
            }//if end
            //------------------------------------------------------
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                System.out.println("rs.getInt(1) = " + rs.getInt(1));
                return rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }//m end

    //프로젝트 세부 리스트 출력
    public ProjectDto getProjectDetail(int spjno){
        System.out.println("J_projectPageDao.getProjectDetail");
        try{
            String sql="select * from salesproject where spjno=?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, spjno);
            rs=ps.executeQuery();
            if(rs.next()){
                ProjectDto projectDto=new ProjectDto().builder()
                        .spjno( rs.getInt("spjno"))
                        .start_date( rs.getString("start_date"))
                        .end_date( rs.getString("end_date"))
                        .rank1_count( rs.getInt("rank1_count"))
                        .rank2_count( rs.getInt("rank2_count"))
                        .rank3_count( rs.getInt("rank3_count"))
                        .title( rs.getString("title"))
                        .request( rs.getString("request"))
                        .note( rs.getString("note"))
                        .compannyname( rs.getString("compannyname"))
                        .state( rs.getInt("state"))
                        .price( rs.getString("price"))
                        .build();
                return projectDto;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }//m end

    //프로젝트 내역 수정
    public boolean updateProjectDetail(ProjectDto projectDto){
        System.out.println("J_projectPageDao.updateProjectDetail");
        System.out.println("projectDto = " + projectDto);
        try{
            String sql="update salesproject set \n" +
                    "\tstart_date=?, \n" +
                    "\tend_date=? ,\n" +
                    "\trank1_count=? ,\n" +
                    "\trank2_count=? ,\n" +
                    "\trank3_count=? ,\n" +
                    "\ttitle=? ,\n" +
                    "\trequest=? ,\n" +
                    "\tnote=? ,\n" +
                    "\tcompannyname=? ,\n" +
                    "\tstate=? ,\n" +
                    "\tprice=?\n" +
                    "where spjno=?;";
            ps=conn.prepareStatement(sql);
            ps.setString(1,projectDto.getStart_date());
            ps.setString(2,projectDto.getEnd_date());
            ps.setInt(3,projectDto.getRank1_count());
            ps.setInt(4,projectDto.getRank2_count());
            ps.setInt(5,projectDto.getRank3_count());
            ps.setString(6,projectDto.getTitle());
            ps.setString(7,projectDto.getRequest());
            ps.setString(8,projectDto.getNote());
            ps.setString(9,projectDto.getCompannyname());
            ps.setInt(10,projectDto.getState());
            ps.setString(11,projectDto.getPrice());
            ps.setInt(12,projectDto.getSpjno());

            int count= ps.executeUpdate();
            System.out.println("count = " + count);
            if(count==1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }

        return false;
    }//m end

    //프로젝트 내역 삭제

    //수주 등록
    public int insertProject(ProjectDto projectDto){
        System.out.println("J_projectPageDao.insertProject");
        System.out.println("projectDto = " + projectDto);
        try{
            String[] key={"spjno"};
            String sql="insert into salesproject(start_date, end_date, rank1_count, rank2_count, rank3_count, title, request, note, compannyname, state, price) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?)";
            ps=conn.prepareStatement(sql, key);
            ps.setString(1,projectDto.getStart_date());
            ps.setString(2,projectDto.getEnd_date());
            ps.setInt(3,projectDto.getRank1_count());
            ps.setInt(4,projectDto.getRank2_count());
            ps.setInt(5,projectDto.getRank3_count());
            ps.setString(6,projectDto.getTitle());
            ps.setString(7,projectDto.getRequest());
            ps.setString(8,projectDto.getNote());
            ps.setString(9,projectDto.getCompannyname());
            ps.setInt(10,projectDto.getState());
            ps.setString(11,projectDto.getPrice());
            System.out.println("check");

            ps.executeUpdate();
            rs=ps.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }//if end
        }//try end
        catch (Exception e ){
            System.out.println("e = " + e);
        }
        return 0;
    }//m end


    //프로젝트 삭제
    public boolean deleteProject(int spjno){
        System.out.println("J_projectPageDao.deleteProject");
        System.out.println("spjno = " + spjno);
        try{
            String sql="delete from salesproject where spjno="+spjno;
            ps=conn.prepareStatement(sql);
            int count= ps.executeUpdate();

            if(count==1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }//m end

    //평가 가능한 프로젝트 리스트 출력
    public List<Object> doPrintPerform(int startRow, int pageBoardSize, int sortkey,
                                            String key, String keyword,
                                            int startPrice, int endPrice){
        System.out.println("J_projectPageDao.doPrintPerform");
        List<Object> list = new ArrayList<>();

        try{
            String sql="select * from (select * from uploadproject as a join salesproject as b using(spjno)) as pj inner join \n" +
                    "(select pjno as pno, case\n" +
                    "\t\t\t\t\t\twhen ((select min(score) from (select * from projectlog where pjno=pno) as b)!=0) then 2\n" +
                    "\t\t\t\t\t\twhen ((select max(score) from (select * from projectlog where pjno=pno) as b)>0) then 1\n" +
                    "\t\t\t\t\t\telse 0\n" +
                    "\t\t\t\t\tend as result from projectlog where state=1 group by pjno) \n" +
                    "as pp on pj.pjno=pp.pno ";

            //------------- 검색기준을 선택한 경우 -------------------
            if(!key.equals("")){
                if(key.equals("price")){ //검색기준이 규모인 경우
                    sql+=" where price between "+startPrice*10000 +" and "+endPrice*10000;
                }
                else {
                    sql += " where " + key + " like '%" + keyword + "%' ";
                }
            }//if end
            //------------------------------------------------------

            //----------------------- 정렬기준 -------------------------
            if(sortkey==1) {
                sql += " order by price limit ?,?";
            }
            else if(sortkey==2){
                sql+=" order by rank1_count+rank2_count+rank3_count limit ?,?";
            }
            else{
                sql+=" order by start_date limit ?,?";
            }
            //----------------------------------------------------------

            ps=conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardSize);
            rs=ps.executeQuery();

            while(rs.next()){
                System.out.println("rs = "+rs.getString("result"));
                //평가 row 1개저장
                ProjectDto3 projectDto3=ProjectDto3.builder()
                        .pjno(rs.getInt("pjno"))
                        .start_date(rs.getString("start_date"))
                        .end_date(rs.getString("end_date"))
                        .title(rs.getString("title"))
                        .request(rs.getString("request"))
                        .note(rs.getString("note"))
                        .compannyname(rs.getString("compannyname"))
                        .price(rs.getInt("price"))
                        .perFormState(rs.getInt("result"))
                        .build();
                System.out.println("projectDto3 result = " + projectDto3.getPerFormState());
                list.add(projectDto3);
            }//w end
            return list;
        }//t end
        catch (Exception e){
            System.out.println("e = " + e);
        }

        return null;
    }//m end

    //평가 가능한 프로젝트 수 출력
    public int getPerformListCount(int sortkey,
                                   String key, String keyword,
                                   int startPrice, int endPrice){
        System.out.println("J_projectPageDao.getPerformListCount");
        try {
            String sql = "select count(*) from (select * from uploadproject as a join salesproject as b using(spjno)) as pj inner join \n" +
                    "(select pjno as pno, case\n" +
                    "\t\t\t\t\t\twhen ((select min(score) from (select * from projectlog where pjno=pno) as b)!=0) then 2\n" +
                    "\t\t\t\t\t\twhen ((select max(score) from (select * from projectlog where pjno=pno) as b)>0) then 1\n" +
                    "\t\t\t\t\t\telse 0\n" +
                    "\t\t\t\t\tend as result from projectlog where state=1 group by pjno) \n" +
                    "as pp on pj.pjno=pp.pno ";

            //------------- 검색기준을 선택한 경우 -------------------
            if (!key.equals("")) {
                if (key.equals("price")) { //검색기준이 규모인 경우
                    sql += " where price between " + startPrice * 10000 + " and " + endPrice * 10000;
                } else {
                    sql += " where " + key + " like '%" + keyword + "%' ";
                }
            }//if end
            //------------------------------------------------------

            //----------------------- 정렬기준 -------------------------
            if (sortkey == 1) {
                sql += " order by price";
            } else if (sortkey == 2) {
                sql += " order by rank1_count+rank2_count+rank3_count";
            } else {
                sql += " order by start_date";
            }
            //----------------------------------------------------------

            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }//m end

    //상세 평가 프로젝트 리스트 출력
    public ProjectDto3 doPerformDetail(int pjno){
        System.out.println("J_projectPageDao.doPerformDetail");
        System.out.println("pjno = " + pjno);
        try{
            String sql="select * from (select * from uploadproject as a join salesproject as b using(spjno)) as pj inner join \n" +
                    "(select pjno as a, case\n" +
                    "\t\t\t\t\t\twhen ((select min(score) from (select * from projectlog where pjno=a) as b)!=0) then 2\n" +
                    "\t\t\t\t\t\twhen ((select max(score) from (select * from projectlog where pjno=a) as b)>0) then 1\n" +
                    "\t\t\t\t\t\telse 0\n" +
                    "\t\t\t\t\tend as result from projectlog where state=1 group by pjno) #case 끝\n" +
                    "as pp on pj.pjno=pp.a where pjno=?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, pjno);
            rs=ps.executeQuery();
            if(rs.next()){
                ProjectDto3 projectDto3=ProjectDto3.builder()
                        .pjno(rs.getInt("pjno"))
                        .start_date(rs.getString("start_date"))
                        .end_date(rs.getString("end_date"))
                        .title(rs.getString("title"))
                        .request(rs.getString("request"))
                        .note(rs.getString("note"))
                        .compannyname(rs.getString("compannyname"))
                        .price(rs.getInt("price"))
                        .perFormState(rs.getInt("result"))
                        .build();
                System.out.println("projectDto3 = " + projectDto3);
                return projectDto3;
            }//if end
        }//try end
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }//m end

    //프로젝트 참여 사원 정보 불러오기
    public List<Map<String,String>> getperformEmployee(int pjno){
        System.out.println("J_projectPageDao.getperformEmployee");
        System.out.println("pjno = " + pjno);
        List<Map<String,String >> list = new ArrayList<>();
        try{
            String sql="select c.eno, c.ename, p.pname, pj.pjno, pj.score,  pj.note, (coalesce(datediff(c.end_date,c.start_date),0))+coalesce(datediff(now(),c.edate),0) as alltime \n" +
                    "from (select * from employee as a left outer join  employeecareer as b using(eno))as c join part as p using(pno) \n" +
                    "inner join projectlog as pj on c.eno=pj.eno where pj.state=1 and pjno=?;";

            ps=conn.prepareStatement(sql);
            ps.setInt(1, pjno);
            rs=ps.executeQuery();
            while(rs.next()){
                Map<String , String > map=new HashMap<>();
                map.put("eno",rs.getString("eno"));
                map.put("ename",rs.getString("ename"));
                map.put("pname",rs.getString("pname"));
                map.put("pjno",rs.getString("pjno"));
                map.put("note",rs.getString("note"));
                map.put("score",rs.getString("score"));
                
                //사원 개별 rank 판별
                int allday=rs.getInt("alltime");
                if(allday < 1459 ){
                    map.put("rank" , "초급");
                }
                else if (allday > 3650) {
                    map.put("rank" , "고급");
                }
                else {
                    map.put("rank" , "중급");
                }

                //사원 개별 평가여부 판별
                int score=rs.getInt("score");
                System.out.println("score = " + score);
                map.put("performState",rs.getInt("score")==0 ? "평가전" : "평가완료");

                list.add(map);
            }//w end
            return list;
        }//try end
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }//m end

    //프로젝트 참여 사원 평가등록
    public boolean doInsertPerform(int pjno, int eno, String note, String score){
        System.out.println("J_projectPageDao.doInsertPerform");
        System.out.println("pjno = " + pjno + ", eno = " + eno + ", note = " + note + ", score = " + score);
        try {
            String sql = "update projectlog set note=?, score=? where pjno=? and eno=?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1,note);
            ps.setString(2,score);
            ps.setInt(3,pjno);
            ps.setInt(4,eno);
            int count=ps.executeUpdate();
            if(count==1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }

        return false;
    }//m end

    //관리자 식별 메소드(return type -> true : 관리자(인사과) , false : 일반사원)
    public boolean indexManager(int eno){
        System.out.println("J_projectPageService.indexManager");
        try{
            String sql="select pno from employee where eno="+eno;
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==1){
                    return true;
                }
                else{
                    return false;
                }//if 2 end
            }//if1 end
        }//t end
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }//m end

}//c end
