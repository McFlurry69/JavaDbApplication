package DAO;

import Models.PersonalInfo;

public abstract class IPersonalInfoRepository extends IGeneralRepositoryCommonImplementation<PersonalInfo> {
    public IPersonalInfoRepository(Class<PersonalInfo> typeParameterClass) {
        super(PersonalInfo.class);
    }
}
