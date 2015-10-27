package net.bafeimao.umbrella.support.data;

/**
 * Created by Administrator on 2015/10/28.
 */
public class HeroEntity extends DataEntity<Long> {
    private String name;
    private int age;
    private int gender;

    public HeroEntity(String name, int age, int gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
