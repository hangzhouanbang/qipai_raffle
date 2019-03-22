package com.anbang.qipai.raffle.cqrs.q.dao.mongodb;

import com.anbang.qipai.raffle.cqrs.q.dao.JuPrizeRecordDao;
import com.anbang.qipai.raffle.cqrs.q.dbo.juprize.JuPrizeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongodbJuPrizeRecordDao implements JuPrizeRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(JuPrizeRecord juPrizeRecord) {
        mongoTemplate.save(juPrizeRecord);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, JuPrizeRecord.class);
    }

    @Override
    public JuPrizeRecord getJuPrizeRecord(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, JuPrizeRecord.class);
    }
}
