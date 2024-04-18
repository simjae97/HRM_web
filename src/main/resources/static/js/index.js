
// 1. 로그인 여부 확인 요청 [ JS 열릴때마다 체크 ]
$.ajax({
    url : '/employee/login/check' ,
    method : 'get' ,
    async : false,
success : (r)=>{
        console.log(r);
        // 1. 어디에
        let login_menu = document.querySelector('#login_menu');
        // 2. 무엇을
        let html = '';
        if(r != ''){ // 로그인 했을떄
        $.ajax({
            url : '/employee/login/checkname' ,
            method : 'get' ,
            data:{eno : r},
            async : false ,
            success : (r2) =>{
                console.log(r2)
                html = `
                    <li class="nav-item">
                        <a class="nav-link" onclick = "logout()">로그아웃</a>
                    </li>
                    <li class="nav-item">
                        ${r2}님
                    </li>
                `;
            }
        });
            }else{  // 로그인 안했을떄
                html = `
                    <li class="nav-item">
                        <a class="nav-link" href="/login">로그인</a>
                    </li>
                `;
            }
            // 3. 대입
            login_menu.innerHTML = html;
    } // s end
}); // ajax end

//2. 로그인
function login(){
    console.log('login')
    //1. HTML 입력값 호출 [document.querySelector()]
    let id =document.querySelector('#id').value; console.log(id);
    let pw=document.querySelector('#pw').value; console.log(pw);
    //2. 객체화
    let info={id : id , pw : pw};
    console.log(info);
    //3. 객체를 배열에 저장
    $.ajax({
        url : '/login',
        method : 'POST',
        data :  info  ,
        success :( result )=>{
            console.log(result)
            if(result){
            alert('로그인성공');
            // js 페이지 전환
            location.href="/"; // 로그인 성공하면 메인 페이지
            }else{
            alert('로그인 실패')
            }
        }
    })
    //4. 결과

}

// 3. 로그아웃
function logout(){
$.ajax({
    url: `/logout`,
    method: `get`,
    success: (r)=>{
        if(r){
        alert('로그아웃 했습니다.');
        location.href='/'
        }else{
        alert('로그아웃 실패 관리자에게 문의')
        }
    }
})
}

//관리자 확인    240317 장은경 수정
function managerCheck(urlString){
    $.ajax({
        url : "/managerCheck",
        method : "Get",
        success : (r) => {
            console.log(r);
            if(r){
                location.href=`${urlString}`;
            }
            else{
                alert("권한이 없습니다.");
            }
        }
    })//ajax end
}//f end

//로그인 확인  > 쪽지페이지 이동 240317 장은경 수정
function loginCheck(){
    $.ajax({
        url : '/employee/login/check' ,
        method : 'get' ,
        async : false,
        success : (r)=>{
            console.log(r);
            // 1. 어디에
            let login_menu = document.querySelector('#login_menu');
            // 2. 무엇을
            let html = '';
            if(r != ''){ // 로그인 했을떄
                location.href="/note/receive";
            }
            else{
                alert("로그인이 필요한 서비스입니다.");
                location.href="/login"; // 로그인 페이지로 이동
            }
        }//s end
    })//ajax end
}//f end

