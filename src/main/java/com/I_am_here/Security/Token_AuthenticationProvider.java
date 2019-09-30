package com.I_am_here.Security;

import com.I_am_here.Database.Account;
import com.I_am_here.Database.Entity.Host;
import com.I_am_here.Database.Entity.Manager;
import com.I_am_here.Database.Entity.Participator;
import com.I_am_here.Database.Repository.HostRepository;
import com.I_am_here.Database.Repository.ManagerRepository;
import com.I_am_here.Database.Repository.ParticipatorRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Service
public class Token_AuthenticationProvider implements AuthenticationProvider {

    private ManagerRepository managerRepository;
    private HostRepository hostRepository;
    private ParticipatorRepository participatorRepository;
    private TokenParser tokenParser;


    public Token_AuthenticationProvider(ManagerRepository managerRepository, HostRepository hostRepository, ParticipatorRepository participatorRepository, TokenParser tokenParser) {
        this.managerRepository = managerRepository;
        this.hostRepository = hostRepository;
        this.participatorRepository = participatorRepository;
        this.tokenParser = tokenParser;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Passed filters");
        try{
            Token_Authentication auth = (Token_Authentication)authentication;
            String token = auth.getToken();
            Account account = findAccount(token);
            if (account == null){
                auth.setAuthenticated(false);
                return auth;
            }
            if(!auth.getPassword().equals(account.getPassword())){
                auth.setAuthenticated(false);
                return auth;
            }
            if(auth.getTokenType() == TokenParser.TYPE.ACCESS && !account.getAccess_token().equals(auth.getToken())){
                auth.setAuthenticated(false);
                return auth;
            }
            if(auth.getTokenType() == TokenParser.TYPE.REFRESH && !account.getRefresh_token().equals(auth.getToken())){
                auth.setAuthenticated(false);
                return auth;
            }
            auth.setAuthenticated(true);
            return auth;

        }catch (Exception e){
            System.out.println("Cannot convert authentication to token auth");
        }
        try{
            AnonymousAuthentication auth = (AnonymousAuthentication)authentication;
            return auth;
        }catch (Exception e){
            System.out.println("Could not convert this authentication: " + authentication.toString());
            authentication.setAuthenticated(false);
            return authentication;
        }
    }



    private Account findAccount(String token){
        TokenParser.ACCOUNT type = tokenParser.getAccountType(token);
        String password = tokenParser.getPassword(token);
        String UUID = tokenParser.getUUID(token);

        if(type == TokenParser.ACCOUNT.ACCOUNT_MANAGER){
            Manager manager = managerRepository.findByUuidAndPassword(UUID, password);
            return manager;

        }else if(type == TokenParser.ACCOUNT.ACCOUNT_HOST){
            Host host  = hostRepository.findByUuidAndPassword(UUID, password);
            return host;
        }else if(type == TokenParser.ACCOUNT.ACCOUNT_PARTICIPATOR){
            Participator participator = participatorRepository.findByUuidAndPassword(UUID, password);
            return participator;
        }else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Token_Authentication.class);
    }
}
