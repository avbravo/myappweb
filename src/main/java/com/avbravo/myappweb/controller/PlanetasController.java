/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.myappweb.controller;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.avbravoutils.printer.Printer;
import com.avbravo.myappweb.ejb.PlanetasFacade;
import com.avbravo.myappweb.entity.Planetas;
import com.avbravo.myappweb.interfaces.IController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped

public class PlanetasController implements Serializable, IController {

    private static final long serialVersionUID = 1L;

    @Inject
    PlanetasFacade planetasFacade;
    List<Planetas> planetasList;
    List<Planetas> planetasFiltered;
    Planetas planetas = new Planetas();
    Planetas selectedPlanetas = new Planetas();

    @Inject
    Printer printer;

    public List<Planetas> getPlanetasList() {
        planetasList=planetasFacade.findAll();
        return planetasList;
    }

    public void setPlanetasList(List<Planetas> planetasList) {
        this.planetasList = planetasList;
    }

    public List<Planetas> getPlanetasFiltered() {
        return planetasFiltered;
    }

    public void setPlanetasFiltered(List<Planetas> planetasFiltered) {
        this.planetasFiltered = planetasFiltered;
    }

    public Planetas getPlanetas() {
        return planetas;
    }

    public void setPlanetas(Planetas planetas) {
        this.planetas = planetas;
    }

    public Planetas getSelectedPlanetas() {
        return selectedPlanetas;
    }

    public void setSelectedPlanetas(Planetas selectedPlanetas) {
        this.selectedPlanetas = selectedPlanetas;
    }

    /**
     * Creates a new instance of PlanetasController
     */
    @PostConstruct
    public void init() {
        try {

            String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

            if (id != null) {
                planetas = planetasFacade.find("idplaneta", id);

            }
            planetasList = new ArrayList<>();
            planetasFiltered = new ArrayList<>();
            planetasList = planetasFacade.findAll();
            System.out.println("----> listado" +planetasList.size());
            for(Planetas p:planetasList){
                System.out.println(p.toString());
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("init() " + e.getLocalizedMessage());
        }

    }

    public PlanetasController() {
    }

    @Override
    public String prepareNew() {
        planetas = new Planetas();
        return "";
    }

    @Override
    public void reset() {

        RequestContext.getCurrentInstance().reset(":form:content");
        prepareNew();
    }

    @Override
    public String save() {
        try {
            System.out.println("======================================");
            System.out.println("pĺanetas: "+planetas.toString());  
            System.out.println("invocare el find()");
            Planetas t = planetasFacade.find("idplaneta", planetas.getIdplaneta());
            System.out.println("invoque el find()");
            if (t == null) {
                System.out.println("voy a guardar");
                if (planetasFacade.save(planetas)) {
             JsfUtil.addSuccessMessage("Guardado");
                } else {
                  JsfUtil.addSuccessMessage("No se pudo guardar " + planetasFacade.getException().toString());
                }
            } else {
                System.out.println("t "+t.toString());
               JsfUtil.addSuccessMessage("Ya existe un planeta con ese idplaneta");

            }
//            System.out.println(">>>>planetas "+planetas.toString());
//            Planetas t =  planetasFacade.find("idplaneta",planetas.getIdplaneta());
//            System.out.println("paso 1");
//          System.out.println("t "+t.toString());
//            if (t != null) {
//                JsfUtil.warningDialog("Mensaje", "Existe un planeta con ese id");
//                return null;
//            }

//            if (planetasFacade.save(planetas)) {
//                JsfUtil.addSuccessMessage("Guardado");
//            } else {
//                JsfUtil.addSuccessMessage("save() " + planetasFacade.getException().toString());
//            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("save()" + e.getLocalizedMessage());
        }
        return "";
    }

    @Override
    public String edit() {
        try {
            planetasFacade.update(planetas);

            JsfUtil.addSuccessMessage("Se actualizo el planeta");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
        }
        return "";
    }

    @Override
    public String delete() {
        if (planetasFacade.delete("idplanetas", planetas.getIdplaneta())) {
            JsfUtil.addSuccessMessage("Eliminado");
            planetas = new Planetas();
        }
        return "/pages/planetas/list.xhtml";
    }

    @Override
    public String remove() {

        if (planetasFacade.delete("idplanetas", planetas.getIdplaneta())) {
            JsfUtil.addSuccessMessage("Eliminado");
        }
        return "";
    }

    @Override
    public String deleteAll() {
        if (planetasFacade.deleteAll() != 0) {
            JsfUtil.addSuccessMessage("Se eliminaron todos los registros");

        }
        return "";
    }

    @Override
    public String print() {
        try {
            List<Planetas> list = new ArrayList<>();
            list.add(planetas);
            String ruta = "/resources/reportes/planetas/planetas.jasper";
            HashMap parameters = new HashMap();

            printer.imprimir(list, ruta, parameters);
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("imprimir() " + ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public String printAll() {
        try {
            List<Planetas> list = new ArrayList<>();
            if (planetasFiltered.isEmpty()) {
                list = planetasList;
            } else {
                list = planetasFiltered;
            }

            String ruta = "/resources/reportes/planetas/planetas.jasper";
            HashMap parameters = new HashMap();

            printer.imprimir(list, ruta, parameters);
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("imprimir() " + ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public String prepareEdit() {
        return "/pages/planetas/view.xhtml";

    }

}
