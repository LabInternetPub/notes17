package cat.tecnocampus.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Profile("security_memory")
public class InMemorySecurityConfig extends BaseSecurityConfig {

    @Bean
    public PasswordEncoder passEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        //final Properties users = new Properties();
        //users.put("user","pass,ROLE_USER,enabled"); //add whatever other user you need
        return new InMemoryUserDetailsManager();
    }


    //Configure user-details sevices
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER").and()
                .withUser("roure").password("roure").roles("USER").and()
                .withUser("admin").password("admin").roles("ADMIN").and()
                .withUser("both").password("both").roles("USER,ADMIN");

        auth.userDetailsService(inMemoryUserDetailsManager());
    }
}