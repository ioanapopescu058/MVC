package ro.teamnet.zth.appl.controllers;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Ioana.Popescu on 7/20/2017.
 */
@MyController(urlPath = "/department")
public class DepartmentController {

    @MyRequestMethod(urlPath = "/all", methodType = "GET")
    private String getAllDepartments() {
        return "allDepartments";
    }

}
