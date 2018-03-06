package datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSource1
{
    private MysqlDataSource dataSource = new MysqlDataSource();
    
    public DataSource1()
    {
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("webprojectdb");
        dataSource.setUser("root");
        dataSource.setPassword("WgkvkcW2");
    }
    
    public MysqlDataSource getDataSource()
    {
        return dataSource;
    }
}
