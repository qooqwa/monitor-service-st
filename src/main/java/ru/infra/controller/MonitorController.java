package ru.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.infra.api.error.ApiError;
import ru.infra.api.monitor.model.api.*;
import ru.infra.model.api.SetOfWorkParameters;
import ru.infra.service.MonitorService;
import ru.infra.api.monitor.controller.MonitorControllerApi;
import ru.infra.api.monitor.exception.DuplicateRegisterAgent;
import ru.infra.lib.error.JavaError;


@RestController
public class MonitorController extends MonitorControllerApi {

    final private MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping(value = "/monitor/api/regAgent")
    @Override
    public ResponseEntity<RespAgentInfo> regAgent(RegisterAgent registerAgent) {
        try {
            return new ResponseEntity<>(RespAgentInfo.success(monitorService.regAgent(registerAgent)), HttpStatus.OK);
        } catch (DuplicateRegisterAgent | IllegalArgumentException e) {
            return new ResponseEntity<>(RespAgentInfo.fail(JavaError.fromException(e)), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/monitor/api/ready", produces = "application/json")
    public ResponseEntity<SetOfWorkParameters> agentReady(@RequestParam("agentNick") String agentNick, @RequestParam("agentId") String agentId) {
        return new ResponseEntity<>(new SetOfWorkParameters("SNAPSHOT", "1"), HttpStatus.OK);
    }

    @PostMapping(value = "/monitor/api/regPluginRelease")
    @Override
    public ResponseEntity<RespPluginRelease> regPluginRelease(RegisterPluginRelease registerPluginRelease) {
        try {
            return new ResponseEntity<>(RespPluginRelease.success(monitorService.regPlugin(registerPluginRelease)), HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(RespPluginRelease.fail(JavaError.fromException(e)), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/monitor/api/regAgentRelease")
    @Override
    public ResponseEntity<RespAgentRelease> regAgentRelease(RegisterAgentRelease registerAgentRelease) {
        try {
            return new ResponseEntity<>(RespAgentRelease.success(monitorService.regAgentRelease(registerAgentRelease)), HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(RespAgentRelease.fail(JavaError.fromException(e)), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/infra-monitor/api/getAgentRegistry")
//    @Override
//    public ResponseEntity<RespAgentRegistry> getAgentRegistry()
//    {
//
//    }
//    @GetMapping(value = "/monitor/api/getPluginList", produces = "application/json")
//    public ResponseEntity<List<PluginInfo>> getPluginList(@RequestParam("agentNick") String agentNick, @RequestParam("agentId") String agentId){
//        return new ResponseEntity<>();
//    }

}
