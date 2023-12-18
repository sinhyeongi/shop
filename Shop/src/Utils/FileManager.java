package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
  // cart.txt
	// user.txt
	// item.txt
	private FileManager() {
		CheckFile();
	}
	private static FileManager instance;
	public final String PATH = "src/"+this.getClass().getPackageName().toString()+"/";
	private final File cart = new File(PATH+"cart.txt");
	private final File user = new File(PATH+"user.txt");
	private final File item = new File(PATH+"item.txt");
	
	
	public static FileManager getInstance() {
		if(instance == null) 
			instance = new FileManager();
		return instance;
	}
	//파일 체크
	private void CheckFile() {
		try {
			if(cart.exists() == false)
				cart.createNewFile();
			if(user.exists() == false)
				user.createNewFile();
			if(item.exists() == false)
				item.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//유저 데이터 로드
	public String UserLoadData() {
		String s = "";
		try(BufferedReader bf = new BufferedReader(new FileReader(this.user))) {
			int i;
			while((i = bf.read()) != -1) {
				s += (char)i;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	//아이템 데이터 로드
	public String ItemLoadData() {
		String s = "";
		try(BufferedReader bf = new BufferedReader(new FileReader(this.item))) {
			int i;
			while((i = bf.read()) != -1) {
				s += (char)i;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	//카트 데이터 로드
	public String CartLoadData() {
		String s = "";
		try(BufferedReader bf = new BufferedReader(new FileReader(this.cart))) {
			int i;
			while((i = bf.read()) != -1) {
				s += (char)i;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	//파일 세이브
	public void SaveData(String user,String item, String cart) {
		if(user.isBlank()) {
			System.out.println("저장할 데이터가 없습니다!");
			return;
		}
		if(user.equals(UserLoadData()) && item.equals(ItemLoadData()) && cart.equals(CartLoadData())) {
			System.out.println("저장되어있는 데이터와 같습니다.");
			return;
		}
		try(FileWriter fuser = new FileWriter(this.user);
				FileWriter fitem = new FileWriter(this.item);
				FileWriter fcart = new FileWriter(this.cart);) {
			fuser.write(user);
			fitem.write(item);
			fcart.write(cart);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("파일 저장 완료");
	}
}
