package com.example.backend_lp3.config;

import com.example.backend_lp3.security.JwtAuthFilter;
import com.example.backend_lp3.security.JwtService;
import com.example.backend_lp3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/clientes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/cores/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/enderecos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/fornecedores/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/funcionarios/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/generos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/gerentes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/metodos-pagamento/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/pedidos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/produtos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/produtos-pedido/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/produtos-venda/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tamanhos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/vendas/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
