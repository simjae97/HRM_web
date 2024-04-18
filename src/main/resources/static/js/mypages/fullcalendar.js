var arr = new Array(); // 빈 배열 생성 출근체크 값 가져오기

// 기본 달력 실행 함수
calendar()

// 출근 체크 통신 함수
function goToWork(){
    console.log('goToWork()');
    $.ajax({
        url: '/mypage/attendance/goToWork',
        method: 'get',
        async : false,
        success: (r) => {
            console.log(r);
            if(r){
                alert('출근체크 성공')
                calendar();
            }else{
                alert('출근체크 실패 관리자에게 문의')
            }
        }
    });
}

// 퇴근 체크 통신 함수
function leaveWork(){
    console.log('leaveWork()');
    $.ajax({
        url: '/mypage/attendance/leaveWork',
        method: 'get',
        async : false ,
        success: (r) => {
            console.log(r);
            if(r){
                alert('퇴근체크 성공')
                calendar();
            }else{
                alert('퇴근체크 실패 관리자에게 문의')
            }
        }
    });

}


// 달력 출력 함수
function calendar(){
    let str = '';
    // 데이터를 JAVA 통신
$.ajax({    // 달력에 출근 , 퇴근 시간 출력 통신
    url: '/mypage/event',
    method: 'get',
    async : false ,
    success: (r) => {
    console.log(r);

    r.forEach(data => {
        if(data.stat_time !== null && data.stat_time !== undefined) {
          str += ' 출근&nbsp&nbsp'+data.stat_time
        } else {
          // null 및 undefined인 경우 실행되는 로직
        }
        if(data.end_time !== null && data.end_time !== undefined) {
          str += ',' + '  퇴근&nbsp&nbsp'+data.end_time
        } else {
          // null 및 undefined인 경우 실행되는 로직
        }
//        str = (' 출근&nbsp&nbsp'+data.stat_time+',' + ' 퇴근&nbsp&nbsp'+data.end_time);

    });

    console.log('str' +  str);
    // 출근 퇴근을 하나의 문자열로 만든 거에 "," 문자를 <br/> 태그로 치환
    let test = str.replace(',', '<br/>');

       // 달력 생성 함수
       function createCalendar(year, month) {
         // 현재 날짜 정보 가져오기
         var today = new Date();
         var currentYear = today.getFullYear();
         var currentMonth = today.getMonth() + 1;
         var currentDay = today.getDate();

         // 이전 달과 다음 달을 계산
         var prevMonth = new Date(year, month - 1, 0);
         var currentMonth = new Date(year, month, 0);
         var nextMonth = new Date(year, month + 1, 0);

         // 달력 헤더 생성
         var calendarHTML = '<table class="calendar">';
         calendarHTML += '<tr class= "calendarCenter" ><th colspan="7">' + currentMonth.getFullYear() + '년 ' + (currentMonth.getMonth() + 1) + '월</th></tr>';
         calendarHTML += '<tr><th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th></tr>';

         // 달력 바디 생성
         calendarHTML += '<tr>';
         var day = 1;
         for (var i = 0; i < 42; i++) {
           if (i < currentMonth.getDay() || day > currentMonth.getDate()) {
             calendarHTML += '<td></td>'; // 이전 달이나 다음 달의 빈 칸 생성
           } else {
             if (year === currentYear && month === currentMonth.getMonth() + 1 && day === currentDay) {
               calendarHTML += '<td class="today">' + day + `<div class="event-text">${test}</div></td>`; // 오늘 날짜일 경우 강조

             } else {
               calendarHTML += '<td>' + day + '</td>'; // 현재 달의 날짜 생성
             }
             day++;
           }
           if ((i + 1) % 7 === 0) {
             calendarHTML += '</tr>';
             if (day > currentMonth.getDate()) break; // 현재 달의 마지막 날짜에 도달하면 루프 종료
             calendarHTML += '<tr>';
           }
         }
         calendarHTML += '</table>';

         // HTML에 달력 삽입
         document.getElementById('calendar').innerHTML = calendarHTML;
       }

       // 현재 날짜를 기준으로 달력 생성
       var currentDate = new Date();
       createCalendar(currentDate.getFullYear(), currentDate.getMonth() + 1);

    }
});

}