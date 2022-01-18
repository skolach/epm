package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.Proposal;


public class ProposalDao {
    private static final Logger log = Logger.getLogger(ProposalDao.class.getName());

    private static final String SQL_FIND_PROPOSAL_BY_ORDER_ID = 
        "select null as `id`, p.`order_id`, p.`proposal_id`, group_concat(c.`number` separator ', ') as `number`, " +
        "max(p.`time_to_wait`) as `time_to_wait`, sum(o.`price`) as `price`, " +
        "concat(max(cl.`name`), ' (', group_concat(fl.`description` separator ', '), ')') as `description` " +
        "from `proposal` p join `car` c on p.`car_id` = c.`id` " +
        "join `order` o on p.`order_id` = o.`id` " +
        "join `class` cl on c.`class_id` = cl.`id` " +
        "join `car_feature` cf on c.`id` = cf.`car_id` " +
        "join `feature` f on cf.`feature_id` = f.`id` " +
        "join `feature_localization` fl on f.`id` = fl.`feature_id` " +
        "join `locale` l on fl.`locale_id` = l.`id` " +
        "where p.`order_id` = ? and l.`code` = ? " +
        "group by p.`order_id`, p.`proposal_id`";

    private ProposalDao(){}

    public static List<Proposal> getProposalByOrderId(int orderId, String localizationCode) throws SQLException{
        List<Proposal> proposals = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PROPOSAL_BY_ORDER_ID); //NOSONAR
            log.trace(SQL_FIND_PROPOSAL_BY_ORDER_ID);
            pstmt.setInt(1, orderId);
            pstmt.setString(2, localizationCode);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Proposal proposal = new Proposal(
                    rs.getInt("id"),
                    rs.getInt("order_id"),
                    rs.getInt("proposal_id"),
                    rs.getString("number"),
                    rs.getTime("time_to_wait"),
                    rs.getBigDecimal("price"),
                    rs.getString("description")
                );
                proposals.add(proposal);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot get list of proposals by order id", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return proposals;
    }
}