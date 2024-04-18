package sierp.springteam1.model.dao.salaryDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dao.boardcount.BoardDAO;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.model.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class SalaryDAO extends SuperDao {

    public boolean insertSalarylog(List<SalaryDto> result){
        String sql = "INSERT INTO salarylog (eno, price) VALUES (?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            for (SalaryDto dto : result) {
                ps.setInt(1, dto.getEmployeeDto().getEno());
                ps.setDouble(2, dto.getPrice());
                ps.addBatch(); // 배치 처리를 위해 추가
            }
            int[] counts = ps.executeBatch(); // 배치 쿼리 실행
            System.out.println("Inserted rows: " + Arrays.toString(counts));
            return true;
        } catch (SQLException e) {
            System.out.println("삽입도중 발생한 오류"+e);
        }
        return false;
    }


    public boolean deleteSalary(SalaryDto salaryDto){
        try {
            System.out.println(salaryDto.getSmonth());
        System.out.println(salaryDto.getEmployeeDto().getEno());
            System.out.println();
            String sql = "delete from salary where eno =? and smonth = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,salaryDto.getEmployeeDto().getEno());
            ps.setString(2,salaryDto.getSmonth());
            int count = ps.executeUpdate();
            return true;
        }
        catch (Exception e){
            System.out.println("샐러리(입금대기 테이블) 삭제도중 발생한 오류입니다 = " + e);
        }
        return false;
    }


    public boolean deleteSalary(List<SalaryDto> result){
        try {
            for (SalaryDto i : result) {
                System.out.println(i.getSmonth());
                System.out.println(i.getEmployeeDto().getEno());
                System.out.println();
                String sql = "delete from salary where eno =? and smonth = ? ";
                ps = conn.prepareStatement(sql);
                ps.setInt(1,i.getEmployeeDto().getEno());
                ps.setString(2,i.getSmonth());
                int count = ps.executeUpdate();
            }
            return true;
        }
        catch (Exception e){
            System.out.println("샐러리(입금대기 테이블) 삭제도중 발생한 오류입니다 = " + e);
        }
        return false;
    }

    public void insertSalray(List<SalaryDto> result){
        try {
            String sql = "insert into salary(eno,price) values" ;
            for (SalaryDto i : result) {
                     sql += "("+i.getEmployeeDto().getEno()+","+i.getPrice()+"),";
            }
            String sql2 = sql.substring(0, sql.length()-1);
            System.out.println("sql = " + sql2);
            ps = conn.prepareStatement(sql2);
            int count = ps.executeUpdate();
            System.out.println("count = " + count);
        }
        catch (Exception e){
            System.out.println("임금 삽입중 발생한 오류 = " + e);
        }

    }

    public List<SalaryDto> findSalarylist(){
        List<SalaryDto> result = new ArrayList<>();
        try {
            String sql = "select a.price , b.* from salary as a inner join employee as b on a.eno = b.eno;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(SalaryDto.builder()
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
                        .build());
            }
        }
        catch (Exception e){
            System.out.println("지급예정 임금 리스트 호출중 발생한 문제 = " + e);
        }
        return result;
    }

    public List<SalaryDto> findSalary(List<EmployeeDto> allEmployee , int allwork){
        List<SalaryDto> result = new ArrayList<>();
        try {
            for (EmployeeDto employeeDto : allEmployee) {
                String sql = "select a.eno as eno , a.pprice as pprice , b.근무일자 as 근무일자 from\n" +
                        "(\n" +
                        "select eno,pprice from\n" +
                        "price as z\n" +
                        "cross join \n" +
                        "(\n" +
                        "select a.eno , (sum(coalesce(datediff(b.end_date,b.start_date),0))+datediff(now(),a.edate)) as alltime \n" +
                        "from employee as a left outer join  employeecareer as b on a.eno = b.eno\n" +
                        "where a.eno in(\n" +
                        "      select d.eno\n" +
                        "      from \n" +
                        "      (select b.pjno , end_date, a.eno  from uploadproject b join salesproject d on b.spjno = d.spjno \n" +
                        "      right outer join projectlog a\n" +
                        "       on a.pjno = b.pjno) as c \n" +
                        "      right outer join\n" +
                        "       employee d \n" +
                        "       on c.eno = d.eno\n" +
                        "       group by d.eno\n" +
                        "\t)\t\n" +
                        "\tgroup by a.eno\n" +
                        ")\n" +
                        "as t\n" +
                        "where z.p_start<= t.alltime and t.alltime <= z.p_end ) as a \n" +
                        "inner join \n" +
                        "(select  eno, count(*) as 근무일자  from attendance_log WHERE MONTH(jday) = MONTH(CURRENT_DATE) - 1 and TIMESTAMPDIFF(HOUR, stat_time, end_time) >= 8  group by eno) as b\n" +
                        "on a.eno = b.eno\n" +
                        "where a.eno = "+employeeDto.getEno();
                ps =conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if(rs.next()){
                    double price = Math.ceil(1.0*rs.getInt("근무일자")/allwork*rs.getInt("pprice"));
                    System.out.println("price = " + price);
                    result.add(SalaryDto.builder()
                            .employeeDto(employeeDto)
                            .price(price)
                            .build());
                }
            }

        }
        catch (Exception e){
            System.out.println("샐러리다오의 파인드샐러리함수 오류 = " + e);
        }

        return result;
    }
}
