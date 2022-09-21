package dao;

import java.sql.SQLException;

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
	public void insert(Member member);
	
	
	/**
	 * 로그인
	 * */
	public Member login(String id, String pwd);
	
	
	/**
	 * 계좌 생성
	 * @throws SQLException 
	 * */
	public boolean newAc(Account account);
	
	
	/**
	 * 입금
	 * */
	public void deposit(String account, String id, int amount);
	
	
	/**
	 * 출금
	 * */
	public Long withdraw(String id, int amount);
	
	/**
	 * 아이디로 회원 찾기
	 * */
	public Member findById(String id);
	
	
	/**
	 * 계좌 비밀번호 체크
	 * */
	public boolean pwdCheck(String ac, String pwd);

}