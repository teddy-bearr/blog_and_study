package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diagnosis extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "diagnosis_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiagnosisStatus status;

    @OneToOne(mappedBy = "diagnosis", fetch = LAZY)
    private Pet pet;

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
