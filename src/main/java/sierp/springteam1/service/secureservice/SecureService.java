package sierp.springteam1.service.secureservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sierp.springteam1.model.dao.EmployeeDao;
import sierp.springteam1.model.dto.EmployeeDto;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecureService {
    private static int SALT_SIZE = 16;

    @Autowired
    EmployeeDao employeeDao;
//    public String set_User(String ID, byte[] Password) throws Exception {
//        String SALT = getSALT();
//        return SALT;
//    }

    public int login(String id,String pw){
        try {
            String newpw = get_UsersPW(id,pw);
            int logineno = employeeDao.login(id,newpw);
            return logineno;
        }
        catch (Exception e){
            System.out.println("로그인 해시검증중 발생한 오류"+e);
        }

        return -1;
    }
    public EmployeeDto doSec(EmployeeDto employeeDto){ //salt부여와 그에따른 비밀번호 변경, 생성 및 비밀번호 수정시 같이 이뤄지게 할 예정
        //암호화부분
        try {
            String[] result = get_UsersPW(employeeDto.getPw());
            employeeDto.setSalt(result[0]);
            System.out.println("새로운 소금 = "+result[0]);
            employeeDto.setPw(result[1]);
        }
        catch (Exception e){
            System.out.println("암호화중 발생한 문제"+e);
        }
        //암호화부분종료

        return employeeDto;
    }

    private String[] get_UsersPW (String password) throws Exception { //가입시 사용,salt값이 아직 DB에 저장되어있지 않으므로
        String salt = getSALT();
        String temp_pass = Hashing(password, salt);	// 얻어온 Salt 와 password 를 조합해본다.

        return new String[]{salt, temp_pass};

    }

    public String get_UsersPW (String id, String password){
        String temp_pass ="";
        try {

            String temp_salt = employeeDao.findSalt(id);                    // 해당 ID의 SALT 값을 찾는다

            temp_pass = Hashing(password, temp_salt);    // 얻어온 Salt 와 password 를 조합해본다.
        }
        catch (Exception e){
            System.out.println(e);
        }
        return temp_pass;

    }


    private String Hashing(String rawpassword, String Salt) throws Exception {
        byte[] password =rawpassword.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");	// SHA-256 해시함수를 사용

        // key-stretching
        for(int i = 0; i < 10000; i++) {                    //10000번의 다이제스트
            String temp = Byte_to_String(password) + Salt;	// 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.getBytes());						// temp 의 문자열을 해싱하여 md 에 저장해둔다
            password = md.digest();							// md 객체의 다이제스트를 얻어 password 를 갱신한다
        }

        return Byte_to_String(password);
    }

    private String getSALT() throws Exception {
        SecureRandom rnd = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        rnd.nextBytes(temp);

        return Byte_to_String(temp);

    }

    private String Byte_to_String(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for(byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }
}
