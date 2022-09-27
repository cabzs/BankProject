package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Account;
import dto.Member;
import dto.Trade;

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
	public void withdraw(String account, int amount);
	
	/**
	 * 아이디로 회원 찾기
	 * */
	public List<Account> findById(String id);
	
	/**
	 * 계좌번호로 계좌정보 구하기
	 * */
	public Account findbyAc(String uAccount);
	
	/**
	 * 계좌 비밀번호 체크
	 * */
	public boolean pwdCheck(String ac, String pwd);
	
	/**
	 * 회원 전체 검색
	 * */
	public List<Member> selectAll();
	
	/**
	 * 계좌 거래 내역 리스트
	 * */
	public List<Trade> tradeList();

}