package ru.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.infra.model.entity.Agent;

import java.util.UUID;

public interface AgentRepository extends JpaRepository<Agent, UUID> {
    boolean existsByAgentNick(String agentNick);

    Agent findByAgentNick(String agentNick);
}
