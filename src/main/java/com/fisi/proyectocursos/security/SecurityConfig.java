//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public FilterRegistrationBean<SecurityFilter> securityFilterRegistrationBean() {
//        FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new SecurityFilter());
//        registrationBean.addUrlPatterns("/*"); // Define aquí las URL que deseas que pasen por el filtro
//        registrationBean.setOrder(1); // Orden de ejecución del filtro
//        return registrationBean;
//    }
//}
