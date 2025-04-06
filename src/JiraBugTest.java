import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.annotations.Test;

public class JiraBugTest {

	@Test
	public static void TestJira() {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://vineet123.atlassian.net";
		
		String createdIssueResponse = given().header("Content-Type","application/json")
				.header("Authorization","Basic dmluZWV0Y2hhbmRlbDEyMzk1QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBjLXg1MUtZNk5aRmhqNzVCVTlkSDFPVk5fSzd6Ty1VcklGN2UtR3VEUUhwWjFlaHZtQWNkT2FnQzk5SGlKaVhVZ2xLdjJLTzBjTHlub2dBalJFTUVMMlZZM2d1bjRMbnF4cjlVM3ZQSGw2TjRBYVR2Zm9UQXNPUUE5bmdfN1RvYXB1ZTExN1o4OU1XSTZpQWpDc29vZkNZcy1XRWJjTjBEenFFSWF4NFpGZDg9Q0ZCRDdBQUI=")
				.body("{\r\n"
						+ "    \"fields\": {\r\n"
						+ "        \"project\": {\r\n"
						+ "            \"key\": \"SCRUM\"\r\n"
						+ "        },\r\n"
						+ "        \"summary\": \"Links are not working\",\r\n"
						+ "            \"issuetype\": {\r\n"
						+ "                \"name\": \"Bug\"\r\n"
						+ "            }\r\n"
						+ "        }\r\n"
						+ "    }")
				.log().all()
				.when()
				.post("rest/api/3/issue")
				.then().log().all()
				.assertThat().statusCode(201).contentType("application/json")
				.extract().response().asString();
		
		JsonPath js = new JsonPath(createdIssueResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);
		
		//Add Attachment
		
		given().pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic dmluZWV0Y2hhbmRlbDEyMzk1QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBjLXg1MUtZNk5aRmhqNzVCVTlkSDFPVk5fSzd6Ty1VcklGN2UtR3VEUUhwWjFlaHZtQWNkT2FnQzk5SGlKaVhVZ2xLdjJLTzBjTHlub2dBalJFTUVMMlZZM2d1bjRMbnF4cjlVM3ZQSGw2TjRBYVR2Zm9UQXNPUUE5bmdfN1RvYXB1ZTExN1o4OU1XSTZpQWpDc29vZkNZcy1XRWJjTjBEenFFSWF4NFpGZDg9Q0ZCRDdBQUI=")
		.multiPart("file", new File("C:\\Users\\vinee\\OneDrive\\Pictures\\wallpaper-1676572.png"))
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		
	}

}
