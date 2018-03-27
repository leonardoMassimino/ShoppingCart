package com.leonardo.shoppingcart.controllers;

import com.leonardo.shoppingcart.entities.site.Cart;
import com.leonardo.shoppingcart.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public abstract class SiteController {

    protected abstract String getHeaderTitle();

    public class ResourceNotFoundException extends RuntimeException { }

    @ModelAttribute("headerTitle")
    public String headerTitle()
    {
        return getHeaderTitle();
    }

    @ModelAttribute("authenticatedUser")
    public AuthenticatedUser authenticatedUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return authenticatedUser;
    }

    public static AuthenticatedUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) return ((AuthenticatedUser) principal);
        return null;
    }

    protected Cart getOrCreateCart(HttpServletRequest request) {
        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if(cart == null){
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        return cart;
    }


}
