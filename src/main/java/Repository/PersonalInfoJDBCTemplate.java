package Repository;

import Models.DAOClasses;
import Models.PersonalInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class PersonalInfoJDBCTemplate implements DAOClasses<PersonalInfo> {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(PersonalInfo entity) {
        String SQL = "INSERT INTO PersonalInfo (email, address, phoneNumber) VALUES (?,?,?)";

        jdbcTemplateObject.update( SQL, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber());
        return;
    }

    @Override
    public PersonalInfo getEntity(int id) {
        String SQL = "SELECT * FROM PersonalInfo WHERE id=?";
        List<PersonalInfo> personalInfo = jdbcTemplateObject.query(SQL, new PersonalInfoMapper());
        return personalInfo.stream().findFirst().get();
    }

    @Override
    public List<PersonalInfo> listEntities() {
        String SQL = "select * from Student";
        List <PersonalInfo> personalInfos = jdbcTemplateObject.query(SQL, new PersonalInfoMapper());
        return personalInfos;
    }
}
