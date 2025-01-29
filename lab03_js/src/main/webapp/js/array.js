/**
 * array.html파일에 포함.
 * 
 * JS 배열: 여러 개의 원소(아이템)들을 하나의 변수 이름으로 저장하기 위한 데이터 타입.(값들이 타입이 달라도 상관 없음 : 다른 타입의 값들을 저장할 수 있음.)
 *  - (예) const array = [1, 'abc', new Date(), 3.14];
 * 자바 배열: "한가지" 타입의 값 여러 개를 저장하기 위한 타입.
 *  - int[], double[], String[], Object[], ...
 * 
 */

// 아이디가 output인 div 요소를 찾음.
const output = document.querySelector('div#output');

// 배열 선언, 초기화
const numbers = [1, 2, -3, -4, 5, 0]; //(Java 비교) int[] numbers = {1, 2, 3, 4};

// 배열의 아이템들을 output 영역에 출력:
let html = '';
for(let i = 0; i < numbers.length; i++){
    html += `${numbers[i]}, `;
}
output.innerHTML = html + '<br />';


//Java 향상된 for 문장: for (변수 선언 : 배열) { ... }
// JS for-of 문장: 배열의 아이템들을 반복(iteration).
html = '';
for(const value of numbers){ //지역변수라서 어짜피 {}끝나면 사라지기때문에 const라고 해도 됨
    html += `${value}, `;
}
output.innerHTML += html + '<br />';


// JS for-in 문장: 배열의 인덱스를 반복(iteration).(인덱스와 값을 같이 알고싶을때)
html = '';
for(const i in numbers){
    html += `${i} : ${numbers[i]}, `;
}
output.innerHTML += html + '<br />';


// 배열 numbers의 원소들의 합과 평균을 output 영역에 출력.
let sum = 0; // 배열 원소들의 합을 저장할 변수.
for(const value of numbers){
    sum += value;
}
const average = sum / numbers.length; // JS에서 나누기연산자(/)는 무조건 실수까지(소수점 이하 계산을 수행)

output.innerHTML += `합계 = ${sum}, 평균 = ${average} <br />`;


const movies = ['1등', '소방관', '위키드'];
// 아이디가 list인 ul에 movies의 아이템들을 li로 추가.
const list = document.querySelector('ul#list');

html = '';
for(const movie of movies){
    html += `<li>${movie}</li>`;
}
list.innerHTML = html;

// destructuring assignment(구조분해 할당)
const array = [1, 2, 3];
const [x, y, z] = array; // 1번째 아이템은 1번째변수에, 2번째 아이템은 2번째변수에, 3번째 아이템은 3번째변수에 할당(저장)
output.innerHTML += `x = ${x}, y = ${y}, z = ${z} <br />`;

// 배열의 아이템 개수보다 적은 destructuring assignment
const [a, b] = array;
output.innerHTML += `a = ${a}, b = ${b} <br />`;

// 배열의 아이템 개수보다 많은 destructuring assingment
const [c, d, e, f] = array;
output.innerHTML += `c = ${c}, d = ${d}, e = ${e}, f = ${f} <br />`;

// rest 연산자를 사용한 destructuring assignment
const [g, ...h] = array;
output.innerHTML += `g = ${g}, h = ${h} <br />`;
//-> g = 1, h = [2, 3]