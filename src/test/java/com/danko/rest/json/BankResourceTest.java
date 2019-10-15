package com.danko.rest.json;

import com.danko.rest.Services.BankService;
import com.danko.rest.beans.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.json.*;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class BankResourceTest {

    @Inject
    BankService service;

    @Test
    public void testGetAccountsEndpoint() {
        given().when().get("/bank/getAll").then().statusCode(200);
    }

    @Test
    public void testAddNewAccount() {
        String uuidName = UUID.randomUUID().toString();
        String uuidPhone = UUID.randomUUID().toString();
        String uuidPassport = UUID.randomUUID().toString();
        int uuidMoney = UUID.randomUUID().hashCode();


        Map<Integer, Account> map = given().contentType("application/json").pathParam("clientName", uuidName).pathParam("clientPhoneNumber", uuidPhone)
                .pathParam("clientPassportNumber", uuidPassport).pathParam("clientMoneyNumber", uuidMoney)
                .when().post("bank/add/{clientName}/{clientPhoneNumber}/{clientPassportNumber}/{clientMoneyNumber}").as(LinkedHashMap.class);

        boolean k = false;
        if (map.toString().contains(uuidName) && map.toString().contains(uuidPhone) && map.toString().contains(uuidPassport)){
            k = true;
        }
        System.out.println(map.toString());
        assertThat(k, equalTo(true));



    }

    @Test
    public void testDeleteAccountEndpoint() {
        String testName = "Danko";
        String testPassport = "123";
        Map<Integer, Account> map = given().contentType("application/json").pathParam("clientName", testName).pathParam("clientPassportNumber", testPassport)
                .when().delete("bank/delete/{clientName}/{clientPassportNumber}").as(Map.class);
        boolean k = false;
        if (!(map.toString().contains(testName) && map.toString().contains(testPassport))){
            k = true;
        }
        assertThat(k, equalTo(true));
    }


    @Test
    public void testAddMoneyToClientEndpoint() {
        String testName = "First";
        String testPassport = "first1";
        int money = 100500;


        Response response = given().contentType("application/json").pathParam("clientName", testName).pathParam("clientPassportNumber", testPassport)
               .pathParam("money", money).when().post("bank/addMoney/{clientName}/{clientPassportNumber}/{money}");

        JSONObject json = new JSONObject(response.getBody().asString());

        System.out.println(json);
    }

    @Test
    public void testRemoveMoneyFromClientEndpoint() {
        String testName = "Danko";
        String testPassport = "123";
        int money = 10;

        given().contentType("application/json").pathParam("clientName", testName).pathParam("clientPassportNumber", testPassport)
                .pathParam("money", money).when().post("bank/removeMoney/{clientName}/{clientPassportNumber}/{money}");
    }

    @Test
    public void testAddMoneyFromFirstToLastEndpoint() {
        String testName1 = "Danko";
        String testPassport1 = "123";
        String testName2 = "First";
        String testPassport2 = "first1";
        int money = 10;

        given().pathParam("firstClientName", testName2).pathParam("firstClientPassportNumber", testPassport2)
                .pathParam("secondClientName", testName1).pathParam("secondClientPassportNumber", testPassport1)
                .pathParam("money", money).when().post("bank/fromFirstToLast/{firstClientName}/{firstClientPassportNumber}/{secondClientName}/{secondClientPassportNumber}/{money}");
    }


}
