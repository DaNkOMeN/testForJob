package com.danko.rest.json;

import com.danko.rest.Services.BankService;
import com.danko.rest.beans.Account;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/bank")

public class BankResource {

    @Inject
    BankService service;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/add/{clientName}/{clientPhoneNumber}/{clientPassportNumber}/{clientMoneyNumber}")
    public Response addNewAccount(@PathParam("clientName")  String clientName,
                                              @PathParam("clientPhoneNumber")String clientPhoneNumber,
                                              @PathParam("clientPassportNumber")String clientPassportNumber,
                                              @PathParam("clientMoneyNumber")int clientMoneyNumber){
        if (service.search(clientName,clientPassportNumber) == null){
            return Response.ok(service.addAccout(clientName, clientPhoneNumber,
                    clientPassportNumber, clientMoneyNumber)).build();
        } else return Response.status(400).entity("Такой клиент в базе данных уже существует").build();

    }

    @GET
    @Produces("application/json")
    @Path("/getAll")
    public Response getAccounts(){
        return Response.ok(service.getAllAccounts()).build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/delete/{clientName}/{clientPassportNumber}")
    public Response deleteAccount(@PathParam("clientName")  String clientName,
                                              @PathParam("clientPassportNumber")String clientPassportNumber){
        if (service.search(clientName , clientPassportNumber)!= null)
            return Response.ok(service.deleteSelectAccounts(clientName ,  clientPassportNumber)).build();
            else return Response.status(400).entity("Данного клиента нет в базе данных банка").build();

    }

    @POST
    @Produces("application/json")
    @Path("/addMoney/{clientName}/{clientPassportNumber}/{money}")
    public Response addMoneyToCLient(@PathParam("clientName") String clientName,
                                     @PathParam("clientPassportNumber") String clientPassportNumber,
                                     @PathParam("money") int money){
        Integer search = service.search(clientName , clientPassportNumber);
        if (search!= null){
            return Response.ok(service.addMoney(search, money)).build();
        } return Response.status(400).entity("Данного клиента нет в базе данных банка").build();
    }

    @POST
    @Produces("application/json")
    @Path("/removeMoney/{clientName}/{clientPassportNumber}/{money}")
    public Response removeMoneyFromCLient(@PathParam("clientName") String clientName,
                                     @PathParam("clientPassportNumber") String clientPassportNumber,
                                     @PathParam("money") int money){
        Integer search = service.search(clientName, clientPassportNumber);
        if (search != null){
            if (service.removeMoney(search, money) != null) {
                return Response.ok(service.getAllAccounts()).build();
            } else return Response.status(400).entity("Денег на счете меньше, чем хотите убрать").build();
        }  return Response.status(400).entity("Данного клиента нет в базе данных банка").build();
    }

    @POST
    @Produces("application/json")
    @Path("/fromFirstToLast/{firstClientName}/{firstClientPassportNumber}/{secondClientName}/{secondClientPassportNumber}/{money}")
    public Response addMoneyFromFirstToLast(@PathParam("firstClientName") String fClientName,
                                            @PathParam("firstClientPassportNumber") String fClientPassportNumber,
                                            @PathParam("secondClientName") String sClientName,
                                            @PathParam("secondClientPassportNumber") String sClientPassportNumber,
                                            @PathParam("money") int money){
        Integer search1 = service.search(fClientName,fClientPassportNumber);
        Integer search2 = service.search(sClientName,sClientPassportNumber);
        if (search1 != null && search2 != null){
            if (service.giveMoneyFromFirstToLast(search1, search2, money) != null){
                return Response.ok(service.getAllAccounts()).build();
            } else return Response.status(400).entity("Денег на счете меньше, чем хотите передать").build();
        } return Response.status(400).entity("Как минимум, одного клиента нет в базе данных банка").build();
    }
}




