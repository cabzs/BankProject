package service;

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
	public boolean insert(Member member) {
		//아이디 받고, 비밀번호 
		boolean result = dao.insert(member);
		
		return result;
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
	public int deposit(String id, int amount) {
		return dao.deposit(id, amount);
	}

	@Override
	public int withdraw(String id, int amount) {
		return dao.withdraw(id, amount);
	}

	
	@Override
	public Member findById(String id) {
		Member member = dao.findById(id);
		return member;
	}


	@Override
	public boolean newAc(Account account) {
		return dao.newAc(account);
	}


}
