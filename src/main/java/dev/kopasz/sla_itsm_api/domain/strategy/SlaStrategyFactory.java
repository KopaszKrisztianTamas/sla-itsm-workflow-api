package dev.kopasz.sla_itsm_api.domain.strategy;

import dev.kopasz.sla_itsm_api.domain.model.Priority;
import org.springframework.stereotype.Component;

@Component
public class SlaStrategyFactory {

    private StandardSlaStrategy standardSlaStrategy;
    private CriticalSlaStrategy criticalSlaStrategy;

    public SlaStrategyFactory(StandardSlaStrategy standardSlaStrategy, CriticalSlaStrategy criticalSlaStrategy) {
        this.standardSlaStrategy = standardSlaStrategy;
        this.criticalSlaStrategy = criticalSlaStrategy;
    }

    public SlaCalculationStrategy getStrategy(Priority priority) {
        // Java 21 exhaustive switch:
        return switch (priority) {
            case CRITICAL -> criticalSlaStrategy;
            case LOW,NORMAL,HIGH -> standardSlaStrategy;
        };
    }

}
