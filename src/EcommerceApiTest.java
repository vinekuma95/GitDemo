import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginEcommerce;
import pojo.LoginResponseEcommerce;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommerceApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginEcommerce loginEcomm = new LoginEcommerce();
		loginEcomm.setUserEmail("vineetchandel12395@gmail.com");
		loginEcomm.setUserPassword("Vinsum@123");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(loginEcomm);
		LoginResponseEcommerce loginResponse = reqLogin.when().post("/api/ecom/auth/login")
		.then().extract().response().as(LoginResponseEcommerce.class);
		
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId = loginResponse.getUserId();
		
		//Add product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification reqAddProduct = given().log().all()
				.spec(addProductBaseReq).param("productName", "Rolex")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "watch")
		.param("productPrice", "11500")
		.param("productDescription", "Titan")
		.param("productFor", "everyone")
		.multiPart("productImage",new File("C:\\Users\\vinee\\OneDrive\\Pictures\\wallpaper-1676572.png"));
		
		String addproductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().extract().response().asString();
		JsonPath js = new JsonPath(addproductResponse);
		String productId = js.get("productId");
		
		//Create Order
		
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(orderDetails);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		RequestSpecification createOrderReq = given().log().all()
				.spec(createOrderBaseReq).body(orders);
		
		String AddOrderResponse = createOrderReq.when().post("/api/ecom/order/create-order")
		.then().extract().response().asString();
		System.out.println(AddOrderResponse);
		
		//Delete Product
		
		RequestSpecification DeleteProdBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProdReq = given().log().all().spec(DeleteProdBaseReq)
				.pathParam("productId", productId);
		
		String deleteProdResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteProdResponse);
		Assert.assertEquals("Product Deleted Successfully",js1.get("message"));
		
	}

}
