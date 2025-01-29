/**
 * index.html에 포함.
 * 
 * 프로그래밍 언어 종류 1:
 * 1. 컴파일 언어: 소스 코드를 컴파일해서 실행가능한 바이너리 파일을 만들어서 실행하는 언어.
 *  (1) *.java(소스코드) ===compile===> *.class(바이너리) ====실행===> 프로그램 실행
 *  (2) Java, C, C++, C#, Kotlin, Swift, ...
 * 2. 인터프리터(스크립트) 언어: 소스 코드를 한 문장씩 (기계어로)번역해서 실행하는 언어.
 *  (예) JavaScript, Python, SQL, ...
 * 
 * 프로그래밍 언어 종류 2:
 * 1. 정적 타이핑(static typing) 언어:
 *  (1) 변수를 선언할 때 변수 타입이 결정이 되고, 결정된 변수의 타입을 바꿀 수 없는 언어.
 *  (2) Java, C, C++, C#, Kotlin, Swift, TypeScript, ...
 * 2. 동적 타이핑(dynamic typing) 언어: 
 *  (1) 변수를 선언할 때 변수 타입을 결정하지 않고, 값을 할당할 때 그 변수의 타입이 결정되는 언어.
 *  (2) JavaScript, Python, ...
 */

// const 키워드: 값을 변경할 수 없는 변수(상수, constant variable) 선언
const x = 1;
//x = 2; //--> 실행 중에 에러가 발생(const 변수는 값을 재할당할 수 없기 때문에)
console.log('x =', x); //브라우저의 콘솔 창에 로그 출력.

//let 키워드: 값을 변경할 수 있는 변수 선언.
let y = 100;
console.log('y =', y);

y = '안녕!'; //let으로 선언된 변수는 재할당할 수 있음.
console.log('y =', y);