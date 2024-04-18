package sierp.springteam1.service.employeeserive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import sierp.springteam1.model.dao.EmployeeDao;
import sierp.springteam1.model.dao.mypageDao.MypageDao;
import sierp.springteam1.model.dto.*;
import sierp.springteam1.service.j_projectPage.J_projectPageService;
import sierp.springteam1.service.mypageService.MypageService;
import sierp.springteam1.service.secureservice.SecureService;

import java.util.List;
import java.util.Random;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private MypageDao mypageDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private J_projectPageService j_projectPageService;

    @Autowired
    private SecureService secureService;

    //사원등록 요청
    public boolean eSignup(EmployeeDto employeeDto){
        System.out.println("EmployeeService.eSignup");
        //증명사진 파일 처리
        String fileName="";
        System.out.println("employeeDto.getMfile() = " + employeeDto.getMfile());
        if(!employeeDto.getMfile().isEmpty()) {
            fileName = fileService.eFileUpload(employeeDto.getMfile());
            if (fileName == null) { // 업로드 성공했으면
                return false;
            }
        }
        //2. DB
        //dto에 업로드 성공한 파일명을 대입한다
        employeeDto.setImg(fileName);

        //1. 아이디 생성 / 후보 : 1.사원번호+이름 2.사원이메일 앞부분 3.?
        //샘플
        String id= employeeDto.getEmail().split("@")[0];
        //2. 초기 비밀번호 난수 부여
        String newPw="";
        Random random=new Random();
        for(int i=1; i<=6; i++){
            newPw+=random.nextInt(10);
        }
        System.out.println("newPw = " + newPw);

        employeeDto.setId(id);
        employeeDto.setPw(newPw);
        //암호화
        employeeDto = secureService.doSec(employeeDto);
        System.out.println(employeeDto);
         boolean result=employeeDao.eSignup(employeeDto);
         String content="귀하의 입사를 축하드리며 자사 프로그램을 사용할 수 있는 아이디와 임시 비밀번호를 알려드립니다. \n 아이디 : "+employeeDto.getId()+"\n 비밀빈호 : "+employeeDto.getPw()+"\n 임시 비밀번호이기 때문에 로그인하시고 바꿔주시길 바랍니다.";
        //*이메일 테스트
        if(result){emailService.send(employeeDto.getEmail(),"팀 프로젝트 로그인 정보 안내",content);}
        return result;

    }

    // 경력로그 등록 요청
    public boolean careerPost(EmployeeCareerDto careerDto){
        System.out.println("EmployeeService.cSignup");
        //경력증명서 파일 처리
        String fileName="";
        System.out.println("careerDto.getCimg() = " + careerDto.getCimg());
        if(!careerDto.getCimg().isEmpty()) {
            fileName = fileService.cFileUpload(careerDto.getCimg());
            if (fileName == null) { // 업로드 성공했으면
                return false;
            }
        }
        //2. DB
        //dto에 업로드 성공한 파일명을 대입한다
        careerDto.setEimg(fileName);
        return employeeDao.careerPost(careerDto);
    }

    // 자격증로그 등록 요청
    public boolean lSignup(EmployeeLicenseDto licenseDto){
        System.out.println("EmployeeService.lSignup");
        return employeeDao.lSignup(licenseDto);
    }
    //=================== 삭제
    //사원 삭제
    public boolean employeeDelete(int eno){
        return employeeDao.employeeDelete(eno);
    }
    //경력 삭제
    public boolean careerDelete(int eno, String companyname){return employeeDao.careerDelete(eno,companyname);}

    //자격증 삭제
    public boolean licenseDelete(int eno,int lno){
        return employeeDao.licenseDelete(eno,lno);
    }
        //=================== 수정
    // 사원정보 수정
    public boolean employeeUpdate (EmployeeDto employeeDto){
            //1. 기존 첨부파일명 구하고
        String bfile = mypageDao.doGetLoginInfo((employeeDto.getEno()+"")).getImg();
        // - 새로운 첨부파일이 있다. 없다.
        if(!employeeDto.getMfile().isEmpty()){// 수정시 새로운 첨부파일이 있으면
            //새로운  첨부파일을 업로드하고 기존 첨부파일 삭제
            String fileName =fileService.eFileUpload(employeeDto.getMfile());
            if(fileName !=null){
                employeeDto.setImg(fileName); // 새로운첨부파일의 이름 Dto 대입
                // 기존 첨부파일 삭제
                //2. 기존 첨부파일 삭제
                fileService.fileDelete(bfile);
            }else {
                return false; // 업로드 실패
            }
        }else {
            employeeDto.setImg(bfile); // 새로운 첨부파일이 없으면 기존 첨부파일명 그대로 대입
        }
        //암호화
        employeeDto = secureService.doSec(employeeDto);
        return employeeDao.employeeUpdate(employeeDto);
    }

    //===================호출
    // 부서 전체 호출
    public List<PartDto> partList (){
        System.out.println("EmployeeService.partDtoList");
        return employeeDao.partList();
    }
    //자격증 선택 호출
    public List<LicenseDto> licenseList(){
        return employeeDao.licenseList();
    }

    // 사원 전체 호출
    public ProjectPageDto employeeList(int page, int pageBoardSize, int key,  String keyword){
        System.out.println("EmployeeService.employeeList");
        //1. start row : 시작할 게시물의 행순서
        int startRow=(page-1)*pageBoardSize;

        //2. 총 페이지 수
        int totalRecode=employeeDao.countEmployeeList(key, keyword);  //총 레코트 출력

        //**페이징 dto 저장 메소드(매개변수 -> page:현재페이지 , totalRecode:전체게시물수 , pageBoardSize:한페이지당 게시물수)
        //리턴 : ProjectPageDto
        ProjectPageDto projectPageDto = j_projectPageService.deliverPageInfo(page, totalRecode, pageBoardSize);

        //3. 한페이지당 List
        //dao에서 한페이지에 나타낼 게시물 리스트 가져오기
        projectPageDto.setObjectList(employeeDao.employeeList(startRow, pageBoardSize, key, keyword));

        return projectPageDto;
    }

    //경력 전체 호출
    public List<EmployeeCareerDto> careerList(int eno){
        System.out.println("EmployeeService.careerList");
        return employeeDao.careerList(eno);
    }
    //자격증 호출
    public List<EmployeeLicenseDto> licenseViewList(int eno){
        System.out.println("eno = " + eno);
        return employeeDao.licenseViewList(eno);
    }

    // 자격증 중복 검색
    public String findLicense(EmployeeLicenseDto licenseDto){
        return employeeDao.findLicense(licenseDto);
    }

    // 합산 경력 호출
    public String careearSum(int eno){
        return employeeDao.careearSum(eno);
    }
}


