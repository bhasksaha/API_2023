package com.books.apis;

import com.utils.Helper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BooksApi {

    private final String url;
    private final String deleteBooksPath;
    private final String addBookPath;
    private final String getBooksInStorePath;
    private final String getBooksInProfilePath;
    private final String userName;
    private final String userId;
    private final String password;
    private final JSONObject obj;
    private final HashMap<String,Object> sessionMap;
    private final Properties propObj;
    Helper helper;
    RequestSpecification rs;

    public BooksApi(){
        helper=new Helper();
        propObj=helper.properReader("/src/test/resources/config.properties");
        url = propObj.getProperty("url");
        deleteBooksPath = propObj.getProperty("deleteBooks");
        addBookPath = propObj.getProperty("addBook");
        getBooksInStorePath = propObj.getProperty("getBooksInStore");
        getBooksInProfilePath = propObj.getProperty("getBooksInProfile");
        obj=new JSONObject(helper.fileReader("/src/test/resources/TestDataJSON/UserData.json"));
        userName=obj.get("userName").toString();
        userId=obj.get("userId").toString();
        password=obj.get("password").toString();
        sessionMap=new HashMap<>();
    }

    public void RequestSpec(){
         rs=given()
                                        .log().all()
                                        .when()
                                        .auth()
                                        .preemptive()
                                        .basic(userName,password)
                                        .queryParam("UserId",userId);


    }

//    public void deleteBooks(){
//        given()
//                .log().all()
//                .when()
//                .auth()
//                .preemptive()
//                .basic(userName,password)
//                .queryParam("UserId",userId)
//                .baseUri(url+deleteBooksPath)
//                .delete()
//                .then()
//                .assertThat()
//                .statusCode(204);
//    }

    public void deleteBooks(){
        RequestSpec();
        given()
                .log().all()
                .when()
                .spec(rs)
                .baseUri(url+deleteBooksPath)
                .delete()
                .then()
                .assertThat()
                .statusCode(204);
    }

    public Response addBook(String payload){
        Response res=given()
                            .log().all()
                            .when()
                            .header("Content-Type","application/json")
                            .auth()
                            .preemptive()
                            .basic(userName,password)
                            .body(payload)
                            .baseUri(url+addBookPath)
                            .post()
                            .then()
                            .assertThat()
                            .statusCode(201)
                            .extract()
                            .response();
        return res;
    }

    public String getBooksInStore(){
        Response response=given()
                .log().all()
                .when()
                .auth()
                .basic(userName,password)
                .baseUri(url+getBooksInStorePath)
                .get()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asString());
        return response.asString();

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
