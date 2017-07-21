package ro.teamnet.zth.web;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.appl.controllers.DepartmentController;
import ro.teamnet.zth.appl.controllers.EmployeeController;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ioana.Popescu on 7/20/2017.
 */
public class MyDispatcherServlet extends HttpServlet {

    Map<String, MethodAttributes> allowedMethods;

    public void init() {

        allowedMethods = new HashMap<>();

        try {
            ArrayList<Class> classes = (ArrayList<Class>)AnnotationScanUtils.getClasses("ro.teamnet.zth.appl.controllers");
            for (Class cl : classes) {
                if (cl.isAnnotationPresent(MyController.class)) {
                    MyController an = (MyController) cl.getDeclaredAnnotation(MyController.class);

                    Method[] methods = cl.getMethods();

                    for (Method m : methods) {
                        if (m.isAnnotationPresent(MyRequestMethod.class)) {
                            m.setAccessible(true);

                            MyRequestMethod anM = (MyRequestMethod) cl.getDeclaredAnnotation(MyRequestMethod.class);

                            MethodAttributes ma = new MethodAttributes();

                            ma.setControllerClass(cl.getName());
                            ma.setMethodName(anM.urlPath());
                            ma.setMethodType(anM.methodType());

                            String url = an.urlPath() + anM.urlPath() + anM.methodType();

                            allowedMethods.put(url, ma);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchReply(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchReply(request, response, "POST");
    }

    private void dispatchReply(HttpServletRequest request, HttpServletResponse response, String reqType) {
        try {
            String resultToDisplay = dispatch(request, reqType);
            reply(resultToDisplay, response);
        }catch (Exception e) {
            sendExceptionError();
        }
    }

    private String dispatch(HttpServletRequest request, String reqType) throws ServletException, IOException{

        String key = request.getPathInfo() + reqType;

        MethodAttributes attributes = allowedMethods.get(key);

        String className = attributes.getControllerClass();

        String result = null;

        try {
            Class cls = Class.forName(className);
            try {
                Method testMethod = cls.getMethod(attributes.getMethodName());
                testMethod.setAccessible(true);
                try {
                    result = (String) testMethod.invoke(cls.newInstance());
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;

    }

    private void reply(String resultToDisplay, HttpServletResponse response) {
        try {
            response.getWriter().write(resultToDisplay);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendExceptionError() {

    }

}
