import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.GetCourses;
import pojo.WebAutomation;
public class OauthTestUsingPojo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String response = given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope","trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		GetCourses gc = given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.as(GetCourses.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());;
		
		for(int i = 0;i<gc.getCourses().getApi().size();i++)
		{
			if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))	
			{
				System.out.println(gc.getCourses().getApi().get(i).getPrice());
				
			}
		}
		
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		ArrayList<String> actualList = new ArrayList<String>();
		
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		for(int i = 0;i<w.size();i++)
		{
		
			System.out.println(w.get(i).getCourseTitle());
			actualList.add(w.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertTrue(actualList.equals(expectedList));
	}

}
