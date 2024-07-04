package ui;

public class Menu {

    public static void loginMenu() {
        System.out.println("***********************************************");
        System.out.println(" 1. 회원 로그인 \t3. 회원 가입");
        System.out.println(" 2. 관리자 로그인 \t4. 종료"); //exit
        System.out.println("***********************************************");
    }

    public static void mainMenu() {
        System.out.println("***********************************************");
        System.out.println(" 1. 내 정보 확인 \t3. 장바구니 확인");
        System.out.println(" 2. 책 목록 \t4. 캐시 충전");
        System.out.println(" 5. 로그아웃"); //return loginMenu()
        System.out.println("***********************************************");
    }

    public static void adminMenu() {
        System.out.println("***********************************************");
        System.out.println(" 1. 회원 정보 확인");
        System.out.println(" 2. 책 관리");
        System.out.println(" 3. 로그아웃"); //return loginMenu()
        System.out.println("***********************************************");
    }

    public static void cartMenu() {
        System.out.println("***********************************************");
        System.out.println(" 1. 내 장바구니 상세 정보");
        System.out.println(" 2. 장바구니 물품 갯수 수정");
        System.out.println(" 3. 장바구니 물품 삭제");
        System.out.println(" 4. 메인메뉴로"); //return mainMenu()
        System.out.println("***********************************************");
    }

    public static void bookManagementMenu() {
        System.out.println("***********************************************");
        System.out.println(" 1. 책 등록");
        System.out.println(" 2. 책 재고 수정");
        System.out.println(" 3. 책 삭제"); // 책 삭제 메뉴 추가
        System.out.println(" 4. 메인 메뉴로");
        System.out.println("***********************************************");
    }


}
