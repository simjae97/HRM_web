package sierp.springteam1.service.projectservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sierp.springteam1.model.dao.projectcontroller.OneprojectDao;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.model.dto.ProjectDto;
import sierp.springteam1.model.dto.ProjectlogDto;
import sierp.springteam1.model.dto.PsendEmployeeDto;

import java.util.ArrayList;
import java.util.Map;

@Service
public class OneprojectService {

    @Autowired
    OneprojectDao oneprojectDao;
    public ProjectDto oneProject(int pjno){
        System.out.println("OneprojectController.oneProject");
        System.out.println(pjno);
        return oneprojectDao.oneProject(pjno);
    }

    public ArrayList<PsendEmployeeDto>[] memberlist(int pjno){
        System.out.println("안뇽.memberlist");
        System.out.println(pjno);
        String start_date = oneprojectDao.oneProject(pjno).getStart_date();
        System.out.println("등록"+start_date);
        return oneprojectDao.memberlist(start_date);
    }

    public boolean createprojectlog(ProjectlogDto projectlogDto) {
        if (checkEmployeecount(projectlogDto)) {
            return oneprojectDao.createprojectlog(projectlogDto);
        }
        return false;
    }
    public int[] autowired(int pjno){
        ArrayList<Integer>[] a = oneprojectDao.findlog(pjno);
        int[] b =oneprojectDao.findrankcount(pjno);
        int[] result = new int[3];
        for(int i = 0; i<3 ; i++){
            result[i] = b[i] - a[i].size();
        }
        return result;
    }
    public boolean updateprojectlog(ProjectlogDto projectlogDto) {
        boolean result = oneprojectDao.deleteprojectlog(projectlogDto);
        if (result) {
            if (checkEmployeecount(projectlogDto)) {
                return oneprojectDao.createprojectlog(projectlogDto);
            }

        }
        return false;
    }


//    public int findscore(int eno){
//        return oneprojectDao.findscore(eno);
//    }
    public ArrayList<PsendEmployeeDto>[] updatememberlist(int pjno){
        String start_date = oneprojectDao.oneProject(pjno).getStart_date();
        return oneprojectDao.updatememberlist(pjno ,start_date);
    }

    public ArrayList<Integer>[] findlog(@RequestParam int pjno){
        return oneprojectDao.findlog(pjno);
    }


    public boolean checkEmployeecount(ProjectlogDto projectlogDto){
        ArrayList<Integer>[] a = oneprojectDao.findlog(projectlogDto.getPjno());
        int[] b =oneprojectDao.findrankcount(projectlogDto.getPjno());
        for (int i = 0 ; i<3; i++) {
            if (a[i].size()+projectlogDto.getEnos()[i].size() > b[i]){
                return false;
            }
        }
        return true;
    }

    public ArrayList<ProjectlogDto> findlog2( int pjno){
        return oneprojectDao.findlog2(pjno);
    }

    public int findspjno(int pjno){
        return oneprojectDao.findspjno(pjno);
    }

}
