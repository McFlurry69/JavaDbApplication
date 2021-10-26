package Repository;

import Models.PersonalInfo;
import Models.Vehicle;

public abstract class IPersonalInfoRepository extends IGeneralRepositoryCommonImplementation<PersonalInfo> {
    public IPersonalInfoRepository(Class<PersonalInfo> typeParameterClass) {
        super(PersonalInfo.class);
    }
}
