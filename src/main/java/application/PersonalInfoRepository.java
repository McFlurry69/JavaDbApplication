package application;

import domain.PersonalInfo;
import infrastructure.Repository.PSQL.PSQLGeneralRepository;

public abstract class PersonalInfoRepository extends PSQLGeneralRepository<PersonalInfo> {
    public PersonalInfoRepository(Class<PersonalInfo> typeParameterClass) {
        super(PersonalInfo.class);
    }
}
