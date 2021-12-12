package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.exception.InvalidDeliveryInfoException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

public class PlaceRushOrderController {
	public static final String HANOI = "Hà Nội";
	
	/**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    
    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeRushOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }
    
    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }
    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }
    
    public void confirmInvoice() {
    	
    }
    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InvalidDeliveryInfoException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InvalidDeliveryInfoException{
    	List<Boolean> validateResults = new ArrayList<>();
    	validateResults.add(validatePhoneNumber(info.get("phone")));
    	validateResults.add(validateName(info.get("name")));
    	validateResults.add(validateAddress(info.get("address")));
    	validateResults.add(validateRushOrderDate(info.get("date")));
    	validateResults.add(validateRushOrderCondition(info.get("condition")));
    	boolean isValid = !validateResults.stream().anyMatch(result -> Objects.equals(result, Boolean.FALSE));
    	if(isValid) {
    		throw new InvalidDeliveryInfoException();
    	}
    }
    
    
    /**
     * The method check if the Media is available or not
     * @param media
     */
    public void checkAvailiabilityOfProduct(Media media) {
    	
    } 
    /**
     * The method calculate rush order delivery method
     * @param condition
     * @throws InterruptedException
     * @throws IOException
     */
    public void calculateRushOrderDeliveryMethod() throws InterruptedException,IOException{
    	
    }
    
    /** This method validate phone nunber of shipping info
     * @param phoneNumber
     * @return result validate
     */
    public boolean validatePhoneNumber(String phoneNumber) {
    	if(Objects.nonNull(phoneNumber) && !phoneNumber.isEmpty()) {
    		//phone number must be start with 0 and have 10 number
    		if(phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
    			return false;
    		}
    		//phone number must be contains only number
    		Pattern pattern = Pattern.compile("[0-9]+");
    		Matcher matcher = pattern.matcher(phoneNumber);
    		return matcher.matches();
    	}
    	return false;
    }
    
    /** This method validate name of shipping info
     * @param name
     * @return result validate
     */
    public boolean validateName(String name) {
    	//must be not null or empty
    	if (Objects.nonNull(name) && !name.isEmpty()) {
            return false;
        }
    	//must be contains only a-z,A-Z
    	Pattern pattern = Pattern.compile("^[a-zA-Z\s]*$");
        Matcher m = pattern.matcher(name);
        return m.matches();
    }
    
    /** This method validate name of shipping info
     * @param name
     * @return result validate
     */
    public boolean validateAddress(String address) {
    	//must be not null or empty
    	if(Objects.nonNull(address) && !address.isEmpty()) {
    		return false;
    	}
    	//must be contains only a-z,A-Z, 0-9
    	Pattern pattern = Pattern.compile("^[a-zA-Z0-9\s]*$");
    	Matcher m = pattern.matcher(address);
    	return m.matches();
    }
    
    /**
     * This method date of delivery info
     * @param date of delivery info
     * @return result validate
     */
    public boolean validateRushOrderDate(String date) {
    	if(Objects.nonNull(date) && !date.isEmpty()) {
    		return false;
    	}
    	try {
    		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    
    /**
     * This method validate rush order adress, system current supported rush order in Ha noi
     * @param address
     * @return
     */
    public boolean validateRushOrderCondition(String address){
    	if(Objects.equals(address, HANOI)) {
    		return true;
    	}
    	return false;
    }
    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
    /**
     * This method process rush order delivery form
     * @throws InvalidDeliveryInfoException
     */
    public void processRushOrderDeliveryForm() throws InvalidDeliveryInfoException{
    	
    }
}
