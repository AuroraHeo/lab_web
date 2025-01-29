/**
 * event.html 파일에 포함.
 */

//DOM(Document Object Model)
//document 객체에 이벤트 리스너를 설정.
//DOMContentLoaded 이벤트 : 브라우저가 HTML문서의 모든 요소들을 생성하면 발생하는 이벤트
//HTML 문서의 초기 HTML이 완전히 로드되고 분석되었을 때 발생합니다. 
//이 이벤트를 사용하면, DOM이 완전히 준비된 후에 JavaScript 코드를 실행할 수 있어서 안전!!
document.addEventListener('DOMContentLoaded', function () {
    //HTML 문서의 모든 요소들이 생성된 이후에 실행될 코드.
    
    //button##btnInput 요소를 찾음.
    const btnInput = document.querySelector('button#btnInput');
    
    //btnInput에 클릭 이벤트 리스너를 설정.
    btnInput.addEventListener('click', function (e) {
        //console.log(e); //-> PointerEvent객체
        //e.target 프로퍼티 : 이벤트가 발생한 HTML 요소.
        
        //input#itemInput 요소를 찾음.
        const itemInput = document.querySelector('input#itemInput');
        //ul#itemList 요소를 찾음.
        const itemList = document.querySelector('ul#itemList');
        //itemList에 입력된 문자열을 itemList의 <li>로 추가(append).
        itemList.innerHTML += `<li>${itemInput.value}</li>`;
        //itemInput 입력된 내용을 지움.
        itemInput.value = '';
    });
    
    //input#itemInput2 요소를 찾음.
    const itemInput2 = document.querySelector('input#itemInput2');
    
    //itemInput2 요소에 'keydown' 이벤트 리스너를 설정.
    itemInput2.addEventListener('keydown', (e) => { // itemInput2.addEventListener('keydown', function (e) {});
        console.log(e); //-> KeyboardEvent
        // e.key 프로퍼티: 어떤 키보드가 down이 됐는지 알려주는 프로퍼티.
        //'Enter'가 입력이 됐을 때
        // itemInput2에 입력된 문자열을 ul#itemList2에 <li>을 추가(append)하고 input의 내용은 지움.
        if(e.key === 'Enter'){ //엔터를 누르면
            const itemList2 = document.querySelector('ul#itemList2');
            itemList2.innerHTML += `<li>${itemInput2.value}</li>`;
            itemInput2.value='';
        }
    });
    
    //input#userid 요소를 찾음.
    const userid = document.querySelector('input#userid');
    
    //userid에 'change' 이벤트 리스너를 설정.
    userid.addEventListener('change', (e) => { //포커스를 잃었을 때
//        console.log(e);//-> Event
        //userid에 입력된 값을 div#result에 출력.
        const result = document.querySelector('div#result');
        result.innerHTML = `<span style="color: red;"> ${userid.value} </span>`;
    });
    
    //img#bulb 요소를 찾음.
    const bulb = document.querySelector('img#bulb');
    
    //bulb에 'mouseenter' 이벤트 리스너를 설정 -> 이미지를 bulb_on.gif로 교체.
    bulb.addEventListener('mouseenter', (e) => {
        bulb.src = 'images/bulb_on.gif';
        bulb.alt = 'bulb_on';
    });
    
    //bulb에 'mouseleave' 이벤트 리스너를 설정 -> 이미지를 bulb_off.gif로 교체.
    bulb.addEventListener('mouseleave', () => {
        bulb.src = 'images/bulb_off.gif';
        bulb.alt = 'bulb_off'; 
    });
}); 