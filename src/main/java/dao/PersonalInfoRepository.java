package dao;

import model.PersonalInfo;

public abstract class PersonalInfoRepository extends GeneralRepositoryImplementation<PersonalInfo> {
    public PersonalInfoRepository(Class<PersonalInfo> typeParameterClass) {
        super(PersonalInfo.class);
    }
}
