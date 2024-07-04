# 1. 개요
본 레포지트리는 객체지향 프로그래밍을 기법으로 한 데이터 관리 프로그램을 서버<->클라이언트 형태로
나눈 레포지트리임.각 브랜치 별로 master(server), main(client)형태로 구성되어 있으며 콘솔 창에서'만' 구현되었음.
GUI 없이 기초적인 자바 문법 + 객체지향 프로그래밍 기법 + 소켓 프로그래밍 기법을 활용하여 설계함.
<br/>
<br/>
<br/>

## 1.1 개발 목적

- 객체지향 프로그래밍을 기법을 활용한 프로그램 설계
- 데이터 관리 관점에 타겟 선정 : 온라인 서점 프로그램 설계
- 네트워크 프로그래밍, 추상 클래스/인터페이스 개념, 패키지 개념 및 예외처리, 동적 배열 개념, 파일 입출력 기법 적용

## 1.2 설계 구성
</br>

<strong>클라이언트(exProject1)</strong>

- 컴파일 시 로그인(User or Admin), 회원가입, 종료 메뉴
- 회원가입 시 User와 Admin 설정 가능
- 로그인 성공 시 온라인 서점 메뉴로 이동
- 로그인 실패 시 예외 처리
- 종료 버튼을 누르면 종료
- 회원가입 시 가입된 회원의 정보는 서버의 Person.txt에 저장

<strong>서버(twoWayServer)</strong>

- 클라이언트에서 Admin 로그인 후 책 추가/삭제 가능, book.txt에 저장
- 유저가 cart에 item(책)을 추가했을 때 cart.txt에 추가 

### 실행 과정
- 클라이언트가 서버에 요청 request를 보냄
- 서버는 response로 응답
① 컴파일 시 메뉴 선택(1:회원 로그인 2:관리자 로그인 3:회원가입 4:종료)<br/>
   1-1 : 회원 로그인 : 실패 시 리턴 예외 처리<br/>
   1-2 : 관리자 로그인 : 실패 시 리턴 예외 처리<br/>
   1-3 : 회원가입 : 서버의 person.txt에 회원 정보 저장(DB와 비슷한 개념)<br/>
② 회원 로그인 후 메뉴 선택(1:내 정보 확인 2:책 목록 3:장바구니 확인 4:캐시 충전 5:로그아웃)<br/>
  2-1 : 내 정보 확인 : ID, Password, Name, Cash<br/>
  2-2 : 책 목록 : 등록된 책 정보 보여줌, 구매할 책 ID 입력
  2-3 : 장바구니 확인
  2-4 : 캐시 충전 : 원하는 만큼 캐시 충전(person.txt의 해당 유저 cash에 전달)
  2-5 : 로그아웃
③ 관리자 로그인 후 메뉴 선택(1:회원 정보 확인 2:책 관리 3:로그아웃)
 3-1 : 회원 정보 확인 : 전체 회원 리스트 보여줌
 3-2 : 책 관리 메뉴(④)
 3-3 : 로그아웃
④ 책 관리 메뉴(1:책 등록 2:책 재고 수정 3:책 삭제 4:메인 메뉴로)
 4-1 : 등록한 책의 정보 입력
 4-2 : 책 ID 입력받아 재고 수정
 4-3 : 등록되어 있는 책 삭제
 4-4 : ④로 이동

# 2. 설계 내용

## 2-1. 클라이언트(exProject1)

package bookitem
Book.java


① 필드(Fields)
- bookid : 책 ID, 문자열
- bookName : 책 제목, 문자열
- category : 책 카테고리, 문자열
- price : 책 가격, 정수
- date : 출판일, 문자열
- writer : 저자, 문자열
- description : 설명, 문자열
- stock : 책 재고, 정수
② 생성자(Constructor)
- public Book(필드 매개변수 . . . .)
③ 메소드(Methods)
- 각 필드에 대한 getter & setter 구현
- toString()으로 객체의 정보를 문자열로 반환

cart.package
Cart.Java


① 필드(Fields)
- cartItems: 장바구니에 담긴 책과 각 책의 수량을 관리하기 위한 맵 Book 객체를 키로 사용하고, 해당 책의 수량을 값으로 저장
② 생성자(Constructor)
- Cart(): 기본 생성자, cartItems 맵을 초기화. 장바구니 객체를 생성할 때마다 비어있는 새로운 장바구니가 생성
③ 메소드(Methods)
- addBook(Book book, int quantity): 장바구니에 책을 추가하는 메소드. 맵에 책과 그 수량을 저장하며, 이미 존재하는 책이면 기존 수량에 추가.
- removeBook(Book book): 장바구니에서 특정 책을 제거하는 메소드. 맵에서 해당 책을 제거
- getItems(): 장바구니에 담긴 모든 책과 각 책의 수량을 반환하는 메소드. 외부에서 장바구니 내용을 조회할 때 사용
- clearCart(): 장바구니를 비우는 메소드. 모든 책을 제거하여 장바구니를 초기화

exception.package
CartException.java


① 생성자
- CartException(String str): 예외 객체를 생성할 때 호출되는 생성자. 문자열 str을 받아서 Exception 클래스의 생성자에 전달
- super(str) : 부모 클래스인 Exception의 생성자를 호출하여 예외 메시지를 설정
handler.package
MenuHandler.java




① 필드(Fields)
- AdminMethod() : 관리자 기능을 처리하는 객체
- UserMethod() : 사용자 기능을 처리하는 객체
- Login() : 로그인 기능을 처리하는 객체
② 메소드(Methods)
- loginMenu : 로그인 메뉴를 처리하는 메소드. 사용자가 선택한 메뉴에 따라 로그인, 회원 가입, 종료 등의 기능을 수행
- mainMenu : 메인 메뉴를 처리하는 메소드. 사용자가 로그인 후에 메인 메뉴에서 선택한 기능을 수행
- adminMenu : 관리자 메뉴를 처리하는 메소드. 관리자가 로그인 후에 관리자 메뉴에서 선택한 기능을 수행
- cartMenu : 장바구니 메뉴를 처리하는 메소드. 사용자가 장바구니 메뉴에서 선택한 기능을 수행
- manageBooks : 책 관리 메뉴를 처리하는 메소드. 관리자가 책 관리 메뉴에서 선택한 기능을 수행
method.package
AdminMethod.java

① 메소드(Methods)
- addBook : 새 책을 추가하는 메소드. 사용자로부터 입력받은 정보를 서버에 전송하여 책을 추가
- updateBookStock : 책의 재고를 수정하는 메소드. 사용자로부터 책 ID와 새로운 재고 수량을 입력받아 서버에 전송
- deleteBook : 책을 삭제하는 메소드. 사용자로부터 책 ID를 입력받아 서버에 삭제 요청을 전송
- viewUsers : 회원 목록을 조회하는 메소드. 서버에 회원 목록을 요청하고 받은 데이터를 파싱하여 출력
- parseUser : 서버로부터 받은 회원 정보 문자열을 파싱하여 형식에 맞게 반환하는 메소드

AdminMethodsInterface


① 메소드(Methods)
- addBook : 새로운 책을 추가하는 메소드. 책의 ID, 카테고리, 가격, 이름, 출판일, 작가, 설명, 재고 등의 정보를 입력받아 처리
- updateBookStock : 책의 재고를 수정하는 메소드. 특정 책의 ID와 새로운 재고 수량을 입력받아 처리
UserMethod(코드 일부만 넣었습니다)


① 필드(Field)
- Cart : 사용자의 장바구니를 관리하는 Cart 객체
② 메소드(Method)
- requestUserInfo : 로그인된 사용자의 정보를 요청
- viewBooks : 사용자가 책 목록을 조회하고 책을 장바구니에 추가하는 메소드
- viewCart : 사용자가 자신의 장바구니를 조회하고 장바구니에 담긴 책을 구매할 수 있는 
- purchaseCart : 장바구니에 담긴 책을 구매하는 메소드
- chargeCash : 사용자가 자신의 계정에 캐시를 충전하는 메소드
③ 보조 메소드
- updateStock : 서버에 책 재고를 업데이트하는 요청을 보내는 메소드
- parseBook : 서버로부터 받은 책 정보를 파싱하여 Book 객체를 생성하는 메소드
- createBookBox : 책 정보를 보기 좋게 출력하는 메소드.
- addCartItemToCart : 서버로부터 받은 장바구니 항목 정보를 파싱하여 Cart 객체에 추가하는 메소드
- parseCartItem : 서버로부터 받은 장바구니 항목 정보를 파싱하여 문자열로 반환하는 메소드

USerMethodInterface
① 메소드(Methods)
- requestUserInfo : 사용자의 정보를 요청하는 메소드
- viewBooks : 책 목록을 조회하는 메소드입니다.

onlineCart
exProject1(메인코드)


① 클래스 설계 내용
- Book 클래스: 책의 속성 (책 ID, 카테고리, 가격, 이름, 출판일, 작가, 설명, 재고)을 포함
- Cart 클래스: 장바구니 기능을 제공하며, 책과 수량을 관리
- UserMethod 클래스: 사용자가 책을 검색하고, 장바구니에 추가하고, 구매하는 기능을 제공
- AdminMethod 클래스: 관리자가 책을 추가, 수정, 삭제할 수 있는 기능을 제공
- UserMethodInterface 및 AdminMethodInterface 인터페이스: 사용자와 관리자의 메소드를 정의
- MenuHandler 클래스: 로그인 및 메뉴 처리 기능을 제공
- Person 클래스: 사용자 정보를 관리
② 파일 입출력 기법 적용
- BufferedReader와 BufferedWriter를 사용하여 서버와의 통신을 처리
③ 네트워크 프로그래밍 적용
- 서버와 클라이언트 간의 통신을 통해 책 정보와 사용자 정보를 주고받음
④ 추상 클래스/인터페이스
- UserMethodInterface 및 AdminMethodInterface 인터페이스를 사용하여 메소드를 정의하고, 이를 각각의 클래스에서 구현
⑤ 패키지 개념 및 예외처리 적용
- method, bookitem, cart, ui, exception, handler, onlineCart, member 등 다양한 패키지로 클래스를 분류하여 관리
- IOException 등 다양한 예외를 처리하여 안정성을 높임
⑥ 동적 배열
- Vector<Book>를 사용하여 책 목록을 동적으로 관리
- Map<Book, Integer>를 사용하여 장바구니의 책 목록을 동적으로 관리
⑦ 필드(Field)
- person: 사용자 정보를 저장하는 객체
- BufferedReader in: 서버로부터 데이터를 읽기 위한 입력 스트림
- BufferedWriter out: 서버로 데이터를 보내기 위한 출력 스트림
- socket: 서버와의 소켓 통신을 담당
- scanner: 사용자 입력을 받기 위한 스캐너 객체
- menuHandler: 로그인 및 메뉴 처리를 담당하는 객체
⑧ 메소드(Methods)
- 서버에 연결하고, 입력 및 출력 스트림을 초기화
- while(true) 루프를 통해 로그인 메뉴를 지속적으로 표시
- 예외 처리를 통해 네트워크 통신 중 발생할 수 있는 오류를 처리
- 프로그램 종료 시, 스트림과 소켓을 닫아 자원을 해제


## 3. 테스트 검증
- 테스트 시나리오를 통한 설계 동작 과정 검증

① 회원가입 중복 확인


- 아이디 중복 시 중복 메시지 출력

② 회원가입


- 서버의 person.txt에 저장되고 회원가입이 이루어지는 것을 확인

③ 로그인

- 로그인 성공시 Admin or User 타입에 따라 메뉴 출력
- 실패 시 메인 로직으로 리턴







④ 사용자 정보 조회

- Admin은 전체 회원 리스트 조회 가능
- User는 본인의 회원 정보만 조회 가능

⑤ 도서 관리 테스트




- Admin으로 로그인 후 도서 추가, 서버의 book.txt에 저장
- Admin은 도서의 특정 id값을 입력받아 도서 삭제가 가능함.
- User는 도서 목록 조회를 위해 클라이언트에서 request, 서버가 response해 book.txt의 목록을 리턴함 




- Admin으로부터 삭제할 책의 id를 입력받았을 때 서버에 request해 book.txt의 입력받은 id가 포함되어 있는 리스트 제거

⑥ 장바구니 관리 테스트



- 클라이언트에서 user가 특정 책의 id를 입력받았을 때 수량을 선택해 서버에 request하여 서버가 response함과 동시에 id의 유니크가 포함되어 있는 목록의 stock을 수량만큼 감소


- 책을 구매할 캐시가 부족하면 서버에서 response로 캐시 부족 메시지 출력. 







- 책을 구매할 cash가 user의 cash보다 같거나 많을 때 구입 가능, 서버에서 response로 구매 완료 메시지 출력

