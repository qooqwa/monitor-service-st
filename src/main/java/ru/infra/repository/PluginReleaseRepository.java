package ru.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.infra.model.entity.PluginReleaseEntity;

public interface PluginReleaseRepository extends JpaRepository<PluginReleaseEntity, String> {
    boolean existsByPluginNick(String pluginNick);

    PluginReleaseEntity findByPluginNick(String pluginNick);
}
