package com.zenika.restdemo.dao;

import com.mongodb.MongoClient;
import com.mysema.commons.lang.CloseableIterator;
import com.mysema.query.mongodb.morphia.MorphiaQuery;
import com.zenika.restdemo.model.Command;
import com.zenika.restdemo.model.QCommand;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: sennen
 * Date: 27/07/15
 * Time: 15:47
 */
@Repository
public class MongoCommandsRepository extends BasicDAO<Command, String> implements CommandsRepository {

  private final Morphia morphia;
  private final MongoClient mongoClient;
  private final String mongoDBName;

  @Autowired
  public MongoCommandsRepository(Morphia morphia, MongoClient mongoClient, String mongoDBName) {
    super(mongoClient, morphia, mongoDBName);
    this.morphia = morphia;
    this.mongoClient = mongoClient;
    this.mongoDBName = mongoDBName;
  }

  @Override
  public List<Command> getCommands(int startIndex, int size) {
    MorphiaQuery<Command> commandsQuery = new MorphiaQuery<>(morphia, morphia.createDatastore(mongoClient, mongoDBName),
                                                             new QCommand("commands"));
    return commandsQuery.offset(startIndex).limit(size).list();
  }

  public long count() {
    return super.count();
  }
}
