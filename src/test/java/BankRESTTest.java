import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mvc.Model.Account;
import mvc.Model.Transacctions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

public class BankRESTTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }

    @Test
    public void testGetAccount() {

        Account account = new Account(2222, "sebastian");

        given()
                .contentType("application/json")
                .body(account)
                .when().post("/bank").then()
                .statusCode(200);
        // test getting the contact
        given()
                .when()
                .get("/account/2222")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("accountNumber", equalTo(2222))
                .body("accountHolder", equalTo("sebastian"));
        //cleanup
        given()
                .when()
                .delete("/account/2222");
    }

    @Test
    public void testCreateAccount() {
        // add the contact
        Account account = new Account(2222, "sebastian");
        given()
                .contentType("application/json")
                .body(account)
                .when().post("/bank").then()
                .statusCode(200);
        // get the contact and verify
        given()
                .when()
                .get("account/2222")
                .then()
                .statusCode(200)
                .and()
                .body("accountNumber", equalTo(2222))
                .body("accountHolder", equalTo("sebastian"));

        //cleanup
        given()
                .when()
                .delete("account/2222");
    }

    @Test
    public void testDeposit() {

        Account account = new Account(3333, "sebastian");
        given()
                .contentType("application/json")
                .body(account)
                .when().post("/bank").then()
                .statusCode(200);

        Transacctions transacctions = new Transacctions(LocalDate.now(), 1.1, 3333);
        given()
                .contentType("application/json")
                .body(transacctions)
                .when().post("/deposit").then()
                .statusCode(200);
//         get the contact and verify

        given()
                .when()
                .get("account/3333")
                .then()
                .statusCode(200)
                .and()
                .body("balance", equalTo(1.1f));

        //cleanup
        given()
                .when()
                .delete("account/3333");
    }

    @Test
    public void testWithDraw() {

        Account account = new Account(4444, "Maria");
        given()
                .contentType("application/json")
                .body(account)
                .when().post("/bank").then()
                .statusCode(200);

        Transacctions transacctions = new Transacctions(LocalDate.now(), 10, 4444);
        given()
                .contentType("application/json")
                .body(transacctions)
                .when().post("/withdraw").then()
                .statusCode(200);
//         get the contact and verify

        given()
                .when()
                .get("account/4444")
                .then()
                .statusCode(200)
                .and()
                .body("balance", equalTo(-10f));

        //cleanup
        given()
                .when()
                .delete("account/4444");
    }
    @Test
    public void testDeleteAccount() {
        // add the contact to be deleted book
        Account account = new Account(5555, "Jorge");
        given()
                .contentType("application/json")
                .body(account)
                .when().post("/bank").then()
                .statusCode(200);

        given()
                .when()
                .delete("account/5555");

        given()
                .when()
                .get("account/5555")
                .then()
                .statusCode(404)
                .and()
                .body("errorMessage",equalTo("AccountNumb= 5555 is not available"));
    }
}
