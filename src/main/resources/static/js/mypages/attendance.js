console.log('attendance.js');
// 출퇴근 조회 출력 JS


getEvents();

// 출퇴근 조회 내역 출력 통신 요청
function getEvents(){
    console.log('getEvents()')
    let tableHtml = ``;
        let html =`
            <table class="table">
            <thead>
                <tr>
                    <th> 날짜 </th>
                    <th> 출근 시간 </th>
                    <th> 퇴근 시간 </th>
                    <th> 근무 시간 </th>
                </tr>
           </thead>
           <tbody class="p_getEventsView">
           </tbody>
           </table>
           `;
           // html 기본 뼈대 넣기 끝
       $.ajax({
           url : "/mypage/attendance/list.do" ,
           method : "get" ,
           async : false ,
           success : (r) => {
               console.log(r);
               // 이전 완료한 프로젝트 리스트 가져오기
                r.forEach(( r2 )=>{
                    tableHtml += `
                    <tr>
                        <th> ${r2.jday} </th>
                        <th> ${r2.stat_time} </th>
                        <th> ${r2.end_time} </th>
                        <th> ${r2.working_time} </th>
                    </tr>
                    `
                });
            document.querySelector("#p_attContent").innerHTML=html;
            document.querySelector(".p_getEventsView").innerHTML=tableHtml;
           } // success end
       }); // ajax end
}
