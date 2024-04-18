console.log("안녕")

console.log(new URL(location.href))
    // 2. 경로상의 변수들
console.log(new URL(location.href).searchParams)
    // 3. 변수들에서 특정 매개변수 호출
console.log(new URL(location.href).searchParams.get("pjno"))

let pjno = new URL(location.href).searchParams.get("pjno")

onView();
//1.게시물 개별 조회
function onView(){
    console.log("onview")
    $.ajax({
        url: "/project/view.do?pjno="+pjno,
        method : "get",
        async: false,
        success : (r) => {
            console.log(r)
            document.querySelector(".pjno").innerHTML = "보고서 번호 : "+r.pjno
            document.querySelector(".start_date").innerHTML = "시작일자 : "+r.start_date
            document.querySelector(".end_date").innerHTML = "종료일자 :"+r.end_date
            document.querySelector(".rank1_count").innerHTML = "요구 초급수 : "+r.rank1_count
            document.querySelector(".rank2_count").innerHTML = "요구 중급수 : "+r.rank2_count
            document.querySelector(".rank3_count").innerHTML = "요구 고급수 : "+r.rank3_count
            document.querySelector(".title").innerHTML = "제목 : "+r.title
            document.querySelector(".request").innerHTML = "요구사항 : "+r.request
            document.querySelector(".note").innerHTML = "비고 : "+r.note
            document.querySelector(".compannyname").innerHTML = "회사이름 : "+r.compannyname
            document.querySelector(".state").innerHTML = "상태 : "+(r.state==0?"진행전":r.state==1?"진행중":"완료")
            document.querySelector(".price").innerHTML = "가격 : "+r.price
            document.querySelector(".buttons").innerHTML = `<button type="button" onclick="goToRec( ${r.state} ,${pjno})">프로젝트 인원 등록</button>
             <button type="button" onclick="goToRe( ${r.state} ,${pjno})">프로젝트 인원 수정</button>`
             onclick(pno,매개변수)
        }
    })

    $.ajax({
        url: "/project/view/list?pjno="+pjno,
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
}


function checkstate(state){
    if(state <= 1){
        return true;
    }
    else{
    return false;
    }
}


function goToRec(state, pjno){
    if(checkstate(state)){
        location.href="/project/view/rec?pjno="+pjno
    }
    else{
        alert("이미 종료된 프로젝트입니다.")
    }
}

function goToRe(state, pjno){
    if(checkstate(state)){
        location.href="/project/view/re?pjno="+pjno
    }
    else{
        alert("이미 종료된 프로젝트입니다.")
    }
}