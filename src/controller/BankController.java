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
		boolean result = service.idCheck(id); 
		return result;
	}
	
	
	/**
	 * 로그인
	 * @throws NotfoundException 
	 * */
	public Member login(String id, String pwd) throws NotfoundException {
//		try {
//			Member member = service.login(id, pwd);
//			//로그인 성공시 subMenu로 이동
//			//SubView.newAccount(member);
//		}catch (Exception e) {
//			e.printStackTrace();
//			//FailView.errorMessage(e.getMessage());
//		}
		return service.login(id, pwd);
	}
	
	/**
	 * 계좌 생성
	 * @throws NotfoundException 
	 * */
	public boolean newAc(Account account) throws Exception {
		if(account == null) {
			throw new NotfoundException("계좌를 찾을 수 없습니다.");
		} else {
			boolean result = service.newAc(account);
			return result;
		}
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
	public boolean pwdCheck(String ac, String pwd) throws Exception {
		return service.pwdCheck(ac, pwd);
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
