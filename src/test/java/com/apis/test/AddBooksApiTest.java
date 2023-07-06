package com.apis.test;

import com.core.BaseTest;
import com.pojo.Addbook;
import com.pojo.Book;
import com.books.apis.BooksApi;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;

public class AddBooksApiTest extends BaseTest {


    private final String getBooksInProfilePath;
    private final String userId;
    private final String password;
    private final JSONObject obj;
    private final HashMap<String,Object> sessionMap;
    Addbook addbook=new Addbook();
    Book book =new Book();
    BooksApi booksApi;

    AddBooksApiTest(){
        getBooksInProfilePath = propObj.getProperty("getBooksInProfile");
        obj=new JSONObject(helper.fileReader("/src/test/resources/TestDataJSON/UserData.json"));
        userId=obj.get("userId").toString();
        password=obj.get("password").toString();
        sessionMap=new HashMap<>();
        booksApi = new BooksApi();
    }

     @Test(description = "add books using pojo")
    public void addBookToProfile(){
         booksApi.deleteBooks();
         String books=booksApi.getBooksInStore();        //get all books in book store
//         String request=helper.fileReader("/src/test/resources/TestDataJSON/AddBook.json"); //add book payload
         JsonPath jObj=JsonPath.from(books);
         sessionMap.put("isbn",jObj.getString("books[0].isbn"));
         addbook.setUserId(userId);
         book.setIsbn(sessionMap.get("isbn").toString());
         addbook.setCollectionOfIsbns(Arrays.asList(book));
         JSONObject jsonObj=new JSONObject(addbook);
         Response res=booksApi.addBook(jsonObj.toString());
         String isbn=JsonPath.from(res.asString()).get("books[0].isbn");
         Assert.assertEquals(isbn,sessionMap.get("isbn"));
     }


}
