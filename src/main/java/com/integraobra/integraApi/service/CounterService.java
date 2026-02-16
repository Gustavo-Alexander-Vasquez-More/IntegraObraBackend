package com.integraobra.integraApi.service;

import com.integraobra.integraApi.Exceptions.BadRequestException;
import com.integraobra.integraApi.model.Counter;
import com.integraobra.integraApi.repository.CounterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    public final CounterRepository counterRepository;

    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    //Servicio para crear el contador inicial
    // Nota: Es mejor hacer esto privado o usar un mecanismo de inicialización
    // al arrancar la app para evitar múltiples llamadas en concurrencia.
    private void createCounter() {
        Long initialId = 1L;
        Long initialSequence = 241315L;
        Counter counter = new Counter(initialId, initialSequence);
        counterRepository.save(counter);
    }

    // Servicio para obtener el siguiente número de secuencia de manera segura
    @Transactional
    public Long getNextSequence() {
        Long counterId = 1L;

        // 1. Verificamos que el contador exista, si no existe lo creamos.
        // Ojo: En producción de muy alta carga, esto debería ser manejado por un script
        // de migración o al inicio de la aplicación para evitar race conditions aquí.
        if (!counterRepository.existsById(counterId)){
            createCounter();
        }

        // 2. Incrementamos el contador de forma atómica en la BD
        counterRepository.incrementSequence(counterId);

        // 3. Obtenemos el nuevo valor actualizado
        return counterRepository.findById(counterId)
                .orElseThrow(() -> new BadRequestException("Error fatal: El contador no existe"))
                .getSequence();
    }
}
