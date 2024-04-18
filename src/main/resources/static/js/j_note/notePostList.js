let pageInfo={
    page : 1,
    pageBoardSize : 10
}

printNoteList(1)

//쪽지 전체리스트 출력
function printNoteList(page){
    console.log("printNoteList-js 실행");
    pageInfo.page=page;

    $.ajax({
        url : "/note/getPost.do",
        method : "Get",
        data : pageInfo,
        success : (r) => {
            console.log(r);

            //전체 리스트 출력
            let html=``;
            let noteCount=r.objectList.length+1;
            r.objectList.forEach((result)=>{
                html+=`<tr>
                           <th>${--noteCount}</th>
                           <td><a href="/note/getPostDetail?nno=${result.nno}">${result.ncontent.substr(0, 8)}...</a></td>
                           <td>${result.sendid}</td>
                           <td>${result.ndate}</td>
                       </tr>`;
            })//for end
            document.querySelector("#projectList").innerHTML=html;

            //===== 페이지네이션 =====
            html=`<li class="page-item"><a class="page-link" onclick="printNoteList(${page-1<1 ? page : page-1})">Previous</a></li>`;
            for(let i=r.startPage; i<=r.endPage; i++){
                html+=`<li class="page-item"><a class="page-link" onclick="printNoteList(${i})">${i}</a></li>`;
            }
            html+=`<li class="page-item"><a class="page-link" onclick="printNoteList(${page+1>r.totalPage ? r.totalPage : page+1})">Next</a></li>`;

            document.querySelector(".pagination").innerHTML=html;
        }//s end
    })//ajax end
}//f end