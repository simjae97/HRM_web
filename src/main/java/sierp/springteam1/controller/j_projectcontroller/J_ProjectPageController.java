package sierp.springteam1.controller.j_projectcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sierp.springteam1.model.dao.j_projectPageDao.J_projectPageDao;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectDto3;
import sierp.springteam1.model.dto.ProjectPageDto;
import sierp.springteam1.model.dto.ProjectlogDto;
import sierp.springteam1.service.j_projectPage.J_projectPageService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/projectPage")
public class J_ProjectPageController {
    @Autowired
    J_projectPageService j_projectPageService;
    @Autowired
    private HttpServletRequest request;


    //프로젝트 관리메뉴 출력
    @GetMapping("/")
    public String test(){
        System.out.println("Jcontroller1.test");
        return "/j_projectPage/salesPage";
    }//m end
    
    //수주 전체 리스트 출력
        @GetMapping("/list")
    @ResponseBody
    public ProjectPageDto printProjectList(int page, int pageBoardSize,
                                           int sortKey,
                                           String key, String keyword,
                                            int startPrice, int endPrice){
        System.out.println("J_ProjectPageController.printProjectList");
        System.out.println("page = " + page + ", pageBoardSize = " + pageBoardSize + ", key = " + key + ", keyword = " + keyword + ", startPrice = " + startPrice + ", endPrice = " + endPrice);

        return j_projectPageService.printProjectList(page, pageBoardSize,sortKey, key, keyword, startPrice, endPrice);
    }//m end
    
    //수주 세부리스트 출력
    @GetMapping("/detail")
    public String printProjectDetail(int spjno){
        System.out.println("J_ProjectPageController.printProjectDetail");

        return "/j_projectPage/salesDetail";
    }//m end

    //수주 세부 리스트 정보 가져오기
    @GetMapping("/detail.do")
    @ResponseBody
    public ProjectDto getProjectDetail(int spjno){
        System.out.println("J_ProjectPageController.printProjectDetail");

        return j_projectPageService.getProjectDetail(spjno);
    }//m end

    //수주 내역 수정페이지 호출
    @GetMapping("/update")
    public String updateProjectDetailPage(){
        System.out.println("J_ProjectPageController.updateProjectDetail");

        return "/j_projectPage/updateProject";
    }//m end

    //수주 내역 수정
    @PutMapping("/update.do")
    @ResponseBody
    public boolean updateProjectDetail(ProjectDto projectDto){
        System.out.println("J_ProjectPageController.updateProjectDetail");
        System.out.println("projectDto = " + projectDto);

        return j_projectPageService.updateProjectDetail(projectDto);
    }//m end

    //프로젝트 내역 삭제

    //수주 등록 페이지 출력
    @GetMapping("/insert")
    public String insertProjectPage(){
        return "/j_projectPage/insertProject";
    }//m end

    //수주 등록
    @PostMapping("/insert.do")
    @ResponseBody
    public int insertProject(ProjectDto projectDto){
        System.out.println("J_ProjectPageController.insertProject");
        System.out.println("projectDto = " + projectDto);

        return j_projectPageService.insertProject(projectDto);
    }//m end

    //수주 삭제
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean deleteProject(int spjno){
        System.out.println("J_ProjectPageController.deleteProject");
        System.out.println("spjno = " + spjno);

        return j_projectPageService.deleteProject(spjno);
    }//m end

    //평가 가능한 프로젝트 페이지 출력
    @GetMapping("/perform")
    public String printPerform(){
        System.out.println("J_ProjectPageController.pringPerform");

        return "/j_projectPage/perform";
    }//m end

    //평가 가능한 프로젝트 리스트 출력
    @GetMapping("/perform.do")
    @ResponseBody
    public ProjectPageDto doPrintPerform(int page, int pageBoardSize, int sortKey,
                                         String key, String keyword,
                                         int startPrice, int endPrice){
        System.out.println("J_ProjectPageController.doPrintPerform");

        return j_projectPageService.doPrintPerform(page, pageBoardSize, sortKey,key, keyword,startPrice,endPrice);
    }//m end
    
    //상세 평가 프로젝트 페이지 출력
    @GetMapping("/performDetail")
    public String printPerformDetail(){
        System.out.println("J_ProjectPageController.printPerformDetail");
        return "/j_projectPage/performDetail";
    }//m end

    //상세 평가 프로젝트 리스트 출력
    @GetMapping("/performDetail.do")
    @ResponseBody
    public ProjectDto3 doPerformDetail(int pjno){
        System.out.println("J_ProjectPageController.doPerformDetail");
        System.out.println("pjno = " + pjno);
        return j_projectPageService.doPerformDetail(pjno);
    }//m end

    //프로젝트 참여 사원 정보 불러오기
    @GetMapping("/performEmployee")
    @ResponseBody
    public List<Map<String,String>> getperformEmployee(int pjno){
        System.out.println("J_ProjectPageController.getperformEmployee");
        System.out.println("pjno = " + pjno);

        return j_projectPageService.getperformEmployee(pjno);
    }//m end

    //프로젝트 참여 사원 평가등록
    @PostMapping("/insertPerform.do")
    @ResponseBody
    public boolean doInsertPerform(int pjno, int eno, String note, String score){
        System.out.println("J_ProjectPageController.doInsertPerform");
        System.out.println("pjno = " + pjno + ", eno = " + eno + ", note = " + note + ", score = " + score);

        return j_projectPageService.doInsertPerform(pjno, eno, note, score);
    }//m end


}//c end
