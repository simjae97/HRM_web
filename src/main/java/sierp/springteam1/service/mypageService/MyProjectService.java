package sierp.springteam1.service.mypageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sierp.springteam1.model.dao.mypageDao.MyProjectDao;
import sierp.springteam1.model.dao.mypageDao.MypageDao;
import sierp.springteam1.model.dto.MyProjectDto;
import sierp.springteam1.model.dto.ProjectDto;

import java.util.List;

@Service
public class MyProjectService {

    @Autowired
    MyProjectDao myProjectDao;
    @Autowired
    MypageDao mypageDao;

    // 진행 중인 프로젝트 전체 출력
    public MyProjectDto myProjectList(String eno){
        System.out.println("MyProjectService.myProjectList");
        MyProjectDto myProjectDto = myProjectDao.myProjectList(eno);
        // DAO 에서 Dto 값이 null 이 아니면 정상 null 이면 진행중 프로젝트 X
        if (myProjectDto != null){
            String id = mypageDao.doGetNameInfo(eno);
            myProjectDto.setId(id);
        }
        return myProjectDto;
    }

    // 즐겨찾기한 프로젝트 전체 출력
    public List<ProjectDto> myProjectLikeView(String eno){
        System.out.println("MyProjectService.myProjectLikeView");
        System.out.println("MyProjectService.myProjectLikeView : eno = " + eno);

        return myProjectDao.myProjectLikeView(eno);
    }

    // 내 이전 프로젝트 전체 출력
    public  List<ProjectDto> myProjectPreviousView(String eno){
        System.out.println("MyProjectService.myProjectPreviousView");
        return myProjectDao.myProjectPreviousView(eno);
    }

}
