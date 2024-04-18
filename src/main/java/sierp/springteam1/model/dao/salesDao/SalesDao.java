package sierp.springteam1.model.dao.salesDao;


import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectDto2;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalesDao extends SuperDao{

    public boolean salesPost(int spjno){
        try {
            String sql = "insert into uploadproject(spjno) values (?)";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,spjno);
            int count = ps.executeUpdate();
            System.out.println("count = " + count);
            if(count == 1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    public List<Object> saleslist(int startRow, int pageBoardSize,
                                      int sortkey,
                                      String key, String keyword,
                                      int startPrice, int endPrice){
        List<Object> projectDtos=new ArrayList<>();
        try{
            String sql="select * from uploadproject as a inner join salesproject as b on a.spjno = b.spjno  ";

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
                sql += " order by price limit ? , ? ";
            }
            else if(sortkey==2){
                sql+=" order by rank1_count+rank2_count+rank3_count limit ? , ? ";
            }
            else{
                sql+=" order by start_date limit ? , ? ";
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
                System.out.println(rs.getInt("pjno"));
                projectDtos.add(projectDto);
            }//w end
            return projectDtos;
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }
    public int projectCount(){
        try{
            String sql="select count(*) from uploadproject";
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


    public ProjectDto oneProject(int spjno){
        ProjectDto projectDto = null;
        try {
            String sql = "select * from salesproject where spjno=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,spjno);
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

    public int findpjno(int spjno){
        try {
            String sql = "select pjno from uploadproject where spjno ="+spjno;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;}

    public boolean salesdel(int pjno){
        try {
            String sql = "delete from uploadproject where pjno="+pjno;
            ps = conn.prepareStatement(sql);
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }
}
