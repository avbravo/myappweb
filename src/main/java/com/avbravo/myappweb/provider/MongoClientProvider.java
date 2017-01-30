/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.myappweb.provider;

/**
 *
 * @author avbravo
 */
import com.avbravo.avbravoutils.JsfUtil;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientProvider {

    private MongoClient mongoClient = null;

    @Lock(LockType.READ)
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @PostConstruct
    public void init() {
        try {

            /*
            String database="";
            String username="";
            String password="";
            String host="localhost";
            String port="27017";
            char[] charArray = password.toCharArray();
            MongoCredential credential = MongoCredential.createCredential(username, database, charArray);
            mongoClient = new MongoClient(new ServerAddress(host,port), Arrays.asList(credential));
             */
            mongoClient = new MongoClient();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("init() " + e.getLocalizedMessage());
        }

    }

}
