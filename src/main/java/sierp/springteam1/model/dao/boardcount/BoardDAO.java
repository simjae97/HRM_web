package sierp.springteam1.model.dao.boardcount;

import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.model.dto.SalaryDto;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class BoardDAO extends SuperDao {

    public int getBoardSize( String tablename ,int state ,String key , String keyWord){
        try{
            String sql = "select count(*) from "+tablename;
            // ==================== 1.만약에 카테고리 조건이 있으면 where 추가.
//            if( state > -2 ){ sql += " where state = "+state ; }
//            if( !keyWord.isEmpty()){System.out.println("검색 키워드가 존재");
//                if(state > -2){ sql += " and ";}
//                else{sql += " where ";}
//                sql +=  key+" like '%"+keyWord+"%' ";
//            }
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if( rs.next() ){ return rs.getInt(1); }
        }catch (Exception e ){  System.out.println("카운트중 발생한 오류 = " + e);}
        return -1;
    }

    public List<Object> doGetBoardViewList(String tablename ,int startRow , int pageBoardSize , int state  , String key , String keyWord ){ System.out.println("BoardDao.doGetBoardViewList");
        List<Object> list = new ArrayList<>();
        System.out.println("startRow = " + startRow + ", pageBoardSize = " + pageBoardSize + ", state = " + state + ", key = " + key + ", keyword = " + keyWord);
        try{  // String sql = "select * from board";
            // SQL 앞부분
            String sql = "select * from "+tablename;
//             SQL 가운데부분 [ 조건에 따라 where 절 추가 ]
            if( state > -2 ){ sql += " where state = "+state ; }
            if( !keyWord.isEmpty()){System.out.println("검색 키워드가 존재");
                if(state > -2){ sql += " and ";}
                else{sql += " where ";}
                sql +=  key+" like '%"+keyWord+"%' ";
            }
            // SQL 뒷부분
            sql += " limit ? , ?";

            ps =conn.prepareStatement(sql);
            ps.setInt( 1 , startRow );
            ps.setInt( 2 , pageBoardSize );

            rs = ps.executeQuery();
            while ( rs.next() ){
                if(tablename.contains("salarylog"))
                    list.add(SalaryDto.builder().employeeDto(EmployeeDto.builder()
                         .eno(rs.getInt("eno"))
                         .id(rs.getString("id"))
                         .ename(rs.getString("ename"))
                         .build())
                         .sno(rs.getInt("sno"))
                         .smonth(rs.getString("smonth"))
                         .price(Double.parseDouble(rs.getString("price")))
                         .build()
                    );
                else if(tablename.contains("salary")){
                    list.add(SalaryDto.builder()
                            .employeeDto(EmployeeDto.builder()
                                    .eno(rs.getInt("eno"))
                                    .id(rs.getString("id"))
                                    .ename(rs.getString("ename"))
                                    .email(rs.getString("email"))
                                    .phone(rs.getString("phone"))
                                    .address(rs.getString("address"))
                                    .sex(rs.getBoolean("sex"))
                                    .build())
                            .price(Double.parseDouble(rs.getString("price")))
                            .smonth(rs.getString("smonth"))
                            .build());
                }

            }
        }
        catch (Exception e ){
            System.out.println("e = " + e);
        }

        return list;
    }

}
