package net.bafeimao.umbrella.web.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.bafeimao.umbrella.support.domain.NamedEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;

@Entity
@Table(name = "ccn_users")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends NamedEntity<Long> {
    @Length(min = 6)
    private String password;
    @NotEmpty(message = "user.email.required")
    @Email
    @XmlElement
    private String email;
    @XmlElement
    private String mobile;
    @XmlElement
    private Date createTime;
    @XmlElement
    private String avatar;

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "ctime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User that = (User) obj;
            return Objects.equal(id, that.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

//    @Override
//    public String toString() {
//        return Objects.toStringHelper(this).add("id", id).add("name", name).add("email", email).add("mobile", mobile)
//                .toString();
//    }
}
