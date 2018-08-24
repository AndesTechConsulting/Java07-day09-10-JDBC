package org.andestech.learning.rfb18;

//jdbc

import java.sql.*;
import java.util.Properties;

public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {


        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://localhost/RFB18";
        Properties props = new Properties();
        props.setProperty("user","user01");
        props.setProperty("password","postgres");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);


        System.out.println(conn);

        Statement st = conn.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

        // DDL

//        st.execute("drop table t2user012");
//        st.execute("create table t2user013(id int, ch2 varchar(25), data timestamp DEFAULT now())");

        PreparedStatement pState = conn.prepareStatement(
                "select * from t2 where id > ? order by data");

        // "select * from t2 where id >" + id + " order by data");

        pState.setInt(1,70000);
        ResultSet rs2 = pState.executeQuery();


        String query = "select * from t2 order by data";

        String updateQuery = "update t2 set data=lower(data) where data like 'W%' ";
        int NRows = st.executeUpdate(updateQuery);

        System.out.println("update N: " + NRows );

        ResultSet rs = st.executeQuery(query);

        // create table

        while(rs.next())
        {
            int index = rs.getInt("id");
            String data =  rs.getString("data");

            System.out.printf("%8d | %-10s\n",index,data);

        }
        System.out.println("-----------------------------------------------------------");

        while(rs2.next())
        {
            int index = rs2.getInt("id");
            String data =  rs2.getString("data");

            System.out.printf("%8d | %-10s\n",index,data);

        }

        rs.close();
        rs2.close();

        pState.close();
        st.close();

        conn.close();

    }
}
