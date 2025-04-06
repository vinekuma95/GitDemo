import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.CoursePrice());

		// print number of course returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// print purchase amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// print title of first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		// print all course titles and their price

		for (int i = 0; i < count; i++) 
		{
			String courseTitle = js.get("courses[" + i + "].title");
			int coursePrice = js.get("courses[" + i + "].price");

			System.out.println(courseTitle);
			System.out.println(coursePrice);

		}

		// print number of copies sold by RPA course

		System.out.println("print number of copies sold by RPA course");
		for (int i = 0; i < count; i++) 
		{
			String courseTitles = js.get("courses[" + i + "].title");

			if (courseTitles.equalsIgnoreCase("RPA")) 
			{
				int copies = js.get("courses[" + i + "].copies");
				System.out.println(copies);
				break;
			}

		}
		
		//total amount must be equal to number of copies sold for course multiply by price
		int actualTotal = 0;
		for(int i =0;i<count;i++)
		{
			int coursePrice2 = js.get("courses[" + i + "].price");
			int copies2 = js.get("courses[" + i + "].copies");
			int firstTotal = coursePrice2*copies2;
			actualTotal = actualTotal+firstTotal;
			
		}
		System.out.println(actualTotal);
		Assert.assertEquals(totalAmount, actualTotal);
	}

}
