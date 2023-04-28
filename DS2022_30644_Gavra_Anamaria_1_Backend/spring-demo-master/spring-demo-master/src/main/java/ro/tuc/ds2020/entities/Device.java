package ro.tuc.ds2020.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Device implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id",updatable = true, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "description", nullable = false, unique=true)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "maxEnergy", nullable = false)
    private float maxEnergy;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private Collection<Consumption> measurements;

    public Device(String description, String address, float maxEnergy, User user){
        this.description = description;
        this.address = address;
        this.maxEnergy = maxEnergy;
        this.user=user;
    }
}