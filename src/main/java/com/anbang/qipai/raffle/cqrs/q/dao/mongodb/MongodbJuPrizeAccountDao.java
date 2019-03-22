package com.anbang.qipai.raffle.cqrs.q.dao.mongodb;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeAccount;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 */
@Component
public class MongodbJuPrizeAccountDao implements JuPrizeAccountDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(JuPrizeAccount juPrizeAccount) {
        mongoTemplate.save(juPrizeAccount);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, JuPrizeAccount.class);
    }

    @Override
    public JuPrizeAccount getJuPrizeAccount(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, JuPrizeAccount.class);
    }

    @Override
    public List<JuPrizeAccount> listJuPrizeAccount() {
        return mongoTemplate.findAll(JuPrizeAccount.class);
    }
}
