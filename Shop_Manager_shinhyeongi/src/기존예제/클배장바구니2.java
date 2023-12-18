package 기존예제;

import java.util.ArrayList;
import java.util.Scanner;

import vo.Cart;
import vo.Item;
import vo.User;


public class 클배장바구니2 {

	public static void main(String[] args) {

		String[] userIdList = { "admin", "aaa", "bbb", "ccc" };
		ArrayList<Integer> list = new ArrayList<>();
		ArrayList<String> list2 =new ArrayList<String>();
		
	
		String[] itemNameList = { "사과", "칸초", "귤", "감" };
		int[] itemPriceList = { 10000, 2000, 6500, 3300 };

		int max = 1000;
		User[] userList = new User[max];
		int userSize = 0;
		for (int i = 0; i < userIdList.length; i++) {
			userList[i] = new User();
			userList[i].setId(userIdList[i]);
			userSize += 1;
		}

		Item[] itemList = new Item[max];
		int itemSize = 0;
		for (int i = 0; i < itemNameList.length; i++) {
			itemList[i] = new Item();
			itemList[i].setName(itemNameList[i]);
			itemList[i].setPrice(itemPriceList[i]);
			itemSize += 1;
		}

		Cart[] cartList = new Cart[max];
		int cartSize = 0;

		Scanner sc = new Scanner(System.in);
		User logUser = null;
		while (true) {
			System.out.println("[0] 종료 ");
			System.out.println("------------------------------");
			
			if (logUser == null) {
				System.out.println("[1] 로그인 [2] 회원가입 ");
				int sel = sc.nextInt();
				if (sel == 0) {
					break;
				} else if (sel == 1) {
					System.out.println("[로그인] 아이디 입력 : ");
					String id = sc.next();

					boolean check = false;
					for (User u : userList) {
						if (u == null)
							break;
						if (id.equals(u.getId())) {
							check = true;
							logUser = u;
						}
					}

					if (check == false) {
						System.out.println("[로그인실패]");
						continue;
					}
				} else if (sel == 2) {

				} else {
					System.out.println("입력 오류");
					continue;
				}
			} else {
				if (logUser.getId() == "admin") {
					System.out.println("[ 관리자 메뉴 ]");
					System.out.println("[1] 아이템 추가 [2] 아이템 삭제 [3] 최신순(주문정보) [4] 로그아웃 ");
				} else {
					System.out.println("[ 사용자 메뉴 ]");
					System.out.println("[" + logUser.getId() + " 로그인]");

					System.out.println(" [1] 쇼핑 [2] 주문확인 [3] 탈퇴(그 회원 주문서 동시에 삭제) [4] 로그아웃");
				}

				int sel = sc.nextInt();

				if (logUser.getId() == "admin" && sel == 1) { // 아이템 추가
					int idx = 0;
					for (Item i : itemList) {
						if (i == null)
							break;
						System.out.printf("(%d) %s %d원 %n", 1 + idx++, i.getName(), i.getPrice());
					}
					while (true) {
						System.out.println("추가 아이템 이름 0(종료)>> ");

						String name = sc.next();

						if (name.equals("0")) {
							break;
						}

						boolean check = false;

						for (Item i : itemList) {
							if (i == null)
								break;
							if (i.getName().equals(name)) {
								check = true;
								break;
							}
						}
						if (check) {
							System.out.println("이미 있는 아이템 입니다");
							continue;
						}
						System.out.println("추가 아이템 가격>> ");

						int price = sc.nextInt();

						if (price <= 0) {
							System.out.println("양수 가격만 입력");
							continue;
						}

						Item item = new Item();
						item.setName(name) ;
						item.setPrice(price);

						itemList[itemSize] = item;
						itemSize += 1;
						System.out.println("[아이템 추가 완료]");

						break;
					}

				} else if (logUser.getId() == "admin" && sel == 2) { // 아이템 삭제
					int idx = 0;
					for (Item i : itemList) {
						if (i == null)
							break;
						System.out.printf("(%d) %s %d원 %n", 1 + idx++, i.getName(), i.getPrice());
					}

					System.out.println("삭제 아이템 이름 >> ");

					String name = sc.next();
					idx = -1;
					for (int i = 0; i < itemSize; i += 1) {
						if (name.equals(itemList[i].getName())) {
							idx = i;
							break;
						}
					}

					if (idx == -1) {
						System.out.println("아이템이 존재하지 않습니다");
						continue;
					}

					for (int i = idx; i < itemSize - 1; i += 1) {
						itemList[i] = itemList[i + 1];
					}
					itemList[itemSize - 1] = null;
					itemSize -= 1;
					System.out.println("[아이템 삭제완료]");

				} else if (logUser.getId() == "admin" && sel == 3) { // 최신순 정렬 (주문정보) : 임시
					if (cartSize == 0) {
						System.out.println("주문 정보가 없습니다");
						continue;
					}
					System.out.printf("%s %s %n", "회원아이디", "상품 이름");
					for (int i = cartSize - 1; i >= 0; i -= 1) {
						System.out.printf("(%d) %s %s %n", cartSize - 1 - i, cartList[i].getUserId(), cartList[i].getItemName());
					}

				}

				else if (sel == 1) { // 쇼핑
					int idx = 0;
					for (Item i : itemList) {
						if (i == null)
							break;
						System.out.printf("(%d) %s %d원 %n", 1 + idx++, i.getName(), i.getPrice());
					}
					System.out.println("============");

					while (true) {
						System.out.println("번호 입력 [0.뒤로가기] >> ");
						idx = sc.nextInt() - 1;

						if (idx == -1)
							break;
						if (idx < 0 || idx >= itemSize) {
							System.out.println("번호 범위 오류 ");
							continue;
						}
						Cart c = new Cart();
						c.userId = logUser.id;
						c.itemName = itemList[idx].name;

						cartList[cartSize] = c;
						cartSize += 1;
						System.out.printf("%s 주문 완료 %n", itemList[idx].name);
					}

				} else if (sel == 2) { // 주문확인

					int[] itemCnt = new int[itemSize];
					int total = 0;
					int cnt = 0;
					for (Cart c : cartList) {
						if (c == null) break;
						if (c.userId.equals(logUser.id)) {
							for (int i = 0; i < itemSize; i += 1) {
								if (c.itemName.equals(itemList[i].name)) {
									itemCnt[i] += 1;
									total += itemList[i].price;
									cnt += 1;
								}
							}
						}

					}

					if (total == 0) {
						System.out.println("=== 주문 내용이 없습니다 ===== ");
						continue;
					}

					System.out.println(" ====== " + logUser.id + " 님의 장바구니 ========");
					int num = 1;
					for (int i = 0; i < itemSize; i += 1) {
						if (itemCnt[i] > 0) {
							System.out.printf(" %3d) %s %10d원 %3d개 %n", num++, itemList[i].name, itemList[i].price,
									itemCnt[i]);
						}
					}

					System.out.printf("====== 총 갯수 %d 총 금액 %d 원 ======= %n ", cnt, total);

				} else if (sel == 3) { // 탈퇴 할때 그 회원 주문서 동시에 삭제되야함

					for (int i = 0; i < cartSize; i += 1) {
						if (logUser.id.equals(cartList[i].userId)) {

							for (int k = i; k < cartSize - 1; k += 1) {
								cartList[k] = cartList[k + 1];
							}
							cartList[cartSize - 1] = null;
							cartSize -= 1;
							i -= 1;

						}
					}

					int idx = -1;
					for (int i = 0; i < userSize; i += 1) {
						if (logUser.id.equals(userList[i].id)) {
							idx = i;
							break;
						}
					}

					for (int i = idx; i < userSize - 1; i += 1) {
						userList[i] = userList[i + 1];
					}
					userList[userSize - 1] = null;
					userSize -= 1;

					logUser = null;

					System.out.println("[ 탈퇴 완료 ]");
				} else if (sel == 4) {
					logUser = null;
				} else {
					System.out.println("입력 오류");
					continue;
				}
			}

		}
		sc.close();
	}

}
