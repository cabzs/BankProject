package controller;

import java.util.List;
import java.util.Scanner;

import dao.BankDAO;
import dao.BankDAOImpl;
import dto.Account;
import dto.Member;
import exception.BalanceInstufficientException;
import exception.NotfoundException;
import service.BankService;
import service.BankServiceImpl;
import view.SubView;

public class BankController {
	private static Scanner sc = new Scanner(System.in);
	static BankService service = new BankServiceImpl();
	static BankDAO dao = new BankDAOImpl();
	Member member;
	Account acconut;
	
	/**
	 * 회원가입
	 * */
	public void insert(Member member) {
		//비밀번호 일치할때 회원가입
		service.insert(member);
		
//		return result;
		
	}
	
	/**
	 * 아이디 중복 체크
	 * */
	public boolean idCheck(String id) {
		boolean result = false;
		try {
			result = service.idCheck(id); 
		} catch (Exception e) {
			System.out.println("이미 존재하는 아이디입니다.");
		}
		return result;
	}
	
	
	/**
	 * 로그인
	 * @throws NotfoundException 
	 * */
	public Member login(String id, String pwd) {
		Member m = null;
		try {
			m = service.login(id, pwd);
		} catch (NotfoundException e) {
			System.out.println("아이디와 비밀번호를 입력해주세요");
		}
		return m;

	}
	
	/**
	 * 계좌 생성
	 * @throws NotfoundException 
	 * */
	public boolean newAc(Account account) {
		boolean result = false;
		try {
			result = service.newAc(account);
		} catch (Exception e) {
			System.out.println("계좌번호를 입력해주세요.");
		}
		return result;
	}

	/**
	 * 회원 조회
	 * */
	public List<Member> selectAll() {
		
		return service.selectAll();
	}
	
	/**
	 * 계좌 비밀번호 일치 확인
	 * @throws Exception 
	 * */
	public boolean pwdCheck(String ac, String pwd) {
		boolean result = false;
		try {
			result = service.pwdCheck(ac, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 입금
	 * */
	public void deposit(String account, String uAccount, int amount) throws BalanceInstufficientException {
		if(amount<0) {
			throw new BalanceInstufficientException("입금은 0원 이상부터 가능합니다.");
		} else if(amount > dao.findbyAc(account).getBalance()) {
			throw new BalanceInstufficientException("출금액이 잔액보다 큽니다.");
		} else {
			service.deposit(account, uAccount, amount);
		}
	}
	
	/**
	 * 출금
	 * */
	public void withdraw(String account, int amount) throws BalanceInstufficientException {
		if(amount<0) {
			throw new BalanceInstufficientException("출금은 0원 이상부터 가능합니다.");
		} else if (amount > dao.findbyAc(account).getBalance() ) {
			throw new BalanceInstufficientException("출금액이 잔액보다 큽니다.");
		}
		
		service.withdraw(account, amount);
	}
	
	/**
	 * 아이디로 계좌 조회하기
	 * @throws NotfoundException 
	 * */
	public List<Account> findById(String id) {
		return service.findById(id);
	}
	
	

}
