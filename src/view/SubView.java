package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.BankController;
import dto.Account;
import dto.Member;
import exception.NotfoundException;

public class SubView {
	
	public static Member member;
	public static Account account;
	private static Scanner sc = new Scanner(System.in);
	public static BankController controller = new BankController();
	static int amount ;
	
	public static void newAccount(Member member) {
		System.out.println("*******************************");
		System.out.println( member.getUserId() +"님 현재 로그인 중....☎  ");
		System.out.println("*******************************");

		
		System.out.println("▶ 최초 입금액과 계좌에서 사용할 비밀번호 네자리를 입력하세요");
                              
		System.out.print("최초 입금액 : ");
		Long bal = sc.nextLong();
		
		System.out.print("비밀번호 : ");
		String accountpwd = sc.next();
		
		Account account = new Account(null,accountpwd,member.getUserId(),null,bal);
		//계좌 생성 dao에 입력해서 데이터 삽입
		
		try {
			controller.newAc(account);
		} catch (NotfoundException e) {
			e.printStackTrace();
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		
	}

}
