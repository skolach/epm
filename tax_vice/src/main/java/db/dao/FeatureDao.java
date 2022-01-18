package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.Feature;

public class FeatureDao {
    private static final Logger log = Logger.getLogger(FeatureDao.class.getName());
    private static final String SQL_SELECT_FEATUREZ_LOCALIZED = 
        "SELECT fe.`id`, fe.`rating`, fl.`description`, fl.`description_long` " +
        "FROM `feature` fe JOIN `feature_localization` fl " +
            "ON fe.`id` = fl.`feature_id` " +
        "JOIN `locale` lo " +
            "ON fl.`locale_id` = lo.`id` "+
        "WHERE lo.`code` = ? ";

    private FeatureDao(){}

    public static List<Feature> getFeaturesLocalized(String localization)
            throws SQLException{

        List<Feature> features = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_FEATUREZ_LOCALIZED); //NOSONAR

            // Default localization is 'en_GB'

            pstmt.setString(1, localization == null ? "en_GB" : localization);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                Feature f = new Feature();
                f.setFeatureDescription(rs.getString("description"));
                f.setFeatureDescriptionLong(rs.getString("description_long"));
                f.setId(rs.getInt("id"));
                f.setRating(rs.getInt("rating"));
                features.add(f);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot get features", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return features;
    }
}