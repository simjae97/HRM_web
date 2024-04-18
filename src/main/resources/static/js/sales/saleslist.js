console.log("안녕")



//============ 페이지 정보 관련 객체 = 여러개의 변수를 묶음 =============
let pageObject={
    page : 1,           //현재페이지
    pageBoardSize : 5,  //페이지당 표시 할 개수
    sortKey : 0,            //정렬 기준
    key : null,   //현재검색 key
    keyword : '',       //현재검색 keyword
    startPrice : 0,     //규모로 검색하는 경우 시작금액
    endPrice : 0        //규모로 검색하는 경우 끝 금액
}
//================================================================

searchProject();    //검색 페이지 띄우기
printProjet(1);
console.log("projectPage-js");

//전체 프로젝트리스트 출력
function printProjet(page){
    console.log("printProjet()");
    pageObject.page=page;   //현재페이지 대입


    $.ajax({
        url : "/sales/list.do",
        method : "get",
        data : pageObject,
        success : (r)=>{
            console.log(r);
            console.log("pageObject.keyword : ");

            //전체 리스트 출력
            let html=``;
            r.objectList.forEach((result)=>{
                html+=`<tr>
                           <th>${result.spjno}</th>
                           <td><a href="/sales/view?spjno=${result.spjno}">${result.title}</a></td>
                           <td>${result.compannyname}</td>
                           <td>${result.price}</td>
                           <td>${result.rank1_count + result.rank2_count + result.rank3_count}</td>
                           <td>${result.state==0 ? "진행전" : (result.state==1 ? "진행중" : "진행완료")}</td>
                           <td>${result.start_date}</td>
                           <td>${result.end_date}</td>
                       </tr>`;
            })//for end
            document.querySelector("#projectList").innerHTML=html;

            //===== 페이지네이션 =====
            html=`<li class="page-item"><a class="page-link" onclick="printProjet(${page-1<1 ? page : page-1})">Previous</a></li>`;
            for(let i=r.startPage; i<=r.endPage; i++){
                html+=`<li class="page-item"><a class="page-link" onclick="printProjet(${i})">${i}</a></li>`;
            }
            html+=`<li class="page-item"><a class="page-link" onclick="printProjet(${page+1>r.totalPage ? r.totalPage : page+1})">Next</a></li>`;

            document.querySelector(".pagination").innerHTML=html;
        }//success end
    })//ajax end
}//f end

//검색 기준 선택
function searchProject(){
    console.log("searchProject-js");

    let searchCategory= document.querySelector(".searchCategory").value;    //검색 기준정보 가져오기
    let searchInput=document.querySelector(".searchInput");     //검색 기준에 따른 input형태가 바뀜
    switch (searchCategory){
        case "1" :
            searchInput.innerHTML=`<input class="searchValue" type="text" />`;
            pageObject.key="title";
            break;
        case "2" :
            searchInput.innerHTML=`<input class="searchValue" type="text" />`;
            pageObject.key="compannyname";
            break;
        case "3" :
            searchInput.innerHTML=`<input class="searchValue1 searchPrice" type="number" />만원 ~ <input class="searchValue2 searchPrice" type="number" />만원`;
            pageObject.key="price";
            break;
        case "4" :
            searchInput.innerHTML=`<select class="searchValue">
                                        <option value="0"> 진행전 </option>
                                        <option value="1"> 진행중 </option>
                                        <option value="2"> 진행완료 </option>
                                    </select>`;
            pageObject.key="state";
            break;
        case "5" :
            searchInput.innerHTML=`<input class="searchValue" type="date" />`;
            pageObject.key="start_date";
            break;
        case "6" :
            searchInput.innerHTML=`<input class="searchValue" type="date" />`;
            pageObject.key="end_date";
            break;
        default :
            searchInput.innerHTML=`<input class="searchValue" type="text" />`;
            pageObject.key=null;
    }//switch end
}//f end

//검색기능
function onSearch(){
    if(pageObject.key!=null){   //검색 기준을 규모로 선택한 경우
        if(document.querySelector(".searchCategory").value=="3"){   //규모를 기준으로 검색하는 경우
            pageObject.startPrice=document.querySelector(".searchValue1").value =="" ? 0 : document.querySelector(".searchValue1").value;    //시작금액
            pageObject.endPrice=document.querySelector(".searchValue2").value =="" ? 0 : document.querySelector(".searchValue2").value;    //끝 금액

        }
        else{
            pageObject.keyword=document.querySelector(".searchValue").value;    //현재 검색어
            console.log("pageObject.keyword : "+document.querySelector(".searchValue").value);
        }
    }//if end
    printProjet(1);
}//f end

//정렬기준
function sortProject(){
let sortStandard = document.querySelector(".sortStandard").value;
    switch (sortStandard) {
        case "1" :  //규모 적은순
            pageObject.sortKey=1;
            break;
        case "2" :  //전체인원 적은순
            pageObject.sortKey=2;
            break;
        default :   //기본정렬 : 시작날짜 빠른순
            pageObject.sortKey=0;
    }//switch end
    onSearch();
}//f end


