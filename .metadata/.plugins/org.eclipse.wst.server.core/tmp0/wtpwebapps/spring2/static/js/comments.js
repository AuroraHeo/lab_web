/**
 * 댓글 보기/감추기, 댓글 CRUD 요청/응답 처리.
 * post/details.jsp파일에 포함될 js임.
 */

document.addEventListener('DOMContentLoaded', () => {
    //btnToggleComment 요소를 찾음.
    const btnToggleComment = document.querySelector('button#btnToggleComment');
    
    // div#collapseComments 요소를 부트스트랩의 Collapse객체로 생성.
    const bsCollapse = new bootstrap.Collapse('div#collapseComments', { toggle: false });
    
    //btnToggleComment 버튼에 클릭 이벤트 리스너를 설정.
    btnToggleComment.addEventListener('click', () => {
        bsCollapse.toggle(); //토글메서드 호출하면 접었다 폈다할 수 있음. //부트스트랩의 Collapse객체를 생성해야 toggle()메서드 사용 가능.
        
        if(btnToggleComment.innerHTML === '댓글 보기'){
            btnToggleComment.innerHTML = '댓글 감추기';
            //댓글 목록 가져오기 요청을 보냄.('댓글 보기' 버튼을 클릭했을때만!!)
            getAllComments();
        } else {
            btnToggleComment.innerHTML = '댓글 보기';
        }
    });
    
    // button#btnRegisterComment 요소를 찾음.
    const btnRegisterComment = document.querySelector('button#btnRegisterComment');
    
    // btnRegisterComment 버튼에 클릭 이벤트 리스너를 설정.
    btnRegisterComment.addEventListener('click', registerComment); //registerComment : (콜백)함수
    
    // 부트스트랩 모달 객체를 생성.
    const commentModal = new bootstrap.Modal('div#commentModal', { backdrop: true }); //{backdrop: true}: 뒷배경을 '검고 희미하게' 만들어주는 기능({}니까 객체) + 창이 아닌 배경을 클릭하면 창이 사라지게도 해줌.
    
    // 모달의 [저장] 버튼을 찾고, 클릭 이벤트 리스너를 설정.
    const btnUpdateCmnt = document.querySelector('button#btnUpdateCmnt');
    btnUpdateCmnt.addEventListener('click', updateComment);
    
    /* --------------------(콜백) 함수 선언---------------------- */
    
    // btnRegisterComment 버튼의 클릭 이벤트 리스너(콜백) : 댓글 등록 
    function registerComment(){
//        alert('댓글 등록!'); //버튼 동작 확인차 넣음.

        //input#id 요소의 값을 읽음 -> 댓글을 등록할 포스트 아이디 // details.jsp에서 <input id="id" class="form-control" type="text"~
        const postId = document.querySelector('input#id').value; // 변수 이름이 CommentCreateDto의 필드인 postId 이름과 같아야함.
        
        //textarea#ctext 요소 값을 읽음 -> 댓글 내용
        const username = document.querySelector('input#username').value; //details.jsp에서 <input class="d-none" id="username" value="guest" readonly/>
        
        //input#username 요소의 값을 읽음 -> 댓글 작성자 아이디
        const ctext = document.querySelector('textarea#ctext').value; //details.jsp에서 <textarea class="form-control" rows="3" id="ctext" placeholder="댓글 입력"></textarea>
                
        //댓글 내용이 비어있는지 체크.
        if(ctext === ''){
            alert('댓글 내용을 입력하세요.');
            return;
        }
        
        //Ajax 요청으로 보낼 데이터 객체.
        const data = { postId, username, ctext } //배열이 아니라 객체({})임!!!!!!밑에 코드에서 property이름을 생략한 간단한 형태.
//      const data = { postId: postId, username: username, ctext: ctext } //property이름이 CommentCreateDto의 필드인 postId 이름과 같아야함.  속성값은 콜백함수 안에서 선언한 지역변수
//      console.log(data); //f12개발자도구에서 콘솔 확인차

        //서버로 POST 방식의 Ajax 요청을 보내고 응답을 처리.
        axios.post('../api/comment', data) //브라우저는 자기 위치를 알기때문에, 현재 주소랑 가려는 주소를 비교하면서 작성하기 // ../api/comment요청주소로 (data를 넣어서) post방식의 요청을 보냄
            .then((response) => { //성공응답이 왔을 때 실행됨.(비동기)
                console.log(response);
                if(response.data === 1){
                    alert('1개 댓글 등록 성공');
                    document.querySelector('textarea#ctext').value = ''; // 적었던 댓글 내용을 자동으로 지워버리기
                    //댓글 목록을 다시 불러옴.(등록 성공하면 등록한 댓글이 바로 보이게)
                    getAllComments();
                }
            })
            .catch((error) => { //에러응답이 왔을 때 실행됨.(비동기)
                console.log(error); 
            }); //then() : ()에 성공 응답이 오면 처리할 콜백(이벤트 리스너) 등록, catch() : ()에 에러 응답이 왔을 때 처리할 콜백(이벤트 리스너) 등록.

    }
    
    //포스트에 달려있는 댓글 목록 가져오기
    function getAllComments(){
        // 댓글 목록을 요청하기 위한 포스트 아이디(글 번호)
        const postId = document.querySelector('input#id').value;
        
        // 댓글 목록을 요청하기 위한 REST API(요청 URI)
        const uri = `../api/comment/all/${postId}`;
        
        // Ajax 요청보내기
        axios
        .get(uri) 
        .then((response) => {
            console.log(response); //-> response.data 속성에 서버가 보낸 댓글 목록이 있음.
            // divComments 영역에 댓글 목록을 출력(details.jsp에 id가 divComments인 부분)
            makeCommentElements(response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    }
    
    //댓글 목록(댓글 객체들의 배열)을 아규먼트로 전달받아서 div에 출력할 html을 작성.
    function makeCommentElements(data){
        const divComments = document.querySelector('div#divComments');
        
        //div에 출력할 html 코드를 저장할 문자열 변수
        let html = '<ul class="list-group list-group-flush">'; //div에 출력할 html 코드를 저장할 문자열 변수
        for(const comment of data){ //배열에서 index(숫자)를 꺼낼 때는 in, 배열에서 원소(객체)를 꺼낼 때는 of
            //timestamp를 날짜/시간 포맷 문자열로 변환
            const modifiedTime = new Date(comment.modifiedTime).toLocaleString(); //1736389063906 -> 2025. 1. 9. 오전 11:17:43로 변환
            
            html += `
            <li class="list-group-item d-flex justify-content-between align-items-start">
               <div>
                   <div class="text-secondary" style="font-size: 0.825rem;"> 
                      <span>${comment.username}</span>
                      <span>${modifiedTime}</span>
                   </div>
                   <div>
                       ${comment.ctext}
                   </div>
               </div>
               `;
               
               //로그인 사용자와 댓글 작성자가 같은 경우에만 삭제/수정 버튼을 추가
               if(signedInUser === comment.username){
                html +=
               `<div>
                  <button class="btnDeleteComment btn btn-outline-danger btn-sm" 
                        data-id="${comment.id}">삭제</button>
                  <button class="btnUpdateComment btn btn-outline-primary btn-sm" 
                        data-id="${comment.id}">수정</button>
               </div>`;
               }
            html += '</li>';
        }
        html += '</ul>';
        
        // 작성된 html을 div에 삽입.
        divComments.innerHTML = html;
        
        // html 코드(버튼을 만든)가 div에 삽입된 후에 삭제/수정 버튼들을 찾을 수 있음.(코드 위치 중요!)
        //-> 이벤트 리스너를 설정할 수 있음. 
        
        // '모든' 댓글 삭제 버튼들을 찾아서 클릭 이벤트 리스너를 설정.
        const btnDeletes = document.querySelectorAll('button.btnDeleteComment'); //btnDeletes : 배열(버튼들이 원소로 들어간)
        for(const btn of btnDeletes){
            btn.addEventListener('click', deleteComment);
        }
        
        // '모든' 댓글 수정 버튼들을 찾아서 클릭 이벤트 리스너를 설정.(댓글 옆 수정 버튼을 누르면 창이 뜨게끔)
        const btnModifies = document.querySelectorAll('button.btnUpdateComment');
        for(const btn of btnModifies){
            btn.addEventListener('click', showCommentModal);
        }
    }
    
    // 댓글 삭제 버튼의 클릭 이벤트 리스너(콜백) 
    function deleteComment(event){
        console.log(event.target);
        //-> 모든 이벤트 리스너(콜백)의 event 객체를 아규먼트로 전달받음.
        //-> event 객체는 target 속성(이벤트가 발생한 HTML 요소)을 가지고 있음.
        
        //댓글 삭제 여부 확인창
        const result = confirm('댓글을 정말 삭제할까요?');
        if(!result){ // yes가 아니면(사용자가 [취소]를 클릭했을 때) 
            return;
        }
        
        // HTML 요소의 속성(attribute)의 값을 찾음:
        const commentId = event.target.getAttribute('data-id'); //속성 이름을 아규먼트로 넘기면 그 속성의 값을 리턴해줌.
    
        // Ajax 댓글 삭제 요청 REST API(요청 URI)
        const uri = `../api/comment/${commentId}`;
        
        // Ajax 요청을 보냄.
        axios
        .delete(uri)
        .then((response) => {
            //console.log(response);
            alert('댓글이 삭제됐습니다.');
            getAllComments(); //댓글 목록 갱신
        })
        .catch((error) => {
            console.log(error);
        });
    }
    
    //댓글 수정 버튼의 클릭 이벤트 리스너(콜백)
    function showCommentModal(event){
        //이벤트가 발생한 타켓(HTML 요소)에서 data-id 속성 값을 찾음.
        const commentId = event.target.getAttribute('data-id');
    
        //TODO: 댓글 아이디로 댓글 1개 검색하기 Ajax 요청
        //-> 성공 콜백에서 모달(commentModal)의 input과 textarea를 채움.
        //-> 모달 보여주기 //bootstrap.modal객체의 show()메서드 호출
        const uri = `../api/comment/${commentId}`;
        
        axios
        .get(uri)
        .then((response) => {
            console.log(response);
            
            //모달의 input에 댓글 아이디를 value 속성으로 저장.
            document.querySelector('input#modalCommentId').value = response.data.id; 
            
            //모달의 textarea에 댓글 내용을 value 속성으로 저장.
            document.querySelector('textarea#modalCommentText').value = response.data.ctext;
            
            commentModal.show(); //bootstrap.Modal 객체의 show()메서드 호출 - 모달 보여주기
        })
        .catch((error) => {
            console.log(error);
        });
    }
    
    //모달의 [저장] 버튼의 이벤트 클릭 리스너(콜백)
    //-> 댓글 업데이트 Ajax 요청을 보내고, 성공/실패 콜백 작성.
    function updateComment(event){
        //업데이트할 댓글 아이디(번호)
        const commentId = document.querySelector('input#modalCommentId').value;
        
        //업데이트할 댓글 내용
        const ctext = document.querySelector('textarea#modalCommentText').value;
        if(ctext === ''){
            alert('업데이트할 댓글 내용을 입력하세요.');
            return;
        } 
        
        //댓글 업데이트 REST API(요청 URI)
        const uri = `../api/comment/${commentId}`;
        
        //Ajax 요청을 보냄.
        axios
        .put(uri, {ctext: ctext}) // {property이름 : 지역변수}로 해도 되고 {지역변수}로 해도되고
        .then((response) => {
            console.log(response); //로그 확인차
            //댓글 업데이트 모달을 닫음.
            commentModal.hide();
            //댓글 내용 갱신
            getAllComments();
        })
        .catch((error) => {
            console.log(error);
        });
    }
    
});