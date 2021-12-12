

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateAddressTest {
	
	private PlaceOrderController placeOrderController;
	private PlaceRushOrderController placeRushOrderController;
	
	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"Hanoi,true",
		"51515,true",
		"Thanh hoa,true",
		"24/2/33 Ngọc Trục, Đại Mỗ, Nam Từ Liêm, Hà Nội,false",
		"Nam Tu Liem /Ha Noi,false",
		",false",
	})
	void test(String address,boolean expected) {
		boolean isValided = placeOrderController.validateAddress(address);
		assertEquals(expected,isValided);
	}
	@ParameterizedTest
	@CsvSource({
		"Hanoi,true",
		"51515,true",
		"Thanh hoa,true",
		"24/2/33 Ngọc Trục, Đại Mỗ, Nam Từ Liêm, Hà Nội,false",
		"Nam Tu Liem /Ha Noi,false",
		",false",
	})
	void testRushOrder(String address,boolean expected) {
		boolean isValided = placeRushOrderController.validateAddress(address);
		assertEquals(expected,isValided);
	}
}
