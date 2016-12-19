package de.hrs.dao;

import de.hrs.model.TradeMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by hrs on 18.12.16.
 */
@Transactional
public interface TradeMessageRepository extends CrudRepository <TradeMessage, Timestamp> {
}
