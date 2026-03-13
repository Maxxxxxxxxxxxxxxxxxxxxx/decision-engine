package task.decisionengine.domain.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String userCode;
    private Double creditModifier;
    private Credit credit;
}
