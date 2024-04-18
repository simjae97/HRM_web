package sierp.springteam1.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sierp.springteam1.model.dao.EmployeeDao;
import sierp.springteam1.model.dto.*;
import sierp.springteam1.service.employeeserive.EmployeeService;
import sierp.springteam1.service.j_projectPage.J_projectPageService;
import sierp.springteam1.service.mypageService.MypageService;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MypageService mypageService;
    //================ 등록 요청

    //사원등록 요청
    @PostMapping("/signup")
    @ResponseBody
    public boolean eSignup(EmployeeDto employeeDto){
        System.out.println("EmployeeController.eSignup");
        return employeeService.eSignup(employeeDto);
    }

    // 경력로그 등록 요청
    @PostMapping("/careerPost")
    @ResponseBody
    public boolean careerPost(EmployeeCareerDto careerDto){
        System.out.println("EmployeeController.cSignup");
        System.out.println(careerDto);
        return employeeService.careerPost(careerDto);
    }

    // 자격증로그 등록 요청
    @PostMapping("/licensePost")
    @ResponseBody
    public boolean lSignup(EmployeeLicenseDto licenseDto){
        System.out.println("EmployeeController.lSignup");
        return employeeService.lSignup(licenseDto);
    }
    //================ 삭제 요청
    // 사원 삭제 요청
    @DeleteMapping("/employee/delete")
    @ResponseBody
    public boolean employeeDelete(int eno){
        return employeeService.employeeDelete(eno);
    }

    @DeleteMapping("/license/delete")
    @ResponseBody
    public boolean licenseDelete(int eno,int lno){
        return employeeService.licenseDelete(eno,lno);
    }

    @DeleteMapping("/career/delete")
    @ResponseBody
    public boolean careerDelete(int eno, String companyname){
        System.out.println("companyname = " + companyname);
        return employeeService.careerDelete(eno,companyname);}
    //================== 수정
    // 사원 정보 수정
    @PutMapping("/employee/update.do")
    @ResponseBody
    public boolean employeeUpdate (EmployeeDto employeeDto){
        return employeeService.employeeUpdate(employeeDto);
    }


    //================= 페이지 요청
    // 인사관리 페이지 요청
    @GetMapping("/employee")
    public String getemployee(){
        return "/employee/employee";
    }

    // 개별인사관리 페이지 요청
    @GetMapping("/employee/view")
    public String employeeView(int eno){
        return "/employee/employeeView";
    }

    //사원등록 페이지 요청
    @GetMapping("/signup")
    public String viewSignup(){
        System.out.println("EmployeeController.viewSignup");
        return "/employee/signup";
    }
    //사원정보 수정 페이지 요청
    @GetMapping("/employee/update")
    public String employeeUpdateView(){
        System.out.println("EmployeeController.viewSignup");
        return "/employee/employeeUpdate";
    }

    //=========== 출력

    //부서명 전체 출력
    @GetMapping("/partList")
    @ResponseBody
    public List<PartDto> partList (){
        System.out.println("EmployeeController.partDtoList");
        return employeeService.partList();
    }

    // 자격증 종류 출력
    @GetMapping("/licenseSelect")
    @ResponseBody
    public List<LicenseDto> licenseList(){
        System.out.println("EmployeeController.licenseList");
        return employeeService.licenseList();
    }

    // 전체 사원 호출
    @GetMapping("/employeeList")
    @ResponseBody
    public ProjectPageDto employeeList(@RequestParam int page, int pageBoardSize, int key,@RequestParam String keyword){
        System.out.println("EmployeeController.employeeList");

        return employeeService.employeeList(page,pageBoardSize, key,keyword);
    }
    // 개별 사원 호출
    @GetMapping("/employee/view.do")
    @ResponseBody
    public EmployeeDto getEmployeeView(String eno){
        //String eno=(String)eno1;
        return mypageService.doGetLoginInfo(eno);
   }

    //사원 경력 로그 출력
    @GetMapping("/careerView")
    @ResponseBody
    public List<EmployeeCareerDto> careerList(int eno){
        System.out.println("EmployeeController.careerList");
        return employeeService.careerList(eno);
    }

    // 사원 자격증 로그 출력
    @GetMapping("/licenseView")
    @ResponseBody
    public List<EmployeeLicenseDto> licenseViewList(int eno){
        System.out.println("eno = " + eno);
        return employeeService.licenseViewList(eno);
    }

    // 자격증 중복 검색
    @PostMapping("/findLicense")
    @ResponseBody
    public String findLicense(EmployeeLicenseDto licenseDto){
        return employeeService.findLicense(licenseDto);
    }

    @GetMapping("/careerSum")
    @ResponseBody
    public String careearSum(int eno){
        System.out.println("EmployeeController.careearSum");
        return employeeService.careearSum(eno);
    }

}
