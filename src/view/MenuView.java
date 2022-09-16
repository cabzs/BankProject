package view;

import java.time.LocalDate;
import java.util.Scanner;

import controller.BankController;
import dto.Member;

public class MenuView {
	private static Scanner sc = new Scanner(System.in);
	private static BankController controller = new BankController();
	
	public void mainMenu(){
		
		LocalDate now = LocalDate.now();
		System.out.println("--------------------------     JAVA 은행    ------------------------------");
		System.out.println("                              "+  now    );
		System.out.println("--------------------------------------------------------------------------");
		
		boolean flag = true;
		while(flag) {
		//회원가입, 로그인
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("  1. 계좌 개설 |   2. 입금  |   3. 출금  |   4. 내 계좌 조회  |   5. 회원 관리  ");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("선택>");	
		
		Scanner sc = new Scanner(System.in);
		int menuNum = sc.nextInt();
		
		

		switch (menuNum) {
		case 1:
			joinLogin();
	
		case 2:
			
			}//switch		
		}//while
	}//mainMenu
	
	public static void joinLogin () {
		System.out.println("----------------------------");
		System.out.println("  1. 회원 가입 |   2. 로그인   ");
		System.out.println("----------------------------");
		int num = sc.nextInt();
		String id,name,phone,pwd,pwd2;

		switch (num) {
		case 1:
			System.out.print("아이디 : ");
			id = sc.next();
			boolean result = controller.idCheck(id);
			if(result) {
				//indexOf는 객체가 없다면 -1 있으면 그 위치를 반환
				//중복이 없다면 -1을 반환한다
				//idList.add(id); //아이디 저장
				System.out.print("이름 : ");
				name = sc.next();
				
				System.out.print("휴대폰 번호 : ");
				phone = sc.next();
				
				System.out.print("비밀번호 : ");
				pwd = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				pwd2 = sc.next();
				
				if(pwd.equals(pwd2)) { //비밀번호 일치하는지 확인
//					System.out.println("최초 입금액을 입력해주세요");
//					System.out.print("입금액 : ");
//					int balance = sc.nextInt();
					
					Member member = new Member(id, pwd, name, phone);
					boolean result1 = controller.insert(member);
					
					if(result1) {
						System.out.println();
						System.out.println("회원가입 성공 :) ");
					break;
					}
					
				} else {
					System.out.println("비밀번호가 일치하지 않습니다.");
					break;
				}
			}
			break;
			
		case 2:
			System.out.println("▶ 로그인할 아이디와 비밀번호를 입력해주세요.");
			
			System.out.print("아이디 : ");
			String userId = sc.next();
			
			System.out.print("비밀번호 : ");
			String userPwd = sc.next();
			
			//아이디 비번이 일치하면 로그인
			controller.login(userId, userPwd);
			break;

		default:
			break;
		}
	}
	
	
	
	
}//class

