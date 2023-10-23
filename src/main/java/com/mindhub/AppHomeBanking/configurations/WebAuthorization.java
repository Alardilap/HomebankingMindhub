package com.mindhub.AppHomeBanking.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity //habilita y configura la seguridad web en tu aplicación.
// Activa Spring Security y permite definir reglas de acceso y personalizar la seguridad
// para proteger tu aplicación
// web contra amenazas comunes.

@Configuration // le dice a Spring que la clase marcada con esta anotación contiene
// información importante sobre cómo debe funcionar tu aplicación.
// En esa clase, puedes definir cosas como:
//**Cómo crear y configurar objetos especiales llamados "beans" que la aplicación necesita.
//        **Cómo establecer ciertas reglas o configuraciones para diferentes partes de tu aplicación.
public class WebAuthorization extends WebSecurityConfigurerAdapter {


    @Override // Estoy sobreescribiendo el metodo configure que viene de WebSecurityConfigurerAdapter
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/api/clients").hasAuthority("ADMIN")
//                .antMatchers("/rest/**").hasAuthority("ADMIN")
//
////    Aquí, se configuran las reglas de autorización. Esto significa que estás definiendo qué usuarios
////    pueden acceder a diferentes partes de tu aplicación.
//                .antMatchers("/api/clients/").hasAuthority("CLIENT")
//                .antMatchers("/api/accounts/").hasAuthority("CLIENT");

        http.authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/web/Images").permitAll()
                .antMatchers("/web/Pages/login.html").permitAll()
                .antMatchers("/app/login").permitAll()
                .antMatchers("/web/**").permitAll()
                .antMatchers("/web/pages/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/accounts/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/").permitAll();

// Esto se refiere a cualquier solicitud entrante, sin importar la ruta o el método HTTP.
//                Esto deniega el acceso a cualquier solicitud que no haya sido autorizada previamente a través de reglas específicas.
        http.formLogin()

                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/app/login"); //cuando un usuario intenta acceder y no esta en nuestra bd se redirigirá a esta url o pagina
        http.logout()
                .logoutUrl("/app/logout").deleteCookies("JESSIONID");

//        http.logout().logoutUrl("/app/logout"); Esto inicia la configuración del proceso de cierre de sesión.
//        Esto establece la URL a la que los usuarios pueden acceder para cerrar sesión. En este caso, al acceder a "/app/logout", la sesión del usuario se cerrará.
        http.csrf()
                .disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers()
                .frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling()
                .authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin()
                .successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin()
                .failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }


}
