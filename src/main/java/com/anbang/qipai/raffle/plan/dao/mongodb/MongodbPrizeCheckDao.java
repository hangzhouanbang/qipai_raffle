package com.anbang.qipai.raffle.plan.dao.mongodb;

import com.anbang.qipai.raffle.plan.bean.PrizeCheck;
import com.anbang.qipai.raffle.plan.dao.PrizeCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @Description:
 */
@Component
public class MongodbPrizeCheckDao implements PrizeCheckDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(PrizeCheck prizeCheck) {
        mongoTemplate.save(prizeCheck);
    }

    @Override
    public PrizeCheck getPrizeCheck(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, PrizeCheck.class);
    }
}
