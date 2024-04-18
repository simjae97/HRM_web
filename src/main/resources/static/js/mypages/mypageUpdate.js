console.log('mypageUpdate.js');

mypageUpdateView();
function mypageUpdateView(){
    console.log('mypageUpdateView');
    $.ajax({
            url: '/mypage/info',
            method: 'get',
            success: (r) => {
            console.log(r);
            document.querySelector('.name').value = r.ename;
            document.querySelector('.email').value = r.email;
            document.querySelector('.phone').value = r.phone;
            document.querySelector('.address').value = r.address;
       }

    });
}

// 마이페이지 내 개인정보 수정 요청
function mypageUpdate(){
     // 데이터 가져오기
     let email = document.querySelector('.email').value
     let phone = document.querySelector('.phone').value
     let address = document.querySelector('.address').value
    // 데이터 객체화
     let data = {
        email : email ,
        phone : phone ,
        address : address
     };
    // 데이터를 JAVA 통신
    $.ajax({
        url: '/mypage/update.do',
        method: 'put',
        data : data ,
        success: (r) => {
            console.log(r);
            if(r){
                alert('수정되었습니다.')
                location.href="/mypage";
            }else{
                alert('수정실패 관리자 문의 바랍니다.')
                location.href="/mypage";
            }
        }
    });
}

// 마이페이지 비밀번호 수정 요청
function mypageUpdatePw(){
     // 데이터 가져오기
     let pw = document.querySelector('.pw').value
     let newpw = document.querySelector('.newpw').value
     // 객체화
     let data = {
        pw : pw ,
        newpw : newpw
     }; console.log(data);
     // 데이터를 JAVA 통신
    $.ajax({
        url: '/mypage/updatepw.do',
        method: 'put',
        data : data ,
        success: (r) => {
            console.log(r);
            if(r){
                alert('수정되었습니다.')
                location.href="/mypage";
            }else{
                alert('기존 비밀번호가 다릅니다.')
                location.href="/mypage";
            }
        }
    });
}


/*
    <div id="wrap">
        <div>
            <label>수정할 비밀번호 :</label> <input type="password"/> <br/>
            <label>수정할 이메일  : </label> <input type="email"/> <br/>
            <label>수정할 전화번호 : </label> <input type="test"/> <br/>
            <label>수정할 주소 : </label> <input type="text"/> <br/>
        </div>
        <button onclick="#" type="button">수정</button>
        <button onclick="#" type="button">취소</button>
    </div>



*/