package Repository;

import Models.PersonalInfo;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalInfoMapper implements RowMapper<PersonalInfo> {
    public PersonalInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(rs.getInt("id"));
        personalInfo.setEmail(rs.getString("email"));
        personalInfo.setAddress(rs.getString("address"));
        personalInfo.setPhoneNumber(rs.getString("phoneNumber"));
        return personalInfo;
    }

}


