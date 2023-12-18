package Controller;

import java.io.IOException;
import java.util.ArrayList;

import Utils.FileManager;
import Utils.InnputManger;
import dao.ItemDAO;
import dao.UserDAO;
import vo.User;

public class ShopController {
	private UserDAO user;
	private ItemDAO item;
	private FileManager f;
	private InnputManger inp;
	private String id;

	public ShopController() {
		f = FileManager.getInstance();
		inp = InnputManger.getInstance();
		user = UserDAO.getInstance();
		item = ItemDAO.getInstance();
		id = "";
		LoadData();
	}

	// =================기타===================
	
	// 입력 범위 확인 0 ~ max 까지
	private boolean CheckNum(int inp, int max) {
		if (inp < 0 || inp > max) {
			System.out.println("0 ~ " + max + "까지 입력 가능");
			return true;
		}
		return false;
	}

	// 입력 범위 확인 0 ~ max 까지 와 max2
	private boolean CheckNum(int inp, int max, int max2) {
		if (inp < 0 || inp > max && inp != max2) {
			System.out.println("0 ~ " + max + " or " + max2 + " 입력 가능");
			return true;
		}
		return false;
	}

	// 유저 출력
	private void printUser() {
		System.out.println(" === " + user.getName(id) + "님 환영합니다 ===");
	}

	// =================관리 관련===================
	
	// 로그아웃
	private void LogOut() {
		id = "";
	}
	// 아이템 관리
	private void ItemManager() {
		while (true) {
			// [1] 아이템 추가 [2] 아이템 삭제 [3]뒤로가기
			int inp = this.inp.getInt("[1] 아이템 추가 [2] 아이템 삭제 [3] 뒤로가기");
			if (inp == 3) {
				break;
			} else if (CheckNum(inp, 3)) {
				continue;
			}
			if (inp == 1) {
				if (item.GetcategorySize() == 0) {
					System.out.println("[아이템 추가]카테고리가 존재 하지 않습니다.\n 카테고리 생성 후 이용해주세요");
					continue;
				}
				String itemname = this.inp.getString("[아이템 추가]추가 하실 아이템 이름을 입력 하세요");
				if (item.GetItemIdx(itemname) != -1) {
					System.err.println("[아이템 추가]중복된 아이템 이름 입니다.");
					continue;
				}
				item.PrintCategory();
				String categoryname = this.inp.getString("[아이템 추가]추가 하실 아이템의 카테고리 이름을 입력 하세요");
				if (item.GetcategoryIdx(categoryname) == -1) {
					System.err.println("[아이템 추가]존재 하지 않는 카테고리입니다.");
					continue;
				}
				int price = this.inp.getInt("[아이템 추가]아이템의 가격을 입력 해주세요");
				if (price < 1) {
					System.err.println("[아이템 추가]1원 이상 입력해주세요");
					continue;
				}
				item.NewItem(itemname, categoryname, price);
			} else if (inp == 2) {
				if (item.GetItemSize() == 0) {
					System.out.println("아이템이 존재 하지 않습니다.");
					continue;
				}
				item.PrintItem();
				String itemname = this.inp.getString("[아이템 삭제]추가 하실 아이템 이름을 입력 하세요");
				if (item.GetItemIdx(itemname) == -1) {
					System.err.println("[아이템 삭제]없는 아이템 이름 입니다.");
					continue;
				}
				item.DeleteItem(item.GetItemIdx(itemname));
				System.out.println("[아이템 삭제]" + itemname + " 아이템 삭제 완료");
			}
		}
	}

	// 카테고리 관리
	private void categoryManager() {
		while (true) {
			item.PrintCategory();
			// [1] 카테고리 추가 [2] 카테고리 삭제 [3] 뒤로가기
			int inp = this.inp.getInt("[1] 카테고리 추가 [2] 카테고리 삭제 [3] 뒤로가기");
			if (inp == 3) {
				break;
			}
			if (inp == 1) {
				String name = this.inp.getString("[카테고리 추가]추가하실 카테고리 이름을 입력하세요");
				if (item.GetcategoryIdx(name) != -1) {
					System.out.println("중복된 카테고리 이름 입니다.");
					continue;
				}
				item.Newcategory(name);
				System.out.println(name + "카테고리 추가 완료");
			} else if (inp == 2) {
				// [2] 카테고리 삭제
				if (item.GetcategorySize() == 0) {
					System.out.println("카테고리가 존재 하지 않습니다.");
					continue;
				}
				String name = this.inp.getString("[카테고리 삭제]삭제하실 카테고리 이름을 입력하세요");
				if (item.GetcategoryIdx(name) == -1) {
					System.err.println("[카테고리 삭제]잘못된 카테고리 이름 입니다.");
					continue;
				}
				item.Deletecategory(item.GetcategoryIdx(name));
				System.out.println("[카테고리 삭제]" + name + " 해당 정보 삭제 완료");
			}
		}
	}

	// 장바구니 관리
	private void CartManager() {
		while (true) {
			int inp = this.inp.getInt("[1]장바구니 조회 [2]회원별 장바구니 [3]뒤로 가기");
			if (inp == 3) {
				break;
			} else if (CheckNum(inp, 3)) {
				continue;
			}
			if (inp == 1) {
				if (item.GetCartSize() == 0) {
					System.err.println("no data");
					continue;
				}
				item.PrintCart();
			} else if (inp == 2) {
				// [2] 회원별 장바구니
				if (item.GetCartSize() == 0) {
					System.err.println("no data");
					continue;
				}
				String data[] = user.GetAllUserId().split("/");
				for (int i = 0; i < data.length; i++) {
					System.out.println(" === " + user.getName(data[i]) + " === ");
					item.UserCartData(data[i]);
					System.out.println("=======================");
				}
			}

		}
	}

	// 유저 관리
	private void UserManager() {
		while (true) {
			if (user.getCountUser() != 0) {
				user.PrintUser();
			}
			int inp = this.inp.getInt("[1] 유저 삭제 [2] 회원 정보 수정 [3]뒤로 가기");
			if (inp == 3) {
				break;
			}
			if (CheckNum(inp, 3)) {
				continue;
			}
			if (user.getCountUser() == 0) {
				System.out.println("no data");
			} else if (inp == 1) {
				// [1] 유저 삭제
				String id = this.inp.getString("[유저 삭제]삭제할 유저의 아이디를 입력하세요");
				if (user.getIdIdx(id) == -1) {
					System.err.println("[유저 삭제]잘못된 아이디 입니다.");
					continue;
				}
				user.DeleteUser(user.getIdIdx(id));
			} else if (inp == 2) {
				// [2] 회원 정보 수정
				String id = this.inp.getString("[회원 정보 수정]수정할 유저의 아이디를 입력하세요");
				if (user.getIdIdx(id) == -1) {
					System.err.println("[회원 정보 수정]잘못된 아이디 입니다.");
					continue;
				}
				String name = this.inp.getString("[회원 정보 수정]수정할 이름을 입력하세요");
				user.UpdateUserName(user.getIdIdx(this.id), name);
			}

		}
	}

	// ================메뉴 출력 관련===================

	// 첫 시작 페이지 메뉴
	private void FirstMenu() {
		while (true) {
			if (user.getIdIdx(id) != -1) {
				printUser();
			}
			int inp = this.inp.getInt("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]" + "\n[100.관리자] [0.종료] ");
			if (inp == 0) {
				break;
			} else if (CheckNum(inp, 4, 100))
				continue;

			if (inp == 1) {
				String id = this.inp.getString("[가입]아이디 입력");
				if (user.getIdIdx(id) != -1) {
					System.out.println("중복된 아이디 입니다");
					continue;
				}
				String pw = this.inp.getString("[가입]비밀번호 입력");
				String name = this.inp.getString("[가입]이름 입력");
				user.NewUseradd(id, pw, name);
			} else if (inp == 2) {
				if (user.getCountUser() == 0) {
					System.err.println("no data");
					continue;
				}
				String id = this.inp.getString("[탈퇴]아이디 입력");
				if (user.getIdIdx(id) == -1) {
					System.err.println("[탈퇴]아이디를 확인하세요");
					continue;
				}
				String pw = this.inp.getString("[탈퇴]비밀번호 입력");
				this.id = user.getUserIdx(id, pw);
				if (this.id.isBlank()) {
					System.err.println("[탈퇴]아이디 또는 비밀 번호를 확인 하세요");
					continue;
				}
				item.DeleCartData(id);
				user.DeleteUser(user.getIdIdx(this.id));
			} else if (inp == 3) {
				if (user.getCountUser() == 0) {
					System.out.println("no data");
					continue;
				} else if (user.getIdIdx(this.id) == -1) {
					String id = this.inp.getString("[로그인]아이디 입력");
					String pw = this.inp.getString("[로그인]비밀번호 입력");
					this.id = user.getUserIdx(id, pw);
					if (id.isBlank()) {
						System.err.println("[로그인] 아이디또는 비밀번호를 확인하세요");
						continue;
					}
				}
				LoginMenu();
			} else if (inp == 4) {
				if (id.isBlank()) {
					System.out.println("로그인 후 로그아웃 가능");
					continue;
				}
				System.out.println("로그아웃....");
				LogOut();
			} else if (inp == 100) {
				id = "관리자";
				ManagerMenu();
			}

		}
		System.out.println("프로그램 종료");
	}

	// 관리자 메뉴
	private void ManagerMenu() {
		while (true) {
			System.out.println(" === " + id + "님 환영합니다 ===");

			int inp = this.inp.getInt("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리] [5.데이터 저장] [6.데이터 불러오기] [0.뒤로가기] ");
			if (inp == 0) {
				LogOut();
				break;
			} else if (CheckNum(inp, 6))
				continue;

			if (inp == 1) {
				// [1.아이템관리]
				ItemManager();
			} else if (inp == 2) {
				// [2.카테고리관리]
				categoryManager();
			} else if (inp == 3) {
				// [3.장바구니관리]
				CartManager();
			} else if (inp == 4) {
				// [4.유저관리]
				UserManager();
			} else if (inp == 5) {
				// [5.데이터 저장]
				f.SaveData(user.GetUserSaveData(), item.GetItemSaveData(), item.GetCartSaveData());
			} else if (inp == 6) {
				// [6.데이터 불러오기]
				LoadData();
			}
		}
	}

	// 사용자 메뉴
	private void LoginMenu() {
		while (true) {
			printUser();
			int inp = this.inp.getInt("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
			if (inp == 0) {
				break;
			} else if (CheckNum(inp, 4))
				continue;
			if (inp == 1) {
				// [1.쇼핑]
				while (true) {
					item.PrintItem();
					inp = this.inp.getInt("[0.종료]");
					if (CheckNum(inp, item.GetItemSize())) {
						continue;
					}
					if (inp == 0) {
						break;
					}
					item.NewCartData(id, inp);
				}
			} else if (inp == 2) {
				// [2.장바구니목록]
				UserMenu();
			}
		}
	}

	// 마이페이지
	private void UserMenu() {
		while (true) {
			int inp = this.inp.getInt("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");
			if (inp == 0) {
				break;
			} else if (CheckNum(inp, 4))
				continue;

			if (item.GetCartSize(id) == 0) {
				System.out.println("장바구니가 비었습니다.");
				continue;
			} else if (inp == 1) {
				item.UserCartData(id);
			} else if (inp == 2) {
				// [2.삭제]
				while (true) {
					if (item.GetCartSize(id) == 0) {
						System.out.println("장바구니가 비었습니다.");
						break;
					}
					item.UserCartData(id);
					String itemname = this.inp.getString("[0.종료]\n[삭제]삭제하실 아이템 이름을 입력하세요");
					if (itemname.equals("0")) {
						break;
					}
					if (item.GetCartSize(id, itemname) == 0) {
						System.out.println(itemname + " 아이템 구매 내역이 없습니다!");
						continue;
					}
					int count = this.inp.getInt("[삭제]삭제하실 아이템 수량을 입력하세요");
					if (count < 1) {
						System.err.println("[삭제]1이상 입력해주세요");
						continue;
					} else if (count > item.GetCartSize(id, itemname)) {
						System.err.println("[삭제]구매 수량 보다 많이 입력하셨습니다.");
						continue;
					}
					item.DeleteCartItem(id, itemname, count);
					System.out.println("[삭제]" + id + " " + count + "개 삭제 완료");
				}
			} else if (inp == 3) {
				// 구입
				item.UserCartData(id);
				System.out.println("총 금액 : " + item.UserItemTotalPrice(id) + "원 입니다.");
				int money = this.inp.getInt("[구입]지불 금액을 입력 해주세요");
				int total = item.UserItemTotalPrice(id);
				if (money < 1) {
					System.out.println("[구입]1원 이상 입력 해주세요");
					continue;
				} else if (money < total) {
					System.out.println("[구입]" + (total - money) + "원 부족합니다!");
					continue;
				} else if (money > total) {
					System.out.println("[구입]" + (money - total) + "원을 반환합니다.");
				}
				item.DeleCartData(id);
				System.out.println("[구입]구매 완료!");

			}
		}

	}

	// ================데이터 세이브 및 불러오기===================
	
	// 데이터 불러오기
	private void LoadData() {
		user.SetUserData(f.UserLoadData());
		item.SetItemData(f.ItemLoadData());
		item.SetCartData(f.CartLoadData());
	}

	// 메인 실행 부분
	public void run() {
		FirstMenu();
		System.out.println("프로그램 종료");
	}
}
