import config.Config;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Student  implements DictionaryDao{
    private static final String GET_STREET = "SELECT street_code, street_name " +
            "FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";


    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jc_students","postgres","postgres");
        return con;
    }

    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_STREET)) {

            stmt.setString(1, "%" + pattern + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Street str = new Street(rs.getLong("street_code"),
                        rs.getString("street_name"));
                result.add(str);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return result;
    }
    public static void main(String[] args) throws Exception {
        //Class.forName("org.postgresql.Driver")
        List<Street> d = new  Student().findStreets("ул");
       for(Street s : d) {
           System.out.println(s.getStreetName());
      }


    }
}
