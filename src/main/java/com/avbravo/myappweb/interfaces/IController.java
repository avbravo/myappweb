package com.avbravo.myappweb.interfaces;

/**
 *
 * @author avbravo
 * @param <T>
 */
public interface IController<T> {

    public String prepareNew();

    public void reset();

    public String save();

    public String edit();

    public String remove();

    public String delete();

    public String deleteAll();

    public String print();

    public String printAll();

    public String prepareEdit();

}
