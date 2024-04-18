let pjno = new URL(location.href).searchParams.get("pjno")


onWrite();


function onWrite(){
    console.log(pjno)

    $.ajax({
        type: "get",
        url: "/project/view/eval.do?pjno="+pjno,
        async: false,
        success: (r) => {
        let html = "";
        r.forEach( (data) => {
            $.ajax({
                type : "get",
                url: "/project/findname?eno="+data.eno,
                success: (re) =>{
                console.log(re)}
            })
            html += `<div> 사원번호 : ${data.eno}  상태: <input type="text" value="${data.state}"> 점수:<input type="text" value="${data.score}"></div>`

        })

        document.querySelector(".container2").innerHTML=html;

        }
    });
}
