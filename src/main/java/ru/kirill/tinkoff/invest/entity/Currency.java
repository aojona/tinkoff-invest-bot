package ru.kirill.tinkoff.invest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency")
@EqualsAndHashCode(callSuper = false)
public class Currency extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String figi;

    @NotBlank
    private String name;

    @NotBlank
    private String abbreviation;

    @NotNull
    private long nominal;

    @NotNull
    private BigDecimal price;

    @ManyToMany(mappedBy = "currencies")
    private List<Subscriber> subscribers;
}
