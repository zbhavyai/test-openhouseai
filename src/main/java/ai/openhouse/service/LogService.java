package ai.openhouse.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ai.openhouse.model.Actions;
import ai.openhouse.model.AppLog;

@ApplicationScoped
public class LogService {

    private final EntityManager em;

    @Inject
    public LogService(final EntityManager entityManager) {

        this.em = entityManager;
    }

    public List<AppLog> retrieveAllLogs() {

        return AppLog.listAll();
    }

    @Transactional
    public List<AppLog> retrieveLogs(Map<String, Object> parameters) {

        if (parameters.isEmpty()) {
            return this.retrieveAllLogs();
        }

        if (((ZonedDateTime) parameters.get("startTime")).isAfter((ZonedDateTime) parameters.get("endTime"))) {
            return new ArrayList<AppLog>();
        }

        String query = parameters.entrySet().stream().map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(" and "));

        return AppLog.find(query, parameters).list();
    }

    @Transactional
    public void persistLog(AppLog appLog) {

        List<Actions> listOfActions = appLog.getActions();
        appLog.setActions(null);

        try {
            // persist to set the id
            appLog.persist();

            // now persist the actions
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

        appLogs.stream().forEach(la -> {
            this.persistLog(la);
        });
    }
}
