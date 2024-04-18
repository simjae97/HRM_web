onSelectPrint()
//부서명 출력
function onSelectPrint(){// 부서명/자격증명 받아오는 함수
    $.ajax({ // 부서명 전체 출력
        url: `/partList`,
        method: `get`,
        async : false,
        success: (r)=>{
        console.log(r)
            let pnoCategory = document.querySelector(".pnoCategory");
            let html='';
            r.forEach( part => {
                 html+=`<option value="${part.pno}">${part.pname}</option>`;
            });
             pnoCategory.innerHTML = html;
         }
    });



}
// **** 현재 유효성검사 체크 현황
//let checkArray=[false,false,false,false,false]

function onSignup(){// 사원등록 함수
    //==============사원 form=====================
    let employeeForm = document.querySelector('.employeeForm');
    console.log(employeeForm)
    let employeeFormData= new FormData(employeeForm);
    console.log(employeeFormData); // new FormData

    $.ajax({
           url : '/signup',
           method : 'post',
           data : employeeFormData,
          contentType: false,
           processData : false,
           success :  (r)=>{
               console.log(r);
               //4. 결과
               if(r){
                   alert('사원정보 등록');
                   location.href=`/employee`;
                   //location.href=`/projectPage/detail?pjno=${r}`;
               }else {
                   alert('사원정보 실패');
               }
           }
   });//ajax 끝

}
// 경력 입력칸 추가 함수
function onPlusCareer(){
console.log('onPlusCareer()')
    let ecareer = document.querySelector('.ecareer');
    let html='';
        html+=`<tr>
                        <td><input type="text" class="companyname" name="companyname"></td>
                        <td><input onchange="" class="start_date" name="start_date"type="date"></td>
                        <td><input onchange=" " class="end_date" name="end_date" type="date"></td>
                        <td><input type="text" class="note" name="note"></td>
                        <td><button onclick="onPlusCareer()">추가</button></td>
                        <td><button onclick="">삭제</button></td>
                    </tr>`
    ecareer.innerHTML += html;
}

// 이메일 검사
function emailcheck(){
    let email = document.querySelector('#email').value;
    let 이메일규칙 = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z]+$/
    let msg='아이디@도메인 입력해주세요';
    if(이메일규칙.test(email)){
        msg='통과';
    }
    document.querySelector('.emailcheckbox').innerHTML=msg;
}