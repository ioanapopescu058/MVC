package ro.teamnet.zth.appl.controllers;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Ioana.Popescu on 7/20/2017.
 */
@MyController(urlPath = "/employee")
public class EmployeeController {

    @MyRequestMethod(urlPath = "/all", methodType = "GET")
    private String getAllEmployees() {
        return "allEmployees";
    }

    @MyRequestMethod(urlPath = "/one", methodType = "GET")
    private String getOneEmployee() {
        return "oneRandomEmployee";
    }

}
