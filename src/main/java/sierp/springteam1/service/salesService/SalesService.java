package sierp.springteam1.service.salesService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sierp.springteam1.model.dao.salesDao.SalesDao;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectDto2;
import sierp.springteam1.model.dto.ProjectPageDto;
import sierp.springteam1.service.j_projectPage.J_projectPageService;

import java.util.List;

@Service
public class SalesService {


    @Autowired
    SalesDao salesDao;
    @Autowired
    J_projectPageService j_projectPageService;



    public boolean salesPost(int spjno){
        System.out.println("SalesService.salesPost");
        return salesDao.salesPost( spjno);
    }

    public ProjectPageDto saleslist(int page, int pageBoardSize,
                                           int sortKey,
                                           String key, String keyword,
                                           int startPrice, int endPrice){

        ProjectPageDto projectPageDto=new ProjectPageDto();
        //start row
        int startRow=(page-1)*pageBoardSize;

        System.out.println("startRow = " + startRow);
        projectPageDto.setPage(page);

        //총 페이지 수
        int totalRecode=salesDao.projectCount();

        //**페이징 dto 저장 메소드(매개변수 -> page:현재페이지 , totalRecode:전체게시물수 , pageBoardSize:한페이지당 게시물수)
        //리턴 : ProjectPageDto
        projectPageDto = j_projectPageService.deliverPageInfo(page, totalRecode, pageBoardSize);

        //한페이지당 List
        projectPageDto.setObjectList(salesDao.saleslist(startRow, pageBoardSize, sortKey, key, keyword, startPrice, endPrice));

        return projectPageDto;
    }//m end

    public ProjectDto oneProject(int spjno){
        System.out.println("OneprojectController.oneProject");
        return salesDao.oneProject(spjno);
    }
    public int findpjno(int spjno){return salesDao.findpjno(spjno);}

    public boolean salesdel(int pjno){
        return salesDao.salesdel(pjno);

    }
}
