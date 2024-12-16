package ru.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.infra.model.entity.AgentReleaseEntity;

public interface AgentReleaseRepository extends JpaRepository<AgentReleaseEntity, String> {
    boolean existsByAgentType(String agentType);

    AgentReleaseEntity findByAgentType(String agentType);
}
