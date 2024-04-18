let pjno = new URL(location.href).searchParams.get("pjno")
let rank1 = [];
let rank2 = [];
let rank3 = [];
let rank1_count = 0;
let rank2_count = 0;
let rank3_count = 0;


$.ajax({
     url: "/project/view.do?pjno="+pjno,
     method : "get",
     async: false,
     success : (r) => {
     rank1_count = r.rank1_count
     rank2_count = r.rank2_count
     rank3_count = r.rank3_count
    }
})


onWrite();

function onWrite(){
    console.log(pjno)
    let html = "";

    $.ajax({
        type: "get",
        url: "/project/view/re.do?pjno="+pjno,
        async : false,
        success: (r) => {
            check = [r[0].length,r[1].length,r[2].length]
                        console.log(check)
                        console.log(r)
                        for(let j=0; j < r[0].length; j++){
                            let obj = {}; // 빈 객체 생성
                            obj[`${r[0][j].employeeDto.eno}`] = r[0][j].score; // 객체에 속성 추가
                            rank1.push(obj); // 객체를 배열에 추가
                            html += `<tr> <td><input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[0][j].employeeDto.eno}" value ="${r[0][j].employeeDto.eno}"></input>
                            <td>${r[0][j].employeeDto.eno}</td>
                            <td>${r[0][j].employeeDto.ename}</td>
                            <td><img src= "/img/eimg/${r[0][j].employeeDto.img}"/></td>
                            <td>${r[0][j].score}</td></tr>
                            `

                        }

                        document.querySelector(".s_check1").innerHTML = html;
                        html = "";
                        for(let j=0; j < r[1].length; j++){
                            let obj = {}; // 빈 객체 생성
                            obj[`${r[1][j].employeeDto.eno}`] = r[1][j].score; // 객체에 속성 추가
                            rank2.push(obj); // 객체를 배열에 추가
                            html += `<tr> <td><input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[1][j].employeeDto.eno}" value ="${r[1][j].employeeDto.eno}"></input>
                                                     <td>${r[1][j].employeeDto.eno}</td>
                                                     <td>${r[1][j].employeeDto.ename}</td>
                                                     <td><img src= "/img/eimg/${r[1][j].employeeDto.img}"/></td>
                                                     <td>${r[1][j].score}</td></tr>`
                        }
                        document.querySelector(".s_check2").innerHTML = html;

                        html = "";
                        for(let j=0; j < r[2].length; j++){
                            let obj = {}; // 빈 객체 생성
                            obj[`${r[2][j].employeeDto.eno}`] = r[2][j].score; // 객체에 속성 추가
                            rank3.push(obj); // 객체를 배열에 추가
                            html += `<tr> <td><input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[2][j].employeeDto.eno}" value ="${r[2][j].employeeDto.eno}"></input>
                                                     <td>${r[2][j].employeeDto.eno}</td>
                                                     <td>${r[2][j].employeeDto.ename}</td>
                                                     <td><img src= "/img/eimg/${r[2][j].employeeDto.img}"/></td>
                                                     <td>${r[2][j].score}</td></tr>`
                        }
                        document.querySelector(".s_check3").innerHTML = html;
                    }
    });

}

function s_doPost(){
    let enos = [[],[],[]];
    let checkboxes1 = document.querySelectorAll('.s_check1  input:checked');
    let checkboxes2 = document.querySelectorAll('.s_check2  input:checked');
    let checkboxes3 = document.querySelectorAll('.s_check3  input:checked');
    checkboxes1.forEach(function(checkbox) {
        enos[0].push(checkbox.value);
    });
    checkboxes2.forEach(function(checkbox) {
            enos[1].push(checkbox.value);
        });
    checkboxes3.forEach(function(checkbox) {
        enos[2].push(checkbox.value);
    });
    console.log(enos)
    let data = {
        enos: enos,
        pjno: pjno
    };

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(data), // 수정된 부분
        url: '/project/view/reassign',
        success: (r) => {
            if(r){
            alert("전송 성공")
            location.href="/projectPage/detail?pjno="+pjno
            }
            else{
            alert("인원수 초과")
            }
        },
        error:(e) => {
            alert("error: " + e);
        }
    });
}
function getCheckboxValue()  {
  // 선택된 목록 가져오기
      let result = '';
    let enos = [[],[],[]];
    let checkboxes1 = document.querySelectorAll('.s_check1  input:checked');
    let checkboxes2 = document.querySelectorAll('.s_check2  input:checked');
    let checkboxes3 = document.querySelectorAll('.s_check3  input:checked');
    checkboxes1.forEach(function(checkbox) {
        enos[0].push(checkbox.value);
        console.log(checkbox)
        result += checkbox.value+"번 ";
    });
    checkboxes2.forEach(function(checkbox) {
            enos[1].push(checkbox.value);
            result += checkbox.value+"번 ";
        });
    checkboxes3.forEach(function(checkbox) {
        enos[2].push(checkbox.value);
        result += checkbox.value+"번 ";
    });

    console.log(enos)

  // 선택된 목록에서 value 찾기

  // 출력
  document.querySelector('.selected').innerHTML = result;
}

function autowired(){
    console.log(rank1)
    console.log(rank2)
    console.log(rank3)
   let keys = []
   let srank1 = rank1.map(obj => ({key: Object.keys(obj)[0], value: Object.values(obj)[0]}));
   let srank2 = rank2.map(obj => ({key: Object.keys(obj)[0], value: Object.values(obj)[0]}));
   let srank3 = rank3.map(obj => ({key: Object.keys(obj)[0], value: Object.values(obj)[0]}));
    srank1.sort((a, b) => {
      return b.value - a.value;
    });
    srank2.sort((a, b) => {
        return b.value - a.value;
    });
    srank3.sort((a, b) => {
    return b.value - a.value;
    });
   let autowired = srank1.slice(0, rank1_count)
                   .concat(srank2.slice(0, rank2_count))
                   .concat(srank3.slice(0, rank3_count));
   console.log(autowired);
    for (let i = 0; i < autowired.length; i++) {
        let obj = autowired[i].key;
        keys.push(obj)
    }
    console.log(keys)
    keys.forEach((i) => {
        console.log(i);
        console.log(`eno${i.key}`)
        let checkbox = document.querySelector(`.eno${i}`);
        checkbox.checked = true;
    });


}




function sortbyscore(){
    console.log(pjno)
    let html = "";

    $.ajax({
        type: "get",
        url: "/project/view/re.do?pjno="+pjno,
        async : false,
        success: (r) => {
check = [r[0].length,r[1].length,r[2].length]
            r[0].sort((a, b) => {
                return b.score - a.score;
            });
            r[1].sort((a, b) => {
                return b.score - a.score;
            });
            r[2].sort((a, b) => {
                return b.score - a.score;
            });

            console.log(r[0])
            for(let j=0; j < r[0].length; j++){
                html += `<tr><td> <input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[0][j].employeeDto.eno}" value ="${r[0][j].employeeDto.eno}"></input> </td>
                <td>${r[0][j].employeeDto.eno}</td>
                <td>${r[0][j].employeeDto.ename}</td>
                <td><img src= "/img/eimg/${r[0][j].employeeDto.img}"/></td>
                <td>${r[0][j].score}</td>
                </tr>`

            }

            document.querySelector(".s_check1").innerHTML = html;
            html = "";
            for(let j=0; j < r[1].length; j++){
                html += `<tr><td> <input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[1][j].employeeDto.eno}" value ="${r[1][j].employeeDto.eno}"></input> </td>
                                         <td>${r[1][j].employeeDto.eno}</td>
                                         <td>${r[1][j].employeeDto.ename}</td>
                                         <td><img src= "/img/eimg/${r[1][j].employeeDto.img}"/></td>
                                         <td>${r[1][j].score}</td>
                                         </tr>`
            }
            document.querySelector(".s_check2").innerHTML = html;

            html = "";
            for(let j=0; j < r[2].length; j++){
                html += `<tr><td> <input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[2][j].employeeDto.eno}" value ="${r[2][j].employeeDto.eno}"></input> </td>
                                         <td>${r[2][j].employeeDto.eno}</td>
                                         <td>${r[2][j].employeeDto.ename}</td>
                                         <td><img src= "/img/eimg/${r[2][j].employeeDto.img}"/></td>
                                         <td>${r[2][j].score}</td>
                                         </tr>`
            }
            document.querySelector(".s_check3").innerHTML = html;
        }
    });
}

//function sortbyeno(){
//    console.log(pjno)
//    let html = "사용 가능한 초급 인원 : <br/>";
//
//    $.ajax({
//        type: "get",
//        url: "/project/view/re.do?pjno="+pjno,
//        async : false,
//        success: (r) => {
//            for(let j=0; j < r[0].length; j++){
//                html += `<div> <input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[0][j].employeeDto.eno}" value ="${r[0][j].employeeDto.eno}">
//                <span>${r[0][j].employeeDto.eno}번</span>
//                <span>이름 : ${r[0][j].employeeDto.ename}번</span>
//                <span>사진 : ${r[0][j].employeeDto.img}번</span>
//                <span>고과점수 : ${r[0][j].score}</span>
//                </input> </div>`
//            }
//            document.querySelector(".s_check1").innerHTML = html;
//            html = "사용 가능한 중급 인원 : <br/>";
//            for(let j=0; j < r[1].length; j++){
//                html += `<div> <input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[1][j].employeeDto.eno}" value ="${r[1][j].employeeDto.eno}">
//                <span>${r[1][j].employeeDto.eno}번</span>
//                <span>이름 : ${r[1][j].employeeDto.ename}</span>
//                <span>사진 : ${r[1][j].employeeDto.img} </span>
//                <span>고과점수 : ${r[1][j].score}</span>
//                </input> </div>`
//            }
//            document.querySelector(".s_check2").innerHTML = html;
//
//            html = "사용 가능한 고급 인원 :<br/>";
//            for(let j=0; j < r[2].length; j++){
//                html += `<div> <input type ="checkbox" onclick='getCheckboxValue()' class ="eno${r[2][j].employeeDto.eno}" value ="${r[0][2].employeeDto.eno}">
//                <span>${r[2][j].employeeDto.eno}번</span>
//                <span>이름 : ${r[2][j].employeeDto.ename}</span>
//                <span>사진 : ${r[2][j].employeeDto.img} </span>
//                <span>고과점수 : ${r[2][j].score}</span>
//                </input> </div>`
//            }
//            document.querySelector(".s_check3").innerHTML = html;
//        }
//    });
//}

function test(){
    $.ajax({
        method:"get",
        url:"/salary/test",
        success:(r)=>{
            console.log("안녕")
        }
    })

}