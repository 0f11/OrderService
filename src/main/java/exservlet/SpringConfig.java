package exservlet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final static String USER = "rommiparman";
    private final static String PASSWORD = "7e19";
    private final static String URL ="jdbc:postgresql://db.mkalmo.xyz/rommiparman";
    private final static String DRIVER = "org.postgresql.Driver";
    private final static String INIT_DB = "schema.sql";

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaxTotal(2);

        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        var intDb = new ResourceDatabasePopulator(new ClassPathResource(INIT_DB));
        DatabasePopulatorUtils.execute(intDb,dataSource);

        return new JdbcTemplate(dataSource);
    }
}
