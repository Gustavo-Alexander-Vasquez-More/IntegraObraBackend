package com.integraobra.integraApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "counters")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Counter {
    @Id
    private Long id;
    private Long sequence;

    public Counter(Long sequence) {
        this.sequence = sequence;
    }
}
