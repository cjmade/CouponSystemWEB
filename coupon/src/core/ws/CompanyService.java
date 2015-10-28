package core.ws;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

	@GET
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("user") String user, @QueryParam("pass") String pass) {

		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}

		CompanyFacade facade;
		try {
			CouponSystem sys = CouponSystem.getInstance();
			facade = (CompanyFacade) sys.login(user, pass, ClientType.company);
		} catch (Exception e) {
			return e.getMessage();
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("facade", facade);
		session.setAttribute("user", user);
		return "login success";
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
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createCoupon(String jsonCoupon)
			throws ConnectionCloseException, ClosedConnectionStatementCreationException, FailedToCreateCouponException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		CompanyFacade facade = (CompanyFacade) session.getAttribute("facade");
		Coupon coupon = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			coupon = mapper.readValue(jsonCoupon, Coupon.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facade.createCoupon(coupon);
		return "created";
	}
	@GET
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCoupon(@QueryParam("coupon") String jsonCoupon) {
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
	@GET
	@Path("removeCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCoupon(@QueryParam("coupon") String jsonCoupon) {
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
	@Path("ByType") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@QueryParam("type") CouponType type) {

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
	@Path("ById") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCouponsById(@QueryParam("id") long id) {

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
	@Path("Bydate") // JSon Coupon
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByDate(@QueryParam("type") String date) {

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
