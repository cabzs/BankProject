package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import dto.Account;
import dto.Member;
import service.AccountService;
import view.SubView;

public class BankDAOImpl implements BankDAO {

	List<Member> members = new ArrayList<Member>();
	LocalDate now = LocalDate.now();
	Map<String, Member> map = new HashMap<String, Member>();
	private static AccountService as = new AccountService(); //계좌번호 생성
	Member member;
	Account account;
	
	//JDBC 연동을 위한 준비
	Connection con = DataBase.getInstance().getConnection();
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	@Override
	public boolean idCheck(String id) {
		boolean result = false;
		String sql = "select * from bank where id = ? ";
		
		if(sql.equals(id)) { //아이디가 존재한다면 false 리턴
			result = false;
		} else {
			result = true;
		}
		return result;
	}
	
	@Override
	public boolean insert(Member member) {
		String sql = " INSERT INTO bank VALUES(?, ?, ?, ?, ?) ";
		//(id, pwd, name, account, join_date , balance ,level)
		boolean re = false;
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, member.getId());
			pst.setString(2, member.getPwd());
			pst.setString(3, member.getName());
			pst.setString(4, member.getPhone());
			pst.setString(5, now.toString());
			//level은 default값으로 bronze 입력됨
			
			int result = pst.executeUpdate();
			String msg = result > -1 ? "성공적으로 가입되었습니다." : "가입에 실패하셨습니다.";
			
			if(result>0) {
				re=true;
			} else {
				re=false;
			}
			
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

		return re;
	}

	@Override
	public Member login(String id, String pwd) {
		//입력받은 사용자 정보와 DB에 저장된 정보 비교
		String sql = " select * from bank where id = ? ";
		Member member = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();

			if(rs.next()) {
				member = new Member(id, pwd, id, pwd, now, null);
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
		
		
	@Override
	public int deposit(String id, int amount) {
		int nowBalance = account.getBalance();
		int newBalance = nowBalance += amount;
		account.setBalance(newBalance);
		//map.put();
		
		return newBalance;
	}

	@Override
	public int withdraw(String id, int amount) {
		int nowBalance = account.getBalance();
		int newBalance = nowBalance -= amount;
		account.setBalance(newBalance);
		
		return newBalance;
	}

	@Override
	public Member findById(String id) {
		Member member = map.get(id);
		return member;
	}

	@Override
	public boolean newAc(Account account) {
		String sql = " INSERT INTO account VALUES(?, ?, ?, ?, ?) ";
		boolean re = false;

		try {			
			pst = con.prepareStatement(sql);
			pst.setString(1, as.random()); //계좌번호 랜덤 생성
			pst.setString(2, account.getAccPwd());
			pst.setString(3, now.toString()); //계좌 개설일
			pst.setString(4, now.toString()); //이자율
			pst.setInt(5, account.getBalance()); //계좌 잔액
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


}
