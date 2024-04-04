package com.backend.backend_web;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import static org.assertj.core.api.Assertions.assertThat;

public class ModelMapperConfigurationTest {

    @Test
    public void modelMapperBean_ShouldBeConfiguredWithStrictMatchingStrategy() {
        // Configuración
        ModelMapperConfiguration configuration = new ModelMapperConfiguration();

        // Ejecución
        ModelMapper modelMapper = configuration.modelMapper();

        // Verificación
        assertThat(modelMapper.getConfiguration().getMatchingStrategy()).isEqualTo(MatchingStrategies.STRICT);
    }
}
