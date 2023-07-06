package com.apis.test;

import com.core.BaseTest;
import com.pojo.GetProfileBook;
import com.utils.ExtentReport;
import com.books.apis.BooksApi;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetProfileBooksApiTest extends BaseTest {

    private final String getBooksInProfilePath;
    private final String userName;
    private final String userId;
    private final String password;
    private final JSONObject obj;
    private final HashMap<String,Object> sessionMap;
    BooksApi booksApi;
//    static final ClassLoader loader = GetProfileBooksApiTest.class.getClassLoader();



    GetProfileBooksApiTest(){
         getBooksInProfilePath = propObj.getProperty("getBooksInProfile");
         obj=new JSONObject(helper.fileReader("/src/test/resources/TestDataJSON/UserData.json"));
         userName=obj.get("userName").toString();
         userId=obj.get("userId").toString();
         password=obj.get("password").toString();
         sessionMap=new HashMap<>();
         booksApi = new BooksApi();



    }

    @Test(description = "get Profile books by reading payload from file")
    public void getProfilebooks(){
        booksApi.deleteBooks();      //delete all books in profile
        String books=booksApi.getBooksInStore();        //get all books in book store
        String request=helper.fileReader("/src/test/resources/TestDataJSON/AddBook.json"); //add book payload
        JsonPath jObj=JsonPath.from(books);
        sessionMap.put("isbn",jObj.getString("books[0].isbn"));
        sessionMap.put("title",jObj.getString("books[0].title"));
        sessionMap.put("subTitle",jObj.getString("books[0].subTitle"));
        sessionMap.put("pages",jObj.getInt("books[0].pages"));

        booksApi.addBook(request.replace("$isbn",sessionMap.get("isbn").toString()));         //add book

        String booksinProfile=getBooksInProfile();
        String resUserId=JsonPath.from(booksinProfile).get("userId");
        String resUserName=JsonPath.from(booksinProfile).get("username");
        String resIsbn=JsonPath.from(booksinProfile).get("books[0].isbn");
        String resTitle=JsonPath.from(booksinProfile).get("books[0].title");
        String resSubTitle=JsonPath.from(booksinProfile).get("books[0].subTitle");
        int pages=JsonPath.from(booksinProfile).get("books[0].pages");

        ExtentReport.test.info("Get profile books reponse : "+getBooksInProfile());

        Assert.assertEquals(resUserId,userId);
        Assert.assertEquals(resUserName,userName);
        Assert.assertEquals(resIsbn,sessionMap.get("isbn").toString());
        Assert.assertEquals(resTitle,sessionMap.get("title").toString());
        Assert.assertEquals(resSubTitle,sessionMap.get("subTitle").toString());
        Assert.assertEquals(pages,sessionMap.get("pages"));
    }

    @Test(description = "get Profile books Using POJO")
    public void getProfilebooksUsingPOJO(){
        booksApi.deleteBooks();      //delete all books in profile
        String books=booksApi.getBooksInStore();        //get all books in book store
        String request=helper.fileReader("/src/test/resources/TestDataJSON/AddBook.json"); //add book payload
        JsonPath jObj=JsonPath.from(books);
        sessionMap.put("isbn",jObj.getString("books[0].isbn"));
        sessionMap.put("title",jObj.getString("books[0].title"));
        sessionMap.put("subTitle",jObj.getString("books[0].subTitle"));
        sessionMap.put("pages",jObj.getInt("books[0].pages"));
        sessionMap.put("website",jObj.getString("books[0].website"));

        booksApi.addBook(request.replace("$isbn",sessionMap.get("isbn").toString()));         //add book

        GetProfileBook gRes=given()
                                    .log().all()
                                    .when()
                                    .auth()
                                    .preemptive()
                                    .basic(userName,password)
                                    .pathParam("userId",userId)
                                    .when()
                                    .get(url+getBooksInProfilePath)
                                    .then()
                                    .assertThat()
                                    .statusCode(200)
                                    .extract()
                                    .as(GetProfileBook.class);
        ExtentReport.test.info(gRes.toString());

        String resUserId=gRes.getUserId();
        String resUserName=gRes.getUsername();
        String resIsbn=gRes.getBooks().get(0).getIsbn();
        String resTitle=gRes.getBooks().get(0).getTitle();
        String resSubTitle=gRes.getBooks().get(0).getSubTitle();
        String resWebsite=gRes.getBooks().get(0).getWebsite();
        int pages=gRes.getBooks().get(0).getPages();

        ExtentReport.test.info("User Id received in response : "+resUserId);

        Assert.assertEquals(resUserId,userId);
        Assert.assertEquals(resUserName,userName);
        Assert.assertEquals(resIsbn,sessionMap.get("isbn").toString());
        Assert.assertEquals(resTitle,sessionMap.get("title").toString());
        Assert.assertEquals(resSubTitle,sessionMap.get("subTitle").toString());
        Assert.assertEquals(resWebsite,sessionMap.get("website").toString());
        Assert.assertEquals(pages,sessionMap.get("pages"));
    }

    public String getBooksInProfile(){
        Response response=given()
                                .log().all()
                                .when()
                                .auth()
                                .preemptive()
                                .basic(userName,password)
                                .pathParam("userId",userId)
                                .when()
                                .get(url+getBooksInProfilePath)
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .extract()
                                .response();
        System.out.println(response.asString());
        return response.asString();

    }

}
