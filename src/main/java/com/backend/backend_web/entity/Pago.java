package com.backend.backend_web.entity;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE pago SET  status = 1 WHERE id=?")
public class Pago {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private long valor;
    private String banco;
    private long numCuenta;
    private Integer status = 0; // Valor predeterminado para el atributo status.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitudarriendo_id")
    private SolicitudArriendo solicitudArriendo;
    

    

}
 