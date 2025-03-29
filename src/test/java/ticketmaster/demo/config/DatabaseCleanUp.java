package ticketmaster.demo.config;

import org.springframework.beans.factory.InitializingBean;

public interface DatabaseCleanUp extends InitializingBean {


    void afterPropertiesSet();

    void truncateAllEntity();
}
