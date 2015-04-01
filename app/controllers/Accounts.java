package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Accounts extends Controller {

    public static void index() {
        render();
    }
    
    public static void signup() {
    	render();
    }

    public static void login()
    {
    	render();
    }
    
    public static void register(String name, String email, String password) {
    	User user = new User(name, email, password);
    	user.save();
    	index();
    }
 
    public static User findByEmail(String email)
    {
    	return User.find("byEmail", email).first();
    }
    
    public static void authenticate(String email, String password)
    {
    	User user = findByEmail(email);
    	if((user != null) && (user.checkPassword(password))) {
    		Home.index();
    	}
    	else {
    		index();
    	}
    }
}