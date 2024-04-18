


let pageObject = {
    page : 1,
    pageBoardSize : 20,
    state : -2 ,       //현재 카테고리
    key : "",
    keyword : ""
}

doViewList(1);


console.log("안녕")

function doViewList(page){
    console.log(page)
    pageObject.page =page;
    console.log(page)
    let html ="지급받을 대상이 없습니다.";
    $.ajax({
        url:"/salary/list.do",
        method:"get",
        data:pageObject,
        success: (r) => {
            html ="";
            console.log(r)
            r.list.forEach((i) => {
                 html += `<tr><td><input type="checkbox"
                 class="eno${i.employeeDto.eno}" value='{"eno":${i.employeeDto.eno},"smonth":"${i.smonth}" , "price":${i.price}}'></td>
                  <td><a href = "#"> ${i.employeeDto.ename} </a></td>
                   <td> ${i.price}만원</td>
                   <td> ${i.smonth.split(" ")[0]} </td>
                   <td><button type="button" onclick="doDel(${i.employeeDto.eno})">제거</button> </td></tr>`  //추후 상세보기 링크로 만들 예정
            })
            document.querySelector("#boardTableBody").innerHTML = html;
            let pagination = document.querySelector(".pagination");
            let pagehtml = "";
            pagehtml += `            <li class="page-item">
                                         <a class="page-link" onclick="doViewList(${page-1 < 1 ?1 : page-1})" >이전</a>
                                     </li>`
            for(let i = r.startbtn; i <=r.endbtn; i++){
                pagehtml +=  `<li class="page-item">
                                  <a class="page-link ${ i == page ? 'active' : '' }" onclick="doViewList(${i})"> ${i}</a>
                             </li>`
            }
            pagehtml += `            <li class="page-item">
                                          <a class="page-link" onclick="doViewList(${page+1 > r.totalpage ? r.totalpage : page+1})" >다음</a>
                                     </li>`
            pagination.innerHTML = pagehtml;
        }
    })
}



function doPost(){
    let checkboxes1 = document.querySelectorAll('input:checked');
    console.log(checkboxes1)
    let result = []
    checkboxes1.forEach(function(checkbox) {
        console.log(checkbox);
        result.push(checkbox.value)
    });
    console.log(result);
    $.ajax({
        type: "POST",
        url: "/salary/insert.do",
        contentType: "application/json",
        data: JSON.stringify(result),
        success: function(response) {
            // 성공적으로 응답을 받았을 때 실행할 코드
            alert("지급 완료")
            location.href="/salary/findlist"
        },
        error: function(xhr, status, error) {
            // 오류가 발생했을 때 실행할 코드
            console.error("Ajax 오류: " + status, error);
        }
    });
}

function doDel(i){

    let checkbox = document.querySelector(`.eno${i}`).value;
    let eno = checkbox.eno;
    let price = checkbox.price;
    console.log(checkbox)
    let result = JSON.parse(checkbox)
    console.log(result.eno)
    console.log(result.price);
    console.log(result.smonth)
    $.ajax({
        type: "get",
        url: "/salary/del.do",
        data : {eno:result.eno,price:result.price,smonth:result.smonth},
        success: (r) => {
            if(r){
                alert("삭제완료")
                location.href="/salary/list"
            }
            else{
                alert("삭제실패")
            }
        }
    })
}

function onPageBoardSize(object){
    console.log(object); console.log(object.value);
    pageObject.pageBoardSize = object.value
    doViewList(1)

}