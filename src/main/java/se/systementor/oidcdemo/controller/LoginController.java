package se.systementor.oidcdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.systementor.oidcdemo.viewmodel.LoginProviderViewModel;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class LoginController {

    private static final String authorizationRequestBaseUri
            = "oauth2/authorization";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {

        List<LoginProviderViewModel> providers = new ArrayList<>();

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->{
            providers.add( new LoginProviderViewModel( registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId(),
                    getImageFor( registration.getClientName())
            ));
        }) ;
        model.addAttribute("providers", providers);

        return "oauth_login";
    }


    private String getImageFor(String clientName) {
        if(clientName.equalsIgnoreCase("google")) return  "fa-brands fa-google";
        if(clientName.equalsIgnoreCase("github")) return  "fa-brands fa-github";
        return "fa-brands fa-question";
    }


}
