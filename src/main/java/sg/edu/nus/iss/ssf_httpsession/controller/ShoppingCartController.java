package sg.edu.nus.iss.ssf_httpsession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.ssf_httpsession.model.Cart;
import sg.edu.nus.iss.ssf_httpsession.model.Item;

@Controller
@RequestMapping(path = "/")
public class ShoppingCartController {
    
    @GetMapping(path = "/cart")
    public String showCart(Model model, HttpSession session){
        Cart c = (Cart) session.getAttribute("cart");
        if (c==null){
            c = new Cart();
            session.setAttribute("cart", c);
        }

        model.addAttribute("item", new Item());
        model.addAttribute("cart", c);
        return "cart";
    }

    @PostMapping
    public String postCart(Model model, HttpSession session, @Valid Item item, BindingResult result){
        Cart c = (Cart) session.getAttribute("cart");
        if (result.hasErrors()){
            model.addAttribute("item", item);
            model.addAttribute("cart", c);
            return "cart";
        }
        if (c==null){
            c=new Cart();
            session.setAttribute("cart", c);
        }
        c.addItemToCart(item);
        model.addAttribute("item", item);
        model.addAttribute("cart", c);
        return "cart";
    }

    @GetMapping(path="/checkout")
    public String checkout(Model m, HttpSession s){
        System.out.println("Checkout....");
        s.invalidate();
        Cart c = (Cart) s.getAttribute("cart");
        if (c==null){
            c = new Cart();
            s.setAttribute("cart", c);
        }
        m.addAttribute("item", new Item());
        m.addAttribute("cart", c);
        return "cart";
    }

}
