/**
 *  switch.html파일에 포함.
 */

//아이디가 weekday인 select 요소를 찾음.
const weekday = document.querySelector('select#weekday');

//아이디가 btn인 button 요소를 찾음.
const btn = document.querySelector('button#btn');

//아이디가 result인 div 요소를 찾음.
const result = document.querySelector('div#result');

//btn에 클릭 이벤트 핸들러(리스너)를 설정: addEventListener(이벤트이름, 핸들러 함수 이름)
btn.addEventListener('click', selectHandler);

// 이벤트 핸들러 함수 선언(정의)
function selectHandler() {
    // weekday에서 선택된 값을 읽음.
    const value = weekday.value;
    console.log(value);
    
    switch (value) {
        case 'mon':
        case 'tue':
        case 'wed':
        case 'thu':
        case 'fri':
            result.innerHTML = '학원 출석.';
            break;
        case 'sat':
        case 'sun':
            result.innerHTML = '주말 휴식.';
            break;
        default:
            result.innerHTML = '몰라요~';
    }
}