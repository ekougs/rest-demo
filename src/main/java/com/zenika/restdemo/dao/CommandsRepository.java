package com.zenika.restdemo.dao;

import com.zenika.restdemo.model.Command;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * User: sennen
 * Date: 27/07/15
 * Time: 15:43
 */
public interface CommandsRepository extends Repository<Command, String> {
  List<Command> getCommands(int startIndex, int size);

  long count();
}
