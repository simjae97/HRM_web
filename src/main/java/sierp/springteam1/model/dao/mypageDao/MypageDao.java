package sierp.springteam1.model.dao.mypageDao;

import org.springframework.stereotype.Component;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.EmployeeDto;

@Component
public class MypageDao extends SuperDao {


    // 사원 정보 요청
    public EmployeeDto doGetLoginInfo(String eno){
        System.out.println("MypageDao.doGetLoginInfo");
        System.out.println("MypageDao eno = " + eno);

        EmployeeDto employeeDto = null;
        try {
            String sql = "select * from employee em , part p where em.pno = p.pno and em.eno=?";
            ps = conn.prepareCall(sql);
            ps.setString(1, eno);
            rs = ps.executeQuery();
            // 한명 이라서 if
            if (rs.next()){
                // 빌드 패턴 사용
                employeeDto = EmployeeDto.builder()
                        .eno(rs.getInt("eno"))
                        .eeducation(rs.getString("eeducation"))
                        .id(rs.getString("id"))
                        .ename(rs.getString("ename"))
                        .email(rs.getString("email"))
                        .phone(rs.getString("phone"))
                        .address(rs.getString("address"))
                        .sex(rs.getBoolean("sex"))//성별
                        .img(rs.getString("img"))
                        .edate(rs.getString("edate"))
                        .pno(rs.getInt("pno"))
                        .pname(rs.getString("pname"))
                        .pw(rs.getString("pw"))
                        .build();
            }
        }catch (Exception e){
            System.out.println("EmployeeDto doGetLoginInfo e = " + e);
        }

        return employeeDto;
    }


    // 사원 번호로 사원 아이디 반환
    public String doGetNameInfo(String eno){
        System.out.println("MypageDao.doGetNameInfo");
        System.out.println("MypageDao.doGetNameInfo eno = " + eno);
        String id = "";
        try {
            String sql = "select id from employee where eno = ?;";
            ps = conn.prepareCall(sql);
            ps.setString(1, eno);
            rs = ps.executeQuery();
            // 한명 이라 if
            if (rs.next()){
                id = rs.getString(1);
            }
        }catch (Exception e){
            System.out.println("MypageDao.doGetNameInfo e = " + e);
        }
        System.out.println("id = " + id);
        return id;
    }

    // 사원 번호로 사원 비밀번호 반환


    // 마이페이지 내 개인정보 수정 요청
    public boolean doMypageUpdate( EmployeeDto employeeDto){
        System.out.println("MypageDao.doMypageUpdate");
        System.out.println("employeeDto = " + employeeDto);
        try {
        String sql = "update employee set email = ? , phone = ? ,address = ? where eno = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,employeeDto.getEmail());
        ps.setString(2,employeeDto.getPhone());
        ps.setString(3,employeeDto.getAddress());
        ps.setString(4, String.valueOf(employeeDto.getEno()));
        int count = ps.executeUpdate();
        if (count == 1){
            return true;
        }
        }catch (Exception e){
            System.out.println("MypageDao.doMypageUpdate : e = " + e);
        }
        return false;
    }

    // 마이페이지 비밀번호 수정 요청
    public boolean doMypageUpdatePw(String eno, String newpw){ // pwc : 변경 패스워드
        System.out.println("MypageDao.doMypageUpdatePw");
        try {
            String sql = "update employee set pw = ? where eno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,newpw);
            ps.setString(2,eno);
            int count = ps.executeUpdate();
            if (count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("MypageDao.doMypageUpdatePw :e = " + e);
        }
        return false;
    }

    // 사원 번호로 패스워드 반환
    public String passwordCheck(String eno){
        System.out.println("MypageDao.passwordCheck");
        System.out.println("MypageDao.passwordCheck : eno = " + eno);
        try {
            String sql = "select pw from employee where eno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eno);
            rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("rs.getString(1) = " + rs.getString(1));
                return rs.getString(1);
            }
        }catch (Exception e){
            System.out.println("MypageDao.passwordCheck : e = " + e);
        }
        
        return null;
    }

/*
    int eno;
    String eeducation;  // 학력 ( 고졸 , 초대졸 , 대졸 )
    String id;          // 아이디
    String pw;          // 비밀번호
    String ename;       // 이름
    String email;       // 이메일
    String phone;       // 전화번호
    String address;     // 주소
    boolean sex;        // 성별 0 남성 1 여성
    String img;         // 업로드한 이미지 이름
    String edate;       // 입사일 ( 등록일 )
    int pno;            // 파트 ( 업무 부서 )
    MultipartFile mfile; // 파일 업로드


* */

}
