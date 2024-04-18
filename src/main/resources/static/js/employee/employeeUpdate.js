

let eno=new URL(location.href).searchParams.get('eno');
onSelectPrint()
employeeView()
licensePrint()
CareerPrint()
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
//출력===========================================
//사원 인적사항 출력
function employeeView(){
    $.ajax({
         url : '/employee/view.do',
         method : 'get',
         data : {'eno':eno},
         success : (r)=>{
             console.log(r);
//             <img src="/img/${r2.uuidFile}">${r} 님
             document.querySelector('#img').innerHTML=`<img src="/img/eimg/${r.img}">`
             document.querySelector('#id').value=r.id;
             document.querySelector('#pw').value=r.pw;
             document.querySelector('#pno').value=r.pno;
             document.querySelector('#ename').value=r.ename;
             document.querySelector('#sex').value=r.sex;
             document.querySelector('#start_date').innerHTML=r.edate;
             document.querySelector('#phone').value=r.phone;
             document.querySelector('#email').value=r.email
             document.querySelector('#address').value=r.address
             document.querySelector('#eeducation').value=r.eeducation
         }
    });//ajax 끝*/
}
//경력 내역 출력
function CareerPrint(){
    $.ajax({
        url: `/careerView`,
        method: `get`,
        data:  {eno:eno} ,
        async : false,
        success: (r)=>{
            console.log(r)
            let careerBox = document.querySelector(".careerBox");
            let html='';
            careerBox.innerHTML = html;
            r.forEach( career => {
            console.log(career.companyname)
            let companyname = career.companyname
                 html+=`
                      <div class="tr">
                         <div class="td ">${career.companyname}</div>
                         <div class="td ">${career.start_date}</div>
                         <div class="td ">${career.end_date}</div>
                         <div class="td ">${career.note}</div>
                         <div class="td ">${career.eimg}</div>
                         <div class="td "><button onclick="onCareerDelete( '${companyname}' )" type="button">삭제</button></div>
                       </div>`;
             careerBox.innerHTML = html;
           });
       }
    });
}
//자격증 출력
function licensePrint(){
   $.ajax({
      url: `/licenseView`,
      method: `get`,
      data:  {eno:eno} ,
      async : false,
      success: (r)=>{
      console.log(r)
      let licenseBox = document.querySelector(".licenseBox");
      let html='';
          r.forEach( license => {
              html+=`
                    <div class="tr">
                       <div class="td ">${license.lname}</div>
                       <div class="td ">${license.ldate}</div>
                       <div class="td linput"><button onclick="onlicenseDelete(${license.lno})" type="button">삭제</button></div>
                     </div>`;
          });
          licenseBox.innerHTML = html;
      }

   });
}



// 수정 업데이트
function onUpdate(){
    let employeeForm = document.querySelector('.employeeForm1');
        console.log(employeeForm)
        let employeeFormData= new FormData(employeeForm);
        console.log(employeeFormData); // new FormData
        employeeFormData.append('eno', eno);
    $.ajax({
           url : '/employee/update.do',
           method : 'put',
           data : employeeFormData,
          contentType: false,
           processData : false,
           success :  (r)=>{
               console.log(r);
               //4. 결과
               if(r){
                   alert('수정 성공');
                   location.href=`/employee/view?eno=${eno}`;
               }else {
                   alert('수정 실패');
               }
           }
   });//ajax 끝
}

//=====================삭제

//자격증 삭제
function onlicenseDelete(lno){
$.ajax({
         url : '/license/delete',
         method : 'delete',
         data : {'eno':eno, 'lno':lno},
         success : (r)=>{
             console.log(r);
            if(r){
                 alert('삭제 성공');
                 licensePrint()
            }else{
                 alert('삭제 실패');
            }


        }
    });//ajax 끝*/
}
// 경력 삭제
function onCareerDelete(companyname){
console.log("경력 삭제")
$.ajax({
         url : '/career/delete',
         method : 'delete',
         data : {'eno':eno, 'companyname':companyname},
         success : (r)=>{
             console.log(r);
            if(r){
                 alert('삭제 성공');
                 CareerPrint()
            }else{
                 alert('삭제 실패');
            }


        }

    });//ajax 끝*/
}