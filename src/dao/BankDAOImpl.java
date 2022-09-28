package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.Account;
import dto.Member;
import dto.Trade;
import dto.UserLevel;
import service.AccountService;

public class BankDAOImpl implements BankDAO {

	LocalDate now = LocalDate.now();
	private static AccountService as = new AccountService(); //계좌번호 생성
	Member member;
	Account account;
	
	//JDBC 연동을 위한 준비
	Connection con = DataBase.getInstance().getConnection();
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	@Override
	public boolean idCheck(String id) {
		
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select * from member where user_id = ? ";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				System.out.println("※ 이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요");
				System.out.println();
				result = false;
			} else {
				System.out.println("※ 사용 가능한 아이디입니다.");
				result = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		return result;
	}
	
	@Override
	public void insert(Member member) {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		String sql = " INSERT INTO member VALUES(?, ?, ?, ?, sysdate, 1) ";
		//(id, pwd, name, phone, date)
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, member.getUserId());
			pst.setString(2, member.getUserPwd());
			pst.setString(3, member.getUserName());
			pst.setString(4, member.getPhone());

			//level은 default값으로 bronze 입력됨
			
			int result = pst.executeUpdate();
			String msg = result > -1 ? "성공적으로 가입되었습니다." : "가입에 실패하셨습니다.";
			
//			if(result>0) {
//				re=true;
//			} else {
//				re=false;
//			}
			
			System.out.println(msg);
			
		} catch (Exception e) {
			System.out.println("데이터베이스를 불러오는데 실패했습니다.");
			
			e.printStackTrace();
		} finally {
			try {
				if(pst != null) pst.close();
				if(con != null) con.close();
			} catch(Exception e) {}
		}

//		return re;
	}

	@Override
	public Member login(String id, String pwd) {
		//입력받은 사용자 정보와 DB에 저장된 정보 비교
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = " select * from member where user_id = ? and user_pwd = ? ";
		Member member = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pwd);
			rs = pst.executeQuery();

			if(rs.next()) {
				member = new Member(rs.getString(1),rs.getString(2));
//				 String dbId = rs.getString(1);
//				 String dbPwd = rs.getString(2);
				System.out.println();
				System.out.println("◈ 로그인 되었습니다. ◈");
//				 if(dbId.equals(id) || dbPwd.equals(pwd)) {
////					 SubView.member = map.get(id);
//				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		return member;
		}
	
	
	/**
	 * 계좌번호로 계좌 객체 구하기
	 * */	
	public Account findbyAc(String uAccount) {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = " select * from account where user_account = ? ";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, uAccount);
			rs = pst.executeQuery();

			if(rs.next()) {
				account = new Account(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getLong(5));
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		return account;
	}
	
	
		
	@Override
	public void transfer(String account, String uAccount, int amount) {
		//만약 해당 계좌번호 id가 로그인중인 id와 일치한다면 잔액 출력
		//계좌주가 다르다면 입금 영수증만 출력
		//account(=account2) - 입금하는 사람 uAccount(=account1) - 입금받는사람
//		System.out.println(account);
//		System.out.println(uAccount);
		
		Account account1 = findbyAc(uAccount); //입금 받는 사람
		Account account2 = findbyAc(account); //입금 하는 사람
		
		Long nowBalance = account1.getBalance(); //입금받는사람 현재 잔고
//		System.out.println(nowBalance);
		Long newBalance = nowBalance + amount; //입금받는 사람 입금 후 잔고
//		System.out.println(newBalance);
		
		//account1.setBalance(newBalance); //입금 받는 사람의 잔액에 +
		
		
		Long accountBal = account2.getBalance(); //입금하는 사람 현재 잔고
		Long accountNowBal = accountBal - amount; //입금하는 사람 입금 후 잔고
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			
			con = DataBase.getInstance().getConnection();
			con.setAutoCommit(false);
			//입금받는 사람의 계좌 잔고 업데이트
			pst = con.prepareStatement(" update account set balance= ? where user_account= ? ");
			pst.setLong(1, newBalance);
			pst.setString(2, uAccount);
			int re = pst.executeUpdate();
			con.commit();
			tradeList(uAccount,account, amount, 1 , newBalance); //입금 받는 사람 계좌거래 목록에 추가
			tradeList(account,uAccount, amount, 2 , accountNowBal); //입금 하는 사람 계좌거래 목록에 추가
			updateTotalBal(account1.getUserId());
			updateTotalBal(account2.getUserId());
//			depositor(account2, accountNowBal, amount); //입금자의 db 업데이트
			
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) 
				try{
					con.rollback();
					}catch(SQLException sqle){}
			
			} 
		
		try {
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(account2.getUserId().equals(account1.getUserId())) {
			System.out.println("▶ 현재 계좌의 잔액은 " +account1.getBalance()+"원 입니다.");
		} else {
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒입금 명세표▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("   출금 계좌 : " + account);
			System.out.println("   입금 계좌 : " + uAccount);
			System.out.println("   입금액 : " + amount);
			System.out.println("==================================");
			depositor(account2, accountNowBal, amount); //입금자의 db 업데이트
			return;
			
		}
		try {
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally { 
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}

	}
	
	//입금하는 사람 계좌 잔액 관리
	public void depositor (Account account2, Long accountNowBal , int amount) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		try {
			
			con = DataBase.getInstance().getConnection();
			con.setAutoCommit(false);
			//입금받는 사람의 계좌 잔고 업데이트
			pst = con.prepareStatement(" update account set balance= ? where user_account= ? ");
			pst.setLong(1, accountNowBal);
			pst.setString(2, account2.getUserAccount());
			int re = pst.executeUpdate();
			con.commit();
			System.out.println("   내 계좌 잔액 : " + accountNowBal);
			System.out.println();

			
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) 
				try{
					con.rollback();
					}catch(SQLException sqle){}
			
			try {
				con.setAutoCommit(true);
			} catch (SQLException eq) {
				e.printStackTrace();
			}
			
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
	}

	@Override
	public void withdraw(String account, int amount) {
		//만약 해당 계좌번호 id가 로그인중인 id와 일치한다면 잔액 출력
		//계좌주가 다르다면 입금 영수증만 출력
		//account(=account2) - 입금하는 사람 uAccount(=account1) - 입금받는사람

		Account account2 = findbyAc(account); //출금하는 사람
		
		Long nowBalance = account2.getBalance(); //출금하는 사람 현재 잔고
		Long newBalance = nowBalance - amount; //출금하는 사람 출금 후 잔고
		
		//커넥션이 null이면.. 새로 만들고 출금
		//아니면 받은걸로 쓰고 커밋
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			
			con = DataBase.getInstance().getConnection();
			con.setAutoCommit(false);
			//입금받는 사람의 계좌 잔고 업데이트
			pst = con.prepareStatement(" update account set balance= ? where user_account= ? ");
			pst.setLong(1, newBalance);
			pst.setString(2, account2.getUserAccount());
			int re = pst.executeUpdate();
			tradeList(account, account, amount, 2, newBalance); //출금 내역 추가
			updateTotalBal(account2.getUserId());
			con.commit();
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒출금 명세표▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("   출금 계좌 : " + account);
			System.out.println("   출금액 : " + amount);
			System.out.println("==================================");
			System.out.println("   내 계좌 잔액 : " + newBalance);
			System.out.println();

			
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) 
				try{
					con.rollback();
					}catch(SQLException sqle){}
			
			try {
				con.setAutoCommit(true);
			} catch (SQLException eq) {
				e.printStackTrace();
			}
			
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
	}
	
	@Override
	public void deposit(String account, int amount) {
		Account account1 = findbyAc(account); //입금하는 사람
		
		Long nowBalance = account1.getBalance(); //입금하는 사람 현재 잔고
		Long newBalance = nowBalance + amount; //입금하는 사람 입금 받은후 잔고
		
		//커넥션이 null이면.. 새로 만들고 출금
		//아니면 받은걸로 쓰고 커밋
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			
			con = DataBase.getInstance().getConnection();
			con.setAutoCommit(false);
			//입금받는 사람의 계좌 잔고 업데이트
			pst = con.prepareStatement(" update account set balance= ? where user_account= ? ");
			pst.setLong(1, newBalance);
			pst.setString(2, account1.getUserAccount());
			int re = pst.executeUpdate();
			tradeList(account, account, amount, 1, newBalance); //입금 내역 추가
			con.commit();
			updateTotalBal(account1.getUserId());
			
			//등급 업데이트 호출 ^^
			//계좌 정보를 가져와서 얼마있는지 본다...
			//어느 범위에 있는지 본다.....
			
			
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒입금 명세표▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("   입금 계좌 : " + account);
			System.out.println("   입금액 : " + amount);
			System.out.println("==================================");
			System.out.println("   내 계좌 잔액 : " + newBalance);
			System.out.println();

			
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) 
				try{
					con.rollback();
					}catch(SQLException sqle){}
			
			try {
				con.setAutoCommit(true);
			} catch (SQLException eq) {
				e.printStackTrace();
			}
			
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
	}
	
	
	
	
	//아이디로 계좌 찾기
	@Override
	public List<Account> findById(String id) {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<Account> list = new ArrayList<Account>();
		
		String sql = " select user_account , balance , start_date from account where user_id = ?";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();

			while(rs.next()) {
				Account ac = new Account(rs.getString(1), rs.getLong(2), rs.getDate(3));
				list.add(ac);
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		
		return list;
	}

	@Override
	public boolean newAc(Account account) {
//		System.out.println(account);
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		String sql = " INSERT INTO account VALUES(?, ?, ?, sysdate, ?) ";
		boolean re = false;

		try {
			pst = con.prepareStatement(sql); //--여기서 에러 발생
			
			pst.setString(1, as.random()); //계좌번호 랜덤 생성
			pst.setString(2, account.getAccountPwd());
			pst.setString(3, account.getUserId());
			pst.setLong(4, account.getBalance()); //계좌 잔액
			//level은 default값으로 bronze 입력됨
			
			int result = pst.executeUpdate();
			String msg = result > -1 ? "계좌가 개설되었습니다." : "계좌 개설에 실패하셨습니다.";
			System.out.println(msg);
			
			if(result>0) {
				re=true;
			} else {
				re=false;
			}
			
		} catch (Exception e) {
			System.out.println("데이터베이스를 불러오는데 실패했습니다.");
			e.printStackTrace();
			
		} finally {
			try {
				if(pst != null) pst.close();
				if(con != null) con.close();
			} catch(Exception e) {}
		}

		return re;
	}

	@Override
	public boolean pwdCheck(String ac, String pwd) {
		// 입력 받은 비밀번호와 db 비밀번호 맞는지 확인
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = " select * from account where user_account = ? and account_pwd = ? ";
		boolean result = false;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, ac);
			pst.setString(2, pwd);
			rs = pst.executeQuery();

			if(rs.next()) {
				account = new Account(rs.getString(1),rs.getString(2));
				System.out.println();
				System.out.println("◈ 계좌 인증 완료. ◈");
				System.out.println();
				result = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		
		
		return result;
	}

	@Override
	public List<Member> selectAll() {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Member> list = new ArrayList<Member>();
		
		String sql = " select  user_id, user_name, phone, join_date, level_id, total_Bal from member where user_id not in('admin') order by join_date desc";

		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Member member = new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(), rs.getString(5), rs.getLong(6));
				List<Account> accounts = this.findById(member.getUserId());
				member.setAcList(accounts);
				list.add(member);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		
		
		return list;
	}
	
	//거래 내역 기록하는 메소드
	@Override
	public void tradeList(String account, String account2, int amount, int type, Long balance) {
		Connection con = null;
		PreparedStatement pst = null;
		
		String sql = "insert into trade (trade_id, user_account, amount ,trade_date ,type_id, trade_bal , other_account ) values( trade_seq.nextval, ? ,? ,sysdate , ? , ?, ? )";
		
		try {
			con = DataBase.getInstance().getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(sql);
			pst.setString(1, account);
			pst.setLong(2, amount);
			pst.setInt(3, type);
			pst.setLong(4, balance);
			pst.setString(5, account2);
			
			int result = pst.executeUpdate();
			con.commit();
			String msg = result > -1 ? "성공하였습니다." : "실패하셨습니다.";

		} catch (Exception e) {
			System.out.println("데이터베이스를 불러오는데 실패했습니다.");
			
			e.printStackTrace();
		} finally {
			try {
				if(pst != null) pst.close();
				if(con != null) con.close();
			} catch(Exception e) {}
		}

	}

	@Override
	public List<Trade> selectAllTrade(String userAcount) {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Trade> list = new ArrayList<Trade>();
		
		String sql = " select trade_id, user_account, amount, trade_date, type_id, trade_bal, other_account from trade where user_account = ? order by trade_date desc";

		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userAcount);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Trade trade = new Trade(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getDate(4).toLocalDate(), rs.getInt(5), rs.getLong(6), rs.getString(7));
				list.add(trade);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		
		return list;
	}

	
	@Override
	public UserLevel selectInterest(String userId) {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		UserLevel userlevel = null;
		
		String sql ="select * from userlevel u left join member m on m.level_id = u.level_id where m.user_id = ? ";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userId);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				userlevel = new UserLevel(rs.getInt(1),rs.getString(2),rs.getDouble(3));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					//열어주는 역순으로 닫아준다
					if(rs != null) rs.close();
					if(pst != null) pst.close();
					if(con != null) con.close();
				} catch(Exception e) {}
			}
		
		return userlevel;
	}

	/**
	 * 회원 등급 변경
	 * 계좌 잔고 3천만원 이상 - 2등급
	 * 계좌 잔고 8천만원 이상 - 3등급
	 * 계좌 잔고 1억원 이상 - 4등급
	 * */
	@Override
	public void updateTotalBal(String userId) {
		Connection con = DataBase.getInstance().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "update member set total_bal = ( select sum(balance) from account where user_id = ? ) where user_id = ? ";
		
		try {
			pst = con.prepareStatement(sql);
			con.setAutoCommit(false);
			pst.setString(1, userId);
			pst.setString(2, userId);
			pst.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//열어주는 역순으로 닫아준다
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(con != null) con.close();
			} catch(Exception e) {}
		}
	}

	/**
	 * 회원 등급 변경
	 * 계좌 잔고 100만원 이상 - 2등급
	 * 계좌 잔고 300만원 이상 - 3등급
	 * 계좌 잔고 500만원 이상 - 4등급
	 * */
	@Override
	public void updateUserLevel(Member member) {
		
	}

}
