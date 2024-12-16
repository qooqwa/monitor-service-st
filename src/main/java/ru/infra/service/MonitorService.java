package ru.infra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.infra.api.monitor.exception.DuplicateRegisterAgent;
import ru.infra.api.monitor.mapper.AgentInfoMapper;
import ru.infra.api.monitor.model.api.RegisterAgent;
import ru.infra.api.monitor.model.api.RegisterAgentRelease;
import ru.infra.api.monitor.model.api.RegisterPluginRelease;
import ru.infra.api.monitor.model.dto.AgentInfo;
import ru.infra.api.monitor.model.dto.AgentRelease;
import ru.infra.api.monitor.model.dto.PluginRelease;
import ru.infra.model.entity.Agent;
import ru.infra.model.entity.AgentReleaseEntity;
import ru.infra.model.entity.PluginReleaseEntity;
import ru.infra.repository.AgentReleaseRepository;
import ru.infra.repository.AgentRepository;
import ru.infra.repository.PluginReleaseRepository;

import java.util.UUID;

@Component
public class MonitorService {


    private final AgentInfoMapper agentInfoMapper;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    PluginReleaseRepository pluginReleaseRepository;

    @Autowired
    AgentReleaseRepository agentReleaseRepository;

    @Autowired
    public MonitorService(AgentInfoMapper agentInfoMapper) {
        this.agentInfoMapper = agentInfoMapper;
    }

    public AgentInfo regAgent(RegisterAgent registerAgent) {
        String regAgentNick = registerAgent.getAgentNick();
        if (regAgentNick != null) {
            if (agentRepository.existsByAgentNick(regAgentNick)) {
                UUID agentId = agentRepository.findByAgentNick(regAgentNick).getAgentId();
                if (!registerAgent.getAgentId().equals(agentId)) {
                    throw new DuplicateRegisterAgent("Попытка регистрации дубликата агента [" + regAgentNick + "]. Ранее зарегистрирован agentId [" + agentId + "], попытка регистрации с agentId [" + registerAgent.getAgentId() + "].");
                } else {
                    updateAgent(registerAgent);
                }
            } else {
                updateAgent(registerAgent);
            }
            return agentInfoMapper.toAgentInfo(registerAgent);
        } else {
            throw new IllegalArgumentException("Не передан ник инстанса агента.");
        }
    }

    public void updateAgent(RegisterAgent registerAgent) {
        Agent agent = new Agent();
        agent.setAgentNick(registerAgent.getAgentNick());
        agent.setAgentId(registerAgent.getAgentId());
        agent.setAgentNote(registerAgent.getAgentNote());
        agent.setGroupNick(registerAgent.getGroupNick());
        agent.setDependencyTags(registerAgent.getDependencyTags());
        agent.setPluginList(registerAgent.getPluginList());
        agentRepository.save(agent);
    }

    public static int compareVersions(String version1, String version2) {
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");

        int maxLength = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < maxLength; i++) {
            int v1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int v2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            if (v1 < v2) {
                return -1;
            } else if (v1 > v2) {
                return 1;
            }
        }

        return 0;
    }

    public PluginRelease regPlugin(RegisterPluginRelease registerPluginRelease) {
        String pluginNick = registerPluginRelease.getPluginNick();
        if (pluginNick != null) {
            if (pluginReleaseRepository.existsByPluginNick(pluginNick)) {
                String inner_plugin_version = pluginReleaseRepository.findByPluginNick(pluginNick).getVersion();
                if (compareVersions(inner_plugin_version, registerPluginRelease.getVersion()) == -1) {
                    updatePluginRelease(registerPluginRelease);
                }
            }
            else {
                updatePluginRelease(registerPluginRelease);
            }
            PluginRelease pluginRelease = new PluginRelease();
            pluginRelease.setPluginNick(registerPluginRelease.getPluginNick());
            pluginRelease.setPluginType(registerPluginRelease.getPluginType());
            pluginRelease.setVersion(registerPluginRelease.getVersion());
            pluginRelease.setPluginName(registerPluginRelease.getPluginName());
            pluginRelease.setStorageId(registerPluginRelease.getStorageId());
            pluginRelease.setDependencyTags(registerPluginRelease.getDependencyTags());
            return pluginRelease;
        }
        else{
            throw new IllegalArgumentException("Не передан ник плагина.");
        }
    }

    public void updatePluginRelease(RegisterPluginRelease registerPluginRelease){
        PluginReleaseEntity plugin = new PluginReleaseEntity();
        plugin.setPluginNick(registerPluginRelease.getPluginNick());
        plugin.setPluginType(registerPluginRelease.getPluginType());
        plugin.setStorageId(registerPluginRelease.getStorageId());
        plugin.setVersion(registerPluginRelease.getVersion());
        plugin.setPluginName(registerPluginRelease.getPluginName());
        plugin.setDependencyTags(registerPluginRelease.getDependencyTags());
        pluginReleaseRepository.save(plugin);
    }

    public AgentRelease regAgentRelease(RegisterAgentRelease registerAgentRelease) {
        String agentType = registerAgentRelease.getAgentType();
        if (agentType != null) {
            if (agentReleaseRepository.existsByAgentType(agentType)) {
                String inner_plugin_version = agentReleaseRepository.findByAgentType(agentType).getVersion();
                if (compareVersions(inner_plugin_version, registerAgentRelease.getVersion()) == -1) {
                    updateAgentRelease(registerAgentRelease);
                }
            }
            else {
                updateAgentRelease(registerAgentRelease);
            }
            AgentRelease agentRelease = new AgentRelease();
            agentRelease.setAgentType(registerAgentRelease.getAgentType());
            agentRelease.setVersion(registerAgentRelease.getVersion());
            agentRelease.setReleaseNote(registerAgentRelease.getReleaseNote());
            agentRelease.setDefDependencyTags(registerAgentRelease.getDefDependencyTags());
            return agentRelease;
        }
        else{
            throw new IllegalArgumentException("Не передан тип агента.");
        }
    }
    public void updateAgentRelease(RegisterAgentRelease registerAgentRelease){
        AgentReleaseEntity agentRelease = new AgentReleaseEntity();
        agentRelease.setAgentType(registerAgentRelease.getAgentType());
        agentRelease.setReleaseNote(registerAgentRelease.getReleaseNote());
        agentRelease.setVersion(registerAgentRelease.getVersion());
        agentRelease.setDefDependencyTags(registerAgentRelease.getDefDependencyTags());
        agentRelease.setStorageId(registerAgentRelease.getStorageId());
        agentReleaseRepository.save(agentRelease);
    }

}