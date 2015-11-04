package core.ws;

import java.util.Collection;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;


import facades.CustomerFacade;
import objects.ClientType;
import objects.Coupon;
import objects.CouponType;
import system.CouponSystem;

@Path("customer")
public class CustomerService {
	@Context
	private HttpServletRequest request;
	private HttpServletResponse response;
	HttpSession session;

	@POST
	@Path("login/{user}/{pass}")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@PathParam("user") String user, @PathParam("pass") String pass) {

		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}

		CustomerFacade facade;
		try {
			CouponSystem sys = CouponSystem.getInstance();
			facade = (CustomerFacade) sys.login(user, pass, ClientType.customer);
		} catch (Exception e) {
			return e.getMessage();
		}
		session = request.getSession(true);
		session.setAttribute("facade", facade);
		session.setAttribute("user", user);
		session.setAttribute("pass", pass);
		return "success";
	}

	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons() {

		Collection<Coupon> coupons;
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		try {
			coupons = facade.getAllPurchasedCoupons();
		} catch (Exception e) {
			return null;
		}
		return coupons;
	}

	@GET
	@Path("ByPrice/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByPrice(@PathParam("price") double price) {

		Collection<Coupon> coupons;
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		try {
			coupons = facade.getAllPurchasedCouponsByPrice(price);
		} catch (Exception e) {
			return null;
		}
		return coupons;
	}

	@GET
	@Path("ByType/{type}") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByType(@PathParam("type") CouponType type) {

		Collection<Coupon> coupons;
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getSession(false).invalidate();
		}

		CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
		try {
			coupons = facade.getAllPurchasedCouponsByType(type);
		} catch (Exception e) {
			return null;
		}
		return coupons;
	}

	@PUT
	@Path("purchaseCoupon/{coupon}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String purchaseCoupon(@PathParam("coupon") String jsonCoupon) {
		HttpSession session = request.getSession(false);

		if (session == null) {

			request.getSession(false).invalidate();

		}

		
		try {
			CustomerFacade facade = (CustomerFacade) session.getAttribute("facade");
			Coupon coupon = null;
			ObjectMapper mapper = new ObjectMapper();
			coupon = mapper.readValue(jsonCoupon, Coupon.class);
			facade.purchaseCoupon(coupon);
		} catch (Exception e) {
			return "Purchase Failed " + e.getMessage();
		}
		return "Coupon was purchased successfuly.";
	}
	
}
