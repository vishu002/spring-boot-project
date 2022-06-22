package com.zemoso.springbootassignment.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "record_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "issued_on")
    @NotNull(message = "field is required")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}",message = "field should be in yyyy-mm-dd pattern")
    private String issuedOn;

    @Column(name = "return_on")
    @NotNull(message = "field is required")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}",message = "field should be in yyyy-mm-dd pattern")
    private String returnOn;

    @Override
    public String toString() {
        return "RecordDetail{" +
                "id=" + id +
                ", issuedOn='" + issuedOn + '\'' +
                ", returnOn='" + returnOn + '\'' +
                '}';
    }
}
