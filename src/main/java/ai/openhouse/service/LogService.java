package ai.openhouse.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.openhouse.model.Actions;
import ai.openhouse.model.AppLog;

@ApplicationScoped
public class LogService {

    private final Logger logger = LoggerFactory.getLogger(LogService.class);

    public List<AppLog> retrieveAllLogs() {

        this.logger.info("Retrieving all logs");

        return AppLog.listAll();
    }

    @Transactional
    public List<AppLog> retrieveLogs(Map<String, Object> parameters) {

        this.logger.info("Retrieving logs");

        if (parameters.isEmpty()) {
            return this.retrieveAllLogs();
        }

        parameters.entrySet().stream().forEach(p -> {
            this.logger.info(String.format("%s=%s", p.getKey(), p.getValue()));
        });

        if (((ZonedDateTime) parameters.get("startTime")).isAfter((ZonedDateTime) parameters.get("endTime"))) {
            return new ArrayList<AppLog>();
        }

        String query = parameters.entrySet().stream().map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(" and "));

        return AppLog.find(query, parameters).list();
    }

    @Transactional
    public void persistLog(AppLog appLog) {

        this.logger.info("Persisting log : {}", appLog);

        List<Actions> listOfActions = appLog.getActions();
        appLog.setActions(null);

        try {
            appLog.persist();

            listOfActions.stream().forEach(a -> {
                a.setLogId(appLog);
                a.persist();
            });
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persistLogs(List<AppLog> appLogs) {

        this.logger.info("Persisting all logs");

        appLogs.stream().forEach(la -> {
            this.persistLog(la);
        });
    }
}
