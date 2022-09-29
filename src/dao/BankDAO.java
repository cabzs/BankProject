package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Account;
import dto.Member;
import dto.Trade;
import dto.UserLevel;

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
	public void transfer(String account, String id, int amount);
	
	/**
	 * 입금
	 * */
	public void deposit(String account, int amount);
	
	
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
	 * 계좌 거래 내역 추가
	 * */
	public void tradeList(String account, String account2 , int amount, int type, Long balance);
	
	/**
	 * 계좌 거래 내역 검색
	 * */
	public List<Trade> selectAllTrade(String userAcount);
	
	/**
	 * 회원 아이디로 이자율 구하기 (등급 객체)
	 * */
	public UserLevel selectInterest(String userId);
	
	
	/**
	 * 회원 총 보유액 추가
	 * */
	public void updateTotalBal(String userId);

	
	/**
	 * 회원 등급 업데이트
	 * */
	public void updateUserLevel(Member member);
	
	/**
	 * 계좌번호로 회원 정보 구하기
	 * */
	public Member findMemberbyAc (String account);
	
}