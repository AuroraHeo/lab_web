/**
 * condition.html파일에 포함.
 * 
 * 자바는 if () 구문에서는 조건식만 사용 가능!
 * 자바 if(식)의 식을 평가했을 때 true/false여야함.
 * (예) if (x % 2 == 1) { ... } --> 문법에 맞는 문장
 * (예) if (x % 2) { ... } --> 문법 오류
 * 
 * 자바스크립트는 false로 취급하는 값들이 있음.
 * - 숫자 0
 * - 빈 문자열('')
 * - 빈 배열([])
 * - null
 * - NaN(Not a number)
 * - undefined(값이 할당되지 않은 변수). (예) let x;  
 * (예) if (x % 2) { ... } --> 자바스크립트에서는 허용되는 문법. ()안에 산술연산식도 가능.
 */

// JS 비교 연산자: ==, !=, ===, !==
// (1) == , != : 자바스크립트가 변수 타입을 암묵적으로 변환해서 값만 비교.
// (2) ===, !== : 값뿐만 아니라 타입도 함께 비교.(타입이 다르면 값도 다르다고 인식)
console.log(1 == '1'); //-> true
console.log(1 === '1'); //-> false

// 아이디가 numberInput인 input 요소를 찾음.
const numberInput = document.querySelector('input#numberInput');
console.log(numberInput);

// 아이디가 result인 div 요소를 찾음.
const result = document.querySelector('div#result');
console.log(result);

function checkEven(){
    // parseInt(string): string을 정수로 변환해서 리턴하는 함수.
    //-> 빈 문자열인 경우 NaN을 리턴.
    const number = parseInt(numberInput.value); //Input에 입력된 값.
    console.log('number =', number);
    
    if(isNaN(number)){ // NaN은 비교 연산자(==, !=)를 사용할 수 없음.
        result.innerHTML = '입력값이 정수가 아닙니다.';
    //if (number % 2 == 1) {}
    }else if(number % 2){ //number를 2로 나눈 나머지가 있으면
        result.innerHTML = '홀수';
    }else{
        result.innerHTML = '짝수';
    }
}

function checkPositive() {
    //Number(string): string을 숫자 타입으로 변환해서 리턴하는 함수.
    //-> 빈 문자열인 경우 숫자0을 리턴.
    // parseFloat(string): string을 float 타입(실수)로 변환해서 리턴하는 함수.
    //-> 빈 문자열인 경우 NaN을 리턴.
    const number = parseFloat(numberInput.value);
    console.log('number =', number);
    
    if(isNaN(number)){ // NaN이 number이면
        result.innerHTML = '입력값은 숫자가 아닙니다.';
    } else if(number > 0){
        result.innerHTML = '양수';
    } else if (number == 0){
        result.innerHTML = '영';
    } else {
        result.innerHTML = '음수';
    }
}

function checkEven2(){
    const number = numberInput.value;
    result.innerHTML = (number % 2) ? 'odd number(홀수)' : 'even number(짝수)' ;
}