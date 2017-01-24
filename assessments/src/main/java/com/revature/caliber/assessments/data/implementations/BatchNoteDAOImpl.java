package com.revature.caliber.assessments.data.implementations;

import com.revature.caliber.assessments.beans.BatchNote;
import com.revature.caliber.assessments.data.BatchNoteDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository(value="batchNoteDAO")
public class BatchNoteDAOImpl implements BatchNoteDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public void createBatchNote(BatchNote batchNote) {
        sessionFactory.getCurrentSession().save(batchNote);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public BatchNote getBatchNote(int batchId, int weekId) {
        BatchNote batchNote = (BatchNote) sessionFactory.getCurrentSession().createCriteria(BatchNote.class)
                .add(Restrictions.eq("batch", batchId))
                .add(Restrictions.eq("week", weekId)).uniqueResult();
        return batchNote;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    @SuppressWarnings("unchecked")
    public List<BatchNote> allBatchNotesByWeek(int weekId) {
        List<BatchNote> batchNotes = sessionFactory.getCurrentSession().createCriteria(BatchNote.class)
                .add(Restrictions.eq("week", weekId)).list();
        return batchNotes;
    }
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public void updateBatchNote(BatchNote batchNote) {
        sessionFactory.getCurrentSession().update(batchNote);
    }
    
    

}