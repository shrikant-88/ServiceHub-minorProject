package __SpringMVC.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import __SpringMVC.bean.ServiceDetails;
import __SpringMVC.bean.User;
import __SpringMVC.repository.ServiceRepository;
import __SpringMVC.service.ServicehubService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/servicehub")
public class ServiceHubController {
	
	@Autowired
	ServicehubService service;
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping(value = "/register")
	public String getRegisterPage(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		return "servicehub/register";
	}
	
	@PostMapping(value="/saveUser")
	public String RegisterUser(@ModelAttribute User user) {
		service.saveUser(user);
		return "redirect:login";
	}
	
	@GetMapping(value = "/login")
	public String getLoginPage(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		return "servicehub/login";
	}
	
	@PostMapping(value = "/save")
	public String saveForm(@ModelAttribute User user) {
	    User tempUser = service.getUserIdAndPassword(user.getUserId(), user.getPassword());

	    if (tempUser != null && tempUser.getUserId() != null) {
	    	session.setAttribute("loggedIn", true);
	        session.setAttribute("userName", tempUser.getName());
	        session.setAttribute("userId", tempUser.getUserId());
	        System.out.println("loggedin success");
	        return "redirect:getHome";
	    } else {
	    	System.out.println("loggedin faild");
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "/getHome")
	public String getHomePage(Model model) {
		
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        model.addAttribute("userName", session.getAttribute("userName"));
	        return "servicehub/home";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "/aboutUs")
	public String getAboutUs() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/about";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "Services/Ac-Services")
	public String getAcService() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/acService";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	@GetMapping(value = "Services/Car-Services")
	public String getCarService() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/carService";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "Services/Home-repair-Services")
	public String getHomeRepairService() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/homeRepairService";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "Services/Painter-Services")
	public String getPainterService() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/painterService";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "Services/Electrician-Services")
	public String getElectricianService() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/electricianService";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "Services/Plumber-Services")
	public String getPlumberService() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/plumberService";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping("/getService")
    public String showServiceAddressForm(@RequestParam(value = "serviceType", required = false) String serviceType, Model model) {
		
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	    	ServiceDetails serviceDetails = new ServiceDetails();
	        serviceDetails.setServiceType(serviceType); // Set serviceType if provided
	        model.addAttribute("serviceDetails", serviceDetails);
	        return "servicehub/service_form";
	    } else {
	        return "servicehub/userNotFound";
	    }
    }
	
	@Autowired
	ServiceRepository serviceRepo;
	
	@PostMapping(value="/save-service-address")
	public String RegisterUser(@ModelAttribute ServiceDetails serviceDetails) {
		
		long millis = System.currentTimeMillis();  
        // Create a new Date object using the current time in milliseconds  
        Date date = new Date(millis);  
   
		String userId = (String)session.getAttribute("userId");
		serviceDetails.setUser(service.findByUserId(userId));
		serviceDetails.setDateAndTime(date.toString());
		serviceRepo.save(serviceDetails);
		
//		User user = service.findByUserId(userId);
//		List<ServiceDetails> l = user.getServiceAddresses();
//		for(ServiceDetails sd : l) {
//			System.out.println("Service Type :: "+sd.getServiceType());
//			System.out.println("Service Address :: "+sd.getAddress());
//			System.out.println("Service order DateAndTime ::" + sd.getDateAndTime());
//		}
		
		
		return "servicehub/thankYou";
	}
	
	@GetMapping(value = "/your-requests")
	public String getRequests(Model model) {
		
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	    	String userId = (String)session.getAttribute("userId");
			User user = service.findByUserId(userId);
			List<ServiceDetails> l = user.getServiceAddresses();
			for(ServiceDetails sd : l) {
				System.out.println("Service Type :: "+sd.getServiceType());
				System.out.println("Service Address :: "+sd.getAddress());
				System.out.println("Service order DateAndTime ::" + sd.getDateAndTime());
			}
			
			model.addAttribute("requestList",l);
			
			return "servicehub/requests";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	@GetMapping(value = "/Contact")
	public String getContact() {
		Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (Boolean.TRUE.equals(loggedIn)) {
	        return "servicehub/Contact";
	    } else {
	        return "servicehub/userNotFound";
	    }
	}
	
	

	
	

	
}
