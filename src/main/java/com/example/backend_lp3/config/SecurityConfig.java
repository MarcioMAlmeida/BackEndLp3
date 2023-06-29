package com.example.backend_lp3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Autowired
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/clientes/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/cores/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/enderecos/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/fornecedores/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/funcionarios/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/generos/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/gerentes/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/metodos-pagamento/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/pedidos/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/produtos/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/produtos-pedido/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/produtos-venda/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tamanhos/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/vendas/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

}