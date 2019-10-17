package exservlet;

import util.ConnectionInfo;
import util.DataSourceProvider;
import util.DbUtil;
import util.FileUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initializeDatabase();
    }

    private void initializeDatabase() {
        ConnectionInfo connectionInfo = DbUtil.loadConnectionInfo();
        DataSourceProvider.setConnectionInfo(connectionInfo);
        String sql = FileUtil.readFileFromClasspath("schema.sql");
        BasicDataSource dataSource = DataSourceProvider.getDataSource();

        try(Connection con = dataSource.getConnection();
            Statement st = con.createStatement()){
            st.executeUpdate(sql);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSourceProvider.closePool();
    }

}
