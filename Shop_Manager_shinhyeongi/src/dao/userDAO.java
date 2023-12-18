package dao;

import java.util.ArrayList;

import vo.User;

public class UserDAO {
	private static ArrayList<User> list;
	private UserDAO(){
		if(list == null)
			list = new ArrayList<User>();
	}
	
	private static UserDAO instance;
	public static UserDAO getInstance() {
		if(instance == null) instance = new UserDAO();
		return instance;
	}
	//id인덱스 값 반환
	public int getIdIdx(String id){
		for(int i = 0 ; i < list.size(); i++)
			if(id.equals(list.get(i).getId()))
				return i;
		return -1;
	}
	//아이디 값에 해당하는 이름 반환
	public String getName(String id) {
		for(int i = 0 ; i < list.size(); i++)
			if(id.equals(list.get(i).getId()))
				return list.get(i).getName();
		return "";
	}
	//로그인 성공시 아이디 반환
	public String getUserIdx(String id,String pw){
		for(int i = 0 ; i < list.size(); i++)
			if(id.equals(list.get(i).getId()) && pw.equals(list.get(i).getPw()))
				return list.get(i).getId();
		return "";
	}
	//유저 수 리턴
	public int getCountUser() {
		return list.size();
	}
	//유저 생성
	public void NewUseradd(String id,String pw,String name) {
		list.add(new User(id, pw, name));
	}
	//유저 삭제
	public void DeleteUser(int idx) {
		if(list.size() == 1) {
			list.clear();
			return;
		}	
		list.remove(idx);
		System.out.println("[탈퇴]탈퇴 완료");
	}
	//유저 출력
	public void PrintUser() {
		System.out.printf("%6s\t%6s\t%6s\n","이름","아이디","비밀번호");
		for(int i = 0 ; i < list.size(); i++) {
			System.out.printf("%6s\t%6s\t%6s\n",list.get(i).getName(),list.get(i).getId(),list.get(i).getPw());
		}
	}
	//유저 이름 업데이트
	public void UpdateUserName(int idx,String name) {
		list.set(idx, new User(list.get(idx).getId(), list.get(idx).getPw(), name));
		System.out.println("[회원 정보 수정 완료");
		System.out.println(list.get(idx).getId() +" "+list.get(idx).getPw()+" "+list.get(idx).getName());
	}
	//전체 아이디 리턴
	public String GetAllUserId() {
		String s = "";
		for(int i = 0 ; i < list.size();i++) {
			s += list.get(i).getId()+"/";
		}
		if(s.length() > 1)
			s = s.substring(0,s.length() -1);
		
		return s;
	}
	//세이브 데이터 리턴
	public String GetUserSaveData() {
		String s ="";
		for(int i = 0 ; i < list.size(); i++) {
			s += list.get(i).toString()+"\n";
		}
		if(!s.isBlank()) {
			s = s.substring(0,s.length()-1);
		}
		return s;
	}
	//데이터 로드
	public void SetUserData(String s) {
		if(s.isBlank()) {
			return;
		}
		if(list.size() != 0) {
			list.clear();
		}
		String data[] = s.split("\n");
		for(int i = 0 ; i < data.length; i++) {
			String u[] = data[i].split("/");
			list.add(new User(u[0],u[1],u[2]));
		}
	}
}
