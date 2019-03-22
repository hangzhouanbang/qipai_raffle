package com.anbang.qipai.raffle.cqrs.q.dao.mongodb;

import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRelease;
import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeReleaseDao;
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
public class MongodbJuPrizeReleaseDao implements JuPrizeReleaseDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(JuPrizeRelease juPrizeRelease) {
        mongoTemplate.save(juPrizeRelease);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, JuPrizeRelease.class);
    }

    @Override
    public JuPrizeRelease getJuPrizeRelease(String game) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(game));
        return mongoTemplate.findOne(query, JuPrizeRelease.class);
    }

    @Override
    public List<JuPrizeRelease> listJuPrize() {
        return mongoTemplate.findAll(JuPrizeRelease.class);
    }
}
