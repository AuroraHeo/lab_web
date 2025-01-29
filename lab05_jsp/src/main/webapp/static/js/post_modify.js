/**
 * 포스트 업데이트, 삭제 기능.
 */

document.addEventListener('DOMContentLoaded', () => { // modify.jsp에 <script src="${ postModifyJS }"></script>가 마지막에 있어서 굳이 안해줘도 되지만, 해주면 더 안전하게 element가 찾기 때문에 안전한 코드
    // form#modifyForm 요소를 찾기.
    const modifyForm = document.querySelector('form#modifyForm'); // querySelector는 아규먼트를 CSS형태 문법으로 넣으면 됨 // form을 찾기
    
    // input#id 요소(글 번호/아이디 입력 필드)를 찾기.
    const inputId = document.querySelector('input#id');
    
    // input#title 요소(글 제목 입력 필드)를 찾기.
    const inputTitle = document.querySelector('input#title');
    
    // textarea#content 요소(글 내용 입력 필드)를 찾기.
    const textareaContent = document.querySelector('textarea#content');
    
    // 삭제 버튼을 찾기.
    const btnDelete = document.querySelector('button#btnDelete');
    
    // 업데이트 버튼을 찾기.
    const btnUpdate = document.querySelector('button#btnUpdate');
    
    // 삭제 버튼에 클릭 이벤트 리스너를 설정하기.(링크 클릭하는 건데 버튼처럼 만듦)
    btnDelete.addEventListener('click', (e) =>{ //자바는 e를 꼭 넣어줘야하지만 js는 필요하면 e 넣고 아니면 넣지 않아도 됨.
        const result = confirm('정말 삭제할까요?');
        //console.log(`confirm result = ${result}`); //->확인 버튼 클릭: true 리턴, 취소 버튼 클릭 : false 리턴.
        if(result){ // 사용자가 [확인] 버튼을 클릭했을 때
            // 새로운 요청 주소로 GET방식 요청0을 보냄.
            location.href= `delete?id=${inputId.value}`; //inputId.value: 글번호 값
            // 요청 주소를 http://localhost:8080/jsp2/post/modify?id=*을 http://localhost:8080/jsp2/post/delete>id=*로 변경.
        }
    });
    
    //업데이트 버튼에 클릭 이벤트 리스너를 설정.(링크 클릭하는 건데 버튼처럼 만듦)
    btnUpdate.addEventListener('click', (e) => {
        // 제목에 입력된 값, 내용에 입력된 값을 읽음.
        const title = inputTitle.value;
        const content = textareaContent.value;
        
        // 제목과 내용이 비어있는지를 체크 -> 비어있으면 alert()함수를 호출 & 함수 종료.
        if(title === '' || content === ''){
            alert('제목과 내용은 반드시 입력해야 합니다.');
            return;
        }
        
        // confirm() 함수를 호출해서 수정된 내용을 저장할지 확인.
        const result = confirm('변경된 내용을 저장하시겠습니까?');
        
        // 사용자가 [확인] 버튼을 선택한 후 양식 데이터(form data)를 제출(submit).
        if(result){
            modifyForm.method = 'post'; // 요청 '방식'을 POST로 설정.
            modifyForm.action = 'update'; // 요청 '주소'를 localhost:8080/jsp2/post/update 로 설정.
            modifyForm.submit(); // form.submit(): 버튼처럼 submit 가능하게 구현 // 양식 데이터(form data) 제출(서버로 요청을 보냄). //개발자도구에 네트워크 > 페이로드 > 양식 데이터 확인해보기
        }
    });
});