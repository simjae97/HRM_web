console.log('myProject.js');
myProjectList()

// 나의 진행중인 프로젝트 리스트 출력
function myProjectList(){
    console.log('myProjectList()')
    let html =``;

    $.ajax({
        url : "/mypage/project/list" ,
        method : "get" ,
        async : false ,
        success : (r) => {
            console.log(r);
            // ajax 통신 후 받은 값이 null 이면 진행중 프로젝트 X
            if(r.pjno === 0){
                html += `
                    <h4>현재 진행중인 프로젝트가 없습니다.</h4>
                    <div>
                        <!--  - 즐겨찾기한 프로젝트 - 이전 프로젝트  버튼 -->
                        <button onclick="p_myProjectLike()" type="button">즐겨찾기한 프로젝트</button>
                        <button onclick="getPreviousProjectInfo()"type="button">내 이전 프로젝트</button>
                    </div>
                    `
            }else{
                html += `
                    <div><span class="viewTitle">사원 명 :</span><span class="id">${r.id}</span></div>
                    <div><span class="viewTitle">프로젝트 번호 :</span><span class="pjno">${r.pjno}</span></div>
                    <div><span class="viewTitle">시작 날짜 :</span><span class="start_date">${r.start_date}</span></div>
                    <div><span class="viewTitle">종료 날짜 :</span><span class="end_date">${r.end_date}</span></div>
                    <div><span class="viewTitle">프로젝트 명 :</span><span class="title">${r.title}</span></div>
                    <div><span class="viewTitle">회사 명 :</span><span class="compannyname">${r.compannyname}</span></div>
                    <div><span class="viewTitle">프로젝트 현재 상황 :</span><span class="state">${r.state == 0 ? "대기중" : r.state == 1 ? "진행중" : r.state == 2 ? "완료" : "관리자 문의"}</span></div>
                    <div>
                        <!--  - 즐겨찾기한 프로젝트 - 이전 프로젝트  버튼 -->
                        <button onclick="p_myProjectLike()" type="button">즐겨찾기한 프로젝트</button>
                        <button onclick="getPreviousProjectInfo()"type="button">내 이전 프로젝트</button>
                    </div>
                    `
            }
                document.querySelector("#p_myProjectdate").innerHTML=html;
        }

    }); // ajax 종료
} // 함수 종료

// 즐겨찾기한 프로젝트
function p_myProjectLike(){
    console.log('p_myProjectLike()');
    let tableHtml = ``;
    let html =`
    <table class="table">
    <thead>
        <tr>
            <th> 프로젝트 번호 </th>
            <th> 프로젝트 명 </th>
            <th> 프로젝트 회사 </th>
            <th> 프로젝트 비용 </th>
        </tr>
   </thead>
   <tbody class="p_myProjectLikeView">
   </tbody>
   </table>
        <div>
        <!--  - 즐겨찾기한 프로젝트 - 이전 프로젝트  버튼 -->
           <button onclick="myProjectList()" type="button">뒤로</button>
           <button onclick="getPreviousProjectInfo()"type="button">내 이전 프로젝트</button>
        </div>
   `;
   // html 기본 뼈대 넣기 끝
        $.ajax({
            url : "/mypage/project/list/like" ,
            method : "get" ,
            async : false ,
            success : (r) => {
                console.log(r);
                // 즐겨찾기한 리스트 가져오기
                r.forEach(( r2 )=>{
                    tableHtml += `
                    <tr>
                        <th> ${r2.pjno} </th>
                        <th> ${r2.title} </th>
                        <th> ${r2.compannyname} </th>
                        <th> ${r2.price} </th>
                    </tr>
                    `
                });
            document.querySelector("#p_myProjectdate").innerHTML=html;
            document.querySelector(".p_myProjectLikeView").innerHTML=tableHtml;
            }
        });
}

// 내 이전프로젝트 함수
function getPreviousProjectInfo(){
    console.log('getPreviousProjectInfo()')
    let tableHtml = ``;
    let html =`
        <table class="table">
        <thead>
            <tr>
                <th> 프로젝트 번호 </th>
                <th> 프로젝트 명 </th>
                <th> 프로젝트 시작일 </th>
                <th> 프로젝트 종료일 </th>
                <th> 프로젝트 상태 </th>
            </tr>
       </thead>
       <tbody class="p_getPreviousProjectView">
       </tbody>
       </table>
            <div>
            <!--  - 즐겨찾기한 프로젝트 - 이전 프로젝트  버튼 -->
               <button onclick="p_myProjectLike()" type="button">즐겨찾기한 프로젝트</button>
               <button onclick="myProjectList()" type="button">뒤로</button>
            </div>
       `;
       // html 기본 뼈대 넣기 끝
       $.ajax({
           url : "/mypage/project/previousList" ,
           method : "get" ,
           async : false ,
           success : (r) => {
               console.log(r);
               // 이전 완료한 프로젝트 리스트 가져오기
                r.forEach(( r2 )=>{
                    tableHtml += `
                    <tr>
                        <th> ${r2.pjno} </th>
                        <th> ${r2.title} </th>
                        <th> ${r2.start_date} </th>
                        <th> ${r2.end_date} </th>
                        <th> ${r2.state} </th>
                    </tr>
                    `
                });
            document.querySelector("#p_myProjectdate").innerHTML=html;
            document.querySelector(".p_getPreviousProjectView").innerHTML=tableHtml;
           } // success end
       }); // ajax end
} // f e

