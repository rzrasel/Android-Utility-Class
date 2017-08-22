/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbapi;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

/**
 *
 * @author developer
 */
public class FBAPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String accessToken = "EAACEdEose0cBACC0ADb6TT6ufg7xIsBZCwVSSRCIJ0vcH6nXDHNFIdO7RMr0YnuEKKGnfDMHgM9DCwyUFsa6NdbQsrbeUZCzsZCYk55v3janvxRSGoFL4dJRoK2lg5LIsP6V3GYPncCwFMO2VvbVZCZAGHxi7EPo3ZAezfOxExYHCQlfNsxih3EHoliHHZCvIvZApy9C5bWKrQZDZD";
        FacebookClient facebookClient;
        facebookClient = new DefaultFacebookClient(accessToken);
        User me = facebookClient.fetchObject("me", User.class);
        System.out.println(me.getName());
        System.out.println(me.getLanguages());
        System.out.println(me.getEmail());
        System.out.println(me.getAbout());
    }
    
}
