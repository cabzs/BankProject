package service;

import java.sql.SQLException;

import dto.Account;
import dto.Member;
import exception.NotfoundException;

public interface BankService {
	
	/**
	 * 아이디 중복 체크
	 * */
	public boolean idCheck(String id);
	
	
	/**
	 * 회원가입
	 * */
	public void insert(Member member);

	
	/**
	 * 로그인
	 * @return 
	 * @throws NotfoundException 
	 * */
	public Member login(String id, String pwd) throws NotfoundException;
	
	
	/**
	 * 계좌 생성
	 * @throws Exception 
	 * */
	public boolean newAc (Account account) throws Exception;
	
	/**
	 * 계좌 비밀번호 확인
	 * @throws Exception 
	 * */
	public boolean pwdCheck(String ac, String pwd) throws Exception;
	
	
	/**
	 * 입금
	 * @throws NotfoundException 
	 * */
	public void deposit(String account, String uAccount, int amount);

	
	/**
	 * 출금
	 * @throws NotfoundException 
	 * */
	public void withdraw(String account, int amount);
	
	
	/**
	 * 아이디로 회원 찾기
	 * */
	public Member findById(String id);
	

}
