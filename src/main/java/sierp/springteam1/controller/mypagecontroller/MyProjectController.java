package sierp.springteam1.controller.mypagecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sierp.springteam1.model.dto.MyProjectDto;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.service.mypageService.MyProjectService;
import sierp.springteam1.service.mypageService.MypageService;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyProjectController {

    @Autowired
    MyProjectService myProjectService;

    @Autowired
    MypageService mypageService;

    // 내 프로젝트 페이지 요청
    @GetMapping("/project")
    public String myProjectView(){
        System.out.println("MyProjectController.myProjectView");
        return "/mypages/myproject";
    }

    // 진행 중인 프로젝트 출력
    @GetMapping("/project/list")
    @ResponseBody
    public MyProjectDto myProjectList(){
        System.out.println("MyProjectController.myProjectList");
        // 세션에서 로그인한 사원번호 가져오기.
        String eno = mypageService.sessionEno();
        return myProjectService.myProjectList(eno);
    }

    // 즐겨찾기한 프로젝트 전체 출력
    @GetMapping("/project/list/like")
    @ResponseBody
    public List<ProjectDto> myProjectLikeView(){
        System.out.println("MyProjectController.myProjectLikeView");
        // 세션에서 로그인한 사원번호 가져오기
        String eno = mypageService.sessionEno();
        System.out.println("MyProjectController.myProjectLikeView : eno = " + eno);
        return myProjectService.myProjectLikeView(eno);
    }

    // 내 이전 프로젝트 전체 출력
    @GetMapping("/project/previousList")
    @ResponseBody
    public  List<ProjectDto> myProjectPreviousView(){
        System.out.println("MyProjectController.myProjectPreviousView");
        // 세션에서 로그인한 사원번호 가져오기
        String eno = mypageService.sessionEno();
        return myProjectService.myProjectPreviousView(eno);
    }




}
