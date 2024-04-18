package sierp.springteam1.controller.salescontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectDto2;
import sierp.springteam1.model.dto.ProjectPageDto;
import sierp.springteam1.service.salesService.SalesService;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleProjectController {

    @Autowired
    SalesService salesService;

    //1.등록
    @PostMapping("/Post.do")
    @ResponseBody
    public boolean salesPost(int spjno){
        System.out.println("SaleProjectController.salesPost");
        return salesService.salesPost(spjno);
    }
    //2.리스트
    @GetMapping("/list.do")
    @ResponseBody
    public ProjectPageDto saleslist(int page, int pageBoardSize,
                                    int sortKey,
                                    String key, String keyword,
                                    int startPrice, int endPrice){
        System.out.println("SaleProjectController.salesPost");
        return salesService.saleslist(page, pageBoardSize,sortKey, key, keyword, startPrice, endPrice);
    }

    //3.삭제
    @GetMapping("/del.do")
    @ResponseBody
    public boolean salesdel(int pjno){
        return salesService.salesdel(pjno);

    }
    //4.수정
    @GetMapping("/findpjno")
    @ResponseBody
    public int findpjno(int spjno){return salesService.findpjno(spjno);}
    @GetMapping("/view.do")
    @ResponseBody
    public ProjectDto oneProject(int spjno){
        System.out.println("OneprojectController.oneProject");
        return salesService.oneProject(spjno);
    }
    //1.리스트
    @GetMapping("/list")
    public String postview(){
       return "/sales/saleslist";
    }
    //2.리스트
    @GetMapping("/view")
    public String getview(int spjno){ return "/sales/postone";}


}
