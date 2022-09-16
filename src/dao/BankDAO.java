package dao;

import dto.Account;
import dto.Member;

public interface BankDAO {
	
	/**
	 * 아이디 중복체크
	 * */
	public boolean idCheck(String id);

	
	/**
	 * 회원가입
	 * */
	public boolean insert(Member member);
	
	
	/**
	 * 로그인
	 * */
	public Member login(String id, String pwd);
	
	
	/**
	 * 계좌 생성
	 * */
	public boolean newAc(Account account);
	
	
	/**
	 * 입금
	 * */
	public int deposit(String id, int amount);
	
	
	/**
	 * 출금
	 * */
	public int withdraw(String id, int amount);
	
	/**
	 * 아이디로 회원 찾기
	 * */
	public Member findById(String id);
	
}
