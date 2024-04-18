loginCheck()
// 1. 로그인 여부 확인 요청 [ JS 열릴때마다 체크 ]
function loginCheck(){
    console.log('loginCheck()')
    $.ajax({
        url : '/employee/login/check' ,
        method : 'get' ,
        async : false,
    success : (r)=>{
        console.log("여기" + r);
        // 로그인 상태가 아니면
        if(r === ""){
            alert('로그인이 필요한 서비스 입니다.')
            location.href="/login"; // 로그인 페이지로 이동
        }
    }

    }); // ajax 끝

}
let info={
    page : 1,           //현재페이지
    pageBoardSize : 5,  //페이지당 표시 할 개수
    key: 0 ,
    keyword : null
}

onEmployeeAllView(1)
// 전체 사원 호출하기
function onEmployeeAllView(page){
    info.page=page;
    $.ajax({
        url:`/employeeList`,
        method:`get`,
        data : info,
        success: (re)=>{
            console.log(info)

            let k_eList = document.querySelector("#k_eList");
                let html='';
                re.objectList.forEach( employee => {
                     html+=`<tr>
                                  <th>${employee.eno}</th>
                                  <td><a href="employee/view?eno=${employee.eno}">${employee.ename}</a></td>
                                  <td>${employee.pname}</td>
                                  <td>${employee.phone}</td>
                                  <td>${employee.edate}</td>
                              </tr>`;
                });
                 k_eList.innerHTML = html;

                 //===== 페이지네이션 =====
                 html=`<li class="page-item"><a class="page-link" onclick="onEmployeeAllView(${page-1<1 ? page : page-1})">Previous</a></li>`;
                 for(let i=re.startPage; i<=re.endPage; i++){
                     html+=`<li class="page-item"><a class="page-link" onclick="onEmployeeAllView(${i})">${i}</a></li>`;
                 }
                 html+=`<li class="page-item"><a class="page-link" onclick="onEmployeeAllView(${page+1>re.totalPage ? re.totalPage : page+1})">Next</a></li>`;

                 document.querySelector(".pagination").innerHTML=html;

        }
    });
}

function onPartPrint(){
     let key=document.querySelector('.key').value;
        let keyword=document.querySelector('.keyword').value;

        //2. 서버에 전송할 객체에 담아주고
        info.key=key;
        info.keyword=keyword;

        //3. 출력 함수 호출
        onEmployeeAllView(1);
        //
}
