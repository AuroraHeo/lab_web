/**
 * username 중복 체크, email 중복체크
 * user/signup.jsp파일에 포함시킬 js임.
 */

document.addEventListener('DOMContentLoaded', () => {
    // username 중복 체크 결과를 저장할 변수.
    // true이면 회원가입이 가능한 username. false이면 [작성완료] 버튼은 비활성화.
    let isUsernameChecked = false;
        
    // password를 입력했는 지 여부를 저장할 변수. false이면 [작성완료] 버튼은 비활성화.
    let isPasswordChecked = false;
        
    // email 중복 체크 결과를 저장할 변수.
    // true이면 회원가입이 가능한 email. false이면 [작성완료] 버튼은 비활성화.
    let isEmailChecked = false;
    
    const inputUsername = document.querySelector('input#username');
    
    const checkUsernameResult = document.querySelector('div#checkUsernameResult');
    
    const inputPassword = document.querySelector('input#password');
    
    const checkPasswordResult = document.querySelector('div#checkPasswordResult');
    
    const inputEmail = document.querySelector('input#email');
    
    const checkEmailResult = document.querySelector('div#checkEmailResult');
    
    const btnSignUp = document.querySelector('button#btnSignUp');
    
    //inputUsername 요소에 'change' 이벤트 리스너를 설정.(주의)클릭이 아님!!!!)
    inputUsername.addEventListener('change', checkUsername);
    
    //inputPassword 요소에 'change' 이벤트 리스너를 설정.(주의)클릭이 아님!!!!)
    inputPassword.addEventListener('change', checkPassword);
    
    //inputEmail 요소에 'change' 이벤트 리스너를 설정.(주의)클릭이 아님!!!!)
    inputEmail.addEventListener('change', checkEmail);
    
    /*--------------- 함수 선언 ------------------*/
    function changeButtonState(){ //isUsernameChecked && isPasswordChecked && isEmailChecked 셋 중에 불리언 값이 바뀔때마다 이 함수를 호출해야함.
        if(isUsernameChecked && isPasswordChecked && isEmailChecked){
            //버튼 활성화 - class 속성들 중에서 'disabled'를 제거.
            btnSignUp.classList.remove('disabled');
        } else {
            //버튼 비활성화 - class 속성에 'disabled'를 추가.
            btnSignUp.classList.add('disabled');
        }
    }
    
    function checkUsername(){
//        alert('change'); //확인차
        const username = inputUsername.value;
        if(username === ''){
            checkUsernameResult.innerHTML = '사용자 아이디는 필수 입력 항목입니다.'; //아이디 입력된 상태에서 지우고 비밀번호로 넘어가보기...
            checkUsernameResult.classList.add('text-danger'); //글자를 빨간색으로해주는 text-danger속성 추가.
            checkUsernameResult.classList.remove('text-success');//text-success속성 제거
            
            isUsernameChecked = false;
            changeButtonState();
            return;
        }
        
        //아이디 중복 체크 REST API(요청 API)
        const uri = `./checkusername?username=${username}`;//.은 현재주소 // 만든 컨트롤러의 @getmapping주소와 같아야함.
        
        axios
        .get(uri)
        .then(handleCheckUsernameResp)
        .catch((error) => console.log(error));
    }
    
    function handleCheckUsernameResp({data}){ //response를 {data}로 바꿈(구조할당분해를 이용한 파라미터 선언) const {data}  = response; // 구조분해할당
        console.log({data});
        if(data === 'Y'){
            checkUsernameResult.innerHTML = '멋진 아이디입니다.';
            checkUsernameResult.classList.add('text-success');
            checkUsernameResult.classList.remove('text-danger');
            isUsernameChecked = true;
        } else {
            checkUsernameResult.innerHTML = '사용할 수 없는 아이디입니다.';
            checkUsernameResult.classList.add('text-danger');
            checkUsernameResult.classList.remove('text-success');
            isUsernameChecked = false;
        }
        changeButtonState();
    }
    
    function checkPassword(){
        if(inputPassword.value === ''){
            checkPasswordResult.innerHTML = '비밀번호는 필수입력 항목입니다.';
            checkPasswordResult.classList.add('text-danger');
            checkPasswordResult.classList.remove('text-success');
            isPasswordChecked = false;
        } else {
            checkPasswordResult.innerHTML = '사용할 수 있는 비밀번호입니다.';
            checkPasswordResult.classList.add('text-success');
            checkPasswordResult.classList.remove('text-danger');
            isPasswordChecked = true;
        }
        changeButtonState();
    }
    
    function checkEmail(){
        // inputEmail에 입력된 값이 있는지 체크.
        if(inputEmail.value === ''){
            checkEmailResult.innerHTML = '이메일은 필수입력 항목입니다.';
            checkEmailResult.classList.add('text-danger');
            checkEmailResult.classList.remove('text-success');// 초록색이 이미 있을지없을지 모르겠으나 초록색을 뺄 수 있음.
            isEmailChecked = false;
            changeButtonState(); //boolean값이 바뀌면 반드시 이 메서드를 호출해야함....
            
            return;
        }
        
        // 이메일 중복 체크 REST API(요청 URI)
        const uri = `./checkemail?email=${encodeURIComponent(inputEmail.value)}`; //encodeURIComponent() : 이메일이 특수문자가 들어가기때문에 UTF-8로 인코딩함.
    
        //Ajax 요청을 보냄.
        axios
        .get(uri)
        .then(handleCheckEmailResp) 
        .catch((error) => { 
            console.log(error);
        });
    }
    
    function handleCheckEmailResp({data}){ //response객체에서 data만 가져오겠다.
        console.log(data);
        if(data === 'Y'){ //회원가입 가능한 이메일
            checkEmailResult.innerHTML = '사용 가능한 이메일입니다.';
            checkEmailResult.classList.add('text-success');
            checkEmailResult.classList.remove('text-danger');
            isEmailChecked = true; // 버튼 활성화
        } else { // 중복된 이메일
            checkEmailResult.innerHTML = '이미 사용중인 이메일입니다.';
            checkEmailResult.classList.add('text-danger');
            checkEmailResult.classList.remove('text-success');
            isEmailChecked = false; // 버튼 비활성화
        }
        changeButtonState();
    }
});

//const obj = { name : '오쌤', age : 16, phone : '1234-5678'};
//const { name, age } = obj; //phone속성은 필요없고 name과 age속성만 필요할때(뽑아내고 싶을때), 구조분해할당문법 사용.
//console.log(name, email);

//밑에 두줄은 const { name, age } = obj;와 같은 코드
//const name = obj.name;
//const age = obj.age;

//const obj = { name : '홍길동', age : 16, email : youjin@2586, phone: '11'};
//const {name, email, ...rest} = obj;
//name은 '홍길동' email은 'youjin@2586', rest는 { age: 16, phone: '11' }임. 이렇게 하나의 객체에서 필요한 부분만 추출하여 사용하고 나머지는 rest에 담는 구조