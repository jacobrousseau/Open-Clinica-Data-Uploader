package nl.thehyve.ocdu.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by piotrzakrzewski on 18/04/16.
 */
@Entity
public class OcUser extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(targetEntity = UploadSession.class)
    private List uploadSessions;
    private String ocEnvironment;

    public OcUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public OcUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                  String ocEnvironment) {
        super(username, password, authorities);
        this.ocEnvironment = ocEnvironment;
    }

    public OcUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List getUploadSessions() {
        return uploadSessions;
    }

    public void setUploadSessions(List uploadSessions) {
        this.uploadSessions = uploadSessions;
    }

    public String getOcEnvironment() {
        return ocEnvironment;
    }

    public void setOcEnvironment(String ocEnvironment) {
        this.ocEnvironment = ocEnvironment;
    }
}
