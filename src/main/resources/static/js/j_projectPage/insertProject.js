//프로젝트 등록
function insertProject(){
//1. form dom 가져온다.
    let insertProjectData=document.querySelector('#insertProjectData');
    //2. 폼 바이트(바이너리) 객체 변환 [첨부파일 보낼때는 필수]
    let projectDto=new FormData(insertProjectData);
    $.ajax({
        url : "/projectPage/insert.do",
        method : "Post",
        data : projectDto,
        contentType : false,
        processData : false,
        success : (r)=>{
            console.log(r);
            if(r){
                alert("등록 성공");
                location.href=`/projectPage/detail?spjno=${r}`;
            }
            else{
                alert("등록 실패");
            }
        }//success end
    })//ajax end
}//f end

//프로젝트 등록 취소
function backList(){
    location.href="/projectPage/";
}