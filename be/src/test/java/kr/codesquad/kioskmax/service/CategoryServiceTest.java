package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.annotation.ServiceTest;
import kr.codesquad.kioskmax.domain.Category;
import kr.codesquad.kioskmax.domain.Menu;
import kr.codesquad.kioskmax.repository.CategoryRepository;
import kr.codesquad.kioskmax.repository.MenuRepository;
import kr.codesquad.kioskmax.service.dto.CategoryInformation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ServiceTest
class CategoryServiceTest {

	@InjectMocks
	private CategoryService categoryService;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@DisplayName("DB에서 카테고리별 메뉴를 가져올수 있다.")
	@Test
	void findAllCategories() {

		//given
		List<Menu> firstCategoryMenus = firstCategoryMenuDummy();
		List<Menu> secondCategoryMenus = secondCategoryMenuDummy();
		List<Menu> thirdCategoryMenus = thirdCategoryMenuDummy();
		List<Category> categories = categoryDummy();

		given(menuRepository.findAllByCategoryId(1L)).willReturn(firstCategoryMenus);
		given(menuRepository.findAllByCategoryId(2L)).willReturn(secondCategoryMenus);
		given(menuRepository.findAllByCategoryId(3L)).willReturn(thirdCategoryMenus);
		given(categoryRepository.findAll()).willReturn(categories);

		//when
		List<CategoryInformation> actual= categoryService.findAllCategories();

		//then
		Assertions.assertThat(actual.get(0).getMenuInformations().size()).isEqualTo(2);
		Assertions.assertThat(actual.get(1).getMenuInformations().size()).isEqualTo(2);
		Assertions.assertThat(actual.get(2).getMenuInformations().size()).isEqualTo(1);
	}

	List<Menu> firstCategoryMenuDummy() {
		Menu menu1 = Menu.builder()
			.id(1L)
			.categoryId(1L)
			.name("Menu1")
			.price(5000L)
			.imageSrc("menu1.jpg")
			.createdDateTime(LocalDateTime.now())
			.build();

		Menu menu2 = Menu.builder()
			.id(2L)
			.categoryId(1L)
			.name("Menu2")
			.price(6000L)
			.imageSrc("menu2.jpg")
			.createdDateTime(LocalDateTime.now())
			.build();
		return new ArrayList<>(Arrays.asList(menu1,menu2));
	}

	List<Menu> secondCategoryMenuDummy() {
			Menu menu3 = Menu.builder()
				.id(3L)
				.categoryId(2L)
				.name("Menu3")
				.price(7000L)
				.imageSrc("menu3.jpg")
				.createdDateTime(LocalDateTime.now())
				.build();

			Menu menu4 = Menu.builder()
				.id(4L)
				.categoryId(2L)
				.name("Menu4")
				.price(8000L)
				.imageSrc("menu4.jpg")
				.createdDateTime(LocalDateTime.now())
				.build();
		return new ArrayList<>(Arrays.asList(menu3,menu4));
	}

	List<Menu> thirdCategoryMenuDummy(){
		Menu menu5 = Menu.builder()
			.id(5L)
			.categoryId(3L)
			.name("Menu5")
			.price(9000L)
			.imageSrc("menu5.jpg")
			.createdDateTime(LocalDateTime.now())
			.build();
		return new ArrayList<>(Arrays.asList(menu5));
	}

	List<Category> categoryDummy(){
		Category category1 = Category.builder()
			.id(1L)
			.name("Category1")
			.createdDateTime(LocalDateTime.now())
			.build();

		Category category2 = Category.builder()
			.id(2L)
			.name("Category2")
			.createdDateTime(LocalDateTime.now())
			.build();

		Category category3 = Category.builder()
			.id(3L)
			.name("Category3")
			.createdDateTime(LocalDateTime.now())
			.build();

		return new ArrayList<>(Arrays.asList(category1,category2,category3));
	}
}
