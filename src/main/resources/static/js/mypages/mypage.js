console.log('mypage.js 호출');

onMypageView()
function onMypageView(){

    let dataView = document.querySelector('#p_dataView');
    console.log(dataView);

    let html = '';
    $.ajax({
        url: '/mypage/info',
        method: 'get',
        async : false ,
        success: (r) => {
            console.log(r);
            let 부서명 = '';
            let 성별 ='';
            if(r.sex){성별 = '여성'}else{ 성별 = '남성'};
            if(r.pno == 1){부서명 = '인사과' };
            if(r.pno == 2){부서명 = '영업' };
            if(r.pno == 3){부서명 = '프로그래머' };

            html += `
            <div class="p_Viewbox" >
                <div>
                    <span>이름 </span>${r.ename}
                </div>
                <div>
                    <span>이메일 </span>${r.email}
                </div>
                <div>
                    <span>전화번호 </span>${r.phone}
                </div>
                <div>
                    <span>주소 </span>${r.address}
                </div>
                <div>
                    <span>학력 </span>${r.eeducation}
                </div>
                <div>
                    <span>업무부서 </span>${부서명}
                </div>
                <div>
                    <span>입사일 </span>${r.edate}
                </div>
            </div>
                <div class="p_ViewImg">
                    <img src="/img/eimg/${r.img}" alt="프로필사진">
                </div>

            `;
                dataView.innerHTML = html;
        }
    });
     // 부서 번호 1 : 인사과 2 : 영업 3: 프로그래머
}




