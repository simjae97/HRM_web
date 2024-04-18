package sierp.springteam1.service.j_projectPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sierp.springteam1.model.dao.j_projectPageDao.J_noteDao;
import sierp.springteam1.model.dto.NoteDto;
import sierp.springteam1.model.dto.ProjectPageDto;

import java.util.List;

@Service
public class J_noteService {
    @Autowired
    J_noteDao j_noteDao;
    @Autowired
    J_projectPageService j_projectPageService;

    //받은쪽지 가져오기
    public ProjectPageDto doGetReceiveNote(int eno, int page, int pageBoardSize){
        System.out.println("J_noteService.doGetReceiveNote");
        System.out.println("eno = " + eno);
        String sendMark="sendeno";

        //1. start row : 시작할 게시물의 행순서
        int startRow=(page-1)*pageBoardSize;

        //2. 총 페이지 수
        int totalRecode=j_noteDao.countNote(sendMark, eno);  //총 레코트 출력

        //**페이징 dto 저장 메소드(매개변수 -> page:현재페이지 , totalRecode:전체게시물수 , pageBoardSize:한페이지당 게시물수)
        //리턴 : ProjectPageDto
        ProjectPageDto projectPageDto = j_projectPageService.deliverPageInfo(page, totalRecode, pageBoardSize);
        
        //dao에서 list객체 넣기
        projectPageDto.setObjectList(j_noteDao.doGetNote(sendMark, eno, startRow, pageBoardSize));

        return projectPageDto;
    }//m end

    //쪽지 보내기
    public boolean doPostNote(NoteDto noteDto){
        System.out.println("J_noteService.doPostNote");
        System.out.println("noteDto = " + noteDto);

        return j_noteDao.doPostNote(noteDto);
    }//m end

    //쪽지 보낼때 > 받는 사람의 사원번호 가져오기
    public int getEnoToId(String sendId){
        System.out.println("J_noteService.getEnoToId");
        System.out.println("sendId = " + sendId);

        return j_noteDao.getEnoToId(sendId);
    }//m end

    //보낸쪽지 가져오기
    public ProjectPageDto doGetPostNote(int eno, int page, int pageBoardSize){
        System.out.println("J_noteService.doGetReceiveNote");
        System.out.println("eno = " + eno);
        String sendMark="posteno";

        //1. start row : 시작할 게시물의 행순서
        int startRow=(page-1)*pageBoardSize;

        //2. 총 페이지 수
        int totalRecode=j_noteDao.countNote(sendMark,eno);  //총 레코트 출력

        //**페이징 dto 저장 메소드(매개변수 -> page:현재페이지 , totalRecode:전체게시물수 , pageBoardSize:한페이지당 게시물수)
        //리턴 : ProjectPageDto
        ProjectPageDto projectPageDto = j_projectPageService.deliverPageInfo(page, totalRecode, pageBoardSize);

        //dao에서 list객체 넣기
        projectPageDto.setObjectList(j_noteDao.doGetNote(sendMark ,eno, startRow, pageBoardSize));

        return projectPageDto;
    }//m end

    //쪽지 상세정보 요청
    public NoteDto doGetNoteDetail(int nno){
        System.out.println("J_noteService.doGetNoteDetail");
        System.out.println("nno = " + nno);

        return j_noteDao.doGetNoteDetail(nno);
    }//m end
}//c end
