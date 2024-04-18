
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

