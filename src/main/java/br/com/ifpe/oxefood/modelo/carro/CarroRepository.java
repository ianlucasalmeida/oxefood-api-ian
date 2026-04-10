package br.com.ifpe.oxefood.modelo.carro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    // Método extra para garantir que não existam placas repetidas, caso precise no Service
    boolean existsByPlaca(String placa);
}