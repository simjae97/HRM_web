package sierp.springteam1.model.dao.j_projectPageDao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import sierp.springteam1.model.dao.SuperDao;
import sierp.springteam1.model.dto.NoteDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class J_noteDao extends SuperDao {
    //쪽지 리스트 가져오기
    public List<Object> doGetNote(String sendMark ,int eno,int startRow, int pageBoardSize){
        System.out.println("J_noteDao.doGetReceiveNote");
        System.out.println("eno = " + eno + ", startRow = " + startRow + ", pageBoardSize = " + pageBoardSize);
        List<Object> list=new ArrayList<>();
        try{
            String sql="select n.*, a.postid, b.sendid from \n" +
                    "(select nno,id as postid from note as n inner join employee as e on n.posteno=e.eno)as a inner join\n" +
                    "(select nno,id as sendid from note as n inner join employee as e on n.sendeno=e.eno)as b on a.nno=b.nno inner join \n" +
                    "note as n on n.nno=b.nno where "+sendMark+"="+eno+" order by ndate desc limit ?,?;";
            ps=conn.prepareStatement(sql);

            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardSize);
            rs=ps.executeQuery();
            while(rs.next()){
                NoteDto noteDto=NoteDto.builder()
                        .nno(rs.getInt("nno"))
                        .posteno(rs.getInt("posteno"))
                        .sendeno(rs.getInt("sendeno"))
                        .ncontent(rs.getString("ncontent"))
                        .ndate(rs.getString("ndate"))
                        .reply(rs.getInt("reply"))
                        .postid(rs.getString("postid"))
                        .sendid(rs.getString("sendid"))
                        .build();

                list.add(noteDto);
            }//w end
            return list;
        }//t end
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }//m end

    //받은쪽지 수 추출
    public int countNote(String sendMark, int eno){
        System.out.println("J_noteDao.countReceiveNote");
        System.out.println("eno = " + eno);
        try{
            String sql="select count(*) from note where "+sendMark+"="+eno;
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    //쪽지 보내기
    public boolean doPostNote(NoteDto noteDto){
        System.out.println("J_noteDao.doPostNote");
        System.out.println("noteDto = " + noteDto);
        try{
            String sql="insert into note(posteno, sendeno, ncontent, reply) values(?,?,?,?) ";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, noteDto.getPosteno());
            ps.setInt(2, noteDto.getSendeno());
            ps.setString(3, noteDto.getNcontent());
            ps.setInt(4,noteDto.getReply());

            int count=ps.executeUpdate();
            if(count==1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }//m end

    //id > 사원번호 가져오기
    public int getEnoToId(String sendId){
        System.out.println("J_noteDao.getEnoToId");
        System.out.println("sendId = " + sendId);
        try{
            String sql="select eno from employee where id='"+sendId+"'";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }//m end

    //쪽지 상세정보 요청
    public NoteDto doGetNoteDetail(int nno){
        System.out.println("J_noteDao.doGetNoteDetail");
        System.out.println("nno = " + nno);
        try {
            String sql = "select n.*, a.postid, b.sendid from \n" +
                    "(select nno,id as postid from note as n inner join employee as e on n.posteno=e.eno)as a inner join\n" +
                    "(select nno,id as sendid from note as n inner join employee as e on n.sendeno=e.eno)as b on a.nno=b.nno inner join \n" +
                    "note as n on n.nno=b.nno where n.nno=" + nno;
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                NoteDto noteDto=NoteDto.builder()
                        .nno(rs.getInt("nno"))
                        .posteno(rs.getInt("posteno"))
                        .sendeno(rs.getInt("sendeno"))
                        .ncontent(rs.getString("ncontent"))
                        .ndate(rs.getString("ndate"))
                        .reply(rs.getInt("reply"))
                        .postid(rs.getString("postid"))
                        .sendid(rs.getString("sendid"))
                        .build();
                System.out.println("noteDto = " + noteDto);
                return noteDto;
            }//if end
        }//t end
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }//m end

}//c end
