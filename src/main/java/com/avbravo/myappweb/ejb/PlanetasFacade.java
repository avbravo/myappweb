/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.myappweb.ejb;

import com.avbravo.jmoordb.facade.AbstractFacade;
import com.avbravo.myappweb.entity.Planetas;
import com.avbravo.myappweb.provider.MongoClientProvider;
import com.mongodb.MongoClient;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *use myappdb
 * @author avbravo
 */
@Stateless
public class PlanetasFacade extends AbstractFacade<Planetas> {
   @EJB
    MongoClientProvider mongoClientProvider;

  @Override
    protected MongoClient getMongoClient() {
        return mongoClientProvider.getMongoClient();
    }
    public PlanetasFacade() {      
        super(Planetas.class, "myappdb", "planetas");
    }

    @Override
    public Object findById(String key, String value) {
      return find(key, value);
    }

    @Override
    public Object findById(String key, Integer value) {
      return find(key, value);
    }

}
    

