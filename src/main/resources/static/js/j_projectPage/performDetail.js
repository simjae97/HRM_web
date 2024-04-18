console.log("salesDetail()-js")
let pjno=new URL(location.href).searchParams.get('pjno');
console.log(pjno);
printperformDetail();
//개별 프로젝트리스트 출력
function printperformDetail(){
    console.log("printProjectDetail()");

    $.ajax({
         url : "/projectPage/performDetail.do",
         method : "get",
         data : {"pjno" : pjno},
         success : (r)=>{
            console.log("결과출력");
            console.log(r);
//            document.querySelector(".detaile_pjno").innerHTML=r.pjno;
//            document.querySelector(".detaile_perFormState").innerHTML=r.perFormState==0 ? '평가전' : (r.perFormState==1 ? '평가중' : '평가완료');
//            document.querySelector(".detaile_start_date").innerHTML=r.start_date;
//            document.querySelector(".detaile_end_date").innerHTML=r.end_date;
//            document.querySelector(".detaile_title").innerHTML=r.title;
//            document.querySelector(".detaile_request").innerHTML=r.request;
//            document.querySelector(".detaile_note").innerHTML=r.note;
//            document.querySelector(".detaile_compannyname").innerHTML=r.compannyname;
//            document.querySelector(".detaile_price").innerHTML=r.price;
            getPjEmployeeInfo();    //참여 사원 정보 불러오기
            document.querySelector(".buttons").innerHTML+=r.perFormState==0 ?
                                                        `<button type="button" onclick="insertPerform()"> 평가등록 </button>` :
                                                        `<button type="button" onclick="insertPerform()"> 수정 </button>`;
            //평가전 : 평가등록버튼 출력  /  평가중/완료 : 수정버튼 출력
            //버튼 수정 등록 평가버튼 추후 삭제 -> 프로젝트 따온 영업이고 실제 등록 안하게 설계 바뀌었으므로
         }//success end
    })//ajax end
//                document.querySelector(".buttons").innerHTML += `<button type="button" onclick="goToRec( ${r.state} ,${spjno})">프로젝트 인원 등록</button>
//                <button type="button" onclick="goToRe( ${r.state} ,${spjno})">프로젝트 인원 수정</button>
    //                <button type="button" onclick="goToEval( ${r.state} ,${spjno})">프로젝트 평가</button>` 이전 버튼들
}//f end

//수정페이지로 이동
function changeToUpdate(){
location.href= `/projectPage/update?pjno=${pjno}`;
}//f end

//프로젝트 참여 사원 정보 불러오기
function getPjEmployeeInfo(){
    $.ajax({
        url : "/projectPage/performEmployee",
        method : "Get",
        data : {"pjno" : pjno},
        sync : false,
        success : (r) =>{
            console.log(r);
            let html=``;
            r.forEach((result) => {
                html+=`<tr>
                          <th> ${result.eno} </th>
                          <td>${result.ename} </td>
                          <td> ${result.pname} </td>
                          <td> ${result.rank} </td>
                          <td> ${result.performState} </td>
                          <td class="insertPfNote note:${result.eno}"><input class="eno" type="number" value="${result.eno}" style="display:none"/><span>${result.note}</span> </td>
                          <td class="insertPfScore score:${result.eno}"><input class="eno" type="number" value="${result.eno}" style="display:none"/><span>${result.score}</span> </td>
                      </tr>`;
            })//forEach end
            document.querySelector(".performEmployeeList").innerHTML=html;
        }//success end
    })//ajax end
}//f end

//삭제
function deleteDetail(){
    console.log("deleteDetail()");
    if(confirm("삭제하시겠습니까?")){
        $.ajax({
            url : "/projectPage/delete",
            method : "Delete",
            data : {"pjno" : pjno},
            success : (r) => {
                console.log(r);
                if(r){
                    alert("삭제 성공");
                    location.href="/projectPage/"
                }
                else{
                    alert("삭제 실패 : 참여중인 인원이 존재합니다.");
                }
            }//success end
        })//ajax end
    }//if end
    else{
        alert("삭제가 취소되었습니다.");
    }
}//f end

//평가등록클릭
function insertPerform(){
    // insertPfNote 클래스를 가진 모든 요소에 대해 반복하며 내부 HTML을 변경
    document.querySelectorAll(".insertPfNote span").forEach(element => {
        let inputNote=element.innerHTML;
        console.log("inputNote="+inputNote);
        element.innerHTML = `<input type="text" value="${inputNote}" class="pfNote" name="note" />`;
    });
    //insertPfScore 클래스를 가진 모든 요소에 대해 반복하며 내부 HTML을 변경
    document.querySelectorAll(".insertPfScore span").forEach(element => {
        let inputScore=element.innerHTML;
        console.log("inputScore="+inputScore);
        element.innerHTML = `<input type="number" value="${inputScore}" class="pfScore" name="score" />`;
    });

    document.querySelector(".buttons").innerHTML=`<button type="button" onclick="onInsertPerform()"> 등록 </button>`;
    document.querySelector(".buttons").innerHTML+=`<button type="button" onclick="location.href='/projectPage/performDetail?pjno=${pjno}'"> 취소 </button>`;
}//f end

//평가등록
function onInsertPerform(){
    console.log("onInsertPerform");

    //알람용 변수
    let al=``;

    //참여하는 인원만큼 반복
    document.querySelectorAll(".insertPfScore .eno").forEach(element =>{
        let eno=element.value;
        console.log(eno);

        let note=document.querySelector(`.note\\:${eno} .pfNote`).value;
        let score=document.querySelector(`.score\\:${eno} .pfScore`).value;
        console.log("note : "+note);
        console.log("score : "+score);

        $.ajax({
            url : "/projectPage/insertPerform.do",
            method : "Post",
            data : {"pjno" : pjno,
                    "eno" : eno,
                    "note" : note,
                    "score" : score},
            async : false,
            success : (r) => {
                console.log(r);
                if(r){
                    al+=eno+"  ";
                }
            }//success end
        })//ajax end
    })//element foEach end
    console.log(al);

        if(al==""){
            alert("평가등록 실패");
        }
        else{
            alert("사원번호 : "+al+"평가등록 성공");
            location.href=`/projectPage/performDetail?pjno=${pjno}`;
        }
}//f end