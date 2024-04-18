let eno=new URL(location.href).searchParams.get('eno');
let index = 3;
let index1 = 1;
employeeView();
CareerPrint();
licensePrint();
//등록======================================
// 경력 등록 함수
function OnCareer(){
    let careerFormList = document.querySelectorAll('.postForm');
    for( let i = 0 ; i< careerFormList.length ; i++  ){
        let postFormData = new FormData(  careerFormList[i] );
        postFormData.append('eno', eno);
        console.log(postFormData);
        $.ajax({
             url : '/careerPost',
             method : 'post',
             data : postFormData,
             contentType: false,
             processData : false,
             success : (r)=>{
                 console.log(r);
                 //4. 결과
                 if(r){
                     alert('경력등록 성공');
                     onClose(1);
                     onCareerSUm();
                 }else {
                     alert('경력등록 실패');
                 }
             }
        });//ajax 끝*/
    }
}
//자격증 등록 함수
function OnLicense(){
    let licenseList=document.querySelectorAll('.licenseB');
    console.log(licenseList)
     let licenseFormList = document.querySelectorAll('.postForm1');
        for( let i = 0 ; i< licenseFormList.length ; i++  ){
            let postFormData = new FormData(  licenseFormList[i] );
            postFormData.append('eno', eno);
            console.log(postFormData);
            $.ajax({// 자격증 중복 등록 검사
                 url : '/findLicense',
                 method : 'post',
                 data : postFormData,
                 contentType: false,
                 processData : false,
                 success : (r)=>{
                     console.log(r);
                     //4. 결과

                     if(r==1){
                         $.ajax({
                              url : '/licensePost',
                              method : 'post',
                              data : postFormData,
                              contentType: false,
                              processData : false,
                              success : (re)=>{
                                  console.log(re);
                                  //4. 결과
                                  if(re){
                                      alert('자격증 등록 성공');
                                      document.querySelector('.licenseBox').innerHTML='';
                                      onClose(2);
                                  }else {
                                      alert('자격증 등록 실패.');
                                  }
                              }
                         });//ajax 끝
                     }else {
                         alert(r+' 이미 등록된 자격증입니다.');
                     }
                 }
            });//ajax 끝*/

        }
}

//사원 삭제 함수
function onEmployeeDelete(){
alert('퇴사처리 하시겠습니까?')
    $.ajax({
         url : '/employee/delete',
         method : 'delete',
         data : {'eno':eno},
         success : (r)=>{
             console.log(r);
             //4. 결과
             if(r){
                 alert('삭제 성공');
                 location.href='/employee';
             }else {
                 alert('삭제 실패');
             }
         }
    });//ajax 끝*/
}

//출력===========================================
//사원 인적사항 출력
function employeeView(){
    $.ajax({
         url : '/employee/view.do',
         method : 'get',
         data : {'eno':eno},
         success : (r)=>{
             console.log(r.img);
//             <img src="/img/${r2.uuidFile}">${r} 님 "../../img/cimg/${r.img}"
             document.querySelector('.img').innerHTML=`<img src="/img/eimg/${r.img}">`
             document.querySelector('.pname').innerHTML=r.pname
             document.querySelector('.ename').innerHTML=r.ename
             document.querySelector('.sex').innerHTML=r.sex? '여성' : '남성';
             document.querySelector('.edate').innerHTML=r.edate;
             document.querySelector('.phone').innerHTML=r.phone
             document.querySelector('.email').innerHTML=r.email
             document.querySelector('.address').innerHTML=r.address
             onCareerSUm()


         }
    });//ajax 끝*/
}
// 경력 합산 출력
function onCareerSUm(){
    $.ajax({
        url: `/careerSum`,
        method: `get`,
        data:  {eno:eno} ,
        async : false,
        success: (r)=>{
            console.log(r)
            document.querySelector(".careerSum").innerHTML=r;

        }
    });
}
//경력 내역 출력
function CareerPrint(num){
    $.ajax({
        url: `/careerView`,
        method: `get`,
        data:  {eno:eno} ,
        async : false,
        success: (r)=>{
        console.log(r)
            let careerBox = document.querySelector(".careerBox");
            let html='';
            r.forEach( career => {
                html+=`
                       <div class="tr">
                          <div class="td ">${career.companyname}</div>
                          <div class="td ">${career.start_date}</div>
                          <div class="td ">${career.end_date}</div>
                          <div class="td ">${career.note}</div>
                          <div class="td ">${career.eimg}</div>
                        </div>`;
                 careerBox.innerHTML = html;
         })
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
                         </div>`;
            });
            licenseBox.innerHTML = html;
        }
    });

}

// 경력 합산 출력
function onCareerSUm(){
    $.ajax({
        url: `/careerSum`,
        method: `get`,
        data:  {eno:eno} ,
        async : false,
        success: (r)=>{
            console.log(r)
            let years = Math.floor(r / 365);
            let months = Math.floor((r % 365) / 30);
            let remainingDays = Math.floor((r % 365) % 30);
            html=` ${years}년 ${months}개월, ${remainingDays}일`
             document.querySelector(".careerSum").innerHTML=html;


        }
    });
}

// 경력 입력칸 함수
function OnCareerPlus(){
    let careerBox = document.querySelector('.careerBox');
    let html='';
    html=`
         <form class="careerForm${index} postForm">
            <div class="tr">
              <div class="td cinput"><input type="text" id="companyname" name="companyname"></div>
              <div class="td cinput"><input onchange="" id="start_date" name="start_date"type="date"></div>
              <div class="td cinput"><input onchange=" " id="end_date" name="end_date" type="date"></div>
              <div class="td cinput"><input type="text" id="note" name="note"></div>
              <div class="td cinput"><input type="file" id="cimg" name="cimg"></div>
            </div>
        </form>
        `
        index++;
    let button=document.querySelector('.cBtn');
    btn=`
    <button onclick="OnCareer()" type="button">경력 등록</button>
    <button onclick="onClose(${1})" type="button">닫기</button>
    `
    button.innerHTML=btn
    careerBox.innerHTML+=html;
}
// 자격증 입력칸
function OnLicensePlus(){
    $.ajax({// 자격증명
        url: `/licenseSelect`,
        method: `get`,
        success: (r)=>{
            console.log(r)
            let licenseBox = document.querySelector('.licenseBox');
            let html='';
                html=`
                    <form class="licenseForm${index} postForm1">
                        <div class="tr licenseB">
                             <div class="td">
                                <select class="licenseCategory${index1}" name="lno">
                                </select>
                             </div>
                             <div class="td"><input type="date" name="ldate"></div>
                        </div>
                    </form>`
            licenseBox.innerHTML+=html;

            let licenseCategory = document.querySelector(".licenseCategory"+index1);
            let option='';
            r.forEach( license => {
                 option+=`<option value="${license.lno}">${license.lname}</option>`;
            });
            index1++;
            let button=document.querySelector('.lBtn');
                btn=`
                <button onclick="OnLicense()" type="button">자격증 등록</button>
                <button onclick="onClose(${2})" type="button">닫기</button>
                `
                button.innerHTML=btn
            licenseCategory.innerHTML += option;
        }
    })

}
//
//입력창 닫기 버튼
function onClose(num){
    console.log('close')
    if(num==1){
        console.log('1')
        document.querySelector('.careerBox').innerHTML='';
        document.querySelector('.cBtn').innerHTML='';
        CareerPrint();
    }
    else if(num==2){
        console.log('2')
        document.querySelector('.licenseBox').innerHTML='';
        document.querySelector('.lBtn').innerHTML='';
        licensePrint();
    }

}
//수정페이지로 이동
function employeeUpdate(){
location.href= `/employee/update?eno=${eno}`;
}//f end