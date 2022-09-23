package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.BankController;
import dto.Account;
import dto.Member;
import exception.NotfoundException;

public class MenuView {
	private static Scanner sc = new Scanner(System.in);
	private static BankController controller = new BankController();
	
	public void mainMenu() throws Exception{
		
		LocalDate now = LocalDate.now();
		System.out.println();
		System.out.println("---------------------------     JAVA 은행    ---------------------------------");
		System.out.println("                              "+  now    );
		System.out.println("-----------------------------------------------------------------------------");
		
		boolean flag = true;
		while(flag) {
		//회원가입, 로그인
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("  1. 계좌 개설 |   2. 입금/계좌이체  |   3. 출금  |   4. 내 계좌 조회  |   5. 회원 관리  ");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("선택>");	
		
		Scanner sc = new Scanner(System.in);
		int menuNum = sc.nextInt();
		
		

		switch (menuNum) {
		case 1:
			joinLogin(); 
			break;
	
		case 2:
			try {
				deposit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				witdraw();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 4:
			findById();
			break;
		
		case 5:
			admin();
			break;
			}//switch		
		}//while
	}//mainMenu
	
	public static void joinLogin () throws Exception {
		System.out.println("비회원이라면 회원가입을 먼저 진행해주세요");
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

				System.out.print("이름 : ");
				name = sc.next();
				
				System.out.print("휴대폰 번호 : ");
				phone = sc.next();
				
				System.out.print("비밀번호 : ");
				pwd = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				pwd2 = sc.next();
				
				if(pwd.equals(pwd2)) { 
					
					Member member = new Member(id, pwd, name, phone);
					controller.insert(member);
					break;
					
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
			
			if(userId.equals("admin") && userPwd.equals("admin")) {
				System.out.println(" ※ 관리자는 계좌 개설이 불가능합니다.");
				return;
			} else {
				//아이디 비번이 일치하면 로그인
				Member member = controller.login(userId, userPwd);
				SubView.newAccount(member);
				break;
			}

		default:
			break;
		}
	}
	
	//입금 메소드
	public static void deposit() throws Exception {
		System.out.println("▶ 본인 계좌번호를 입력하세요");
		System.out.print("계좌번호 : ");
		String account = sc.next();
		
		System.out.println("▶ 계좌 비밀번호 네자리를 입력하세요 ");
		System.out.print("계좌 비밀번호 : ");
		String pwd = sc.next();
		
		boolean result = controller.pwdCheck(account, pwd);
		
		if(result) { // 내 계좌정보 확인 되면 이동
			
			System.out.println("▶ 입금하실 계좌번호를 입력하세요");
			System.out.print("계좌번호 : ");
			String uAccount = sc.next();
			
			System.out.println("▶ 입금하실 금액을 입력하세요");
			System.out.print("입금 금액 : ");
			int amount = sc.nextInt();
			
			System.out.println("입력하신 계좌번호: " + uAccount);
			System.out.println("입금하실 금액: " + amount);
			
			System.out.println("▶ 해당 계좌로의 입금을 진행하시겠습니까?");
			System.out.println("---------------------");
			System.out.println("  1. 예 |   2. 아니요  ");
			System.out.println("---------------------");
			int num = sc.nextInt();
			
			switch (num) {
			case 1:
				//계좌번호 비밀번호 입력, 
				System.out.println("▶ 계좌 비밀번호 네자리를 한번 더 입력하세요 ");
				System.out.print("계좌 비밀번호 : ");
				String pwd1 = sc.next();
				if(pwd.equals(pwd1)) {
					controller.deposit(account, uAccount, amount);
				}
				break;
				
			case 2:
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
	
	//출금 메소드
	public static void witdraw() throws Exception {
		System.out.println("▶ 출금하실 계좌번호를 입력하세요");
		System.out.print("계좌번호 : ");
		String account = sc.next();
		
		System.out.println("▶ 계좌 비밀번호 네자리를 입력하세요 ");
		System.out.print("계좌 비밀번호 : ");
		String pwd = sc.next();
		
		boolean result = controller.pwdCheck(account, pwd);
		
		if(result) { // 내 계좌정보 확인 되면 이동

			System.out.println("▶ 출금하실 금액을 입력하세요");
			System.out.print("출금 금액 : ");
			int amount = sc.nextInt();

			System.out.println("출금하실 금액: " + amount);
			
			System.out.println("▶ 해당 계좌로의 출금을 진행하시겠습니까?");
			System.out.println("---------------------");
			System.out.println("  1. 예 |   2. 아니요  ");
			System.out.println("---------------------");
			int num = sc.nextInt();
			
			switch (num) {
			case 1:
				//계좌번호 비밀번호 입력, 
				System.out.println("▶ 계좌 비밀번호 네자리를 한번 더 입력하세요 ");
				System.out.print("계좌 비밀번호 : ");
				String pwd1 = sc.next();
				if(pwd.equals(pwd1)) {
					controller.withdraw(account, amount);
				}
				
			case 2:
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
	
	//계좌 조회 메소드
	public static void findById () throws NotfoundException {
		System.out.println("▶ 로그인할 아이디와 비밀번호를 입력해주세요.");
		
		System.out.print("아이디 : ");
		String userId = sc.next();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.next();
		
		//아이디 비번이 일치하면 로그인
		Member member = controller.login(userId, userPwd);
		if(member != null) {
			
			List<Account> list = controller.findById(userId);
			
			System.out.println("====================================================");
			System.out.println("              " + userId + "님의 계좌 목록입니다.");
			System.out.println("====================================================");
			System.out.println("      계좌번호       |      잔액       |     계좌 개설일  ");
			for(Account acc : list) {
				System.out.println("  "+acc.getUserAccount()+"   \t  "+acc.getBalance()+"    \t  "+acc.getStartDate());
			}

		}

	}
	
	//회원 관리 메소드
	public static void admin() throws NotfoundException {
		System.out.println("▶ 관리자 아이디와 비밀번호를 입력해주세요.");
		
		System.out.print("아이디 : ");
		String userId = sc.next();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.next();
		
		if(userId.equals("admin") && userPwd.equals("admin")) {
			controller.login(userId, userPwd);
			System.out.println("관리자로 로그인 되었습니다.");
			List<Member> list = controller.selectAll();
			System.out.println("===================================================================");
			System.out.println("                           JAVA 은행 회원 목록                  ");
			System.out.println("===================================================================");
			for(Member m : list) {
				System.out.println("    아이디   |    회원명   |    연락처    |   회원 등급  |      가입일  ");
				System.out.println("===================================================================");
				System.out.println("    "+m.getUserId()+"       "+m.getUserName()+"      "+m.getPhone()+"     "+m.getLevelId() +"        "+m.getDate());
				System.out.println("-------------------------------------------------------------------");
				System.out.println("         계좌        |         잔액        |        계좌 개설일        ");
				System.out.println("-------------------------------------------------------------------");
				for(Account a : m.getAcList()) {
					System.out.println("  ㄴ "+a.getUserAccount()+"         "+a.getBalance()+" 원              "+a.getStartDate());
				}
				System.out.println("===================================================================");
			}
		} else {
			System.out.println("※ 해당 메뉴는 관리자만 접근 가능합니다.");
			return;
		}
	}
}//class

