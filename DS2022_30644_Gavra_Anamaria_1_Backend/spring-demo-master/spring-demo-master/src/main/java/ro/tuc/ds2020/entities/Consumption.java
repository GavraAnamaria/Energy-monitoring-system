package ro.tuc.ds2020.entities;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "value", nullable = false)
    private Float value;

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;

    public Consumption(LocalDateTime time, Float value, Device d1) {
        this.time=time;
        this.value = value;
        this.device = d1;
    }
}