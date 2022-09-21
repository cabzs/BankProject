package service;

import java.sql.SQLException;

import dao.BankDAO;
import dao.BankDAOImpl;
import dto.Account;
import dto.Member;
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
	public void deposit(String account, String uAccount, int amount) {
		dao.deposit(account, uAccount, amount);
	}

	@Override
	public Long withdraw(String id, int amount) {
		return dao.withdraw(id, amount);
	}

	
	@Override
	public Member findById(String id) {
		Member member = dao.findById(id);
		return member;
	}


	@Override
	public boolean newAc(Account account) throws Exception {
		if(account == null) {
			throw new Exception();
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


}
