package core.ws;

import java.io.IOException;
import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import exceptions.ClosedConnectionStatementCreationException;
import exceptions.ConnectionCloseException;
import exceptions.FailedToCreateCouponException;
import facades.CompanyFacade;
import facades.CustomerFacade;
import objects.ClientType;
import objects.Coupon;
import objects.CouponType;
import system.CouponSystem;

@Path("company")
public class CompanyService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse responce;

	@POST
	@Path("login/{user}/{pass}")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@PathParam("user") String user, @PathParam("pass") String pass) {

		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}
String str=null;
		CompanyFacade facade;
		try {
			CouponSystem sys = CouponSystem.getInstance();
			facade = (CompanyFacade) sys.login(user, pass, ClientType.company);
			if(facade!=null){
				str="success";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("facade", facade);
		session.setAttribute("user", user);
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
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		try {
			coupons = facade.getAllCoupons();
		} catch (Exception e) {
			return null;
		}
		return coupons;
	}

	@POST
	@Path("createCoupon/{amount}/{startdate}/{enddate}/{message}/{title}/{type}/{price}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCoupon(@PathParam("amount") int amount, @PathParam("startdate") String startdate,
			@PathParam("enddate") String enddate, @PathParam("message") String message,
			@PathParam("title") String title, @PathParam("type") CouponType type, @PathParam("price") double price)
					throws ConnectionCloseException, ClosedConnectionStatementCreationException,
					FailedToCreateCouponException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		Coupon coupon = new Coupon();
		coupon.setAmount(amount);
		coupon.setEndDate(java.sql.Date.valueOf(enddate));
		coupon.setMessage(message);
		coupon.setStartDate(java.sql.Date.valueOf(startdate));
		coupon.setPrice(price);
		coupon.setType(type);
		coupon.setTitle(title);
		// ObjectMapper mapper = new ObjectMapper();
		// try {
		// coupon = mapper.readValue(jsonCoupon, Coupon.class);
		// } catch (IOException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		facade.createCoupon(coupon);
		return "created";
	}

	@PUT
	@Path("updateCoupon/{coupon}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCoupon(@PathParam("coupon") String jsonCoupon) {
		HttpSession session = request.getSession(false);

		if (session == null) {

			request.getSession(false).invalidate();

		}

		try {
			CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
			Coupon coupon = null;
			ObjectMapper mapper = new ObjectMapper();
			coupon = mapper.readValue(jsonCoupon, Coupon.class);
			facade.updateCoupon(coupon);
		} catch (Exception e) {
			return "update Failed " + e.getMessage();
		}
		return "Coupon was updated successfuly.";
	}

	@DELETE
	@Path("removeCoupon/{coupon}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCoupon(@PathParam("coupon") String jsonCoupon) {
		HttpSession session = request.getSession(false);

		if (session == null) {

			request.getSession(false).invalidate();

		}

		try {
			CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
			Coupon coupon = null;
			ObjectMapper mapper = new ObjectMapper();
			coupon = mapper.readValue(jsonCoupon, Coupon.class);
			facade.removeCoupon(coupon);
		} catch (Exception e) {
			return "remove Failed " + e.getMessage();
		}
		return "Coupon was removed successfuly.";
	}

	@GET
	@Path("ByType/{type}") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@PathParam("type") CouponType type) {

		Collection<Coupon> coupons;
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getSession(false).invalidate();
		}

		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		try {
			coupons = facade.getCouponByType(type);
		} catch (Exception e) {
			return null;
		}
		return coupons;
	}

	@GET
	@Path("ById/{id}") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCouponsById(@PathParam("id") long id) {

		Coupon coupon;
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getSession(false).invalidate();
		}

		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		try {
			coupon = facade.getCoupon((int) id);
		} catch (Exception e) {
			return null;
		}
		return coupon;
	}

	@GET
	@Path("Bydate/{date}") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByDate(@PathParam("date") String date) {

		Collection<Coupon> coupons;
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getSession(false).invalidate();
		}

		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		try {
			coupons = facade.getCouponTillDate(date);
		} catch (Exception e) {
			return null;
		}
		return coupons;
	}
}
