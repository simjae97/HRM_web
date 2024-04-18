//쪽지보내기
function doPostNote(){
    let sendid=document.querySelector(".sendid").value;
    let sendeno=getSendenoToId(sendid);
    if(sendeno!=0){
        let ncontent=document.querySelector(".ncontent").value;

        $.ajax({
            url : "/note/post.do",
            method : "Post",
            data : {"sendeno" : sendeno,
                    "ncontent" : ncontent,
                    "reply" : 0},
            success : (r)=>{
                console.log(r);
                alert("쪽지 전송 성공");
                location.href="/note/getPost"
            }//s end
        })//ajax end
    }
}//f end

//쪽지 id > eno변경
function getSendenoToId(sendid){
    console.log(sendid);
    let sendeno=0;
    $.ajax({
        url : "/note/geteno.do",
        method : "get",
        data : {"sendid" : sendid},
        async: false,
        success : (r)=>{
            console.log(r);
            if(r!=0){
                sendeno=r;
            }
            else{
                alert("받는이의 id를 확인 해 주십시오.");
            }
        }//s end
    })//ajax end
    return sendeno;
}//f end