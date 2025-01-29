/**
 * destructure.html 파일에 포함.
 */

function println1(student){
    console.log(`번호: ${student.no}, 이름: ${student.name}, 학년: ${student.grade}, 반: ${student.classNo}`);
}

function println2(student) {
    const no = student.no;
    const name = student.name;
    const grade = student.grade;
    const classNo = student.classNo;
    
    console.log(`번호: ${no}, 이름: ${name}, 학년: ${grade}, 반: ${classNo}`);
}

function println3({no, name, grade, classNo}){  //{no: no, name: name, grade: grade, classNo: classNo}
    console.log(`번호: ${no}, 이름: ${name}, 학년: ${grade}, 반: ${classNo}`);
}

const stu1 = {
    no: 100,
    name: '오쌤',
    grade: 1,
    classNo: 2,
};

println1(stu1);
println2(stu1);
println3(stu1);

const stu2 = {
    no: 200,
    name: '홍길동',
};

println1(stu2);
println2(stu2);
println3(stu2);

function printStudent({name, ...rest}){
    console.log(`name = ${name}`);
    console.log(rest);
}

printStudent(stu1);
printStudent(stu2);
printStudent({name: '아이티', no: 123, email:'it@itwill.com'}); //메서드 호출할때 필요한 객체를 바로 같이 생성해버려도 됨

//지역변수를 사용해서 객체 생성 & 프로퍼티 초기화:
const x = 1;
const y = 2;
const point1 = {x: x, y: y}; //{프로퍼티이름:값, ...}
console.log(point1);

// 객체의 프로퍼티 이름이 지역변수 이름과 동일할 경우, 간단히 쓸 수 있음.
//const point2 = {x: x, y: y};
const point2 = {x, y}; // {x, y}는 배열 X, 객체임!!! 구조분해 할당과도 혼동하지 말 것!
console.log(point2);