package view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import controller.BankController;
import dto.Account;
import dto.Member;
import dto.Trade;
import dto.UserLevel;
import exception.BalanceInstufficientException;
import exception.NotfoundException;

public class MenuView {
	private static Scanner sc = new Scanner(System.in);
	private static BankController controller = new BankController();
	final private static String numeng = "^[a-zA-Z0-9]*$"; //영문+숫자 조합만
	final private static String koreng = "^[가-힣a-zA-Z]*$"; //한글+영문 조합만
	final private static String phonenum = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"; //휴대폰번호 양식
	final private static String kor = "^[가-힣]*$"; //한글만
	
	private static DecimalFormat formatter = new DecimalFormat("###,###");
	
	public void mainMenu() {
		
		LocalDate now = LocalDate.now();
		System.out.println();
		System.out.println("--------------------------------------     JAVA 은행    ---------------------------------------------------");
		System.out.println("                                          "+  now    );
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		
		boolean flag = true;
		while(flag) {
		//회원가입, 로그인
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		System.out.println("  1. 계좌 개설 |   2. 입금  |   3. 출금  |   4. 계좌 이체  |   5. 내 계좌 조회  |   6. 거래 내역 조회  |   7. 회원 관리  ");
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		System.out.println("선택>");	
		
		Scanner sc = new Scanner(System.in);
		try {
			int menuNum = Integer.parseInt(sc.nextLine());

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
				transfer();
				break;
				
			case 5:
				findById();
				break;
				
			case 6:
				tradeList();
				break;
				
			case 7:
				admin();
				break;
				}//switch		
			
			} catch (InputMismatchException e) {
				System.out.println("※ error! 메뉴 입력은 숫자만 가능합니다. ");
				
			}
		}//while
	}//mainMenu
	
	public static void joinLogin () {
		System.out.println(" ★ 비회원이라면 회원가입을 먼저 진행해주세요 ★");
		System.out.println("----------------------------");
		System.out.println("  1. 회원 가입 |   2. 로그인   ");
		System.out.println("----------------------------");
		
		try {
		
		int num = (Integer.parseInt(sc.nextLine()));
		String id,name,phone,pwd,pwd2;
		boolean result = false;
		switch (num) {
		case 1:
			System.out.print("아이디 : ");
			id = sc.next();
			boolean a = Pattern.matches(numeng, id);
			if(a) {
				result = controller.idCheck(id);
				if(result) {
					
					System.out.print("이름 : ");
					name = sc.next();
					boolean a1 = Pattern.matches(koreng, name);
					if(!a1) {
						System.out.println(" ※ 이름에는 숫자가 들어갈 수 없습니다. ");
						System.out.print("이름 : ");
						name = sc.next();
					}
					
					
					System.out.println("ex) 000-0000-0000 ");
					System.out.print("휴대폰 번호 : ");
					phone = sc.next();
					boolean a2 = Pattern.matches(phonenum, phone);
					if(!a2) {
						System.out.println(" ※ 양식에 맞게 입력하세요.");
						System.out.print("휴대폰 번호 : ");
						phone = sc.next();
					}
					
					System.out.print("비밀번호 : ");
					pwd = sc.next();
					boolean a3 = Pattern.matches(numeng, pwd);
					if(!a3) {
						System.out.println(" ※ 비밀번호는 영문 숫자 조합으로 입력하세요.");
						System.out.print("비밀번호 : ");
						pwd = sc.next();
					}
					
					System.out.print("비밀번호 확인 : ");
					pwd2 = sc.next();
					sc.nextLine();
					if(pwd.equals(pwd2)) { 
						
						Member member = new Member(id, pwd, name, phone);
						controller.insert(member);
						break;
						
					} else {
						System.out.println("비밀번호가 일치하지 않습니다.");
						break;
					}
					
				} 
			} else {
				System.out.println(" ※ 아이디는 영문,숫자 조합으로 입력해주세요 ");

			}
			
			break;
			
		case 2:
			System.out.println("▶ 로그인할 아이디와 비밀번호를 입력해주세요.");
			
			System.out.print("아이디 : ");
			String userId = sc.nextLine();
			
			System.out.print("비밀번호 : ");
			String userPwd = sc.nextLine();
			
			if(userId.equals("admin") && userPwd.equals("admin")) {
				System.out.println(" ※ 관리자는 계좌 개설이 불가능합니다.");
				return;
			} else {
				//아이디 비번이 일치하면 로그인
				Member member = controller.login(userId, userPwd);
				if(member==null) {
					System.out.println();
					System.out.println(" ※ 존재하지 않는 회원입니다. \n먼저 회원 가입을 진행해주세요.");
					System.out.println();
					return;
				}
				SubView.newAccount(member);
				break;
			}

		default:
			break;
		}
		
		} catch (InputMismatchException e) {
			e.printStackTrace();
			System.out.println("※ error! 숫자만 입력하세요. ");
		}
	}
	
	//입금 메소드
	public static void transfer() {
		System.out.println("▶ 본인 계좌번호를 입력하세요");
		System.out.print("계좌번호 : ");
		String account = sc.next();
		boolean a = Pattern.matches(koreng, account);
		if(a) {
			System.out.println(" ※ 올바른 양식의 계좌번호를 입력하세요");
			return;
		}
		
		
		System.out.println("▶ 계좌 비밀번호 네자리를 입력하세요 ");
		System.out.print("계좌 비밀번호 : ");
		String pwd = sc.next();
		
		boolean result = controller.pwdCheck(account, pwd);
		
		if(result) { // 내 계좌정보 확인 되면 이동
			
			System.out.println("▶ 입금하실 계좌번호를 입력하세요");
			System.out.print("계좌번호 : ");
			String uAccount = sc.next();
			boolean a1 = Pattern.matches(koreng, uAccount);
			if(a1) {
				System.out.println(" ※ 올바른 양식의 계좌번호를 입력하세요");
				return;
			}
			
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
					try {
						controller.transfer(account, uAccount, amount);
					} catch (BalanceInstufficientException e) {
						e.printStackTrace();
					}
				}
				break;
				
			case 2:
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		} else {
			System.out.println();
			System.out.println(" ※ 비밀번호가 틀립니다. \n 프로그램을 종료합니다.");
			return;
			
		}
	}
	
	//출금 메소드
	public static void witdraw() {
		System.out.println("▶ 출금하실 계좌번호를 입력하세요");
		System.out.print("계좌번호 : ");
		String account = sc.next();
		boolean a1 = Pattern.matches(kor, account);
		
		if(a1) {
			System.out.println(" ※ 올바른 양식의 계좌번호를 입력하세요");
			return;
		}
		
		System.out.println("▶ 계좌 비밀번호 네자리를 입력하세요 ");
		System.out.print("계좌 비밀번호 : ");
		String pwd = sc.next();
		boolean a2 = Pattern.matches(koreng, pwd);
		if(a2) {
			System.out.println(" ※ 계좌 비밀번호는 숫자만 입력할 수 있습니다.");
			return;
		}
		
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
					try {
						controller.withdraw(account, amount);
					} catch (BalanceInstufficientException e) {
						e.printStackTrace();
					}
				}
				break;
				
			case 2:
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		} else {
			System.out.println();
			System.out.println(" ※ 비밀번호가 틀립니다. \n 프로그램을 종료합니다.");
			return;
			
		}
	}
	
	//계좌 조회 메소드
	public static void findById () {
		System.out.println("▶ 로그인할 아이디와 비밀번호를 입력해주세요.");
		
		System.out.print("아이디 : ");
		String userId = sc.next();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.next();
		
		//아이디 비번이 일치하면 로그인
		Member member = controller.login(userId, userPwd);
		UserLevel ul = controller.selectInterest(userId);
		
		if(member != null) {
			
			List<Account> list = controller.findById(userId);
			int bal = 0;
			for(Account acc : list) {
				bal += acc.getBalance();
			}
			
			System.out.println("====================================================");
			System.out.println("              " + userId + "님의 계좌 목록입니다.");
			System.out.println("            총 거래액 : " + formatter.format(bal) +"원 입니다.");
			System.out.println("====================================================");
			System.out.println("      계좌번호       |      잔액       |     계좌 개설일  ");
			
			
			for(Account acc : list) {
				System.out.println("  "+acc.getUserAccount()+"   \t  "+formatter.format(acc.getBalance())+"    \t  "+acc.getStartDate());
			}
			System.out.println();
			System.out.println("========================================================================");
			System.out.println("           " + userId + "님의 회원 등급은 현재 "+ ul.getLevelName() +"입니다.");
			System.out.println("           연간 이자율은 현재 "+ ul.getInterest() +"% 입니다.");
			System.out.println("           "+ LocalDate.now().plusYears(1)+"일 까지의 예상 이자는 총 " +formatter.format( bal * ul.getInterest() / 100 )+" 원 입니다.");
			System.out.println("========================================================================");
			
		}

	}
	
	//회원 관리 메소드
	public static void admin() {
		System.out.println("▶ 관리자 아이디와 비밀번호를 입력해주세요.");
		
		System.out.print("아이디 : ");
		String userId = sc.next();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.next();
		
		if(userId.equals("admin") && userPwd.equals("admin")) {
			controller.login(userId, userPwd);
			System.out.println("관리자로 로그인 되었습니다.");
			List<Member> list = controller.selectAll();
			System.out.println("===========================================================================");
			System.out.println("                              JAVA 은행 회원 목록                  ");
			System.out.println("===========================================================================");
			for(Member m : list) {
				String str = null;
				if(m.getLevelId().equals("1")) {
					str = "Bronze";
				}else if(m.getLevelId().equals("2")) {
					str = "Silver";
				}else if(m.getLevelId().equals("3")) {
					str = "Gold";
				}else if(m.getLevelId().equals("4")) {
					str = "Platinum";
				}
				System.out.println("    아이디   |    회원명   |    연락처       |     회원 등급    |      가입일  ");
				System.out.println("===========================================================================");
				System.out.println("    "+m.getUserId()+"       "+m.getUserName()+"      "+m.getPhone()+"         " +str + "          "+m.getDate());
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("         계좌        |         잔액        |        계좌 개설일        ");
				System.out.println("---------------------------------------------------------------------------");
				for(Account a : m.getAcList()) {
					System.out.println("  ㄴ "+a.getUserAccount()+"         "+formatter.format(a.getBalance())+" 원              "+a.getStartDate());
				}
				System.out.println("===========================================================================");
			}
		} else {
			System.out.println("※ 해당 메뉴는 관리자만 접근 가능합니다.");
			return;
		}
	}
	
	//계좌 거래 목록 조회
	public static void tradeList() {
		System.out.println("▶ 거래내역을 조회할 계좌번호를 입력하세요");
		System.out.print("계좌번호 : ");
		String account = sc.next();
		boolean a1 = Pattern.matches(kor, account);
		
		if(a1) {
			System.out.println(" ※ 올바른 양식의 계좌번호를 입력하세요");
			return;
		}
		
		System.out.println("▶ 계좌 비밀번호 네자리를 입력하세요 ");
		System.out.print("계좌 비밀번호 : ");
		String pwd = sc.next();
		boolean a2 = Pattern.matches(koreng, pwd);
		if(a2) {
			System.out.println(" ※ 계좌 비밀번호는 숫자만 입력할 수 있습니다.");
			return;
		}
		
		boolean result = controller.pwdCheck(account, pwd);
		if(result) {
			List<Trade> list = controller.selectAllTrade(account);
			System.out.println("=================================================================================================");
			System.out.println("                                   "+ account +"  계좌 거래 내역 ");
			System.out.println("-------------------------------------------------------------------------------------------------");
			System.out.println("         거래계좌        |      거래유형     |        거래액       |       잔액       |       거래일        ");
			System.out.println("-------------------------------------------------------------------------------------------------");
			for(Trade t : list) {
				String str = null;
				if(t.getTypeId()==1) {
					str = "입금";
				} else {
					str = "출금";
				}
				System.out.println("    " + t.getOtherAccount() + "            " + str + "               " + formatter.format(t.getAmount()) +
						"             " + formatter.format(t.getTradeBal()) + "         " + t.getTradeDate());
				
			}
			System.out.println();
		}
	} 
	
	//출금 메소드
		public static void deposit() {
			System.out.println("▶ 입금하실 계좌번호를 입력하세요");
			System.out.print("계좌번호 : ");
			String account = sc.next();
			boolean a1 = Pattern.matches(kor, account);
			
			if(a1) {
				System.out.println(" ※ 올바른 양식의 계좌번호를 입력하세요");
				return;
			}
			
			System.out.println("▶ 계좌 비밀번호 네자리를 입력하세요 ");
			System.out.print("계좌 비밀번호 : ");
			String pwd = sc.next();
			boolean a2 = Pattern.matches(koreng, pwd);
			if(a2) {
				System.out.println(" ※ 계좌 비밀번호는 숫자만 입력할 수 있습니다.");
				return;
			}
			
			boolean result = controller.pwdCheck(account, pwd);
			
			if(result) { // 내 계좌정보 확인 되면 이동

				System.out.println("▶ 입금하실 금액을 입력하세요");
				System.out.print("입금 금액 : ");
				int amount = sc.nextInt();

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
						try {
							controller.deposit(account, amount);
						} catch (BalanceInstufficientException e) {
							e.printStackTrace();
						}
					}
					break;
					
				case 2:
					System.out.println("프로그램을 종료합니다.");
					break;
				}
			} else {
				System.out.println();
				System.out.println(" ※ 비밀번호가 틀립니다. \n 프로그램을 종료합니다.");
				return;
				
			}
		}
	
}//class

