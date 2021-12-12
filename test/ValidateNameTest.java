

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateNameTest {

	private PlaceOrderController mPlaceOrderController;
	private PlaceRushOrderController mPlaceRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		mPlaceOrderController = new PlaceOrderController();
		mPlaceRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"huyhoan,true",
		"huyhoan2k,false",
		"HuyHoan2k,false",
		"@huyhoan,false",
		",false",
	})
	void test(String name,boolean expected) {
		boolean isValided = mPlaceOrderController.validateName(name);
		assertEquals(expected,isValided);
	}
	
	@ParameterizedTest
	@CsvSource({
		"huyhoan,true",
		"huyhoan2k,false",
		"HuyHoan2k,false",
		"@huyhoan,false",
		",false",
	})
	void testRushOrder(String name,boolean expected) {
		boolean isValided = mPlaceRushOrderController.validateName(name);
		assertEquals(expected,isValided);
	}
}
