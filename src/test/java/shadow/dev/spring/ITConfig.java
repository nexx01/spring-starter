//package shadow.dev.spring;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManagerFactory;
//
//@Configuration
//public class ITConfig {
//
////    @Bean
////    public IDAO<FuelStation> getFSService() {
////        return new FSService();
////    }
//
//    @Bean
//    public LocalEntityManagerFactoryBean emfBean() {
//        LocalEntityManagerFactoryBean e = new LocalEntityManagerFactoryBean();
//        e.setPersistenceUnitName("org.superbapps.db_OWSDB_PU");
//
//        return e;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory em) {
//        return new JpaTransactionManager(em);
//    }
//
//}
