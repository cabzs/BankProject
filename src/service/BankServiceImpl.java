package service;

import java.sql.SQLException;
import java.util.List;

import dao.BankDAO;
import dao.BankDAOImpl;
import dto.Account;
import dto.Member;
import dto.Trade;
import dto.UserLevel;
import exception.NotfoundException;

public class BankServiceImpl implements BankService {
	
	BankDAO dao = new BankDAOImpl();

	@Override
	public boolean idCheck(String id) {
		boolean result = dao.idCheck(id);
		return result;
	}

	@Override
	public void insert(Member member) {
		//아이디 받고, 비밀번호 
		dao.insert(member);
		
//		return result;
	}

	@Override
	public Member login(String id, String pwd) throws NotfoundException {
		//입력받은 아이디로 map에 저장된 정보 꺼내서 입력받은 암호 비교하기
		if(id ==null) {
			throw new NotfoundException("아이디를 입력해주세요");
			}
		Member member = dao.login(id, pwd);
		
		return member;
	}
	
	@Override
	public void transfer(String account, String uAccount, int amount) {
		dao.transfer(account, uAccount, amount);
	}

	@Override
	public void withdraw(String account, int amount) {
		dao.withdraw(account, amount);
	}

	
	@Override
	public List<Account> findById(String id) {
		return dao.findById(id);
	}


	@Override
	public boolean newAc(Account account) throws NotfoundException {
		if(account == null) {
			throw new NotfoundException();
		}
		return dao.newAc(account);
	}

	@Override
	public boolean pwdCheck(String ac, String pwd) throws Exception {
		if(ac == null && pwd == null) {
			throw new Exception();
		} else {
			return dao.pwdCheck(ac, pwd);
		}
	}

	@Override
	public List<Member> selectAll() {
		return dao.selectAll();
	}

	@Override
	public List<Trade> selectAllTrade(String userAcount) {
		return dao.selectAllTrade(userAcount);
	}

	
	@Override
	public UserLevel selectInterest(String userId) {
		return dao.selectInterest(userId);
	}

	@Override
	public void deposit(String account, int amount) {
		dao.deposit(account, amount);
		
	}


}
