package ai.openhouse.rest;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ai.openhouse.enums.ActionType;
import ai.openhouse.model.AppLog;
import ai.openhouse.service.LogService;

@Path("api")
public class LogRest {

    private final LogService service;

    @Inject
    public LogRest(final LogService logservice) {
        this.service = logservice;
    }

    @GET
    @Path("retrieve/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppLog> retrieveAllLogs() {
        return this.service.retrieveAllLogs();
    }

    @GET
    @Path("retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppLog> retrieveLogs(@QueryParam("user") @DefaultValue("") String user,
            @QueryParam("start") @DefaultValue("") String startTimeString,
            @QueryParam("end") @DefaultValue("") String endTimeString,
            @QueryParam("type") @DefaultValue("") String actionTypeString) {

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (!user.equals("")) {
            parameters.put("user", user);
        }

        if (!startTimeString.equals("")) {
            parameters.put("startTime", ZonedDateTime.parse(startTimeString));
        }

        if (!endTimeString.equals("")) {
            parameters.put("endTime", ZonedDateTime.parse(endTimeString));
        }

        if (!actionTypeString.equals("")) {
            parameters.put("endTime", ActionType.valueOf(actionTypeString));
        }

        return this.service.retrieveLogs(parameters);
    }

    @POST
    @Path("store")
    public void storeLogs(AppLog appLog) {
        this.service.persistLog(appLog);
    }

    @POST
    @Path("storeall")
    public void storeAllLogs(List<AppLog> appLogs) {
        this.service.persistLogs(appLogs);
    }

}
