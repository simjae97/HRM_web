let spjno=new URL(location.href).searchParams.get('spjno');
printProjectDetail();
//개별 프로젝트리스트 출력
function printProjectDetail(){
    console.log("printProjectDetail()");

    $.ajax({
         url : "/projectPage/detail.do",
         method : "get",
         async: false,
         data : {"spjno" : spjno},
         success : (r)=>{
            console.log("결과출력");
            console.log(r);
            document.querySelector(".detaile_spjno").innerHTML=r.spjno;
            document.querySelector(".detaile_start_date").innerHTML=r.start_date;
            document.querySelector(".detaile_end_date").innerHTML=r.end_date;
            document.querySelector(".detaile_rank1_count").innerHTML=r.rank1_count;
            document.querySelector(".detaile_rank2_count").innerHTML=r.rank2_count;
            document.querySelector(".detaile_rank3_count").innerHTML=r.rank3_count;
            document.querySelector(".detaile_title").innerHTML=r.title;
            document.querySelector(".detaile_request").innerHTML=r.request;
            document.querySelector(".detaile_note").innerHTML=r.note;
            document.querySelector(".detaile_compannyname").innerHTML=r.compannyname;
            document.querySelector(".detaile_state").innerHTML=r.state==0 ? "진행전" : (r.state==1 ? "진행중" : "진행완료");
            document.querySelector(".detaile_price").innerHTML=r.price;
            document.querySelector(".buttons").innerHTML += `<button type="button" onclick="goToRec( ${r.state} ,${spjno})">프로젝트 인원 등록</button>
            <button type="button" onclick="goToRe( ${r.state} ,${spjno})">프로젝트 인원 수정</button>
            <button type="button" onclick="goToEval( ${r.state} ,${spjno})">프로젝트 평가</button>
            <button type="button" onclick="doPostuploadProject(${r.state} ,${spjno})">프로젝트 등록</button>`
            //버튼 수정 등록 평가버튼 추후 삭제 -> 프로젝트 따온 영업이고 실제 등록 안하게 설계 바뀌었으므로
         }//success end
    })//ajax end
//                document.querySelector(".buttons").innerHTML += `<button type="button" onclick="goToRec( ${r.state} ,${spjno})">프로젝트 인원 등록</button>
//                <button type="button" onclick="goToRe( ${r.state} ,${spjno})">프로젝트 인원 수정</button>
//                <button type="button" onclick="goToEval( ${r.state} ,${spjno})">프로젝트 평가</button>` 이전 버튼들
        $.ajax({
            url: "/project/view/list?spjno="+spjno,
            method:"get",
            success : (r) =>{
                console.log(r.length)
                let html = "프로젝트에 참여중인 인원: ";
                console.log(r[1])
                console.log(r[0])
                for(let i = 0; i < r.length; i++){
                    html += `${r[i]} `
                }
                console.log(html)
                document.querySelector(".logs").innerHTML= html;
            }
        })
}//f end

//수정페이지로 이동
function changeToUpdate(){
location.href= `/projectPage/update?spjno=${spjno}`;
}//f end

//삭제
function deleteDetail(){
    console.log("deleteDetail()");
    if(confirm("삭제하시겠습니까?")){
        $.ajax({
            url : "/projectPage/delete",
            method : "Delete",
            data : {"spjno" : spjno},
            success : (r) => {
                console.log(r);
                if(r){
                    alert("삭제 성공");
                    location.href="/projectPage/"
                }
                else{
                    alert("삭제 실패");
                }
            }//success end
        })//ajax end
    }//if end
    else{
        alert("삭제가 취소되었습니다.");
    }
}//f end

function goToEval(state,spjno){
    if (state >= 1){
         location.href = "/project/view/eval?spjno="+spjno
    }
    else{
        alert("아직 종료되지 않은 프로젝트입니다")
    }
}

function doPostuploadProject(state, spjno){
    if(state == -1){
        $.ajax({
            url:"/sales/Post.do",
            method:"post",
            data:{spjno:spjno},
            success: (r) => {
                if(r){
                    alert("등록성공")
                    location.href="/projectPage/"
                }
            }
        })
    }
    else{
        alert("이미 등록된 프로젝트입니다.")
    }
}
function goToRec(state, spjno){
    if(state <= 1){
        location.href="/project/view/rec?spjno="+spjno
    }
    else{
        alert("이미 종료된 프로젝트입니다.")
    }
}

function goToRe(state, spjno){
    if(state <= 1){
        location.href="/project/view/re?spjno="+spjno;
    }
    else{
        alert("이미 종료된 프로젝트입니다.")
    }
}