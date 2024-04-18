
let pageObject = {
    page : 1,
    pageBoardSize : 20,
    state : -2 ,       //현재 카테고리
    key : "",
    keyword : ""
}

doViewList(1);


function doViewList(page){
    pageObject.page =page; //매개변수로 들어온 페이지를 현재 페이지에 대입

    $.ajax({
        url: "/salary/findlist.do",
        method: "get",
        data: pageObject,
        success : (r) => {
            console.log(r)
            let boardTableBody = document.querySelector("#boardTableBody")
            let html = ""
            r.list.forEach(board => {
                html += `        <tr>
                                     <th >${board.sno}</th>
                                     <td ><a href = "/board/view?bno=${board.bno}" >${board.employeeDto.id} </a></td>
                                     <td >${board.smonth.split(" ")[0]}</td>
                                     <td >${board.price}</td>
                                 </tr>`
            })
            boardTableBody.innerHTML = html
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

    return;
}

function onPageBoardSize(object){
    console.log(object); console.log(object.value);
    pageObject.pageBoardSize = object.value
    doViewList(1)

}

//function onBcno(bcno){
//    // bcno : 카테고리 식별번호 [0: 없다 -> 전체보기 ] [1:1번카테고리만 ,2:2번카테고리만]
//    pageObject.bcno = bcno;
//    //검색 제거
//    pageObject.key = "b.btitle";
//    pageObject.keyword = "";
//    document.querySelector(".key").value = "b.btitle";
//    document.querySelector(".keyword").value = "";
//    let categoryBtns = document.querySelectorAll(".catebtn > button");
//    console.log(categoryBtns)
//    for (let i = 0; i<categoryBtns.length; i++){
//        categoryBtns[i].classList.remove("usecate");
//    }
//    categoryBtns[bcno].classList.add("usecate");
//    doViewList(1);
//}

function onSearch(){
    let key = document.querySelector(".key").value;
    let keyword = document.querySelector(".keyword").value;
    pageObject.key = key;
    pageObject.keyword = keyword;

    doViewList(1);
}