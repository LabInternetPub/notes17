package cat.tecnocampus.configuration.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

	//Configure Spring security's filter chain
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    //Configure how requests are secured by interceptors
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
                // order matters. First the most specific, last anyRequest()
        		.antMatchers("/static/**").permitAll()
        		.antMatchers("/h2-console/**").permitAll()
        		.mvcMatchers("/createUser").permitAll()
        		.mvcMatchers("/users").hasRole("USER")
        		.mvcMatchers("/users/{userId}").access("authentication.name == #userId") //cannot use principal.username because when user is not logged in principal does not exist
				.mvcMatchers("/users/{userId}/createNote").access("@webSecurity.checkUserId(authentication,#userId)  and isFullyAuthenticated()")
				.antMatchers("/byebye**").permitAll()
				.antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin() //a login form is showed when no authenticated request
        		.and()
			.httpBasic()
				.and()
            .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("notes")
                .and()
        	.logout()
            	.logoutSuccessUrl("/byebye.html"); //where to go when logout is successful
            //.logoutUrl("logoutpage") // logout page

        //Required to allow h2-console work
        http
        	.csrf().disable()
        	.headers()
        		.frameOptions().disable();
    }
}
