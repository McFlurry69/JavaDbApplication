package Models;

public class User {
    private Integer id;
    private String Name;
    private Integer Age;
    private Integer PersonalInfoId;
    private PersonalInfo PersonalInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public Integer getPersonalInfoId() {
        return PersonalInfoId;
    }

    public void setPersonalInfoId(Integer personalInfoId) {
        PersonalInfoId = personalInfoId;
    }

    public Models.PersonalInfo getPersonalInfo() {
        return PersonalInfo;
    }

    public void setPersonalInfo(Models.PersonalInfo personalInfo) {
        PersonalInfo = personalInfo;
    }
}
