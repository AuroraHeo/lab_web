/**
 * array_function.html 파일에 포함.
 * 
 * JS 배열 객체의 함수(메서드)들.
 * - mutable method: 원본 배열을 바꾸는 메서드
 *  (예) push, pop, sort, ...
 * - immutable method: 원본 배열을 바꾸지 않고, 새로운 배열을 리턴하는 메서드.
 *  (예) concat, slice, toSorted, ...
 */

const arr = [1, 2, 3];
console.log(arr);

//Array.push(arg): 아규먼트 필요. 배열의 끝에 새로운 아이템을 추가해주는 메서드. '원본 배열이 바뀜.'
arr.push(100);
console.log(arr); //-> [1, 2, 3, 100]

//Array.concat(arg): '원본 배열을 바꾸지 않고', 아이템이 추가된 '새로운 배열'을 리턴!
let result = arr.concat(200);
console.log(arr); //-> [1, 2, 3, 100]. 원본 배열을 그대로 유지.
console.log(result); //-> [1, 2, 3, 100, 200]

//Array.pop(): 아규먼트 필요없음. 배열의 마지막 아이템을 삭제. '원본 배열이 바뀜.'
arr.pop();
console.log(arr); //-> [1, 2, 3]. 원본 배열이 바뀜.

//Array.slice(start, end): 아규먼트 2개 필요. 배열에서 인덱스 start부터 end까지의 원소들을 잘라낸 '새로운 배열'을 리턴. '원본 배열이 바뀌지 않음'
//start <= index < end 범위의 아이템들로 이루어진 배열을 리턴.
result = arr.slice(0, 2);
console.log(arr); //-> [1, 2, 3]. 원본 배열이 바뀌지 않음.
console.log(result); //-> [1, 2]

arr.slice(0, -1);// 방향:뒤에서 앞으로... 마지막 원소만 잘라버림.
console.log(arr); //-> [1, 2, 3]. 원본 배열이 바뀌지 않음.
console.log(result); //-> [1, 2]

//       [ 1, 2, 3, 4, 5, 6]
//인덱스 0  1  2  3  4  5  
//      -6 -5 -4 -3 -2 -1


const arr2 = [10, 100, -1, 9, 80, "abc"];
console.log(arr2);
//Array.toSorted(): 아규먼트 없이 사용 가능. 아규먼트 갖는 형태로도 사용 가능. 오름차순으로 정렬된 새로운 배열을 리턴. '원본 배열이 바뀌지 않음'
// - 배열의 아이템들을 '문자열로 변환한 후' 크기를 비교함. 사전식 순서.
result = arr2.toSorted();
console.log(arr2); //-> 원본 배열은 바뀌지 않음.
console.log(result); //-> 정렬된 배열이 리턴됨. but 정렬이 정상적이지 않음....

// Array.toSorted(callback): 배열 아이템의 크기 비교에서 사용될 콜백(함수)을 아규먼트를 전달.
// callback(x, y) => 숫자 // 숫자가 양수이면 x가 더 큰 값, 음수이면 y가 더 큰 값, 0이면 x와 y가 서로 같은 값
result = arr2.toSorted((x, y) => x - y);
console.log(result);



//Array.sort(): 원본 배열의 아이템 순서를 오름차순으로 변경. 원본 배열이 바뀜!
// - 배열의 아이템들을 '문자열로 변환한 후' 크기를 비교함. 사전식 순서.
arr2.sort();
//원본 배열이 바뀌기 때문에 result에 값 저장할 필요 없음.
console.log(arr2); //사전식 순서

//Array.sort(callback): 배열의 아이템 크기 비교에서 사용되는 콜백 (함수)을 아규먼트로 전달.
arr2.sort((x, y) => x - y);
console.log(arr2);

//Array.forEach, Array.filter, Array.map, Array.reduce 써보기
const numbers = [1, 2, 3, 4, 5, 6];
console.log(numbers);
numbers.forEach(x => console.log(x)); //(x) => { console.log(x); }
//for(const x of numbers){
//    console.log(x);
//}

// 배열 numbers의 아이템들 중에서 홀수들로만 이루어진 새로운 배열을 만들고 출력.
//i)
result = numbers.filter(x => x % 2 === 1);
console.log(result);

//ii)
const odds = [];
for(const x of numbers){
    if(x % 2){
        odds.push(x);
        //odds = odds.concat(x);
    }
}
console.log(odds);

// 배열 numbers의 아이템들의 제곱을 아이템으로 갖는 새로운 배열을 만들고 출력.
//i)
result = numbers.map(x => x * x);
console.log(result);

//ii)
let squares = []; // 아이템의 제곱을 저장하기 위한 빈 배열을 선언.
for(const x of numbers){
    squares = squares.concat(x * x);
}
console.log(squares);

// 배열 numbers의 아이템들 중에서 홀수들의 제곱을 아이템으로 갖는 새로운 배열을 만들고 출력.
//i)
result = numbers.filter(x => x % 2).map(x => x * x);
console.log(result);

//ii)
const oddSquares = []; // 홀수의 제곱들을 저장할 배열.
for(const x of numbers){
    if(x % 2){
        oddSquares.push(x * x);
    }
}
console.log(oddSquares);

// 배열 numbers의 모든 아이템들의 합계를 계산하고 출력.
//i)
let sum = 0;
for(const x of numbers){
    sum += x;
}
console.log(sum);

//ii) Array.reduce(callback(ex.(accumulator, currentValue) => accumulator + currentValue), initialValue(ex.0)) 메서드 호출. 
result = numbers.reduce((accumulator, currentValue) => accumulator + currentValue, 0); // (acc(이전값), cur(현재값))=>(함수, acc의 초기값);, cur은 배열의 아이템을 하나씩 꺼냄
//(0, 1) => 0 + 1을 리턴; >> (1, 2) => 1 + 2을 리턴; >> (3,3) => 3+3을 리턴; ....
console.log(result);

// numbers의 모든 원소들의 곱을 계산하고 출력.
//i)
result = numbers.reduce((accumulator, currentValue) => accumulator * currentValue, 1);
console.log(result);

//ii) 
result = 1;
for(const x of numbers){
    result = result * x;
}
console.log(result);

// numbers의 아이템들 중에서 짝수들의 합(2+4+6)을 계산하고 출력.
//i)
const evenSquare = numbers.filter(number => number % 2 === 0).reduce((accumulator, currentValue) => accumulator + currentValue, 0);
console.log(evenSquare);

//ii)
sum = 0;
for(const x of numbers){
    if(x % 2 === 0){
        sum += x;
    }
}
console.log(sum);

// numbers의 아이템의 제곱들의 합을 계산하고 출력.
//i)
const square = numbers.map(x => x * x).reduce((accumulator, currentValue) => accumulator + currentValue, 0);
console.log(square);

// numbers의 아이템들 중에서 짝수의 제곱들의 합을 계산하고 출력.
result = numbers.filter(x => x % 2 === 0).map(x => x * x).reduce((accumulator, currentValue) => accumulator + currentValue, 0);
console.log(result);