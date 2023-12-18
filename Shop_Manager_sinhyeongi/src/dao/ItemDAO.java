package dao;

import java.util.ArrayList;

import vo.Cart;
import vo.Item;

public class ItemDAO {
	private static ArrayList<Item> list;
	private static ArrayList<String> categorylist;
	private static ArrayList<Cart> cartlist;

	private ItemDAO() {
		list = new ArrayList<Item>();
		categorylist = new ArrayList<String>();
		cartlist = new ArrayList<Cart>();
	}

	private static ItemDAO instance;

	public static ItemDAO getInstance() {
		if (instance == null)
			instance = new ItemDAO();
		return instance;
	}

	// =================아이템===================

	// 아이템 사이즈 리턴
	public int GetItemSize() {
		return list.size();
	}

	// 아이템 중 카테고리에 해당하는 아이템 삭제
	private void DeleteItemCategory(String category) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCategory().equals(category)) {
				DeleteCart(list.get(i).getName());
				if (list.size() == 1) {
					list.clear();
					break;
				}
				list.remove(i);
				i--;
			}
		}
	}

	// 아이템 이름에 해당 인덱스 리턴
	public int GetItemIdx(String name) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}

	// 아이템 출력
	public void PrintItem() {
		System.out.printf("no %6s\t%4s\t%5s\n", "아이템 이름", "카테고리", "가격");
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("%d. %6s\t%4s\t%5s원\n", (i + 1), list.get(i).getName(), list.get(i).getCategory(),
					list.get(i).getPrice());
		}
	}

	// 새로운 아이템 추가
	public void NewItem(String name, String category, int price) {
		list.add(new Item(name, price, category));
		System.out.println("아이템 : " + name + " 카테고리 : " + category + " 가격 : " + price + "원 추가 완료");
	}

	// 아이템 삭제
	public void DeleteItem(int idx) {
		if (list.size() == 1) {
			list.clear();
		} else {
			list.remove(idx);
		}
	}

	// =================카테고리===================

	// 카테고리 사이즈 리턴
	public int GetcategorySize() {
		return categorylist.size();
	}

	// 카테고리 이름에 해당 인덱스 리턴
	public int GetcategoryIdx(String name) {
		for (int i = 0; i < categorylist.size(); i++) {
			if (categorylist.get(i).equals(name))
				return i;
		}
		return -1;
	}

	// 새로운 카테고리 추가
	public void Newcategory(String name) {
		categorylist.add(name);
	}

	// 카테고리 삭제
	public void Deletecategory(int idx) {
		// 아이템 삭제 호출 및 카테고리 이름 넘겨주기
		DeleteItemCategory(categorylist.get(idx));
		if (categorylist.size() == 1) {
			categorylist.clear();
		} else {
			categorylist.remove(idx);
		}
	}

	// 카테고리 출력
	public void PrintCategory() {
		System.out.println(" === 카테고리 ===");
		for (int i = 0; i < categorylist.size(); i++) {
			System.out.println((i + 1) + ". " + categorylist.get(i));
		}
	}

	// 카테고리 데이터 리턴
	private String GetCategorySaveData() {
		String s = "";
		for (int i = 0; i < categorylist.size(); i++) {
			s += categorylist.get(i) + "/";
		}
		if (!s.isBlank()) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	// 카테고리 데이터 로드
	private void SetCategoryData(String s) {
		if (s.isBlank()) {
			return;
		}
		if (categorylist.size() != 0) {
			categorylist.clear();
		}
		String data[] = s.split("/");
		for (int i = 0; i < data.length; i++) {
			categorylist.add(data[i]);
		}
	}
	// =================구매내역(카트)===================

	// 구매내역 갯수 리턴
	public int GetCartSize() {
		return cartlist.size();
	}

	// 구매내역에서 아이디값으로 있는 갯수 리턴
	public int GetCartSize(String id) {
		int count = 0;
		for (int i = 0; i < cartlist.size(); i++) {
			if (cartlist.get(i).getUserId().equals(id)) {
				count++;
			}
		}
		return count;
	}

	// 구매내역에서 아이디와 아이템 이름이 같은 갯수 리턴
	public int GetCartSize(String id, String name) {
		int count = 0;
		for (int i = 0; i < cartlist.size(); i++) {
			if (cartlist.get(i).getUserId().equals(id) && name.equals(cartlist.get(i).getItemName())) {
				count++;
			}
		}
		return count;
	}

	// 구매내역에서 해당 카테고리 정보 삭제
	private void DeleteCart(String name) {
		for (int i = 0; i < cartlist.size(); i++) {
			if (name.equals(cartlist.get(i).getItemName())) {
				if (cartlist.size() == 1) {
					cartlist.clear();
					break;
				} else {
					cartlist.remove(i);
				}
				i--;
			}
		}
	}

	// 구매내역 출력
	public void PrintCart() {
		int c = 1;
		System.out.println("구매자\t아이템 이름");
		for (int i = cartlist.size() - 1; i >= 0; i--) {
			System.out.println(cartlist.get(i).getUserId() + "\t" + cartlist.get(i).getItemName());
		}
	}

	// 구매내역 출력 아이디 값과 같은 구매자 출력
	public void PrintCart(String id) {
		System.out.println("구매자\t아이템 이름");
		for (int i = 0; i < cartlist.size(); i++) {
			if (id.equals(cartlist.get(i).getUserId()))
				System.out.println(cartlist.get(i).getUserId() + "\t" + cartlist.get(i).getItemName());
		}
	}

	// 구매내역 추가
	public void NewCartData(String id, int idx) {
		cartlist.add(new Cart(id, list.get(idx - 1).getName()));
		System.out.println(list.get(idx - 1).getName() + " 1개 구매 완료");
	}

	// 회원 아이디별 구매내역 출력
	public void UserCartData(String userId) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i).getName() + "\t";
			int count = 0;

			for (int i2 = 0; i2 < cartlist.size(); i2++) {
				if (cartlist.get(i2).getUserId().equals(userId)
						&& list.get(i).getName().equals(cartlist.get(i2).getItemName())) {
					count++;
					sum += list.get(i).getPrice();
				}
			}
			if (count != 0) {
				s += "구매 수량 : " + count;
				System.out.println(s);
			}
		}
		if (sum != 0) {
			System.out.println("총금액 :" + sum + "원");
		}
	}

	// 구매내역 에서 유저가 구입한 총 금액 리턴
	public int UserItemTotalPrice(String id) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int i2 = 0; i2 < cartlist.size(); i2++) {
				if (cartlist.get(i2).getItemName().equals(list.get(i).getName())
						&& cartlist.get(i2).getUserId().equals(id)) {
					sum += list.get(i).getPrice();
				}
			}
		}
		return sum;
	}

	// 구매내역 중 해당 아이템 이름과 아이디에 해당 하는 갯수만큼 삭제
	public void DeleteCartItem(String id, String name, int count) {
		for (int i = 0; i < count; i++) {
			for (int i2 = 0; i2 < cartlist.size(); i2++) {
				if (id.equals(cartlist.get(i2).getUserId()) && name.equals(cartlist.get(i2).getItemName())) {
					cartlist.remove(i2);
					break;
				}
			}
		}
	}

	// 해당 아이디 구매내역 삭제
	public void DeleCartData(String id) {
		for (int i = 0; i < cartlist.size(); i++) {
			if (id.equals(cartlist.get(i).getUserId())) {
				if (cartlist.size() == 1) {
					cartlist.clear();
					break;
				}
				cartlist.remove(i);
				i--;
			}
		}
	}

	// =================세이브===================
	// 아이템 + 카테고리 세이브 데이터 스트링 리턴
	public String GetItemSaveData() {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s += list.get(i).toString() + "\n";
		}
		if (!s.isBlank()) {
			s = s.substring(0, s.length() - 1);
		}
		if (!(GetCategorySaveData().isBlank()))
			s += "\n+" + GetCategorySaveData();

		return s;
	}

	// 구매내역 세이브 데이터 리턴
	public String GetCartSaveData() {
		String s = "";
		for (int i = 0; i < cartlist.size(); i++) {
			s += cartlist.get(i).toString() + "\n";
		}
		if (!s.isBlank()) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	// =================로드===================

	// 아이템 + 카테고리데이터 로드
	public void SetItemData(String s) {
		if (s.isBlank()) {
			return;
		}
		if (list.size() != 0) {
			list.clear();
		}
		String data[];
		if (s.indexOf("+") != -1) {
			String d = s.substring(s.indexOf("+") + 1);
			SetCategoryData(d);
			s = s.substring(0, s.indexOf("+"));
		}
		data = s.split("\n");
		for (int i = 0; i < data.length; i++) {
			String sp[] = data[i].split("/");
			list.add(new Item(sp[0], Integer.parseInt(sp[1]), sp[2]));
		}
	}

	// 구매내역 데이터로드
	public void SetCartData(String s) {
		if (s.isBlank()) {
			return;
		}
		if (cartlist.size() != 0) {
			cartlist.clear();
		}

		String data[] = s.split("\n");
		for (int i = 0; i < data.length; i++) {
			String sp[] = data[i].split("/");
			cartlist.add(new Cart(sp[0], sp[1]));
		}
	}
}
