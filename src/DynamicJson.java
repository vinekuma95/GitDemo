import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().header("Content-Type","application/json")
		.body(Payload.addBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"asdf","344"},{"fsff","4345"},{"dds","35"}};
	}

}
