package Repository;

import Models.PersonalInfo;

public class PersonalInfoRepository implements IGeneralRepository<PersonalInfo> {

    PersonalInfoJDBCTemplate studentJDBCTemplate = (PersonalInfoJDBCTemplate)
            context.getBean("studentJDBCTemplate");

    @Override
    public PersonalInfo getEntityById(int id) {

    }

    @Override
    public boolean deleteEntityById(int id) {
        return false;
    }

    @Override
    public PersonalInfo updateEntity(PersonalInfo entity) {
        return null;
    }

    @Override
    public int createEntity(PersonalInfo entity) {
        return 0;
    }
}
