package sierp.springteam1.service.employeeserive;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {
    String eUpload="C:\\Users\\504\\Desktop\\springteam1\\build\\resources\\main\\static\\img\\eimg\\";
    String cUpload="C:\\Users\\504\\Desktop\\springteam1\\build\\resources\\main\\static\\img\\cimg\\";

    public String eFileUpload(MultipartFile multipartFile){
        System.out.println("FileService.eFileUpload");

        String uuid= UUID.randomUUID().toString();
        String filename =uuid+"_"+multipartFile.getOriginalFilename().replace("_","-");
        File file= new File(eUpload+filename);
        System.out.println("file = " + file);
        System.out.println("file.exists() = " + file.exists());
        //2.
        try {
            multipartFile.transferTo(file);
        }catch (Exception e){
            System.out.println("e = " + e);
            return null;
        }
        return filename;
    }

    public String cFileUpload(MultipartFile multipartFile){
//        String cUpload= "C:\\Users\\USER P\\IntelliJ IDEA Community Edition 2023.3.2\\modules\\springteam1\\build\\resources\\main\\static\\img\\cimg";
        String uuid= UUID.randomUUID().toString();
        String filename = uuid+"_"+multipartFile.getOriginalFilename().replace("_","-");
        File file1= new File(cUpload+filename);
        System.out.println("file = " + file1);
        System.out.println("file.exists() = " + file1.exists());
        //2.
        try {
            multipartFile.transferTo(file1);
        }catch (Exception e){
            System.out.println("e = " + e);
            return null;
        }
        return filename;
    }

    //3. 파일 삭제 [게시물 삭제시 만약에 첨부파일 있으면 첨부파일도 같이 삭제, 게시물 수정시 첨부파일 변경하면 기존 첨부파일 삭제]
    public boolean fileDelete(String bfile){

        //1. 경로와 파일을 합쳐서 파일 위치 찾기
        String filePath=eUpload+bfile;
        //2. File클래스의 메소드 활용
        //.exists() : 해당 파일의 존재 여부
        // .length() : 해당 파일의 크기/용량(바이트 단위)
        File file = new File(filePath);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }
}
