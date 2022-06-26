package ai.openhouse.service;

import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.openhouse.enums.ActionType;
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

        this.logger.info("Mapped parameters: user={}, startTime={}, endTime={}, type={}", parameters.get("user"),
                parameters.get("startTime"), parameters.get("endTime"), parameters.get("type"));

        if (parameters.isEmpty()) {
            return this.retrieveAllLogs();
        }

        ZonedDateTime start = (ZonedDateTime) parameters.get("startTime");
        ZonedDateTime end = (ZonedDateTime) parameters.get("endTime");
        ActionType type = (ActionType) parameters.get("type");

        List<AppLog> allLogs;

        if (parameters.get("user") == null) {
            allLogs = AppLog.listAll();
        } else {
            allLogs = AppLog.find("user_id", parameters.get("user")).list();
        }

        for (int i = 0; i < allLogs.size(); i++) {
            AppLog currentLog = allLogs.get(i);

            List<Actions> allActions = currentLog.getActions();

            Iterator<Actions> itr = allActions.iterator();

            while (itr.hasNext()) {
                Actions actions = itr.next();

                if (start != null && actions.getTime().isBefore(start)) {
                    itr.remove();
                    continue;
                }

                if (end != null && actions.getTime().isAfter(end)) {
                    itr.remove();
                    continue;
                }

                if (type != null && !actions.getType().equals(type)) {
                    itr.remove();
                    continue;
                }
            }
        }

        return allLogs;
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
