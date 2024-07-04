2-2. 서버

Object.package
BookObject.java(코드일부)

① 필드(Field)
- bookId : 책의 고유 ID를 저장, 문자열
- bookName: 책 이름을 저장, 문자열
- category: 책의 카테고리를 저장, 문자열
- price: 책의 가격을 저장, 정수
- date: 책의 출판일을 저장, 문자열
- writer: 책의 작가를 저장, 문자열
- description: 책의 설명을 저장, 문자열
- stock: 책의 재고 수량을 저장, 정수
② 생성자(Constructor)
- BookObject : 모든 필드 변수를 초기화하는 생성자
③ Getter 및 Setter 메소드
- 각 필드 변수에 접근하고 수정할 수 있는 메소드
④ toString 메소드
- 책의 정보를 문자열로 반환하는 메소드. 서버와의 통신에서 책 정보를 문자열로 전송할 때 유용하게 사용

Person.java(코드일부)

① 필드(Field)
- id : 사용자 ID를 저장, 문자열
- password: 사용자 비밀번호를 저장, 문자열
- name: 사용자 이름을 저장, 문자열
- auth: 사용자 권한을 저장. User(사용자), Admin(관리자)
- cash: 사용자의 보유 현금을 저장, 정수
② 생성자
- Person(String id, String password, String name, String auth, int cash):
각 필드 변수를 초기화하는 생성자
사용자의 ID, 비밀번호, 이름, 권한, 현금을 매개변수로 받아 이를 초기화
③ Getter 및 Setter 메소드
- getId() 및 setId(String id): 사용자 ID를 가져오고 설정
- getPassword() 및 setPassword(String password): 사용자 비밀번호를 가져오고 설정
- getName() 및 setName(String name): 사용자 이름을 가져오고 설정
- getAuth() 및 setAuth(String auth): 사용자 권한을 가져오고 설정
- getCash() 및 setCash(int cash): 사용자의 보유 현금을 가져오고 설정합니다.
toString 메소드
- toString(): 객체의 상태를 문자열로 반환합니다.
- 반환되는 문자열 형식은 "Person{id='id', password='password', name='name', auth='auth', cash=cash}“

Service.package
Main.java


① Vector 초기화
- personVector와 bookObjectVector를 초기화하여 사용자와 책 정보를 각각 저장
② 서버 소켓 설정
- ServerSocket listener를 통해 서버 소켓을 생성하고, 클라이언트의 연결을 기다림
- 클라이언트가 연결되면 Socket socket 객체를 통해 연결을 처리
③ 데이터 스트림 설정
- BufferedReader in와 BufferedWriter out를 사용하여 클라이언트와의 데이터 통신을 처리
④ 클라이언트 요청 처리 루프
- 무한 루프로 클라이언트의 요청을 읽고, 각 요청에 대해 적절한 처리를 수행합니다.
⑤ 주요 요청 처리:
- CHECKID: 아이디 중복 확인
- REGISTER: 사용자 회원가입
- LOGIN: 사용자 로그인
- USERINFO: 사용자 정보 조회
- ADDBOOK: 책 등록
- UPDATESTOCK: 책 재고 업데이트
- GETBOOKS: 모든 책 정보 조회
- BUYUPDATESTOCK: 책 구매 후 재고 업데이트
- ADDTOCART: 장바구니에 책 추가
- CARTINFO: 장바구니 정보 조회
- CHARGECASH: 사용자 현금 충전
- PURCHASE: 책 구매 처리
- DELETEBOOK: 책 삭제
- GETUSERS: 모든 사용자 정보 조회
⑥ 파일 입출력 처리
- 사용자와 책 정보를 파일에서 로드하고 저장하는 기능을 포함
- 각 요청 처리 후 변경된 데이터를 파일에 저장하여 영속성을 유지
⑦ 예외 처리 및 자원 해제
- try-catch-finally 블록을 사용하여 IO 예외를 처리하고, 자원(소켓, 데이터 스트림)을 안전하게 해제

Data.package
book.txt, cart.txt, person.txt

① book.txt : 클라이언트에서 관리자가 입력해 request받은 데이터를 서버의 txt 파일에 저장. 


② cart.txt : 클라이언트에서 사용자가 book.txt 내에 있는 책을 cart에 추가했을 때 request 받아 서버의 txt 파일에 저장
③ person.txt : 클라이언트에서 사용자 or 관리자가 회원가입 시 정보 저장, Admin만 person.txt의 리스트를 서버에서 클라이언트로 리턴할 수 있음
