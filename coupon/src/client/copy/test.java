package client.copy;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.*;

import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import objects.ClientType;
import objects.Company;
import system.CouponSystem;

public class test {

	
	public static void main(String[] args) throws SQLException {
		ClientConfig config = new DefaultClientConfig(); 
        Client client = Client.create(config); 
    	
		
			
       //////////////////////
       URI baseUri = UriBuilder.fromUri("http://localhost:8080/coupon/rest/customer").build();
        WebResource service = client.resource(baseUri);
        
        
       //System.out.println(service.path("DeleteCompany").path("14").accept(MediaType.APPLICATION_JSON).delete(String.class));
        //System.out.println(service.path("getAllCompanies").accept(MediaType.APPLICATION_JSON).get(String.class));
        //System.out.println(service.path("AddCompany").path("shayfasss").path("shay@gmail.com").path("1987").accept(MediaType.APPLICATION_JSON).get(String.class));
         //System.out.println(service.path("GetCompany").path("2").accept(MediaType.APPLICATION_JSON).get(String.class));
        //System.out.println(service.path("UpdateCompany").path("11").path("shayfassshay").path("shayshay").accept(MediaType.TEXT_PLAIN).put(String.class));
        
       // System.out.println(service.path("DeleteCustomer").path("1").accept(MediaType.APPLICATION_JSON).delete(String.class));
        // System.out.println(service.path("getAllCustomers").accept(MediaType.APPLICATION_JSON).get(String.class));
         //System.out.println(service.path("AddCustomer").path("shayfasss").path("1987").accept(MediaType.APPLICATION_JSON).post(String.class));
         //System.out.println(service.path("GetCustomer").path("9").accept(MediaType.APPLICATION_JSON).get(String.class));
        //System.out.println(service.path("UpdateCustomer").path("9").path("shayfassshay").accept(MediaType.TEXT_PLAIN).put(String.class));
        
        CompanyFacade c=new CompanyFacade();
    // System.out.println(service.path("login").path("fass").path("1987").accept(MediaType.TEXT_PLAIN).post(String.class));
    // System.out.println(service.path("getAllCoupons").accept(MediaType.APPLICATION_JSON).get(String.class));
    
       
      
      
      
       
    
      
	}

}
