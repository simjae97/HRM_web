package sierp.springteam1.controller.projectcontroller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sierp.springteam1.controller.salarycontroller.Salarycontroller;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectlogDto;
import sierp.springteam1.model.dto.PsendEmployeeDto;
import sierp.springteam1.service.mypageService.MyProjectService;
import sierp.springteam1.service.mypageService.MypageService;
import sierp.springteam1.service.projectservice.OneprojectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class OneprojectController {

    @Autowired
    OneprojectService oneprojectService;
    @Autowired
    MypageService mypageService;

    @Autowired
    Salarycontroller salarycontroller;
    //세부게시물 페이지 출력
    @GetMapping("/view")
    public String oneProjectview(int pjno){
        return "project/oneproject";
    }
    //게시물의 인원 배정페이지 출력
    @GetMapping("/view/rec")
    public String oneProjectd(int pjno){
        return "project/recproject";
    }

    @GetMapping("/view/re")
    public String updateProjectd(int pjno){
        return "project/upemp";
    }


    @GetMapping("/view/eval")
    public String evalProject(){
        return "project/evalproject";
    }
//    @GetMapping("/view/rec.check")
//    @ResponseBody
//    public int findscore(int eno){
//
//        return oneprojectService.findscore(eno);
//    }

    @GetMapping("/view.do")
    @ResponseBody
    public ProjectDto oneProject(int pjno){
        System.out.println("OneprojectController.oneProject");
        return oneprojectService.oneProject(pjno);
    }
    @GetMapping("/view/re.do")
    @ResponseBody
    public ArrayList<PsendEmployeeDto>[] updatememberlist(int pjno){
        return oneprojectService.updatememberlist(pjno);
    }
    @GetMapping("/view/rec.do")
    @ResponseBody
    public  ArrayList<PsendEmployeeDto>[] memberlist(@RequestParam int pjno){
        System.out.println("안뇽안뇽");
        return oneprojectService.memberlist(pjno);
    }

    @PostMapping("/view/assign")
    @ResponseBody
    public boolean createprojectlog(@RequestBody ProjectlogDto projectlogDto){
        for(ArrayList<Integer> i : projectlogDto.getEnos()){
            for(int j : i){
                System.out.println("update"+j);
            }
        }
        return oneprojectService.createprojectlog(projectlogDto);
    }

    @PostMapping("/view/reassign")
    @ResponseBody
    public boolean updateprojectlog(@RequestBody ProjectlogDto projectlogDto){

        return oneprojectService.updateprojectlog(projectlogDto);
    }

    @GetMapping("/view/list")
    @ResponseBody
    public ArrayList<Integer>[] findlog(@RequestParam int pjno){
        return oneprojectService.findlog(pjno);
    }
    @GetMapping("/view/auto")
    @ResponseBody
    public int[] autowired(@RequestParam int pjno){
        System.out.println("오토컨트롤러체크"+pjno);
        return oneprojectService.autowired(pjno);

    }

    @GetMapping("/view/eval.do")
    @ResponseBody
    public ArrayList<ProjectlogDto> findlog2( int pjno){
        return oneprojectService.findlog2(pjno);
    }

    @GetMapping("/findname")
    @ResponseBody
    public String findname( String eno){
        return mypageService.doGetLoginInfo(eno).getEname();
    }

    @GetMapping("/find/spjno")
    @ResponseBody
    public int findspjno(int pjno){
        return oneprojectService.findspjno(pjno);

    }
}
