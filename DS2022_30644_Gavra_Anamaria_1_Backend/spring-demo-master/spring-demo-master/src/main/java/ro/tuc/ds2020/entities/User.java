package ro.tuc.ds2020.entities;

import ro.tuc.ds2020.enums.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
@Entity

@AllArgsConstructor
@Builder
@Table(name= "user_platforms")
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false, unique=true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Device> devices;



}
