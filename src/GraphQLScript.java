import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Query
		String response = given().log().all().header("content-type","application/json")
		.body("{\"query\":\"query($characterId:Int!,$locationId:Int!,$episodeId: Int!,$name: String,$episode:String){\\n  \\n  character(characterId:$characterId)\\n  {\\n    name\\n    status\\n    gender\\n    type    \\n  }\\n  \\n  location(locationId:$locationId)\\n  {\\n    name\\n    dimension    \\n  }\\n  \\n  episode(episodeId:$episodeId)\\n  {\\n    name\\n    air_date\\n    episode\\n    id\\n  }\\n  \\n  characters(filters:{name:$name})\\n  {\\n    info\\n    {\\n      count\\n    }\\n    \\n    result\\n    {\\n      id\\n      name\\n      type\\n    }\\n  }\\n  \\n  episodes(filters:{episode:$episode})\\n  {\\n    info\\n    {\\n      count\\n    }\\n    result\\n    {\\n      id\\n      name\\n      air_date\\n      \\n    }\\n  }\\n}\",\"variables\":{\"characterId\":13703,\"locationId\":19826,\"episodeId\":13862,\"name\":\"Harsh\",\"episode\":\"MXPlayer\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName, "Pooja");
		
		//Mutation
		String mutationResponse = given().log().all().header("content-type","application/json")
				.body("{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!)\\n{\\n \\n  createLocation(location: {name:$locationName,type:\\\"SouthZone\\\",dimension:\\\"356\\\"})\\n  {\\n    id\\n  }\\n  \\n  createCharacter(character:{name:$characterName,type:\\\"Macho\\\",status:\\\"Alive\\\",species:\\\"Human\\\",gender:\\\"Male\\\",image:\\\"png\\\",originId:19820,locationId:19820})\\n  {\\n    id\\n  }\\n  \\n  createEpisode(episode:{name:$episodeName,air_date:\\\"Oct-19-2019\\\",episode:\\\"MXPlayer\\\"})\\n  {\\n    id\\n  }\\n  \\n  deleteLocations(locationIds:[19821])\\n  {\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"locationName\":\"Ind\",\"characterName\":\"Akhil\",\"episodeName\":\"Spiderman\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().extract().response().asString();
				System.out.println(mutationResponse);
				
	}

}
