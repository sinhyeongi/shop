package Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InnputManger {
	private static Scanner scanner;
	private InnputManger() {
		scanner = new Scanner(System.in);
	}
	
	private static InnputManger instance;
	
	public static InnputManger getInstance() {
		if(instance == null) instance = new InnputManger();
		return instance;
	}
	
	public String getString(String s) {
		System.out.print(s+"\n>>");
		return scanner.next();
	}
	public int getInt(String s) {
		System.out.print(s+"\n>>");
		int inp;
		try {
			inp = scanner.nextInt();
		}catch(InputMismatchException e) {
			System.err.println("숫자만 입력해주세요");
			scanner.nextLine();
			return -1;
		}
		return inp;
	}
}
